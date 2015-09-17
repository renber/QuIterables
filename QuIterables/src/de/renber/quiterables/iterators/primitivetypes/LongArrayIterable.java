/*******************************************************************************
 * This file is part of the Java QuIterables Library
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Ren� Bergelt
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
package de.renber.quiterables.iterators.primitivetypes;

import java.util.Iterator;

import de.renber.quiterables.iterators.LazyIterator;

/**
 * An Iterable wrapper for primitive-type long-arrays
 * @author Ren� Bergelt
 *
 */
public class LongArrayIterable implements Iterable<Long> {

	long[] wrapped;
	
	public LongArrayIterable(long[] array) {
		wrapped = array;
	}
	
	@Override
	public Iterator<Long> iterator() {
		return new LongArrayIterator(wrapped);
	}
}

class LongArrayIterator extends LazyIterator<Long>
{
	long[] wrapped;
	int currentIndex = 0;
	
	public LongArrayIterator(long[] array) {
		wrapped = array;
	}
	
	@Override
	protected Long findNextElement() {
		if (wrapped == null || currentIndex >= wrapped.length)
			return null;
		
		Long element = wrapped[currentIndex];
		currentIndex++;
		return element;
	}	
}

