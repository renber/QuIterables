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

import java.util.Comparator;

/**
 * Represents a Queriable which has been ordered
 * @author René Bergelt
 *
 */
public interface OrderedQueriable<T> extends Queriable<T> {
		
	/**
	 * Define a secondary ordering criterion
	 * @param func Function to retrieve values to order by
	 * @return Ordered queriable
	 */
	public OrderedQueriable<T> thenBy(ItemFunc<T, Comparable> func);
	
	/**
	 * Define a secondary ordering criterion using the given Comparator
	 * @param valueFunc Function to retrieve values to order by
	 * @param comparator Comparator to compare values for ordering
	 * @param <TComparable> Type of the values to compare
	 * @return Ordered queriable
	 */
	public <TComparable> OrderedQueriable<T> thenBy(ItemFunc<T, TComparable> valueFunc, Comparator<TComparable> comparator);
	
	/**
	 * Define a secondary ordering criterion (descending)
	 * @param func Function to retrieve values to order by
	 * @return Ordered queriable
	 */
	public OrderedQueriable<T> thenByDescending(ItemFunc<T, Comparable> func);
	
	/**
	 * Define a secondary ordering criterion using the given Comparator (descending)
	 * @param valueFunc Function to retrieve values to order by
	 * @param comparator Comparator to compare values for ordering
	 * @param <TComparable> Type of the values to compare
	 * @return Ordered queriable
	 */
	public <TComparable> OrderedQueriable<T> thenByDescending(ItemFunc<T, TComparable> valueFunc, Comparator<TComparable> comparator);
}
