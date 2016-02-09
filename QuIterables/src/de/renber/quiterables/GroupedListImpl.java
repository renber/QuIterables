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

import java.util.ArrayList;
import java.util.List;

import de.renber.quiterables.grouping.Group;
import de.renber.quiterables.grouping.GroupKey;
import de.renber.quiterables.grouping.GroupedList;

/**
 * Actual implementation of the GroupedList interface used by the QuIterables library
 * @author René Bergelt
 *
 * @param <T>
 */
class GroupedListImpl<T> extends ArrayList<Group<T>> implements GroupedList<T>  {
		
	public GroupedListImpl() {
		// --
	}
	
	public GroupedListImpl(Iterable<Group<T>> elements) {		
		for(Group<T> g: elements)
			add(g);
	}
	
	@Override
	public Group<T> get(GroupKey key) {
		for (Group<T> g : this) {
			if (g.getKey().equals(key)) {
				return g;
			}
		}

		return null;
	}
	
	@Override
	public Group<T> get(Object...keyElements) {		
		return get(new GroupKey(keyElements));
	}
	
	/**
	 * GroupedList.get(int) is ambiguous. To avoid confusion use either get(new GroupKey(int)) or elementAt(int) 
	 * To conform to the java.util.List interface this method returns the element at the given position and _not_
	 * the group with the given integer key
	 * If you want to get the group with a specific numerical index use get(new Integer(value))	 
	 */	
	public Group<T> get(int index) {
		return elementAt(index);			
	}
		
	/**
	 * Return the element at the given position in the list
	 * @param index
	 * @return
	 */
	public Group<T> elementAt(int index) {
		return super.get(index);			
	}
}
