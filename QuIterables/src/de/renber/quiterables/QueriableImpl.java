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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import de.renber.quiterables.grouping.Group;
import de.renber.quiterables.grouping.GroupFunction;
import de.renber.quiterables.grouping.GroupKey;
import de.renber.quiterables.grouping.GroupedList;
import de.renber.quiterables.grouping.GroupedQueriable;
import de.renber.quiterables.grouping.SingleKeyGroupFunction;
import de.renber.quiterables.iterators.LazySkipIterable;
import de.renber.quiterables.iterators.LazyTakeIterable;
import de.renber.quiterables.iterators.LazyWhereIterable;
import de.renber.quiterables.iterators.ListReverseIterable;
import de.renber.quiterables.iterators.LazyConcatIterable;
import de.renber.quiterables.iterators.LazyDistinctIterable;
import de.renber.quiterables.iterators.LazySelectIterable;
import de.renber.quiterables.iterators.LazySelectManyIterable;

/**
 * Actual Queriable<T> implementation used in this library and which is returned
 * by the Query methods
 * 
 * @author René Bergelt
 *
 */
class QueriableImpl<T> implements Queriable<T> {

	protected Iterable<T> containedIter;

	protected QueriableImpl(Iterable<T> forIterable) {

		if (forIterable == null)
			throw new IllegalArgumentException("forIterable must not be null");

		containedIter = forIterable;
	}
	
	// ***************************
	// Some helper methods
	// ***************************
	
	/**
	 * Throws an IllegalArgumentException if any of the arguments is null
	 */
	protected void throwIfArgumentIsNull(Object...arguments) throws IllegalArgumentException {
		for(Object argument: arguments)
			if (argument == null)
				throw new IllegalArgumentException("Argument must not be null.");		
	}	
	
	/**
	 * Either returns element or throws NoSuchElementException
	 * if element is null
	 */
	private <TIn> TIn throwIfNull(TIn element) throws NoSuchElementException {
		if (element == null)
			throw new NoSuchElementException();
		return element;
	}

	// ***************************
	// Queriable<T> implementation
	// ***************************

	@Override
	public List<T> toList() {
		ArrayList<T> list = new ArrayList<T>();
		for(T element: containedIter)
			list.add(element);		
		return list;		
	}
	
	@Override
	public Set<T> toSet() {
		HashSet<T> set = new HashSet<T>();		
		for(T element: distinct()) {
			set.add(element);
		}
		return set;
	}

	@Override
	public T[] toArray(Class<T> classType) {
		throwIfArgumentIsNull(classType);		
		
		// Todo: reduce memory consumption
		// right now we have to iterate twice through containedIter (first in count() then with the loop)
		@SuppressWarnings("unchecked")
		T[] a = (T[]) Array.newInstance(classType, count());
		int idx = 0;
		for (T element : containedIter) {
			a[idx] = element;
			idx++;
		}
		return a;
	}
	
	@Override
	public PrimitiveArrayTransformer<T> toPrimitiveArray() {
		return new PrimitiveArrayTransformerImpl<T>(containedIter);
	}

	@Override
	public boolean isEmpty() {
		return !containedIter.iterator().hasNext();
	}
	
	@Override
	public Queriable<T> defaultIfEmpty(T defaultValue) {
		if (isEmpty()) {
			List<T> list = new ArrayList<T>();
			list.add(defaultValue);
			return Query.list(list);
		}
		
		return this;
	}
	
	@Override
	public T elementAt(int index) throws NoSuchElementException {
		return throwIfNull(elementAtOrDefault(index));		
	}
	
	@Override
	public T elementAtOrDefault(int index) {
		if (containedIter instanceof List) {
			// if we wrap a list use the direct access
			
			List<T> innerList = (List<T>)containedIter;
			if (index < innerList.size())
				return innerList.get(index);
			
			return null;
		}
		
		// we have to iterate to the given index
		Iterator<T> it = containedIter.iterator();
		for(int i = 0; i < index; i++) {
			if (!it.hasNext())
				return null;
			it.next();
		}
		
		return it.hasNext() ? it.next() : null;
	}
	
	@Override
	public T elementAtOrDefault(int index, T defaultValue) {
		throwIfArgumentIsNull(defaultValue);
		
		T element = elementAtOrDefault(index);
		return element == null ? defaultValue : element;
	}
	
