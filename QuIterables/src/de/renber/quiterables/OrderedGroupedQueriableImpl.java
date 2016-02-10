package de.renber.quiterables;

import java.util.Comparator;

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

	LazyOrderIterable orderedContainedIter;
	
	protected LazyOrderIterable getOrderIterable() {
		return (LazyOrderIterable)containedIter;
	}
	
	public OrderedGroupedQueriableImpl(Iterable<Group<T>> forIterable, ItemFunc<Group<T>, Comparable> func, SortOrder sortOrder) {
		super(new LazyOrderIterable<Group<T>, Comparable>(forIterable, func, sortOrder));		
	}
	
	public <TComparable> OrderedGroupedQueriableImpl(Iterable<Group<T>> forIterable, ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator, SortOrder sortOrder) {		
		super(new LazyOrderIterable<Group<T>, Comparable>(forIterable, valueFunc, comparator, sortOrder));
	}

	@Override
	public OrderedGroupedQueriable<T> thenBy(ItemFunc<Group<T>, Comparable> valueFunc) {
		getOrderIterable().addSecondaryOrderFunction(valueFunc, SortOrder.Ascending);
		return this;
	}
	
	@Override
	public <TComparable> OrderedGroupedQueriable<T> thenBy(ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator) {
		getOrderIterable().addSecondaryOrderFunction(valueFunc, comparator, SortOrder.Ascending);
		return this;
	}	
	
	@Override
	public OrderedGroupedQueriable<T> thenByDescending(ItemFunc<Group<T>, Comparable> func) {
		getOrderIterable().addSecondaryOrderFunction(func, SortOrder.Descending);
		return this;
	}	
	
	@Override
	public <TComparable> OrderedGroupedQueriable<T> thenByDescending(ItemFunc<Group<T>, TComparable> valueFunc, Comparator<TComparable> comparator) {
		getOrderIterable().addSecondaryOrderFunction(valueFunc, comparator, SortOrder.Descending);
		return this;
	}	
}
