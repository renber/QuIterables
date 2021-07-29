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
package de.renebergelt.quiterables;

import java.util.*;
import java.util.function.Function;

import de.renebergelt.quiterables.grouping.GroupedQueriable;
import de.renebergelt.quiterables.grouping.GroupFunction;
import de.renebergelt.quiterables.grouping.SingleKeyGroupFunction;

/**
 * An Iterable which can be queried
 * @param <T> Type of elements
 * @author René Bergelt
 */
public interface Queriable<T> extends Iterable<T> {
	    	
	/**
	 * Return if the enumeration is empty
	 * @return True if the enumeration is empty
	 */
	public boolean isEmpty();
	
	/**
	 * Returns this enumeration itself if it contains any elements
	 * or if it is empty an enumeration only containing the given defaultElement
	 * @param defaultValue The element to return if the enumeration is empty
	 * @return this enumeration or an enumeration containing defaultValue if this is empty
	 */
	public Queriable<T> defaultIfEmpty(T defaultValue);
	
	/**
	 * Return the element at the given position or throw NoSuchElementException
	 * if no element at this index exits (i.e. the enumeration contains less than (index+1) elements)
	 * @param index Element index
	 * @return the element at the given index
	 */
	public T elementAt(int index) throws NoSuchElementException;
	
	/**
	 * Return the element at the given position or return null
	 * if no element at this index exits (i.e. the enumeration contains less than (index+1) elements)
	 * @param index Element index
	 * @return the element at the given index or null if none exists
	 */
	public T elementAtOrDefault(int index);
	
	/**
	 * Return the element at the given position or return the given default value
	 * if no element at this index exits (i.e. the enumeration contains less than (index+1) elements)
	 * @param index Index of the elemt to return
	 * @param defaultValue The value to return when no element at the given index exists
	 * @return The element at the index or defaultValue
	 */
	public T elementAtOrDefault(int index, T defaultValue);	
	
	/**
	 * Checks whether this enumeration and the given iterable contain the same elements and the same position and have the same length
	 * (Equality is checked using Object.equals())
	 * @param iterable The iterable to compare with
	 * @return whether this and the given Iterable are considered equal
	 */
	public boolean sequenceEquals(Iterable<T> iterable);
	
	/**
	 * Checks whether this enumeration and the given iterable contain the same elements and the same position and have the same length
	 * (Equality is checked using the given custom Equivalence comparer)
	 * @param iterable The iterable to compare with
	 * @param equalityComparer The comparison function to use
	 * @return whether this and the given Iterable are considered equal
	 */
	public boolean sequenceEquals(Iterable<T> iterable, Equivalence<T> equalityComparer);
	
	/**
     * Return if all elements in the enumeration fulfill the given predicate
     *     
     * @param predicate The predicate to evaluate
	 * @return whether all elements satisfy the given predicate
     */
    public boolean all(Predicate<T> predicate);
    
    /**
     * Test if at least one element of the enumeration fulfills the condition     
     * @param predicate Condition
	 * @return whether at least one element satisfies the given predicate
     */
    public boolean exists(Predicate<T> predicate); 
    
    /**
     * Returns if this enumeration contains the given element
     * (Equality is checked using Object.equals())
	 * @param element The element to check for
	 * @return whether the given element is contained in this sequence
     */
    public boolean contains(T element);    
    
    /**
     * Returns if this enumeration contains the given element
     * (Equality is checked using the given Equivalence function)
	 * @param element The element to check for
	 * @param equalityComparer The function to decide equality
	 * @return whether the given element is contained in this sequence
     */
    public boolean contains(T element, Equivalence<T> equalityComparer);      
    
    /**
     * Return the first element of the enumeration or throw NoSuchElementException if the enumeration is empty
	 * @return The first element
	 * @throws NoSuchElementException Thrown if there is no element present
     */
    public T first() throws NoSuchElementException;
    
    /**
     * Return the first element of the enumeration or null if the enumeration is empty
	 * @return The first element or null
     */
    public T firstOrDefault();
    
    /**
     * Return the first element of the enumeration or the passed defaultValue if the enumeration is empty
	 * @param defaultValue The deafult value to use
	 * @return The first element or the given default value
     */
    public T firstOrDefault(T defaultValue);
    
