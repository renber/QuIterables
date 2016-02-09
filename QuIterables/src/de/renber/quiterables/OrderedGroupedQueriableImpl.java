package de.renber.quiterables;

import de.renber.quiterables.grouping.Group;
import de.renber.quiterables.grouping.GroupedList;
import de.renber.quiterables.iterators.LazyOrderIterable;

/**
 * The actual implementation of the OrderedGroupedQueriable<T> interface used in the
 * library
 * 
 * @author René Bergelt
 *
 * @param <T>
 */
class OrderedGroupedQueriableImpl<T> extends GroupedQueriableImpl<T> implements OrderedGroupedQueriable<T> {

	protected LazyOrderIterable<Group<T>> getOrderIterable() {
		return (LazyOrderIterable<Group<T>>)containedIter;
	}
	
	public OrderedGroupedQueriableImpl(Iterable<Group<T>> forIterable, ItemFunc<Group<T>, Comparable> func, SortOrder sortOrder) {
		super(new LazyOrderIterable<Group<T>>(forIterable, func, sortOrder));		
	}	

	@Override
	public OrderedGroupedQueriable<T> thenBy(ItemFunc<Group<T>, Comparable> func) {
		getOrderIterable().addSecondaryOrderFunction(func, SortOrder.Ascending);
		return this;
	}
	
	@Override
	public OrderedGroupedQueriable<T> thenByDescending(ItemFunc<Group<T>, Comparable> func) {
		getOrderIterable().addSecondaryOrderFunction(func, SortOrder.Descending);
		return this;
	}	
}
