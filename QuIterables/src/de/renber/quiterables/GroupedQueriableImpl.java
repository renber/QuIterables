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

import java.util.List;

import de.renber.quiterables.grouping.Group;
import de.renber.quiterables.grouping.GroupKey;
import de.renber.quiterables.grouping.GroupedList;
import de.renber.quiterables.grouping.GroupedQueriable;

/**
 * The actual implementation of the GroupedQueriable<T> interface used in the library
 * @author René Bergelt
 *
 * @param <T>
 */
class GroupedQueriableImpl<T> extends QueriableImpl<Group<T>> implements GroupedQueriable<T> {

	protected GroupedList<T> getGroupedList() {
		return (GroupedList<T>)containedIter;
	}
	
	public GroupedQueriableImpl(GroupedList<T> groupedList) {
		super(groupedList);
	}

	@Override
	public Queriable<T> get(GroupKey key) {
		Group<T> g = getGroupedList().get(key);
		if (g == null)
			return null;
		else
			return new QueriableImpl<T>(g);
	}
	
	public GroupedList<T> toList() {
		GroupedList<T> gl = new GroupedListImpl<T>();
		gl.addAll(getGroupedList());
		return gl;
	}

}
