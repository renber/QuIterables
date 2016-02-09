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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import de.renber.quiterables.grouping.GroupedQueriable;
import de.renber.quiterables.grouping.GroupFunction;
import de.renber.quiterables.grouping.SingleKeyGroupFunction;

/**
 * An Iterable<T> which can be queried
 * @author berre
 */
public interface Queriable<T> extends Iterable<T> {
	    	
	/**
	 * Return if the enumeration is empty	 
	 */
	public boolean isEmpty();
	
	/**
	 * Returns this enumeration itself if it contains any elements
	 * or if it is empty an enumeration only containing the given defaultElement
	 */
	public Queriable<T> defaultIfEmpty(T defaultValue);
	
	/**
	 * Return the element at the given position or throw NoSuchElementException
	 * if no element at this index exits (i.e. the enumeration contains less than (index+1) elements)
	 */
	public T elementAt(int index) throws NoSuchElementException;
	
	/**
	 * Return the element at the given position or return null
	 * if no element at this index exits (i.e. the enumeration contains less than (index+1) elements)
	 */
	public T elementAtOrDefault(int index);
	
	/**
	 * Return the element at the given position or return the given default value
	 * if no element at this index exits (i.e. the enumeration contains less than (index+1) elements)
	 */
	public T elementAtOrDefault(int index, T defaultValue);	
	
	/**
	 * Checks whether this enumeration and the given iterable contain the same elements and the same position and have the same length
	 * (Equality is checked using Object.equals())	 
	 */
	public boolean sequenceEquals(Iterable<T> iterable);
	
	/**
	 * Checks whether this enumeration and the given iterable contain the same elements and the same position and have the same length
	 * (Equality is checked using the given custom Equivalence comparer)	 
	 */
	public boolean sequenceEquals(Iterable<T> iterable, Equivalence<T> equalityComparer);
	
	/**
     * Return if all elements in the enumeration fulfill the given predicate
     *     
     * @param predicate The predicate to evaluate
     * @return
     */
    public boolean all(Predicate<T> predicate);
    
    /**
     * Test if at least one element of the enumeration fulfills the condition     
     * @param predicate Condition
     * @return 
     */
    public boolean exists(Predicate<T> predicate); 
    
    /**
     * Returns if this enumeration contains the given element
     * (Equality is checked using Object.equals())
     */
    public boolean contains(T element);    
    
    /**
     * Returns if this enumeration contains the given element
     * (Equality is checked using the given Equivalence function)
     */
    public boolean contains(T element, Equivalence<T> equalityComparer);      
    
    /**
     * Return the first element of the enumeration or throw NoSuchElementException if the enumeration is empty             
     */
    public T first() throws NoSuchElementException;
    
    /**
     * Return the first element of the enumeration or null if the enumeration is empty             
     */
    public T firstOrDefault();
    
    /**
     * Return the first element of the enumeration or the passed defaultValue if the enumeration is empty             
     */
    public T firstOrDefault(T defaultValue);
    
    /**
     * Return the first element of the enumeration for which the predicate is true
     * or throw NoSuchElementException if no corresponding element exists     
     */
    public T first(Predicate<T> predicate) throws NoSuchElementException;
    
    /**
     * Return the first element in the enumeration for which the predicate is true or
     * null if no corresponding element exists     
     * @param predicate The predicate to evaluate
     * @return 
     */
    public T firstOrDefault(Predicate<T> predicate);
    
    /**
     * Return the first element in the enumeration for which the predicate is true or
     * the given default value if no corresponding element exists     
     * @param predicate The predicate to evaluate
     * @return 
     */
    public T firstOrDefault(Predicate<T> predicate, T defaultValue);    
    
    /**
     * Return the last element of the enumeration or throw NoSuchElementException if the enumeration is empty             
     */
    public T last() throws NoSuchElementException;
    
    /**
     * Return the last element of the enumeration or null if the list is empty             
     */
    public T lastOrDefault();
    
    /**
     * Return the last element of the enumeration for which the predicate is true
     * or throw NoSuchElementException if no corresponding element exists           
     */
    public T last(Predicate<T> predicate) throws NoSuchElementException;
    
    /**
     * Return the last element in the enumeration for which the predicate is true or
     * null if no corresponding element exists  
     * This is basically the same as .where(predicate).lastOrDefault()   
     * @param predicate The predicate to evaluate
     * @return 
     */
    public T lastOrDefault(Predicate<T> predicate);    
    
