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

import java.util.List;

import de.renber.quiterables.grouping.GroupFunction;
import de.renber.quiterables.grouping.GroupedQueriable;
import de.renber.quiterables.grouping.SingleKeyGroupFunction;

/**
 * An Iterable<T> which can be queried
 * @author berre
 */
public interface Queriable<T> extends Iterable<T> {
	    		
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
     * Return the first element of the enumeration or null if the list is empty             
     */
    public T firstOrDefault();
    
    /**
     * Return the first element in the enumeration for which the predicate is true or
     * null if no corresponding element exists     
     * @param predicate The predicate to evaluate
     * @return 
     */
    public T firstOrDefault(Predicate<T> predicate);
    
    /**
     * Return the last element of the enumeration or null if the list is empty             
     */
    public T lastOrDefault();
    
    /**
     * Return the last element in the enumeration for which the predicate is true or
     * null if no corresponding element exists  
     * This is basically the same as .where(predicate).lastOrDefault()   
     * @param predicate The predicate to evaluate
     * @return 
     */
    public T lastOrDefault(Predicate<T> predicate);    
    
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
     * Return the maximum from the enumeration where the value for each item
     * is calculated by the given function     
     * @param valFunc The function to calculate the value which shall be maximized for a single list item, the return value
     * must always be of a single type (so for one list all return values for instance have to be either integers or doubles, but mixing is not possible)
     * @return the maximum value or null if the list is empty
     */
	public Number max(NumberFunc<T> valFunc);
    
    /**
     * Return the minimum from the enumeration where the value for each item
     * is calculated by the given function     
     * @param list The list to retrieve the minimum from
     * @param valFunc The function to calculate the value which shall be minimized for a single list item, the return value
     * must always be of a single type (so for one list all return values for instance have to either integers or doubles, but mixing is not possible)
     * @return the minimum value or null if the list is empty
     */	
	public Number min(NumberFunc<T> valFunc);
    
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
	public <T2> Queriable<T2> selectMany(Selector<T, List<T2>> selector);
        
    /**
     * Cast each element of the enumeration to the given type and return an enumeration of this type     
     * @param targetType
     */	
	public <TOut> Queriable<TOut> cast(Class<TOut> targetType);
	
    /**
     * Returns an enumeration where each item of the enumeration appears exactly once
     * (Equality is checked using the equals(...) method)     
     * @param targetType
     */	
	public Queriable<T> distinct();	
	
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
	 * return by the order function
	 */
	public OrderedQueriable<T> orderBy(ItemFunc<T, Comparable> func);
	
	/**
	 * Return a list which contains all elements of this enumeration
	 * (This will evaluate the whole enumeration, if it is infinite this will block forever.)	 	
	 */
	public List<T> toList();
	
	/**
	 * Return an array which contains all elements of this enumeration
	 * (This will evaluate the whole enumeration, if it is infinite this will block forever.)
	 * @param classType The class type of T	 	
	 */
	public T[] toArray(Class<T> classType);
	
	/**
	 * Return an object which allows to transform this iterable to an primitive-type array	 	 
	 */
	public PrimitiveArrayTransformer<T> toPrimitiveArray();
}
