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

/**
 * Iterable which concatenates two other iterables
 * @param <T> Type of the elements in this Iterable
 * @author René Bergelt
 *
 */
public class LazyConcatIterable<T> implements Iterable<T> {

	Iterable<T> it1;
	Iterable<T> it2;

	/**
	 * Create an iterable which concats the two given iterables
	 * @param _it1 First iterable
	 * @param _it2 Second iterable
	 */
	public LazyConcatIterable(Iterable<T> _it1, Iterable<T> _it2) {
		it1 = _it1;
		it2 = _it2;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new LazyConcatIterator<T>(it1.iterator(), it2.iterator());
	}
}

class LazyConcatIterator<T> extends LazyIterator<T> {

	Iterator<T> it1;
	Iterator<T> it2;
	
	boolean isInSecondIterator = false;
	
	public LazyConcatIterator(Iterator<T> _it1, Iterator<T> _it2) {
		it1 = _it1;
		it2 = _it2;
	}
	
	@Override
	protected T findNextElement() {
		if (isInSecondIterator) {
			return it2.hasNext() ? it2.next() : null;			
		}
		// still in the first iterator
		if (!it1.hasNext()) {
			isInSecondIterator = true;
			return findNextElement();
		}
		
		return it1.next();
	}
	
}