package de.renber.quiterables;

import java.util.Comparator;

import de.renber.quiterables.grouping.Group;
import de.renber.quiterables.grouping.GroupedQueriable;

/**
 * Represents a grouped Queriable which has been ordered
 * @author René Bergelt
 */
public interface OrderedGroupedQueriable<T> extends OrderedQueriable<Group<T>>, GroupedQueriable<T> {
	
	// we want the secondary order functions to also return a OrderedGroupedQueriable	
	// so override them accordingly
	
	@Override
	public OrderedGroupedQueriable<T> thenBy(ItemFunc<Group<T>, Comparable> func);
	
	@Override
	public <TComparable> OrderedGroupedQueriable<T> thenBy(ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator);
	
	@Override
	public OrderedGroupedQueriable<T> thenByDescending(ItemFunc<Group<T>, Comparable> func);
	
	@Override
	public <TComparable> OrderedGroupedQueriable<T> thenByDescending(ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator);
}
