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
package de.renber.quiterables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.renber.quiterables.grouping.Group;
import de.renber.quiterables.grouping.GroupKey;
import de.renber.quiterables.grouping.GroupedList;
import de.renber.quiterables.grouping.GroupedQueriable;

/**
 * The actual implementation of the GroupedQueriable<T> interface used in the
 * library
 * 
 * @author René Bergelt
 *
 * @param <T>
 */
class GroupedQueriableImpl<T> extends QueriableImpl<Group<T>> implements GroupedQueriable<T> {

	protected Iterable<Group<T>> getGroupedIter() {
		return (Iterable<Group<T>>) containedIter;
	}
	
	public GroupedQueriableImpl(Iterable<Group<T>> sequenceOfGroups) {
		super(sequenceOfGroups);
	}		

	@Override
	public Queriable<T> get(GroupKey key) {
		Group<T> g = null;
		for(Group<T> element: getGroupedIter()) {
			if (element.getKey().equals(key))
			{
				g = element;
				break;
			}
		}			
		return g == null ? null : new QueriableImpl<T>(g);
	}

	@Override
	public GroupedList<T> toList() {
		GroupedList<T> gl = new GroupedListImpl<T>();
		for(Group g: getGroupedIter())
			gl.add(g);
		return gl;
	}

	@Override
	public Map<GroupKey, Iterable<T>> toMap() {
		HashMap<GroupKey, Iterable<T>> map = new HashMap<>();

		for (Group<T> group : this) {
			map.put(group.getKey(), new ArrayList<T>(group));
		}

		return map;
	}

	// ---
	// override return type of Queriable functions to GroupedQueriable for
	// convenience (grouped lists, stay grouped)
	// ---

	@Override
	public GroupedQueriable<T> where(Predicate<Group<T>> predicate) {
		return new GroupedQueriableImpl<T>(super.where(predicate));		
	}
	
	@Override
	public GroupedQueriable<T> defaultIfEmpty(Group<T> defaultValue) {
		return new GroupedQueriableImpl<T>(super.defaultIfEmpty(defaultValue));
	}

	@Override
	public GroupedQueriable<T> concat(Iterable<Group<T>> toConcat) {
		return new GroupedQueriableImpl<T>(super.concat(toConcat));
	}

	@Override
	public GroupedQueriable<T> union(Iterable<Group<T>> toConcat) {
		return new GroupedQueriableImpl<T>(super.union(toConcat));
	}

	@Override
	public GroupedQueriable<T> union(Iterable<Group<T>> toConcat, Equivalence<Group<T>> equalityComparer) {
		return new GroupedQueriableImpl<T>(super.union(toConcat, equalityComparer));
	}

	@Override
	public GroupedQueriable<T> intersect(Iterable<Group<T>> intersectWith) {
		return new GroupedQueriableImpl<T>(super.intersect(intersectWith));
	}

	@Override
	public GroupedQueriable<T> intersect(Iterable<Group<T>> intersectWith, Equivalence<Group<T>> equalityComparer) {
		return new GroupedQueriableImpl<T>(super.intersect(intersectWith, equalityComparer));
	}

	@Override
	public GroupedQueriable<T> except(Iterable<Group<T>> elementsToSubtract) {
		return new GroupedQueriableImpl<T>(super.except(elementsToSubtract));
	}

	@Override
	public GroupedQueriable<T> except(Iterable<Group<T>> elementsToSubtract, Equivalence<Group<T>> equalityComparer) {
		return new GroupedQueriableImpl<T>(super.except(elementsToSubtract, equalityComparer));
	}

	@Override
	public GroupedQueriable<T> distinct() {
		return new GroupedQueriableImpl<T>(super.distinct());
	}

	@Override
	public GroupedQueriable<T> distinct(Equivalence<Group<T>> equalityComparer) {
		return new GroupedQueriableImpl<T>(super.distinct(equalityComparer));
	}

	@Override
	public GroupedQueriable<T> take(int amount) {
		return new GroupedQueriableImpl<T>(super.take(amount));
	}

	@Override
	public GroupedQueriable<T> takeWhile(Predicate<Group<T>> condition) {
		return new GroupedQueriableImpl<T>(super.takeWhile(condition));
	}

	@Override
	public GroupedQueriable<T> skip(int amount) {
		return new GroupedQueriableImpl<T>(super.skip(amount));
	}

	@Override
	public GroupedQueriable<T> skipWhile(Predicate<Group<T>> condition) {
		return new GroupedQueriableImpl<T>(super.skipWhile(condition));
	}

	@Override
	public GroupedQueriable<T> reverse() {
		return new GroupedQueriableImpl<T>(super.reverse());
	}
	
	@Override
	public OrderedGroupedQueriable<T> orderBy(ItemFunc<Group<T>, Comparable> func) {
		throwIfArgumentIsNull(func);
		
		return new OrderedGroupedQueriableImpl<T>(containedIter, func, SortOrder.Ascending);
	}
	
	@Override
	public <TComparable> OrderedGroupedQueriable<T> orderBy(ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator) {
		throwIfArgumentIsNull(valueFunc, comparator);
		
		return new OrderedGroupedQueriableImpl<T>(containedIter, valueFunc, comparator, SortOrder.Ascending);
	}
	
	@Override
	public OrderedGroupedQueriable<T> orderByDescending(ItemFunc<Group<T>, Comparable> func) {
		throwIfArgumentIsNull(func);
		
		return new OrderedGroupedQueriableImpl<T>(containedIter, func, SortOrder.Descending);		
	}
	
	@Override
	public <TComparable> OrderedGroupedQueriable<T> orderByDescending(ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator) {
		throwIfArgumentIsNull(valueFunc);
		
		return new OrderedGroupedQueriableImpl<T>(containedIter, valueFunc, comparator, SortOrder.Descending);
	}
}