    /**
     * Return the last element in the enumeration for which the predicate is true or
     * the given default value if no corresponding element exists  
     * This is basically the same as .where(predicate).lastOrDefault()   
     * @param predicate The predicate to evaluate
     * @return 
     */
    public T lastOrDefault(Predicate<T> predicate, T defaultValue);       
    
    /**
     * If the iterable contains only one element return this element.
     * Otherwise throw NoSuchElementException (i.e. if list is empty or contains more than 1 element)     
     */
    public T single() throws NoSuchElementException;
    
    /**
     * If the iterable contains only one element which satisfies the condition return this element.
     * Otherwise throw NoSuchElementException (i.e. if no or multiple elements satisfies the condition)     
     */
    public T single(Predicate<T> predicate) throws NoSuchElementException;    
    
    /**
     * If the iterable contains only one element return this element.
     * Otherwise return null (i.e. if list is empty or contains more than 1 element)     
     */
    public T singleOrDefault();
    
    /**
     * If the iterable contains only one element which satisfies the condition return this element.
     * Otherwise return null (i.e. if no or multiple elements satisfies the condition)     
     */
    public T singleOrDefault(Predicate<T> predicate);        
    
    /**
     * Return the amount of elements in the enumeration if it is finite      
     */
    public int count();
    
    /**
     * Return the amount of elements in the (finite) enumeration which satisfy the given predicate  
     * This is basically the same as .where(predicate).count()    
     */
    public int count(Predicate<T> predicate);
    
    /**
     * Returns the maximum element of the enumeration
     * (Elements have to implement Comparable)     
     */
    public T max();
    
    /**
     * Return the maximum from the enumeration where the value for each item
     * is calculated by the given function     
     * @param valFunc The function to calculate the value which shall be maximized for a single list item, the return value
     * must always be of a single type (so for one list all return values for instance have to be either integers or doubles, but mixing is not possible)
     * @return the maximum value or null if the list is empty
     */
	public Number max(NumberFunc<T> valFunc);
    
	/**
     * Returns the minimum element of the enumeration
     * (Elements have to implement Comparable)     
     */
	public T min();
	
    /**
     * Return the minimum from the enumeration where the value for each item
     * is calculated by the given function     
     * @param list The list to retrieve the minimum from
     * @param valFunc The function to calculate the value which shall be minimized for a single list item, the return value
     * must always be of a single type (so for one list all return values for instance have to be either integers or doubles, but mixing is not possible)
     * @return the minimum value or null if the list is empty
     */	
	public Number min(NumberFunc<T> valFunc);
	
	/**
     * Return the average from the enumeration where the value for each item
     * is calculated by the given function     
     * @param valFunc The function to calculate the values which shall be averagem, the return value
     * must always be of a single type (so for one list all return values for instance have to be either integers or doubles, but mixing is not possible)
     * @return the average or null if the list is empty
     */	
	public Number average(NumberFunc<T> valFunc);
    
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
     * @return 
     */
	public Queriable<T> where(Predicate<T> predicate);	
	
    /**
     * Transforms each element of the enumeration using a selector     
     * @param selector The transformation function
     * @return
     */	
	public <TOut> Queriable<TOut> select(Selector<T, TOut> selector);
      
    /**
     * Transform each element of the enumeration into another enumeration and combine all results to a single list          
     * @param selector The transformation function
     * @return
     */	
	public <T2> Queriable<T2> selectMany(Selector<T, Iterable<T2>> selector);
        
    /**
     * Cast each element of the enumeration to the given type and return an enumeration of this type     
     * (This is an unchecked cast, so if any element in the enumeration cannot be cast to the given type
     * an exception will be thrown)
     * @param targetType
     */	
	public <TOut> Queriable<TOut> cast(Class<TOut> targetType);
	
	 /**
     * Return the elements of the enumeration which are of the requested type
     * or can be cast to it     
     */
	public <TOut> Queriable<TOut> ofType(Class<TOut> targetType);
	
	/**
	 * Return an enumeration which contains all elements of the current
	 * enumeration and all elements of the enumeration given as argument	 
	 */
	public Queriable<T> concat(Iterable<T> toConcat);
	
	/**
	 * Return an enumeration which contains all elements of the current
	 * enumeration and all elements of the enumeration given as argument
	 * without duplicates
	 * (Equality is checked using the equals(...) method)  
	 */
	public Queriable<T> union(Iterable<T> toConcat);	
	