	@Override
	public Queriable<T> where(Predicate<T> predicate) {
		throwIfArgumentIsNull(predicate);
		
		return Query.iterable(new LazyWhereIterable<T>(containedIter, predicate));
	}
	
	@Override
	public boolean sequenceEquals(Iterable<T> iterable) {
		throwIfArgumentIsNull(iterable);
		
		Iterator<T> it1 = this.iterator();
		Iterator<T> it2 = iterable.iterator();
		
		while(it1.hasNext() && it2.hasNext()) {
			T item1 = it1.next();
			T item2 = it2.next();
			
			if (!item1.equals(item2))
				return false;
		}
		
		// if we compared all elements then the sequences are equal
		return !it1.hasNext() && !it2.hasNext();
	}
	
	@Override
	public boolean sequenceEquals(Iterable<T> iterable, Equivalence<T> equalityComparer) {
		throwIfArgumentIsNull(iterable, equalityComparer);
		
		Iterator<T> it1 = this.iterator();
		Iterator<T> it2 = iterable.iterator();
		
		while(it1.hasNext() && it2.hasNext()) {
			T item1 = it1.next();
			T item2 = it2.next();
			
			if (!equalityComparer.areEqual(item1, item2))
				return false;
		}
		
		// if we compared all elements then the sequences are equal
		return !it1.hasNext() && !it2.hasNext();
	}

