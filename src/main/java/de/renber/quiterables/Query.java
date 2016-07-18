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
package de.renber.quiterables;

import java.util.List;

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
 * Main class of the QuIterables library
 * Mimics some of C#'s generic LINQ convenience methods for Java 8 and below<br/>
 * Use anonymous methods for the predicates. <br/>
 * Beginning with Java 8 you may use lambda expression to increase readability
 * 
 * Example to retrieve the first element of a string list which ends with ".jpg", if any:
 * <br/><br/>
 * Recommended method for Java 8 and above: 
 * <code><pre>
 * Collection{@literal <}String{@literal >} lst = ...; 
 * String r = Query.list(lst).firstOrDefault((x) -> x.endsWith(".jpg"));
 *   if (r != null) {
 *   	// process the element
 *   } else {
 *   	System.out.println("There is no such element.");
 *   }
 *   </pre></code>
 * Java 7 and below:
 * <code><pre>
 * Collection{@literal <}String{@literal >} lst = ...; 
 * String r = Query.list(lst).firstOrDefault(new Predicate{@literal <}String{@literal >}() {
 *   {@literal @}Override
 *   public boolean evaluate(String argument) {
 *   	return argument.endsWith(".jpg");
 *   };
 *   if (r != null) {
 *   	// process the element
 *   } else {
 *   	System.out.println("There is no such element.");
 *   }
 *   </pre></code>
 * Chaining of methods
 * <code><pre>
 * Collection{@literal <}String{@literal >} lst = ...; 
 * Collection<String> resultCollection = Query.list(lst)
 * 	.where(x -> x.StartsWith(".jpg"))
 * 	.select(x -> x.substring(0, 3))
 *  .distinct();
 * </pre></code>
 * @author René Bergelt
 */
public class Query {
	
	/**
	 * Only allow the static methods of this class to be used
	 */
	private Query() {
		// --
	}
	
	/**
	 * Return a queriable object for the given iterable	
	 */
	public static <T> Queriable<T> iterable(Iterable<T> lst) {
		return new QueriableImpl<T>(lst);
	}

	/**
	 * Return a queriable object for the given list	
	 */
	public static <T> Queriable<T> list(List<T> lst) {
		return new QueriableImpl<T>(lst);
	}
	
	/**
	 * Return a queriable object for the given array	
	 */
	public static <T> Queriable<T> array(T[] array) {
		return new QueriableImpl<T>(new ArrayIterable<T>(array));
	}
	
	/**********************************************
	 * Overloaded methods for primitive-type arrays
	 **********************************************/
	
	/**
	 * Return a Queriable which wraps the given primitive-type int-array
	 */
	public static Queriable<Integer> array(int[] array) {
		return new QueriableImpl<Integer>(new IntArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type short-array
	 */
	public static Queriable<Short> array(short[] array) {
		return new QueriableImpl<Short>(new ShortArrayIterable(array));
	}

	/**
	 * Return a Queriable which wraps the given primitive-type long-array
	 */
	public static Queriable<Long> array(long[] array) {
		return new QueriableImpl<Long>(new LongArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type float-array
	 */
	public static Queriable<Float> array(float[] array) {
		return new QueriableImpl<Float>(new FloatArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type double-array
	 */
	public static Queriable<Double> array(double[] array) {
		return new QueriableImpl<Double>(new DoubleArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type byte-array
	 */
	public static Queriable<Byte> array(byte[] array) {
		return new QueriableImpl<Byte>(new ByteArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type boolean-array
	 */
	public static Queriable<Boolean> array(boolean[] array) {
		return new QueriableImpl<Boolean>(new BooleanArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type char-array
	 */
	public static Queriable<Character> array(char[] array) {
		return new QueriableImpl<Character>(new CharArrayIterable(array));
	}	
}