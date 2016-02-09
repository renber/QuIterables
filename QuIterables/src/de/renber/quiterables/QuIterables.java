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
package de.renber.quiterables;

import de.renber.quiterables.grouping.GroupedList;
import de.renber.quiterables.grouping.GroupedQueriable;
import de.renber.quiterables.iterators.ArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.BooleanArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.ByteArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.CharArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.DoubleArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.FloatArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.IntArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.LongArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.ShortArrayIterable;

/**
 * Convenience class of the QuIterables library 
 * for static importing the query(..) method
 * @author René Bergelt
 *
 */
public class QuIterables {

	/** 
	 * "static" class
	 */
	private QuIterables() {
		// --
	}	
	
	/**
	 * Return a queriable object for the given iterable	
	 */
	public static <T> Queriable<T> query(Iterable<T> it) {
		return new QueriableImpl<T>(it);
	}
	
	/**
	 * Return a grouped queriable for the given grouped list
	 */
	public static <T> GroupedQueriable<T> query(GroupedList<T> groupedList) {
		return new GroupedQueriableImpl<T>(groupedList);
	}
		
	/**
	 * Return a queriable object for the given iterable	
	 */
	public static <T> Queriable<T> query(T[] array) {
		return new QueriableImpl<T>(new ArrayIterable<T>(array));
	}
		
	/**
	 * Return a Queriable which wraps the given primitive-type int-array
	 */
	public static Queriable<Integer> query(int[] array) {
		return new QueriableImpl<Integer>(new IntArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type short-array
	 */
	public static Queriable<Short> query(short[] array) {
		return new QueriableImpl<Short>(new ShortArrayIterable(array));
	}

	/**
	 * Return a Queriable which wraps the given primitive-type long-array
	 */
	public static Queriable<Long> query(long[] array) {
		return new QueriableImpl<Long>(new LongArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type float-array
	 */
	public static Queriable<Float> query(float[] array) {
		return new QueriableImpl<Float>(new FloatArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type double-array
	 */
	public static Queriable<Double> query(double[] array) {
		return new QueriableImpl<Double>(new DoubleArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type byte-array
	 */
	public static Queriable<Byte> query(byte[] array) {
		return new QueriableImpl<Byte>(new ByteArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type boolean-array
	 */
	public static Queriable<Boolean> query(boolean[] array) {
		return new QueriableImpl<Boolean>(new BooleanArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type char-array
	 */
	public static Queriable<Character> query(char[] array) {
		return new QueriableImpl<Character>(new CharArrayIterable(array));
	}	
	
}
