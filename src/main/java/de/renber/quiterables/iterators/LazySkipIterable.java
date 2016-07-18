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
 * Iterable which skips a certain amount of elements or
 * skips as long as all elements match a predicate
 * @author berre
 *
 * @param <T>
 */
public class LazySkipIterable<T> implements Iterable<T> {

	Iterable<T> wrapped;	
	int amount;	
	Predicate<T> skipWhileCondition;
	
	public LazySkipIterable(Iterable<T> _wrapped, int _amount) {
		wrapped = _wrapped;
		amount = _amount;
	}
	
	public LazySkipIterable(Iterable<T> _wrapped, Predicate<T> _skipWhileCondition) {
		wrapped = _wrapped;
		skipWhileCondition = _skipWhileCondition;
	}
	
	@Override
	public Iterator<T> iterator() {
		if (skipWhileCondition == null)						
			return new LazySkipIterator<T>(wrapped.iterator(), amount);
		else
			return new LazySkipWhileIterator<T>(wrapped.iterator(), skipWhileCondition);
	}
}

class LazySkipIterator<T> extends LazyIterator<T>
{
	Iterator<T> wrapped;
	T nextElement = null;
	int amount;
	boolean skippingDone = false;
	
	public LazySkipIterator(Iterator<T> _wrapped, int _amount) {
		wrapped = _wrapped;
		amount = _amount;	
	}
		
	protected T findNextElement()
	{
		if (skippingDone)			
			return wrapped.hasNext()? wrapped.next() : null;					
		
		for(int i = 0; i < amount; i++)
		{
			if (!wrapped.hasNext())
				return null;
			
			wrapped.next();
		}
			
		skippingDone = true;
		return wrapped.hasNext()? wrapped.next() : null;
	}	
}

class LazySkipWhileIterator<T> extends LazyIterator<T>
{
	Iterator<T> wrapped;
	T nextElement = null;	
	Predicate<T> skipWhileCondition;
	boolean skippingDone = false;
	
	public LazySkipWhileIterator(Iterator<T> _wrapped, Predicate<T> _skipWhileCondition) {
		wrapped = _wrapped;
		skipWhileCondition = _skipWhileCondition;
	}
		
	protected T findNextElement()
	{		
		if (skippingDone)			
			return wrapped.hasNext()? wrapped.next() : null;					
		
		while(wrapped.hasNext())
		{			
			T element = wrapped.next();
			if (!skipWhileCondition.evaluate(element))
			{
				// element does not match the skip condition, start here 
				skippingDone = true;
				return element;
			}
		}	
		
		return null;
	}	
}