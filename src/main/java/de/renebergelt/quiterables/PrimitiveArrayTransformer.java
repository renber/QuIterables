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
package de.renebergelt.quiterables;

/**
 * Helper class to convert an Iterable to primitive arrays
 * @author René Bergelt
 */
public interface PrimitiveArrayTransformer<T> {
	
	/**
	 * Transform to an int array
	 * @return int array of elements
	 */
	public int[] intArray();
	
	/**
	 * Transform to a short array
	 * @return short array of elements
	 */
	public short[] shortArray();
	
	/**
	 * Transform to a long array
	 * @return long array of elements
	 */
	public long[] longArray();
	
	/**
	 * Transform to a float array
	 * @return float array of elements
	 */
	public float[] floatArray();
	
	/**
	 * Transform to a double array
	 * @return double array of elements
	 */
	public double[] doubleArray();
	
	/**
	 * Transform to a byte array
	 * @return byte array of elements
	 */
	public byte[] byteArray();
	
	/**
	 * Transform to a boolean array
	 * @return boolean array of elements
	 */
	public boolean[] booleanArray();
	
	/**
	 * Transform to a char array
	 * @return char array of elements
	 */
	public char[] charArray();	
}
