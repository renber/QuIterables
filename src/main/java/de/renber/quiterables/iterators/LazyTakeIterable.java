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
package de.renber.quiterables.iterators;

import java.util.Iterator;

import de.renber.quiterables.Predicate;

/**
 * Iterable which takes a certain amount of elements or
 * takes as long as all elements match a predicate
 * @author berre
 *
 * @param <T>
 */
public class LazyTakeIterable<T> implements Iterable<T> {

	Iterable<T> wrapped;	
	int amount;	
	Predicate<T> takeWhileCondition;
	
	public LazyTakeIterable(Iterable<T> _wrapped, int _amount) {
		wrapped = _wrapped;
		amount = _amount;
	}
	
	public LazyTakeIterable(Iterable<T> _wrapped, Predicate<T> _takeWhileCondition) {
		wrapped = _wrapped;
		takeWhileCondition = _takeWhileCondition;
	}
	
	@Override
	public Iterator<T> iterator() {
		if (takeWhileCondition == null)						
			return new LazyTakeIterator<T>(wrapped.iterator(), amount);
		else
			return new LazyTakeWhileIterator<T>(wrapped.iterator(), takeWhileCondition);
	}
}

class LazyTakeIterator<T> extends LazyIterator<T>
{
	Iterator<T> wrapped;
	T nextElement = null;
	int amount;
	int current = 0;
	
	public LazyTakeIterator(Iterator<T> _wrapped, int _amount) {
		wrapped = _wrapped;
		amount = _amount;	
	}
		
	protected T findNextElement()
	{
		if (current >= amount)
			return null;
		
		if (wrapped.hasNext()) {
			current++;
			return wrapped.next();
		}
		
		return null;
	}	
}

class LazyTakeWhileIterator<T> extends LazyIterator<T>
{
	Iterator<T> wrapped;
	T nextElement = null;	
	Predicate<T> takeWhileCondition;
	
	public LazyTakeWhileIterator(Iterator<T> _wrapped, Predicate<T> _takeWhileCondition) {
		wrapped = _wrapped;
		takeWhileCondition = _takeWhileCondition;
	}
		
	protected T findNextElement()
	{		
		if (wrapped.hasNext()) {
			T element = wrapped.next();
			
			// if this element matches the condition take it
			// otherwise the enumeration ends
			if (takeWhileCondition.evaluate(element))
				return element;
		}
		
		return null;
	}	
}