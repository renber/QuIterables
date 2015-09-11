/*******************************************************************************
 * This file is part of the Java IterQuery Library
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
package de.renber.quiterables.grouping;

import java.util.ArrayList;
import java.util.List;

/**
 * A list which has been grouped and therefore contains groups
 * of a given element type
 */
public class GroupedList<T> extends ArrayList<Group<T>> {
	
	/**
	 * Return the group with the given group key or null if no such group exists
	 * 
	 * @param key
	 * @return
	 */
	public Group<T> get(GroupKey key) {
		for (Group<T> g : this) {
			if (g.getKey().equals(key)) {
				return g;
			}
		}

		return null;
	}
	
	/**
	 * Return the group with the group key composed of the given elements or null if no such group exists
	 * In order to use this function with a single key element of type int, call it like get(new Integer(value))
	 * 
	 * @param key
	 * @return
	 */
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
