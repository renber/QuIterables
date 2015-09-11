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
package de.renber.quiterables;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.renber.quiterables.grouping.Group;
import de.renber.quiterables.grouping.GroupFunction;
import de.renber.quiterables.grouping.GroupKey;
import de.renber.quiterables.grouping.GroupedList;
import de.renber.quiterables.grouping.GroupedQueriable;
import de.renber.quiterables.grouping.SingleKeyGroupFunction;
import de.renber.quiterables.iterators.LazySkipIterable;
import de.renber.quiterables.iterators.LazyTakeIterable;
import de.renber.quiterables.iterators.LazyWhereIterable;

/**
 * Actual Queriable<T> implementation used in this library and which is returned
 * by the Query methods
 * 
 * @author René Bergelt
 *
 */
class QueriableImpl<T> implements Queriable<T> {

	protected Iterable<T> containedIter;

	protected QueriableImpl(Iterable<T> forIterable) {

		if (forIterable == null)
			throw new IllegalArgumentException("forIterable must not be null");

		containedIter = forIterable;
	}

	// ***************************
	// Queriable<T> implementation
	// ***************************

	@Override
	public List<T> toList() {
		ArrayList<T> list = new ArrayList<T>();
		containedIter.forEach(list::add);
		return list;
	}

	public T[] toArray(Class<T> classType) {
		// Todo: reduce memory consumption
		// right now we have to iterate twice through containedIter
		@SuppressWarnings("unchecked")
		T[] a = (T[]) Array.newInstance(classType, count());
		int idx = 0;
		for (T element : containedIter) {
			a[idx] = element;
			idx++;
		}
		return a;
	}
	
	public PrimitiveArrayTransformer<T> toPrimitiveArray() {
		return new PrimitiveArrayTransformerImpl<T>(containedIter);
	}

	@Override
	public Queriable<T> where(Predicate<T> predicate) {
		return Query.iterable(new LazyWhereIterable<T>(containedIter, predicate));
	}