    /**
     * Return the first element of the enumeration for which the predicate is true
     * or throw NoSuchElementException if no corresponding element exists
	 * @param predicate The predicate to evaluate
	 * @return The first element which satisfies the predicate
	 * @throws NoSuchElementException Thrown if there is no element present which satisfies the predicate
     */
    public T first(Predicate<T> predicate) throws NoSuchElementException;
    
    /**
     * Return the first element in the enumeration for which the predicate is true or
     * null if no corresponding element exists     
     * @param predicate The predicate to evaluate
	 * @return The first element which satisfies the predicate or null
     */
    public T firstOrDefault(Predicate<T> predicate);
    
    /**
     * Return the first element in the enumeration for which the predicate is true or
     * the given default value if no corresponding element exists     
     * @param predicate The predicate to evaluate
	 * @param defaultValue The deafult value to use
	 * @return The first element which satisfies the predicate or defaultValue
     */
    public T firstOrDefault(Predicate<T> predicate, T defaultValue);    
    
    /**
     * Return the last element of the enumeration or throw NoSuchElementException if the enumeration is empty
	 * @return the last element of this sequence
	 * @throws NoSuchElementException Thrown if there is no element present which satisfies the predicate
     */
    public T last() throws NoSuchElementException;
    
    /**
     * Return the last element of the enumeration or null if the list is empty
	 * @return the last element of this sequence or null
     */
    public T lastOrDefault();
    
    /**
     * Return the last element of the enumeration for which the predicate is true
     * or throw NoSuchElementException if no corresponding element exists
	 * @param predicate The predicate to evaluate
	 * @return The last element which satisfies the predicate or defaultValue
	 * @throws NoSuchElementException Thrown if there is no element present which satisfies the predicate
     */
    public T last(Predicate<T> predicate) throws NoSuchElementException;
    
    /**
     * Return the last element in the enumeration for which the predicate is true or
     * null if no corresponding element exists  
     * This is basically the same as .where(predicate).lastOrDefault()   
     * @param predicate The predicate to evaluate
	 * @return The last element which satisfies the predicate or null
     */
    public T lastOrDefault(Predicate<T> predicate);    
    
    /**
     * Return the last element in the enumeration for which the predicate is true or
     * the given default value if no corresponding element exists  
     * This is basically the same as .where(predicate).lastOrDefault()   
     * @param predicate The predicate to evaluate
	 * @param defaultValue The deafult value to use
	 * @return The last element which satisfies the predicate or defaultValue
     */
    public T lastOrDefault(Predicate<T> predicate, T defaultValue);       
    
    /**
     * If the iterable contains only one element return this element.
     * Otherwise throw NoSuchElementException (i.e. if list is empty or contains more than 1 element)
	 * @return the only element in this sequence
	 * @throws NoSuchElementException Thrown if there is not exactly one element present
     */
    public T single() throws NoSuchElementException;
    
    /**
     * If the iterable contains only one element which satisfies the condition return this element.
     * Otherwise throw NoSuchElementException (i.e. if no or multiple elements satisfies the condition)
	 * @param predicate The predicate to evaluate
	 * @return the only element in this sequence which fulfills the predicate
	 * @throws NoSuchElementException Thrown if there is not exactly one element which satisfies the predicate
     */
    public T single(Predicate<T> predicate) throws NoSuchElementException;    
    
    /**
     * If the iterable contains only one element return this element.
     * Otherwise return null (i.e. if list is empty or contains more than 1 element)
	 * @return the only element in this sequence or null
     */
    public T singleOrDefault();
    
    /**
     * If the iterable contains only one element which satisfies the condition return this element.
     * Otherwise return null (i.e. if no or multiple elements satisfies the condition)
	 * @param predicate The predicate to evaluate
	 * @return the only element in this sequence which fulfills the predicate or null
     */
    public T singleOrDefault(Predicate<T> predicate);        
    
    /**
     * Return the amount of elements in the enumeration if it is finite
	 * @return Count of elements in this sequence
     */
    public int count();
    
    /**
     * Return the amount of elements in the (finite) enumeration which satisfy the given predicate  
     * This is basically the same as .where(predicate).count()
	 * @param predicate The predicate to evaluate
	 * @return Count of elements in this sequence
     */
    public int count(Predicate<T> predicate);
    
