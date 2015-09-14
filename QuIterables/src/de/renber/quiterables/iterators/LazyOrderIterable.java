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
package de.renber.quiterables.iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import de.renber.quiterables.ItemFunc;
import de.renber.quiterables.SortOrder;

/**
 * AN Iterable which sorts its elements before returning an iterator
 * 
 * @author Ren� Bergelt
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class LazyOrderIterable<T> implements Iterable<T> {

	Iterable<T> wrapped;		
	List<OrderFunc<T>> orderFuncs;

	List<T> sortedList;

	public LazyOrderIterable(Iterable<T> _wrapped, ItemFunc<T, Comparable> _orderFunc, SortOrder sortOrder) {
		wrapped = _wrapped;
		
		orderFuncs = new ArrayList<OrderFunc<T>>();
		orderFuncs.add(new OrderFunc<T>(_orderFunc, sortOrder));
	}	
	
	/**
	 * Add a secondary ordering function which is used to compare elements
	 * for which all previous ordering functions return "equal"	 
	 */
	public void addSecondaryOrderFunction(ItemFunc<T, Comparable> func, SortOrder sortOrder) {
		orderFuncs.add(new OrderFunc<T>(func, sortOrder));
	}

	@Override
	public Iterator<T> iterator() {
		// sort the elements now
		// TODO: use a lazy-sorting Iterator

		if (sortedList == null) {
			sortedList = new ArrayList<T>();
			wrapped.forEach(sortedList::add);

			Collections.sort(sortedList, new Comparator<T>() {
				
				@Override
				public int compare(T item1, T item2) {					
					
					int result = 0;
					
					for(OrderFunc f: orderFuncs) {
						Comparable c1 = (Comparable)f.func.exec(item1);
						Comparable c2 = (Comparable)f.func.exec(item2);
						
						result = c1.compareTo(c2);												
						if (result != 0)
							return f.sortOrder == SortOrder.Ascending ? result : -result;
					}
					
					return result;
				}
			});
		}

		return sortedList.iterator();
	}

	class OrderFunc<T> {
		public ItemFunc<T, Comparable> func;
		public SortOrder sortOrder; 
		
		public OrderFunc(ItemFunc<T, Comparable> _func, SortOrder _sortOrder) {
			func = _func;
			sortOrder = _sortOrder;
		}
	}
	
}