	public boolean all(Predicate<T> predicate) {
		for (T item : containedIter) {
			if (!predicate.evaluate(item)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean exists(Predicate<T> predicate) {
		for (T item : containedIter) {
			if (predicate.evaluate(item)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public T firstOrDefault(Predicate<T> predicate) {
		for (T item : containedIter) {
			if (predicate.evaluate(item)) {
				return item;
			}
		}

		return null;
	}

	@Override
	public T firstOrDefault() {
		Iterator<T> it = containedIter.iterator();
		return it.hasNext() ? it.next() : null;
	}

	@Override
	public T lastOrDefault() {
		Iterator<T> it = containedIter.iterator();

		T lastElement = null;
		while (it.hasNext())
			lastElement = it.next();

		return lastElement;
	}

	@Override
	public T lastOrDefault(Predicate<T> predicate) {
		return Query.iterable(containedIter).where(predicate).lastOrDefault();
	}

	@Override
	public int count() {
		int c = 0;
		for (Iterator<T> it = containedIter.iterator(); it.hasNext(); it.next())
			c++;
		return c;
	}

	@Override
	public int count(Predicate<T> predicate) {
		int c = 0;
		for (T element : containedIter)
			if (predicate.evaluate(element))
				c++;

		return c;
	}

	@Override
	public Number max(NumberFunc<T> valFunc) {
		Number max = null;

		for (T item : containedIter) {
			Number c = valFunc.getValue(item);
			// this only works if the valFunc always returns the same type of
			// number (i.e. only Integer, only Double, etc.) or types which are
			// comparable with each other

			if (!(c instanceof Comparable)) {
				throw new RuntimeException("The numerical value for the list element '" + item.toString()
						+ "' does not implement the Comparable interface.");
			}

			if (max == null || ((Comparable) c).compareTo((Comparable) max) == 1) {
				max = c;
			}
		}

		return max;
	}

	@Override
	public Number min(NumberFunc<T> valFunc) {
		Number min = null;

		for (T item : containedIter) {
			Number c = valFunc.getValue(item);

			if (!(c instanceof Comparable)) {
				throw new RuntimeException("The numerical value for the list element '" + item.toString()
						+ "' does not implement the Comparable interface.");
			}

			// this only works if the valFunc always returns the same type of
			// number (i.e. only Integer, only Double, etc.)
			if (min == null || ((Comparable) c).compareTo((Comparable) min) == -1) {
				min = c;
			}
		}

		return min;
	}

	@Override
	public Number sum(NumberFunc<T> valFunc) {
		Number sum = null;

		for (T item : containedIter) {

			//
			Number c = valFunc.getValue(item);

			if (sum == null) {
				sum = c;
			} else {
				// this only works if the valFunc always returns the same type
				// of number (i.e. only Integer, only Double, etc.)
				if (c instanceof Integer) {
					sum = ((Integer) sum) + (Integer) c;
				} else if (c instanceof Double) {
					sum = ((Double) sum) + (Double) c;
				} else {
					// fallback
					sum = sum.doubleValue() + c.doubleValue();
				}
			}

		}

		return sum == null ? 0 : sum;
	}

	@Override
	public <TOut> Queriable<TOut> select(Selector<T, TOut> selector) {
		List<TOut> rList = new ArrayList<TOut>();
		for (T item : containedIter) {
			rList.add(selector.select(item));
		}
		return Query.list(rList);
	}

	@Override
	public <TOut> Queriable<TOut> selectMany(Selector<T, List<TOut>> selector) {
		List<TOut> rList = new ArrayList<TOut>();
		for (T item : containedIter) {
			rList.addAll(selector.select(item));
		}
		return Query.list(rList);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <TOut> Queriable<TOut> cast(Class<TOut> targetType) {
		List rList = new ArrayList();
		for (T o : containedIter) {
			rList.add(o);
		}
		return Query.list(rList);
	}

	@Override
	public Queriable<T> distinct() {
		List<T> rList = new ArrayList<T>();
		Queriable<T> ql = Query.list(rList);

		for (T o : containedIter) {

			final T fo = o;

			if (!ql.exists((x) -> x.equals(fo))) {
				rList.add(o);
			}
		}

		return Query.list(rList);
	}

	@Override
	public Queriable<T> take(int amount) {
		return Query.iterable(new LazyTakeIterable<T>(containedIter, amount));
	}

	@Override
	public Queriable<T> takeWhile(Predicate<T> condition) {
		return Query.iterable(new LazyTakeIterable<T>(containedIter, condition));
	}

	@Override
	public Queriable<T> skip(int amount) {
		return Query.iterable(new LazySkipIterable<T>(containedIter, amount));
	}

	public Queriable<T> skipWhile(Predicate<T> condition) {
		return Query.iterable(new LazySkipIterable<T>(containedIter, condition));
	}

	@Override
	public OrderedQueriable<T> orderBy(ItemFunc<T, Comparable> func) {
		return new OrderedQueriableImpl<T>(containedIter, func);
	}

	@Override
	public GroupedQueriable<T> group(GroupFunction<T> func) {
		HashMap<GroupKey, Group<T>> groups = new HashMap<GroupKey, Group<T>>();
		// put each element into a group
		for (T element : containedIter) {
			// get the group key for this element
			GroupKey gk = func.getKeyFor(element);

			Group<T> els = groups.get(gk);
			if (els == null) {
				// new group key
				els = new Group<T>(gk);
				groups.put(gk, els);
			}

			// add this element to the corresponding group
			els.add(element);
		}

		// return the groups
		GroupedList<T> gList = new GroupedList<T>();
		gList.addAll(groups.values());

		return new GroupedQueriableImpl<T>(gList);
	}

	@Override
	public GroupedQueriable<T> groupSingle(SingleKeyGroupFunction<T> func) {
		return group(x -> new GroupKey(func.getKeyElementFor(x)));
	}

	// --------------------------
	// Iterable<T> implementation
	// --------------------------

	@Override
	public Iterator<T> iterator() {
		return containedIter.iterator();
	}
}
