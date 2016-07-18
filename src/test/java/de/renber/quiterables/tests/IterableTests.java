package de.renber.quiterables.tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import de.renber.quiterables.QuIterables;
import de.renber.quiterables.iterators.EmptyIterable;
import de.renber.quiterables.iterators.RangeIterable;

/**
 * Tests for the built-in Iterable convenience classes
 * @author René Bergelt
 */
public class IterableTests {

	@Test
	public void test_EmptyIterable() {
		EmptyIterable iterable = EmptyIterable.getInstance();
		Iterator it = iterable.iterator();
		
		assertFalse(it.hasNext());		
		assertTrue(QuIterables.query(iterable).isEmpty());
		
		assertTrue(QuIterables.empty().isEmpty());
	}
	
	@Test
	public void test_RangeIterable() {
		RangeIterable iterable = new RangeIterable(5, 10);
		Iterator it = iterable.iterator();
		
		int count = 0;
		while (it.hasNext()) {			
			count++;
			it.next();
		}		
		assertEquals(6, count);
		
		assertEquals(45, QuIterables.range(5, 10).sum().intValue());
	}
	
}
