package de.renebergelt.quiterables.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.renebergelt.quiterables.Queriable;
import de.renebergelt.quiterables.Query;

public class LazyEvaluationTest {

	List<LazyObject> sampleData;

	@Before
	public void createSampleData() {
		// generate test data for each test case
		// will be generated anew for each test
		sampleData = new ArrayList<>();

		sampleData.add(new LazyObject("Object One", 1));
		sampleData.add(new LazyObject("Object Two", 2));
		sampleData.add(new LazyObject("Object Three", 3));
		sampleData.add(new LazyObject("Object Four", 4));
	}
	
	@Test
	public void test_lazy_where() {
		// test if the evaluation is actually lazy
		// i.e. result data is generated on the go
		Iterable<LazyObject> list = Query.list(sampleData).where(x -> x.getText().endsWith("Three"));
		
		// no object should have been touched by now
		assertFalse(sampleData.get(0).wasQueried);
		assertFalse(sampleData.get(1).wasQueried);
		assertFalse(sampleData.get(2).wasQueried);
		assertFalse(sampleData.get(3).wasQueried);
		
		// get the first result which is actually the third element
		Iterator<LazyObject> it = list.iterator();
		LazyObject result = it.next();
		assertNotNull(result);
		assertTrue(sampleData.get(0).wasQueried);
		assertTrue(sampleData.get(1).wasQueried);
		assertTrue(sampleData.get(2).wasQueried);
		assertFalse(sampleData.get(3).wasQueried);
		
		// check if this was the last element
		assertFalse(it.hasNext());
		// by now the whole list has been traversed
		assertTrue(sampleData.get(3).wasQueried);
	}
	
	@Test
	public void test_lazy_where_infinite() {
		Queriable<Integer> allNumbers = Query.iterable(new GenericIterable<Integer>(new NaturalNumbersIterator()));
		
		// get the first ten equal numbers
		// as the where should be lazy evaluated, it should not block forever
		int cnt = 0;		
		Iterable<Integer> it = allNumbers.where(x -> x % 2 == 0);
		
		for(int i: it) {
			assertTrue(i % 2 == 0);
			
			cnt++;
			if (cnt >= 10)
				break;
		}				
	}
	
	@Test
	public void test_lazy_takeWhile() {		
		Queriable<LazyObject> q = Query.list(sampleData).takeWhile(x -> x.getValue() < 3);
		
		Iterator<LazyObject> it = q.iterator();
		
		assertTrue(it.hasNext());
		LazyObject o = it.next();
		assertEquals("Object One", o.text);
		assertTrue(o.wasQueried);
		assertFalse(sampleData.get(1).wasQueried);
		assertFalse(sampleData.get(2).wasQueried);
		assertFalse(sampleData.get(3).wasQueried);
		
		assertTrue(it.hasNext());
		o = it.next();
		assertEquals("Object Two", o.text);
		assertTrue(o.wasQueried);		
		assertFalse(sampleData.get(2).wasQueried);
		assertFalse(sampleData.get(3).wasQueried);
		
		assertFalse(it.hasNext());		
	}	
	
	@Test
	public void test_lazy_skip_infinite() {
		Queriable<Integer> allNumbers = Query.iterable(new GenericIterable<Integer>(new NaturalNumbersIterator()));
		
		assertEquals(new Integer(10), allNumbers.skip(10).firstOrDefault());
	}
	
	@Test
	public void test_lazy_skipWhile_infinite() {
		Queriable<Integer> allNumbers = Query.iterable(new GenericIterable<Integer>(new NaturalNumbersIterator()));
		
		assertEquals(new Integer(20), allNumbers.skipWhile(x -> x < 20).firstOrDefault());
	}	
	
	class GenericIterable<T> implements Iterable<T>
	{
		Iterator<T> iterator;
		
		public GenericIterable(Iterator<T> _iterator) {
			iterator = _iterator;
		}

		@Override
		public Iterator<T> iterator() {
			return iterator;
		}
	}
	
	/**
	 * Iterator which returns natural numbers and is therefore infinite
	 * (will rollover at some point but will never stop outputting elements)	
	 * @author René Bergelt	 
	 */
	class NaturalNumbersIterator implements Iterator<Integer> {

		int current = 0;		
		
		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public Integer next() {
			return current++;
		}
		
	}
	
}