    /**
     * Returns the maximum element of the enumeration
     * (Elements have to implement Comparable)     
     * @throws IllegalStateException if there are no elements
	 * @return the maximum element
     */
    public T max();
    
    /**
     * Return the maximum from the enumeration where the value for each item
     * is calculated by the given function     
     * @param valFunc The function to calculate the value which shall be maximized for a single list item, the return value
     * must always be of a single type (so for one list all return values for instance have to be either integers or doubles, but mixing is not possible)
     * @return the maximum value or null if the list is empty
     * @throws IllegalStateException if there are no elements
     */
	public Number max(NumberFunc<T> valFunc);
    
	/**
     * Returns the minimum element of the enumeration
     * (Elements have to implement Comparable)
	 * @return the minimum element
     * @throws IllegalStateException if there are no elements
     */
	public T min();
	
    /**
     * Return the minimum from the enumeration where the value for each item
     * is calculated by the given function*
     * @param valFunc The function to calculate the value which shall be minimized for a single list item, the return value
     * must always be of a single type (so for one list all return values for instance have to be either integers or doubles, but mixing is not possible)
     * @return the minimum value
     * @throws IllegalStateException if there are no elements
     */	
	public Number min(NumberFunc<T> valFunc);
	
	/**
     * Return the average from the enumeration
     * (Elements will be cast to java.lang.Number) 
     * @return the average value
     * @throws IllegalStateException if there are no elements
     */	
	public Number average();
	
	/**
     * Return the average from the enumeration where the value for each item
     * is calculated by the given function     
     * @param valFunc The function to calculate the values which shall be averagem, the return value
     * must always be of a single type (so for one list all return values for instance have to be either integers or doubles, but mixing is not possible)
     * @return the average value
     * @throws IllegalStateException if there are no elements
     */	
	public Number average(NumberFunc<T> valFunc);
    
	/**
     * Return the sum from the enumeration where the value for each item
	 * (Elements will be cast to java.lang.Number) 
     * @return the sum or 0 (integer zero) if the list is empty
     */	
	public Number sum();
	
    /**
     * Return the sum from the enumeration where the value for each item
     * is calculated by the given function     
     * @param valFunc The function to calculate the values which shall be summed up for a single list item, the return value
     * must always be of a single type (so for one list all return values for instance have to either integers or doubles, but mixing is not possible)
     * @return the sum or 0 (integer zero) if the list is empty
     */	
	public Number sum(NumberFunc<T> valFunc);
    
	/**
     * Return all elements from the enumeration for which the predicate holds true or
     * return an empty list if no such elements exist     
     * @param predicate The predicate to evaluate
	 * @return A queriable sequence of the elements which satisfy the given predicate
     */
	public Queriable<T> where(Predicate<T> predicate);	
	
    /**
     * Transforms each element of the enumeration using a selector     
     * @param selector The transformation function
	 * @param <TOut> The target type of the selection
	 * @return A queriable sequence of the transformed elements
     */	
	public <TOut> Queriable<TOut> select(Selector<T, TOut> selector);
      
    /**
     * Transform each element of the enumeration into another enumeration and combine all results to a single list          
     * @param selector The transformation function
	 * @param <TOut> The target type of the selection
	 * @return A queriable sequence of the transformed elements
     */	
	public <TOut> Queriable<TOut> selectMany(Selector<T, Iterable<TOut>> selector);
        
    /**
     * Cast each element of the enumeration to the given type and return an enumeration of this type     
     * (This is an unchecked cast, so if any element in the enumeration cannot be cast to the given type
     * an exception will be thrown)
     * @param targetType The type to cast to
	 * @param <TOut> The target type of the selection (equals targetType)
	 * @return A queriable sequence of type-cast elements
     */	
	public <TOut> Queriable<TOut> cast(Class<TOut> targetType);
	
	 /**
     * Return the elements of the enumeration which are of the requested type
     * or can be cast to it
	 * @param targetType The type to filter
	 * @param <TOut> The target type of the filtering (equals targetType)
     * @return A queriable sequence of elements of the given type
     */
	public <TOut> Queriable<TOut> ofType(Class<TOut> targetType);
	
