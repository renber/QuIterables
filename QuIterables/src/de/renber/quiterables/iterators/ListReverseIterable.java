/*******************************************************************************
 * This file is part of the Java QuIterables Library
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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Iterable which iterates a list in reverse
 * @author Ren� Bergelt
 *
 * @param <T>
 */
public class ListReverseIterable<T> implements Iterable<T> {

	List<T> list;
	
	public ListReverseIterable(List<T> _list) {
		list = _list;		
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ListReverseIterator<T>(list.listIterator());
	}
}

class ListReverseIterator<T> extends LazyIterator<T> {

	ListIterator<T> listIterator;
	
	public ListReverseIterator(ListIterator<T> _listIterator) {
		listIterator = _listIterator;
	}
	
	@Override
	protected T findNextElement() {
		if (!listIterator.hasPrevious())
			return null;
		
		return listIterator.previous();
	}
	
}