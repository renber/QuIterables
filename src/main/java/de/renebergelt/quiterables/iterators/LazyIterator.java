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
import java.util.NoSuchElementException;

/**
 * Base class for the lazy iterators
 * Derived classes only need to implement the findNextElement() function
 * @param <T> Type of the elements in this Iterable
 * @author René Bergelt
 */
public abstract class LazyIterator<T> implements Iterator<T> {

	/**
	 * The cached next element (if any)
	 */
	protected T nextElement = null;

	/**
	 * Indicates if this iterator reached the end
	 */
	protected boolean ended = false;

	@Override
	public boolean hasNext() {
		if (ended)
			return false;

		// get the next element and store it
		// so we do not have to re-evaluate when the user calls next()
		if (nextElement == null)
			nextElement = nextElement();

		return nextElement != null;
	}

	@Override
	public T next() {
		if (ended) {
			throw new NoSuchElementException("No more elements in iterator. Use hasNext() to check before calling next().");		
		}

		T currentElement = nextElement; // use the cached next element if
										// available
		nextElement = null; // the next element needs to be evaluated

		return currentElement == null ? nextElement() : currentElement;
	}

	/**
	 * Request the next element
	 * If there is none, ended is set to true
	 */
	private T nextElement() {
		if (!ended) {
			T element = findNextElement();
			if (element == null)
				ended = true;
			return element;
		} else
			return null;
	}

	/**
	 * Return the next element which satisfies the predicate
	 * Return null to indicate that there will be no more objects
	 * @return The next element or null
	 */
	protected abstract T findNextElement();
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
