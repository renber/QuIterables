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
 * An Iterable implementation which returns a range of integer numbers
 * @author René Bergelt
 */
public class RangeIterable implements Iterable<Integer> {	
	
	int start;
	int end;
	
	/**
	 * Creates an instance of the RangeIterable which contains the numbers starting at start until end (inclusive, i.e. [start, end])
	 * @param start first number
	 * @param end last number
	 */
	public RangeIterable(int start, int end) {
		if (end < start)
			throw new IllegalArgumentException("Parameter end must be large ror equal to start.");
		
		this.start = start;
		this.end = end;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new RangeIterator(start, end);
	}	
}

class RangeIterator implements Iterator<Integer> {

	int current;
	int end;
	
	public RangeIterator(int start, int end) {
		current = start;
		this.end = end;
	}
	
	@Override
	public boolean hasNext() {
		return current <= end;
	}

	@Override
	public Integer next() {
		if (current > end)
			throw new NoSuchElementException("No more elements in iterator. Use hasNext() to check before calling next().");
		
		int ret = current;
		current++;
		return ret;
	}
	
}
