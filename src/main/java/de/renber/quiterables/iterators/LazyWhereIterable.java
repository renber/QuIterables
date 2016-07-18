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
package de.renber.quiterables.iterators;

import java.util.Iterator;

import de.renber.quiterables.Predicate;

/**
 * Iterable which returns elements matching a predicate
 * using lazy evaluation
 * @author berre
 *
 * @param <T>
 */
public class LazyWhereIterable<T> implements Iterable<T> {

	Iterable<T> wrapped;	
	Predicate<T> predicate;	
	
	public LazyWhereIterable(Iterable<T> _wrapped, Predicate<T> _predicate) {
		wrapped = _wrapped;
		predicate = _predicate;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new LazyWhereIterator<T>(wrapped.iterator(), predicate);
	}
}

class LazyWhereIterator<T> extends LazyIterator<T>
{
	Iterator<T> wrapped;
	T nextElement = null;
	Predicate<T> predicate;	
	
	public LazyWhereIterator(Iterator<T> _wrapped, Predicate<T> _predicate) {
		wrapped = _wrapped;
		predicate = _predicate;		
	}
		
	// find the next element which satisfies the predicate if any
	@Override
	protected T findNextElement()
	{
		// find the next element in the base iterable which satisfies the condition
		while (wrapped.hasNext()) {
			T element = wrapped.next();
			if (predicate.evaluate(element))			
				return element;							
		}
		
		return null;
	}
	
}