	public boolean all(Predicate<T> predicate) {
		throwIfArgumentIsNull(predicate);
		
		for (T item : containedIter) {
			if (!predicate.evaluate(item)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean exists(Predicate<T> predicate) {
		throwIfArgumentIsNull(predicate);
		
		for (T item : containedIter) {
			if (predicate.evaluate(item)) {
				return true;
			}
		}

		return false;
	}
	
	public boolean contains(T element) {
		throwIfArgumentIsNull(element);
		
		for(T item: this) {
			if (item.equals(element))
				return true;
		}
			
		return false;
	}
	
	public boolean contains(final T element, Equivalence<T> equalityComparer) {
		throwIfArgumentIsNull(element, equalityComparer);
		
		for(final T item: this) {
			if (equalityComparer.areEqual(element, item))
				return true;
		}
			
		return false;
	}

	@Override
	public T first() throws NoSuchElementException {
		return throwIfNull(firstOrDefault());		
	}
	
	@Override
	public T firstOrDefault() {		
		// explicit cast to resolve "method is ambiguous" compiler error
		return firstOrDefault((T)null);
	}
	
	@Override
	public T firstOrDefault(T defaultValue) {
		Iterator<T> it = containedIter.iterator();
		return it.hasNext() ? it.next() : defaultValue;
	}
	
	@Override
	public T first(Predicate<T> predicate) throws NoSuchElementException {
		throwIfArgumentIsNull(predicate);
		
		return throwIfNull(firstOrDefault(predicate));		
	}
	
	@Override
	public T firstOrDefault(Predicate<T> predicate) {
		throwIfArgumentIsNull(predicate);
		
		for (T item : containedIter) {
			if (predicate.evaluate(item)) {
				return item;
			}
		}

		return null;
	}
	
	public T firstOrDefault(Predicate<T> predicate, T defaultValue) {
		throwIfArgumentIsNull(predicate, defaultValue);
		
		T element = firstOrDefault(predicate);
		return element == null ? defaultValue : element;
	}

	@Override
	public T last() throws NoSuchElementException {
		return throwIfNull(lastOrDefault());		
	}
	
	@Override
	public T lastOrDefault() {
		Iterator<T> it = containedIter.iterator();

		T lastElement = null;
		while (it.hasNext())
			lastElement = it.next();

		return lastElement;
	}
	
	@Override
	public T last(Predicate<T> predicate) throws NoSuchElementException {
		throwIfArgumentIsNull(predicate);
		
		return throwIfNull(lastOrDefault(predicate));
	}

	@Override
	public T lastOrDefault(Predicate<T> predicate) {
		throwIfArgumentIsNull(predicate);
		
		return Query.iterable(containedIter).where(predicate).lastOrDefault();
	}
	
	@Override
	public T lastOrDefault(Predicate<T> predicate, T defaultValue) {
		throwIfArgumentIsNull(predicate, defaultValue);
		
		T element = lastOrDefault(predicate);
		return element == null ? defaultValue : element;
	}
	
	@Override
    public T single() throws NoSuchElementException {
		return throwIfNull(singleOrDefault());		
    }

	@Override
    public T singleOrDefault() {
		Iterator<T> it = containedIter.iterator();
    	
		if (!it.hasNext())
    		// enumeration is empty
    		return null;
    	
    	T element = it.next();
    	
    	if (it.hasNext()) 
    		// enumeration contains more than one element
    		return null;
    	
    	return element;
    }
    
	@Override
    public T single(Predicate<T> predicate) throws NoSuchElementException {
		throwIfArgumentIsNull(predicate);
		
		return throwIfNull(singleOrDefault(predicate));
    }
	
	@Override
    public T singleOrDefault(Predicate<T> predicate) {
		throwIfArgumentIsNull(predicate);
		
		return Query.iterable(containedIter).where(predicate).singleOrDefault();
	}
	
	@Override
	public int count() {		
		// if the wrapped Iterable is a list we do not
		// have to iterate to get the number of elements;
		if (containedIter instanceof Collection) {
			return ((Collection<T>)containedIter).size();
		}				
		
		int c = 0;		
		for (Iterator<T> it = containedIter.iterator(); it.hasNext(); it.next())
			c++;
		return c;
	}

	@Override
	public int count(Predicate<T> predicate) {
		throwIfArgumentIsNull(predicate);
		
		int c = 0;
		for (T element : containedIter)
			if (predicate.evaluate(element))
				c++;

		return c;
	}

	@Override
	public T max() {				
		T max = null;

		for (T item : containedIter) {
			// this only works if the valFunc always returns the same type of
			// number (i.e. only Integer, only Double, etc.) or types which are
			// comparable with each other

			if (!(item instanceof Comparable)) {
				throw new RuntimeException("The value for the list element '" + item.toString()
						+ "' does not implement the Comparable interface.");
			}

			if (max == null || ((Comparable) item).compareTo((Comparable) max) == 1) {
				max = item;
			}
		}

		return max;
	}
	
	@Override
	public Number max(NumberFunc<T> valFunc) {
		throwIfArgumentIsNull(valFunc);
		
		Number max = null;

		for (T item : containedIter) {
			Number c = valFunc.getValue(item);
			// this only works if the valFunc always returns the same type of
			// number (i.e. only Integer, only Double, etc.) or types which are
			// comparable with each other

			if (!(c instanceof Comparable)) {
				throw new RuntimeException("The numerical value for the list element '" + item.toString()
						+ "' does not implement the Comparable interface.");
			}

			if (max == null || ((Comparable) c).compareTo((Comparable) max) == 1) {
				max = c;
			}
		}

		return max;
	}
	
	@Override
	public T min() {				
		T min = null;

		for (T item : containedIter) {
			if (!(item instanceof Comparable)) {
				throw new RuntimeException("The value for the list element '" + item.toString()
						+ "' does not implement the Comparable interface.");
			}

			// this only works if the valFunc always returns the same type of
			// number (i.e. only Integer, only Double, etc.)
			if (min == null || ((Comparable) item).compareTo((Comparable) min) == -1) {
				min = item;
			}
		}

		return min;
	}

	@Override
	public Number min(NumberFunc<T> valFunc) {
		throwIfArgumentIsNull(valFunc);
		
		Number min = null;

		for (T item : containedIter) {
			Number c = valFunc.getValue(item);

			if (!(c instanceof Comparable)) {
				throw new RuntimeException("The numerical value for the list element '" + item.toString()
						+ "' does not implement the Comparable interface.");
			}

			// this only works if the valFunc always returns the same type of
			// number (i.e. only Integer, only Double, etc.)
			if (min == null || ((Comparable) c).compareTo((Comparable) min) == -1) {
				min = c;
			}
		}

		return min;
	}
	
	@Override
	public Number average(NumberFunc<T> valFunc) {
		throwIfArgumentIsNull(valFunc);
		
		Double avg = null;
		int count = 0;

		for (T item : containedIter) {
			Number c = valFunc.getValue(item);								
			
			if (avg == null) { 
				avg = c.doubleValue();
			} else {				
				avg = (avg.doubleValue()*count + c.doubleValue()) / (count+1);				
			}						
			
			count++;
		}

		return avg;
	}

	
	@Override
	public Number sum(NumberFunc<T> valFunc) {
		throwIfArgumentIsNull(valFunc);
		
		Number sum = null;

		for (T item : containedIter) {

			//
			Number c = valFunc.getValue(item);

			if (sum == null) {
				sum = c;
			} else {
				// this only works if the valFunc always returns the same type
				// of number (i.e. only Integer, only Double, etc.)
				if (c instanceof Integer) {
					sum = ((Integer) sum) + (Integer) c;
				} else if (c instanceof Double) {
					sum = ((Double) sum) + (Double) c;
				} else {
					// fallback
					sum = sum.doubleValue() + c.doubleValue();
				}
			}

		}

		return sum == null ? 0 : sum;
	}

	@Override
	public <TOut> Queriable<TOut> select(Selector<T, TOut> selector) {
		throwIfArgumentIsNull(selector);
		
		return new QueriableImpl<TOut>(new LazySelectIterable<T, TOut>(containedIter, selector));
	}

	@Override
	public <TOut> Queriable<TOut> selectMany(Selector<T, Iterable<TOut>> selector) {
		throwIfArgumentIsNull(selector);
		
		return new QueriableImpl<TOut>(new LazySelectManyIterable<T, TOut>(containedIter, selector));
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public <TOut> Queriable<TOut> cast(Class<TOut> targetType) {
		throwIfArgumentIsNull(targetType);
		
		// use select with a selector which casts the elements
		return Query.iterable(new LazySelectIterable<T, TOut>(containedIter, 
			new Selector<T, TOut>() {
			@Override
			public TOut select(T item) {
				return (TOut)item;
			}}));		
	}
	
	public <TOut> Queriable<TOut> ofType(final Class<TOut> targetType) {
		throwIfArgumentIsNull(targetType);
		
		// first filter all elements which are of the given type
		// then cast these elements
		return Query.iterable(where(
				new Predicate<T>() {
					@Override
					public boolean evaluate(T item) {
						return targetType.isAssignableFrom(item.getClass());
					}}
				
				).cast(targetType));
	}
	
	@Override
	public Queriable<T> concat(Iterable<T> toConcat) {
		throwIfArgumentIsNull(toConcat);
		
		return Query.iterable(new LazyConcatIterable<T>(containedIter, toConcat));
	}
		
	@Override
	public Queriable<T> union(Iterable<T> toConcat) {
		throwIfArgumentIsNull(toConcat);
		
		return Query.iterable(new LazyConcatIterable<T>(containedIter, toConcat)).distinct();
	}
	
	@Override
	public Queriable<T> union(Iterable<T> toConcat, Equivalence<T> equalityComparer) {
		throwIfArgumentIsNull(toConcat, equalityComparer);
		
		return Query.iterable(new LazyConcatIterable<T>(containedIter, toConcat)).distinct(equalityComparer);
	}
	
	@Override
	public Queriable<T> intersect(Iterable<T> intersectWith) {
		throwIfArgumentIsNull(intersectWith);
		
		// TODO: maybe also do this lazy
		List<T> intersectList = Query.iterable(intersectWith).toList();
		List<T> resultList = new ArrayList<T>();
		
		for(T element: this) {
			if (intersectList.contains(element))
				resultList.add(element);
		}
		
		return Query.list(resultList);
	}
	
	@Override
	public Queriable<T> intersect(Iterable<T> intersectWith, final Equivalence<T> equalityComparer) {
		throwIfArgumentIsNull(intersectWith, equalityComparer);
		
		// TODO: maybe also do this lazy		
		Queriable<T> intersectQueriable = Query.iterable(intersectWith);
		List<T> resultList = new ArrayList<T>();
		
		for(final T element: this) {
			if (intersectQueriable.exists(
					new Predicate<T>() {
						@Override
						public boolean evaluate(T item) {
							 return equalityComparer.areEqual(element, item);
						}}
					))
				resultList.add(element);
		}
		
		return Query.list(resultList);
	}

	@Override
	public Queriable<T> except(Iterable<T> elementsToSubtract) {
		throwIfArgumentIsNull(elementsToSubtract);
		
		// TODO: maybe also do this lazy
		List<T> intersectList = Query.iterable(elementsToSubtract).toList();
		List<T> resultList = new ArrayList<T>();
		
		for(T element: this) {
			if (!intersectList.contains(element))
				resultList.add(element);
		}
		
		return Query.list(resultList);
	}
	
	@Override
	public Queriable<T> except(Iterable<T> elementsToSubtract, final Equivalence<T> equalityComparer) {
		throwIfArgumentIsNull(elementsToSubtract, equalityComparer);
		
		// TODO: maybe also do this lazy		
		Queriable<T> intersectQueriable = Query.iterable(elementsToSubtract);
		List<T> resultList = new ArrayList<T>();
		
		for(final T element: this) {
			if (!intersectQueriable.exists(					
					new Predicate<T>() {
						@Override
						public boolean evaluate(T item) {
							return equalityComparer.areEqual(element, item);
						}}
					
					))
				resultList.add(element);
		}
		
		return Query.list(resultList);
	}
	
	@Override
	public Queriable<T> distinct() {
		return Query.iterable(new LazyDistinctIterable<>(containedIter));
	}
	
	@Override
	public Queriable<T> distinct(final Equivalence<T> equalityComparer) {
		throwIfArgumentIsNull(equalityComparer);
		
		return Query.iterable(new LazyDistinctIterable<>(containedIter, equalityComparer));
	}

	@Override
	public Queriable<T> take(int amount) {
		return Query.iterable(new LazyTakeIterable<T>(containedIter, amount));
	}

	@Override
	public Queriable<T> takeWhile(Predicate<T> condition) {
		throwIfArgumentIsNull(condition);
		
		return Query.iterable(new LazyTakeIterable<T>(containedIter, condition));
	}

	@Override
	public Queriable<T> skip(int amount) {
		return Query.iterable(new LazySkipIterable<T>(containedIter, amount));
	}

	public Queriable<T> skipWhile(Predicate<T> condition) {
		throwIfArgumentIsNull(condition);
		
		return Query.iterable(new LazySkipIterable<T>(containedIter, condition));
	}

	@Override
	public OrderedQueriable<T> orderBy(ItemFunc<T, Comparable> func) {
		throwIfArgumentIsNull(func);
		
		return new OrderedQueriableImpl<T>(containedIter, func, SortOrder.Ascending);
	}
	
	@Override
	public <TComparable> OrderedQueriable<T> orderBy(ItemFunc<T, TComparable> valueFunc, Comparator<TComparable> comparator) {
		throwIfArgumentIsNull(comparator);
				
		return new OrderedQueriableImpl<T>(containedIter, valueFunc, comparator, SortOrder.Ascending);
	}	
	
	@Override
	public OrderedQueriable<T> orderByDescending(ItemFunc<T, Comparable> func) {
		throwIfArgumentIsNull(func);
		
		return new OrderedQueriableImpl<T>(containedIter, func, SortOrder.Descending);			
	}
	
	@Override
	public <TComparable> OrderedQueriable<T> orderByDescending(ItemFunc<T, TComparable> valueFunc, Comparator<TComparable> comparator) {
		throwIfArgumentIsNull(comparator);
				
		return new OrderedQueriableImpl<T>(containedIter, valueFunc, comparator, SortOrder.Descending);
	}		
	
	@Override
	public Queriable<T> reverse() {
		
		// if the wrapped Iterable is a list we can use
		// the ListIterator to traverse it in reverse order
		if (containedIter instanceof List) {
			return Query.iterable(new ListReverseIterable<T>((List<T>)containedIter));
		}
		
		// otherwise we have to enumerate all elements and reverse the collection afterwards
		List<T> lst = toList();		
		Collections.reverse(lst);
		return Query.iterable(lst);
	}

	@Override
	public GroupedQueriable<T> group(GroupFunction<T> func) {
		throwIfArgumentIsNull(func);
		
		HashMap<GroupKey, Group<T>> groups = new HashMap<GroupKey, Group<T>>();
		// put each element into a group
		for (T element : containedIter) {
			// get the group key for this element
			GroupKey gk = func.getKeyFor(element);

			Group<T> els = groups.get(gk);
			if (els == null) {
				// new group key
				els = new GroupImpl<T>(gk);
				groups.put(gk, els);
			}

			// add this element to the corresponding group
			els.add(element);
		}

		// return the groups
		GroupedList<T> gList = new GroupedListImpl<T>();
		gList.addAll(groups.values());

		return new GroupedQueriableImpl<T>(gList);
	}

	@Override
	public GroupedQueriable<T> groupSingle(final SingleKeyGroupFunction<T> func) {
		throwIfArgumentIsNull(func);
		
		return group(new GroupFunction<T>() {
			@Override
			public GroupKey getKeyFor(T element) {
				return new GroupKey(func.getKeyElementFor(element));
			}});
	}

	// --------------------------
	// Iterable<T> implementation
	// --------------------------

	@Override
	public Iterator<T> iterator() {
		return containedIter.iterator();
	}
}
