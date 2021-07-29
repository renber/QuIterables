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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import de.renebergelt.quiterables.ItemFunc;
import de.renebergelt.quiterables.SortOrder;

/**
 * AN Iterable which sorts its elements before returning an iterator
 * @param <T> Type of the elements in this Iterable
 * @author René Bergelt
 *
 */
@SuppressWarnings("unchecked")
public class LazyOrderIterable<T, TComparable> implements Iterable<T> {

	Iterable<T> wrapped;		
	List<OrderFunc> orderFuncs;

	List<T> sortedList;
	
	static final NaturalComparator defaultComparator = new NaturalComparator();

	/**
	 * Create a new lazy order iterable which wraps the given iterable
	 * @param _wrapped The wrapped iterable
	 * @param valueFunc Function to retrieve the value to order by
	 * @param comparator Comparator to use for comparing values
	 * @param sortOrder The sort order
	 * @param <TComparable> Type of the values to compare
	 */
	public <TComparable> LazyOrderIterable(Iterable<T> _wrapped, ItemFunc<T, TComparable> valueFunc, Comparator comparator, SortOrder sortOrder) {
		wrapped = _wrapped;		
		orderFuncs = new ArrayList<OrderFunc>();
		orderFuncs.add(new OrderFunc(valueFunc, comparator, sortOrder));
	}	
	
	/**
	 * Creates a LazyOrderIterable which uses the default Comparator
	 * @param _wrapped The iterable which will be wrapped (and sorted)
	 * @param valueFunc The function to retrieve the values to compare
	 * @param sortOrder The sort order
	 */
	public LazyOrderIterable(Iterable<T> _wrapped, ItemFunc<T, Comparable> valueFunc, SortOrder sortOrder) {
		this(_wrapped, valueFunc, defaultComparator, sortOrder);
	}		
	
	/**
	 * Adds a secondary ordering function which is used to compare elements
	 * for which all previous ordering functions return "equal"
	 * @param func The function to retrieve the values to compare
	 * @param sortOrder The sort order
	 */
	public void addSecondaryOrderFunction(ItemFunc<T, Comparable> func, SortOrder sortOrder) {
		orderFuncs.add(new OrderFunc(func, defaultComparator, sortOrder));
	}
	
	/**
	 * Add a secondary ordering function which is used to compare elements
	 * for which all previous ordering functions return "equal"
	 * @param func The function to retrieve the values to compare
	 * @param comparator Custom comparator
	 * @param sortOrder The sort order
	 * @param <TComparable> Type of the values to compare
	 */
	public <TComparable> void addSecondaryOrderFunction(ItemFunc<T, TComparable> func, Comparator<TComparable> comparator, SortOrder sortOrder) {
		orderFuncs.add(new OrderFunc(func, comparator, sortOrder));
	}	

	@Override
	public Iterator<T> iterator() {
		// sort the elements now
		// TODO: use a lazy-sorting Iterator

		if (sortedList == null) {
			sortedList = new ArrayList<T>();
			for(T element: wrapped)
				sortedList.add(element);

			Collections.sort(sortedList, new Comparator<T>() {
				
				@Override
				public int compare(T item1, T item2) {					
					
					int result = 0;
					
					for(OrderFunc f: orderFuncs) {
						
						result = f.compare(item1, item2);
																	
						if (result != 0)
							return result;

						// if the to elements are considered equal
						// continue with the next order func
					}
					
					return result;
				}
			});
		}

		return sortedList.iterator();
	}	
}

/**
 * Holds an order function and the requested sort order
 * @author René Bergelt	 
 */
class OrderFunc<T, TComparable> {
	public ItemFunc<T, TComparable> func;
	// the comparator to use, when comparing the values returned by the given ItemFunc
	public Comparator comparator;
	public SortOrder sortOrder; 
	
	public OrderFunc(ItemFunc<T, TComparable> _func, Comparator _comparator, SortOrder _sortOrder) {
		func = _func;
		sortOrder = _sortOrder;
		comparator = _comparator;
	}
	
	/**
	 * Compare the two elements with this order func
	 * @param element1 First element
	 * @param element2 Second element
	 * @return Result of comparison
	 */
	public int compare(T element1, T element2) {			
		int result = comparator.compare(func.exec(element1), func.exec(element2));															
		return sortOrder == SortOrder.Ascending ? result : -result;
	}
}

/**
 * The default comparator used by the LazyOrderIterable	 
 */
class NaturalComparator implements Comparator
{
	@Override
	public int compare(Object o1, Object o2) {			
		return ((Comparable)o1).compareTo((Comparable)o2);
	}		
}
