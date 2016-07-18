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
package de.renber.quiterables;

import java.util.Comparator;

import de.renber.quiterables.grouping.Group;
import de.renber.quiterables.grouping.GroupedList;
import de.renber.quiterables.iterators.LazyOrderIterable;

/**
 * The actual implementation of the OrderedGroupedQueriable<T> interface used in the
 * library
 * 
 * @author René Bergelt
 *
 * @param <T>
 */
class OrderedGroupedQueriableImpl<T> extends GroupedQueriableImpl<T> implements OrderedGroupedQueriable<T> {

	LazyOrderIterable orderedContainedIter;
	
	protected LazyOrderIterable getOrderIterable() {
		return (LazyOrderIterable)containedIter;
	}
	
	public OrderedGroupedQueriableImpl(Iterable<Group<T>> forIterable, ItemFunc<Group<T>, Comparable> func, SortOrder sortOrder) {
		super(new LazyOrderIterable<Group<T>, Comparable>(forIterable, func, sortOrder));		
	}
	
	public <TComparable> OrderedGroupedQueriableImpl(Iterable<Group<T>> forIterable, ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator, SortOrder sortOrder) {		
		super(new LazyOrderIterable<Group<T>, Comparable>(forIterable, valueFunc, comparator, sortOrder));
	}

	@Override
	public OrderedGroupedQueriable<T> thenBy(ItemFunc<Group<T>, Comparable> valueFunc) {
		getOrderIterable().addSecondaryOrderFunction(valueFunc, SortOrder.Ascending);
		return this;
	}
	
	@Override
	public <TComparable> OrderedGroupedQueriable<T> thenBy(ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator) {
		getOrderIterable().addSecondaryOrderFunction(valueFunc, comparator, SortOrder.Ascending);
		return this;
	}	
	
	@Override
	public OrderedGroupedQueriable<T> thenByDescending(ItemFunc<Group<T>, Comparable> func) {
		getOrderIterable().addSecondaryOrderFunction(func, SortOrder.Descending);
		return this;
	}	
	
	@Override
	public <TComparable> OrderedGroupedQueriable<T> thenByDescending(ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator) {
		getOrderIterable().addSecondaryOrderFunction(valueFunc, comparator, SortOrder.Descending);
		return this;
	}	
}
