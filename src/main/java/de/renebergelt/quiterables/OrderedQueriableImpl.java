/*******************************************************************************
 * This file is part of the Java QuIterables Library
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2016 René Bergelt
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package de.renebergelt.quiterables;

import java.util.Comparator;

import de.renebergelt.quiterables.iterators.LazyOrderIterable;

/**
 * The actual implementation of OrderedQueriable used in the library
 * @author René Bergelt
 */
class OrderedQueriableImpl<T> extends QueriableImpl<T> implements OrderedQueriable<T> {

	protected LazyOrderIterable getOrderIterable() {
		return (LazyOrderIterable)containedIter;
	}
	
	protected OrderedQueriableImpl(Iterable<T> forIterable, ItemFunc<T, Comparable> valueFunc, SortOrder sortOrder) {
		super(new LazyOrderIterable<T, Comparable>(forIterable, valueFunc, sortOrder));
	}
	
	protected <TComparable> OrderedQueriableImpl(Iterable<T> forIterable, ItemFunc<T, TComparable> valueFunc, Comparator<TComparable> comparator, SortOrder sortOrder) {
		super(new LazyOrderIterable(forIterable, valueFunc, comparator, sortOrder));
	}
	
	@Override
	public OrderedQueriable<T> thenBy(ItemFunc<T, Comparable> func) {
		getOrderIterable().addSecondaryOrderFunction(func, SortOrder.Ascending);
		return this;
	}
	
	@Override
	public <TComparable> OrderedQueriableImpl<T> thenBy(ItemFunc<T, TComparable> valueFunc, Comparator<TComparable> comparator) {
		getOrderIterable().addSecondaryOrderFunction(valueFunc, comparator, SortOrder.Ascending);
		return this;
	}
	
	@Override
	public OrderedQueriable<T> thenByDescending(ItemFunc<T, Comparable> func) {
		getOrderIterable().addSecondaryOrderFunction(func, SortOrder.Descending);
		return this;
	}
	
	@Override
	public <TComparable> OrderedQueriableImpl<T> thenByDescending(ItemFunc<T, TComparable> valueFunc, Comparator<TComparable> comparator) {
		getOrderIterable().addSecondaryOrderFunction(valueFunc, comparator, SortOrder.Descending);
		return this;
	}
}
