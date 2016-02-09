package de.renber.quiterables;

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
	public OrderedGroupedQueriable<T> thenByDescending(ItemFunc<Group<T>, Comparable> func);	
}
