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
package de.renebergelt.quiterables.grouping;

import java.util.Comparator;
import java.util.Map;

import de.renebergelt.quiterables.Equivalence;
import de.renebergelt.quiterables.ItemFunc;
import de.renebergelt.quiterables.OrderedGroupedQueriable;
import de.renebergelt.quiterables.Predicate;
import de.renebergelt.quiterables.Queriable;

/**
 * Interface which allows easy chaining of the ListMatcher methods
 * for grouped lists
 * @author berre
 */
public interface GroupedQueriable<T> extends Queriable<Group<T>> {

	/**
	 * Return the elements of the group with the given group keys
	 */
	public Queriable<T> get(GroupKey key);	
	
	/**
	 * Convert this grouped queriable to a grouped list
	 */
	@Override
	public GroupedList<T> toList();
	
	/**
	 * Convert	this grouped queriable to a map
	 * where the keys are the groupkeys and the value is a iterable containing
	 * the group's items
	 */
	public Map<GroupKey, Iterable<T>> toMap();
	
	// ---
	// override return type of Queriable funtctions to GroupedQueriable for convenience
	// ---
	
	@Override
	public GroupedQueriable<T> where(Predicate<Group<T>> predicate);	
	
	@Override
	public GroupedQueriable<T> defaultIfEmpty(Group<T> defaultValue);

	@Override
	public GroupedQueriable<T> concat(Iterable<Group<T>> toConcat);

	@Override
	public GroupedQueriable<T> union(Iterable<Group<T>> toConcat);

	@Override
	public GroupedQueriable<T> union(Iterable<Group<T>> toConcat, Equivalence<Group<T>> equalityComparer);

	@Override
	public GroupedQueriable<T> intersect(Iterable<Group<T>> intersectWith);

	@Override
	public GroupedQueriable<T> intersect(Iterable<Group<T>> intersectWith, Equivalence<Group<T>> equalityComparer);

	@Override
	public GroupedQueriable<T> except(Iterable<Group<T>> elementsToSubtract);

	@Override
	public GroupedQueriable<T> except(Iterable<Group<T>> elementsToSubtract, Equivalence<Group<T>> equalityComparer);	

	@Override
	public GroupedQueriable<T> distinct();

	@Override
	public GroupedQueriable<T> distinct(Equivalence<Group<T>> equalityComparer);	

	@Override
	public GroupedQueriable<T> take(int amount);

	@Override
	public GroupedQueriable<T> takeWhile(Predicate<Group<T>> condition);	

	@Override
	public GroupedQueriable<T> skip(int amount);

	@Override
	public GroupedQueriable<T> skipWhile(Predicate<Group<T>> condition);

	@Override
	public GroupedQueriable<T> reverse();
	
	@Override
	public OrderedGroupedQueriable<T> orderBy(ItemFunc<Group<T>, Comparable> func);
	
	@Override
	public <TComparable> OrderedGroupedQueriable<T> orderBy(ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator);
	
	@Override
	public OrderedGroupedQueriable<T> orderByDescending(ItemFunc<Group<T>, Comparable> func);	
	
	@Override
	public <TComparable> OrderedGroupedQueriable<T> orderByDescending(ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator);
}
