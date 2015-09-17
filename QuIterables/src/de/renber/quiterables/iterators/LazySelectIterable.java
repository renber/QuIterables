/*******************************************************************************
 * This file is part of the Java QuIterables Library
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 René Bergelt
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
package de.renber.quiterables.iterators;

import java.util.Iterator;

import de.renber.quiterables.Predicate;
import de.renber.quiterables.Selector;

/**
 * Iterable which converts the elements of a source iterable of type TIn to
 * elements of type TOut using a Selector function 
 *
 * @param <T>
 */
public class LazySelectIterable<TIn, TOut> implements Iterable<TOut> {

	Iterable<TIn> wrapped;		
	Selector<TIn, TOut> selectorFunc;
	
	public LazySelectIterable(Iterable<TIn> _wrapped, Selector<TIn, TOut> _selectorFunc) {
		wrapped = _wrapped;
		selectorFunc = _selectorFunc;
	}
	
	@Override
	public Iterator<TOut> iterator() {
		return new LazySelectIterator<TIn, TOut>(wrapped.iterator(), selectorFunc);
	}

}

class LazySelectIterator<TIn, TOut> extends LazyIterator<TOut> {

	Iterator<TIn> wrapped;		
	Selector<TIn, TOut> selectorFunc;
	
	public LazySelectIterator(Iterator<TIn> _wrapped, Selector<TIn, TOut> _selectorFunc) {
		wrapped = _wrapped;
		selectorFunc = _selectorFunc;
	}
	
	@Override
	protected TOut findNextElement() {
		if (!wrapped.hasNext())
			return null;
		
		return selectorFunc.select(wrapped.next());
	}
	
}