	/**
	 * Return an enumeration which contains all elements of the current
	 * enumeration and all elements of the enumeration given as argument
	 * @param toConcat Elements to concatenate
	 * @return A queriable sequence which contains the elements of this sequence and of the elements from toConcat
	 */
	public Queriable<T> concat(Iterable<T> toConcat);
	
	/**
	 * Return an enumeration which contains all elements of the current
	 * enumeration and all elements of the enumeration given as argument
	 * without duplicates
	 * (Equality is checked using the equals(...) method)
	 * @param toUnite Elements to unite with
	 * @return A queriable sequence which contains the union of this sequence and the given Iterable
	 */
	public Queriable<T> union(Iterable<T> toUnite);
	
	/**
	 * Return an enumeration which contains all elements of the current
	 * enumeration and all elements of the enumeration given as argument
	 * without duplicates	 
	 * (Equality is checked using the given equalityComparer)
	 * @param toUnite Elements to unite with
	 * @param equalityComparer Comparer to determine if two elements are equal
	 * @return A queriable sequence which contains the union of this sequence and the given Iterable
	 */
	public Queriable<T> union(Iterable<T> toUnite, Equivalence<T> equalityComparer);
	
	/**
	 * Returns an iterable which contains the elements of this enumeration
	 * which also exist in the given Iterable
	 * (Equality is checked using the equals(...) method)
	 * @param intersectWith Elements to intersect with
	 * @return A queriable sequence which contains the intersection of this sequence and the given Iterable
	 */
	public Queriable<T> intersect(Iterable<T> intersectWith);
	
	/**
	 * Returns an iterable which contains the elements of this enumeration
	 * which also exist in the given Iterable 
	 * (Equality is checked using the given equalityComparer)
	 * @param intersectWith Elements to intersect with
	 * @param equalityComparer Comparer to determine if two elements are equal
	 * @return A queriable sequence which contains the union of this sequence and the given Iterable
	 */
	public Queriable<T> intersect(Iterable<T> intersectWith, Equivalence<T> equalityComparer);
	
	/**
	 * Returns an iterable which contains only the elements of this enumeration
	 * which do not exist in the given Iterable
	 * (Equality is checked using the equals(...) method)
	 * @param elementsToSubtract Elements to exclude
	 * @return A queriable sequence which contains only elements of this sequence which are not containedin the given Iterable
	 */
	public Queriable<T> except(Iterable<T> elementsToSubtract);
	
	/**
	 * Returns an iterable which contains only the elements of this enumeration
	 * which do not exist in the given Iterable
	 * (Equality is checked using the given equalityComparer)
	 * @param elementsToSubtract Elements to exclude
	 * @param equalityComparer Comparer to determine if two elements are equal
	 * @return A queriable sequence which contains only elements of this sequence which are not containedin the given Iterable
	 */
	public Queriable<T> except(Iterable<T> elementsToSubtract, Equivalence<T> equalityComparer);	
	
    /**
     * Returns an enumeration where each item of the enumeration appears exactly once
     * (Equality is checked using the equals(...) method)
	 * @return all elements of this sequence without duplicates
     */	
	public Queriable<T> distinct();
	
	/**
     * Returns an enumeration where each item of the enumeration appears exactly once
     * (Equality is checked using the given equalityComparer)
	 * @param equalityComparer Comparer to determine if two elements are equal
	 * @return all elements of this sequence without duplicates
     */	
	public Queriable<T> distinct(Equivalence<T> equalityComparer);	
	
	/**
	 * Take the given amount of elements from the enumeration or
	 * take elements until the enumeration ends
	 * @param amount max. amount of elements to take
	 * @return The first 'amount' of elements of this sequence (or less if fewer elements are present)
	 */
	public Queriable<T> take(int amount);
	
	/**
	 * Take elements from the enumeration as long as they satisfy the condition
	 * or until the enumeration ends
	 * @param condition Condition to take an element
	 * @return The taken elements
	 */
	public Queriable<T> takeWhile(Predicate<T> condition);	
	
	/**
	 * Skip the given amount of elements in the list and
	 * return a Queriable which starts at the (amount+1)th element or is empty if
	 * the enumeration did not contain more than amount elements
	 * @param amount Number of elements to skip
	 * @return The elements of this sequence after the first 'amount' elements
	 */
	public Queriable<T> skip(int amount);
	