	/**
	 * Return an enumeration which contains all elements of the current
	 * enumeration and all elements of the enumeration given as argument
	 * without duplicates	 
	 * (Equality is checked using the given equalityComparer)  
	 */
	public Queriable<T> union(Iterable<T> toConcat, Equivalence<T> equalityComparer);	
	
	/**
	 * Returns an iterable which contains the elements of this enumeration
	 * which also exist in the given Iterable
	 * (Equality is checked using the equals(...) method) 
	 */
	public Queriable<T> intersect(Iterable<T> intersectWith);
	
	/**
	 * Returns an iterable which contains the elements of this enumeration
	 * which also exist in the given Iterable 
	 * (Equality is checked using the given equalityComparer)
	 */
	public Queriable<T> intersect(Iterable<T> intersectWith, Equivalence<T> equalityComparer);
	
	/**
	 * Returns an iterable which contains only the elements of this enumeration
	 * which do not exist in the given Iterable
	 * (Equality is checked using the equals(...) method) 
	 */
	public Queriable<T> except(Iterable<T> elementsToSubtract);
	
	/**
	 * Returns an iterable which contains only the elements of this enumeration
	 * which do not exist in the given Iterable
	 * (Equality is checked using the given equalityComparer)
	 */
	public Queriable<T> except(Iterable<T> elementsToSubtract, Equivalence<T> equalityComparer);	
	
    /**
     * Returns an enumeration where each item of the enumeration appears exactly once
     * (Equality is checked using the equals(...) method)     
     */	
	public Queriable<T> distinct();
	
	/**
     * Returns an enumeration where each item of the enumeration appears exactly once
     * (Equality is checked using the given equalityComparer)     
     */	
	public Queriable<T> distinct(Equivalence<T> equalityComparer);	
	
	/**
	 * Take the given amount of elements from the enumeration or
	 * take elements until the enumeration ends
	 * @param amount max. amount of elements to take	 
	 */
	public Queriable<T> take(int amount);
	
	/**
	 * Take elements from the enumeration as long as they satisfy the condition
	 * or until the enumeration ends
	 * @param condition
	 * @return
	 */
	public Queriable<T> takeWhile(Predicate<T> condition);	
	
	/**
	 * Skip the given amount of elements in the list and
	 * return a Queriable which starts at the (amount+1)th element or is empty if
	 * the enumeration did not contain more than amount elements
	 * @param amount	 
	 */
	public Queriable<T> skip(int amount);
	
	/**
	 * Return a queriable which starts at the first element of the enumeration
	 * which does not satisfy the condition, if no such element exists the returned Queriable
	 * is empty
	 * @param condition skip condition	 
	 */
	public Queriable<T> skipWhile(Predicate<T> condition);
	
	/**
	 * Groups the elements of the enumeration according to the given grouping function
	 */
	public GroupedQueriable<T> group(GroupFunction<T> func);	
	
	 /**
	  * Groups the elements of the enumeration according to the given grouping function
	  * (Convenience function for grouping with only one group key element)
     */
	public GroupedQueriable<T> groupSingle(SingleKeyGroupFunction<T> func);	
	
	/**
	 * Order the elements of this enumeration according	the values
	 * returned by the order function
	 */
	public OrderedQueriable<T> orderBy(ItemFunc<T, Comparable> func);
	
	/**
	 * Order the elements of this enumeration according	the values
	 * returned by the order function in descending order
	 */
	public OrderedQueriable<T> orderByDescending(ItemFunc<T, Comparable> func);
	
	/**
	 * Reverse the order of elements of this enumeration	 
	 */
	public Queriable<T> reverse();
	
	/**
	 * Return a list which contains all elements of this enumeration
	 * (This will evaluate the whole enumeration, if it is infinite this will block forever.)	 	
	 */
	public List<T> toList();
	
	/**
	 * Return a list which contains all elements of this enumeration but 
	 * no duplicates
	 * Returns a set which contains the elements which are also returned by distinct() 
	 */
	public Set<T> toSet();
	
	/**
	 * Return an array which contains all elements of this enumeration
	 * (This will evaluate the whole enumeration, if it is infinite this will block forever.)
	 * @param classType The class type of T	 	
	 */
	public T[] toArray(Class<T> classType);
	
	/**
	 * Return an object which allows to transform this Iterable to a primitive-type array	 	 
	 */
	public PrimitiveArrayTransformer<T> toPrimitiveArray();
}
