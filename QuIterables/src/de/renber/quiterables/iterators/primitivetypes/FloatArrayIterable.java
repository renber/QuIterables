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
package de.renber.quiterables.iterators.primitivetypes;

import java.util.Iterator;

import de.renber.quiterables.iterators.LazyIterator;

/**
 * An Iterable wrapper for primitive-type float-arrays
 * @author René Bergelt
 *
 */
public class FloatArrayIterable implements Iterable<Float> {

	float[] wrapped;
	
	public FloatArrayIterable(float[] array) {
		wrapped = array;
	}
	
	@Override
	public Iterator<Float> iterator() {
		return new FloatArrayIterator(wrapped);
	}
}

class FloatArrayIterator extends LazyIterator<Float>
{
	float[] wrapped;
	int currentIndex = 0;
	
	public FloatArrayIterator(float[] array) {
		wrapped = array;
	}
	
	@Override
	protected Float findNextElement() {
		if (wrapped == null || currentIndex >= wrapped.length)
			return null;
		
		Float element = wrapped[currentIndex];
		currentIndex++;
		return element;
	}	
}