	/**
	 * Return a queriable which starts at the first element of the enumeration
	 * which does not satisfy the condition, if no such element exists the returned Queriable
	 * is empty
	 * @param condition skip condition
	 * @return The elements after the skipped elements
	 */
	public Queriable<T> skipWhile(Predicate<T> condition);
	
	/**
	 * Groups the elements of the enumeration according to the given grouping function
	 * @param func Function to group elements by
	 * @return The grouped elements
	 */
	public GroupedQueriable<T> group(GroupFunction<T> func);	
	
	 /**
	  * Groups the elements of the enumeration according to the given grouping function
	  * (Convenience function for grouping with only one group key element)
	  * @param func Function to group elements by
	  * @return The grouped elements
      */
	public GroupedQueriable<T> groupSingle(SingleKeyGroupFunction<T> func);	
	
	/**
	 * Order the elements of this enumeration according to	the values
	 * returned by the order function
	 * @param func Function to retrieve the value to compare from an element
	 * @return The ordered elements
	 */
	public OrderedQueriable<T> orderBy(ItemFunc<T, Comparable> func);
	
	/**
	 * Order the elements of this enumeration according to the values
	 * returned by the value function function using the given comparator
	 * @param valueFunc Function to retrieve the value to compare from an element
	 * @param comparator Comparator to compare values
	 * @param <TComparable> The type to compare with
	 * @return The ordered elements
	 */
	public <TComparable> OrderedQueriable<T> orderBy(ItemFunc<T, TComparable> valueFunc, Comparator<TComparable> comparator);
	
	/**
	 * Order the elements of this enumeration according	the values
	 * returned by the order function in descending order
	 * @param func Function to retrieve the value to compare from an element
	 * @return The ordered elements
	 */
	public OrderedQueriable<T> orderByDescending(ItemFunc<T, Comparable> func);
	
	/**
	 * Order the elements of this enumeration according to the values
	 * returned by the value function function using the given comparator in descending order
	 * @param valueFunc Function to retrieve the value to compare from an element
	 * @param comparator Comparator to compare values
	 * @param <TComparable> The type to compare with
	 * @return The ordered elements
	 */
	public <TComparable> OrderedQueriable<T> orderByDescending(ItemFunc<T, TComparable> valueFunc, Comparator<TComparable> comparator);
	
	/**
	 * Reverse the order of elements of this enumeration
	 * @return A reversed sequence
	 */
	public Queriable<T> reverse();
	
	/**
	 * Return a list which contains all elements of this enumeration
	 * (This will evaluate the whole enumeration, if it is infinite this will block forever.)
	 * @return List of the elements ot this sequence
	 */
	public List<T> toList();
	
	/**
	 * Return a list which contains all elements of this enumeration but 
	 * no duplicates
	 * Returns a set which contains the elements which are also returned by distinct()
	 * @return Set of the elements ot this sequence
	 */
	public Set<T> toSet();

	/**
	 * COnstructs a map from the elements of this Queriable
	 * @param keyFunc The function to retrieve the map key from an element
	 * @param valueFunc The function to retrieve the map value from an element
	 * @param <TKey> The type of the map keys
	 * @param <TValue> The type of the map values
	 * @return The map
	 */
	public <TKey, TValue> Map<TKey, TValue> toMap(Function<T, TKey> keyFunc, Function<T, TValue> valueFunc);

	/**
	 * Constructs a map from the elements of this Queriable where each element is
	 * assigned a key
	 * @param keyFunc The function to retrieve the map key from an element*
	 * @param <TKey> The type of the map keys
	 * @return The map
	 */
	public <TKey> Map<TKey, T> toMap(Function<T, TKey> keyFunc);

	/**
	 * Return an array which contains all elements of this enumeration
	 * (This will evaluate the whole enumeration, if it is infinite this will block forever.)
	 * @param classType The class type of T
	 * @return An array of the elements ot this sequence
	 */
	public T[] toArray(Class<T> classType);
	
	/**
	 * Return an object which allows to transform this Iterable to a primitive-type array
	 * @return An instance of PrimitiveArrayTransformer
	 */
	public PrimitiveArrayTransformer<T> toPrimitiveArray();
}
