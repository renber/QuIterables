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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import de.renebergelt.quiterables.Equivalence;
import de.renebergelt.quiterables.Predicate;
import de.renebergelt.quiterables.Queriable;
import de.renebergelt.quiterables.Query;

/**
 * Iterable which returns the elements of a source iterable
 * but filters duplicates either by using the equals() method of
 * the objects or a custom equality comparer
 * @param <T> Type of the elements in this Iterable
 * @author René Bergelt 
 */
public class LazyDistinctIterable<T> implements Iterable<T> {

	Iterable<T> wrapped;
	Equivalence<T> equalityComparer;

	/**
	 * Create a new lazy distinct iterable which wraps the given iterable
	 * @param _wrapped The wrapped iterable
	 */
	public LazyDistinctIterable(Iterable<T> _wrapped) {
		wrapped = _wrapped;
	}

	/**
	 * Create a new lazy distinct iterable which wraps the given iterable
	 * using teh given equality comparer
	 * @param _wrapped The wrapped iterable
	 * @param _equalityComparer Comparer to decide if two elements are equal
	 */
	public LazyDistinctIterable(Iterable<T> _wrapped, Equivalence<T> _equalityComparer) {
		wrapped = _wrapped;
		equalityComparer = _equalityComparer;
	}
	
	@Override
	public Iterator<T> iterator() {
		if (equalityComparer == null)
			return new LazyDistinctIterator<T>(wrapped.iterator());
		else
			return new LazyDistinctWithEquivalenceIterator<T>(wrapped.iterator(), equalityComparer);
	}

}

class LazyDistinctIterator<T> extends LazyIterator<T> {

	Iterator<T> wrapped;	
	
	// keep track of already used items
	HashSet<T> usedItems = new HashSet<T>();
	
	public LazyDistinctIterator(Iterator<T> _wrapped) {
		wrapped = _wrapped;		
	}
	
	@Override
	protected T findNextElement() {
		
		while (wrapped.hasNext()) {
			T element = wrapped.next();
			if (!usedItems.contains(element)) {
				usedItems.add(element);
				return element;
			}
		}
		
		// iteration ended
		usedItems.clear();
		return null;
	}
}

class LazyDistinctWithEquivalenceIterator<T> extends LazyIterator<T> {

	Iterator<T> wrapped;	
	Equivalence<T> equalityComparer;
	
	// keep track of already used items
	List<T> usedItems = new ArrayList<T>();
	Queriable<T> qList = Query.list(usedItems);
	
	public LazyDistinctWithEquivalenceIterator(Iterator<T> _wrapped, Equivalence<T> _equalityComparer) {
		wrapped = _wrapped;		
		equalityComparer = _equalityComparer;
	}
	
	@Override
	protected T findNextElement() {
		
		while (wrapped.hasNext()) {
			final T element = wrapped.next();
			
			if (!qList.exists(new Predicate<T>() {public boolean evaluate(T item) {return equalityComparer.areEqual(item, element);}})) {
				usedItems.add(element);
				return element;
			}			
		}
		
		// iteration ended
		usedItems.clear();
		return null;
	}
	
}