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
import java.util.NoSuchElementException;

/**
 * An iterable implementation which cotnains no elements
 * @author René Bergelt
 */
@SuppressWarnings("rawtypes")
public class EmptyIterable<T> implements Iterable<T> {
	
	// at runtime there only exists one empty iterable instance
	// even if the user uses more empty iterables 
	private static EmptyIterable instance = new EmptyIterable();
	
	// we use the same iterator instance for all EmptyIterable instance	
	static EmptyIterator it = new EmptyIterator();
	
	private EmptyIterable() {
		// --
	}	
	
	/**
	 * Return an instance of the EmptyIterable class
	 * (Internally always uses the same instance)	 
	 */
	public static EmptyIterable getInstance() {
		return instance;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<T> iterator() {	
		return it;
	}
	
	static class EmptyIterator<T> implements Iterator<T> {

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public T next() {
			throw new NoSuchElementException("No more elements in iterator. Use hasNext() to check before calling next().");
		}		
	}
}
