/*******************************************************************************
 * This file is part of the Java IterQuery Library
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
package de.renber.quiterables;

import java.util.Iterator;

class PrimitiveArrayTransformerImpl<T> implements PrimitiveArrayTransformer<T> {

	Iterable<T> iter;
	
	public PrimitiveArrayTransformerImpl(Iterable<T> _iter) {
		iter = _iter;
	}
	
	/**
	 * Return the number of elements in iter	 
	 */
	protected int count() {
		Iterator<T> it = iter.iterator();		
		int count = 0;
		while(it.hasNext()) {
			it.next();
			count++;
		}
		
		return count;
	}
	
	@Override
	public int[] intArray() {					
		int[] array = new int[count()];		
		Iterator<T> it = iter.iterator();
		
		for(int i = 0; i < array.length && it.hasNext(); i++) {
			T v = it.next();
			array[i] = (int)v;
		}	
		
		return array;
	}

	@Override
	public short[] shortArray() {
		short[] array = new short[count()];		
		Iterator<T> it = iter.iterator();
		
		for(int i = 0; i < array.length && it.hasNext(); i++) {
			T v = it.next();
			array[i] = (short)v;
		}	
		
		return array;
	}

	@Override
	public long[] longArray() {
		long[] array = new long[count()];		
		Iterator<T> it = iter.iterator();
		
		for(int i = 0; i < array.length && it.hasNext(); i++) {
			T v = it.next();
			array[i] = (long)v;
		}	
		
		return array;
	}

	@Override
	public float[] floatArray() {
		float[] array = new float[count()];		
		Iterator<T> it = iter.iterator();
		
		for(int i = 0; i < array.length && it.hasNext(); i++) {
			T v = it.next();
			array[i] = (float)v;
		}	
		
		return array;
	}

	@Override
	public double[] doubleArray() {
		double[] array = new double[count()];		
		Iterator<T> it = iter.iterator();
		
		for(int i = 0; i < array.length && it.hasNext(); i++) {
			T v = it.next();
			array[i] = (double)v;
		}	
		
		return array;
	}

	@Override
	public byte[] byteArray() {
		byte[] array = new byte[count()];		
		Iterator<T> it = iter.iterator();
		
		for(int i = 0; i < array.length && it.hasNext(); i++) {
			T v = it.next();
			array[i] = (byte)v;
		}	
		
		return array;
	}

	@Override
	public boolean[] booleanArray() {
		boolean[] array = new boolean[count()];		
		Iterator<T> it = iter.iterator();
		
		for(int i = 0; i < array.length && it.hasNext(); i++) {
			T v = it.next();
			array[i] = (boolean)v;
		}	
		
		return array;
	}

	@Override
	public char[] charArray() {
		char[] array = new char[count()];		
		Iterator<T> it = iter.iterator();
		
		for(int i = 0; i < array.length && it.hasNext(); i++) {
			T v = it.next();
			array[i] = (char)v;
		}	
		
		return array;
	}
}