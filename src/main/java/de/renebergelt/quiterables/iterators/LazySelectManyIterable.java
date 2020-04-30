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
package de.renebergelt.quiterables.iterators;

import java.util.Iterator;

import de.renebergelt.quiterables.Selector;

/**
 * Iterable which converts the elements of a source iterable of type TIn to an
 * Iterable of type TOut using a Selector function and flattens the result
 * enumeration
 *
 * @param <T>
 */
public class LazySelectManyIterable<TIn, TOut> implements Iterable<TOut> {

	Iterable<TIn> wrapped;
	Selector<TIn, Iterable<TOut>> selectorFunc;

	public LazySelectManyIterable(Iterable<TIn> _wrapped, Selector<TIn, Iterable<TOut>> _selectorFunc) {
		wrapped = _wrapped;
		selectorFunc = _selectorFunc;
	}

	@Override
	public Iterator<TOut> iterator() {
		return new LazySelectManyIterator<TIn, TOut>(wrapped.iterator(), selectorFunc);
	}

}

class LazySelectManyIterator<TIn, TOut> extends LazyIterator<TOut> {

	Iterator<TIn> wrapped;
	Iterator<TOut> subIter;

	Selector<TIn, Iterable<TOut>> selectorFunc;

	public LazySelectManyIterator(Iterator<TIn> _wrapped, Selector<TIn, Iterable<TOut>> _selectorFunc) {
		wrapped = _wrapped;
		selectorFunc = _selectorFunc;
	}

	@Override
	protected TOut findNextElement() {
		// check if we have more items in the sub iterator
		
		// if we have many items which all contain no elements
		// it was possible to get a StackOverFlow since findNextElement() keeps calling itself
		// -> restructured to an iterative approach		
		while (true) {
			if (subIter != null) {
				if (subIter.hasNext())
					return subIter.next();

				subIter = null;
			}

			if (!wrapped.hasNext())
				return null;

			subIter = selectorFunc.select(wrapped.next()).iterator();
		}
	}

}