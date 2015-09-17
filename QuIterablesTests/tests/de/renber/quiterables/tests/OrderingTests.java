package de.renber.quiterables.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.renber.quiterables.Query;
import de.renber.quiterables.iterators.ListReverseIterable;

public class OrderingTests {

	@Test
	public void test_orderBy() {
		// generated using listofrandomnames.com
		List<String> testList = Arrays.asList(new String[] {"Lashaunda Saladino", 
				"Rosalyn Sterling", "Jessia Suire", "Lilly Fabian", "Long Grange",
				"Mahalia Luton", "Roberta Fowler", "Luciano Vann", "Camellia Risch",
				"Tanya Goodpaster"});
		
		List<String> orderedList = Arrays.asList(new String[] {"Camellia Risch", 
				"Jessia Suire", "Lashaunda Saladino", "Lilly Fabian", 
				"Long Grange", "Luciano Vann", "Mahalia Luton", "Roberta Fowler",
				"Rosalyn Sterling", "Tanya Goodpaster"});
				
		List<String> resultList = Query.list(testList).orderBy(x -> x).toList();
		
		assertEquals(orderedList, resultList);
	}
	
	@Test
	public void test_orderBy_int() {
		int[] numbers = new int[] {34, 76, 3, 12, 55, 23, 105, 67, 12};		
		int[] testArray = Query.array(numbers).orderBy(x -> x).toPrimitiveArray().intArray();
		int[] sortedArray = new int[] {3, 12, 12, 23, 34, 55, 67, 76, 105};		
		
		for (int i = 0; i < sortedArray.length; i++)
			if (testArray[i] != sortedArray[i])
				fail("Arrays do not match.");			
	}
	
	@Test
	public void test_orderByDescending_int() {
		int[] numbers = new int[] {34, 76, 3, 12, 55, 23, 105, 67, 12};		
		int[] testArray = Query.array(numbers).orderByDescending(x -> x).toPrimitiveArray().intArray();
		int[] sortedArray = new int[] {105, 76, 67, 55, 34, 23, 12, 12, 3};		
		
		for (int i = 0; i < sortedArray.length; i++)
			if (testArray[i] != sortedArray[i])
				fail("Arrays do not match.");			
	}
	
	@Test 
	public void test_thenBy() {
		List<TestPerson> testList = Arrays.asList(new TestPerson[] {
				new TestPerson("Gamma", "Omega"), new TestPerson("Beta", "Gamma"),
				new TestPerson("Delta", "Alpha"), new TestPerson("Delta", "Beta"),
				new TestPerson("Alpha", "Omega"), new TestPerson("Iota", "Theta")});
		
		List<TestPerson> orderedList = Arrays.asList(new TestPerson[] {
				new TestPerson("Delta", "Alpha"), new TestPerson("Delta", "Beta"),
				new TestPerson("Beta", "Gamma"), new TestPerson("Alpha", "Omega"),
				new TestPerson("Gamma", "Omega"), new TestPerson("Iota", "Theta")});
		
		List<TestPerson> resultList = Query.list(testList).orderBy(x -> x.lastName).thenBy(x -> x.firstName).toList();
		
		assertEquals(orderedList, resultList);
	}	
	
	@Test
	public void test_reverse() {
		int[] numbers = new int[] {34, 76, 3, 12, 55, 23, 105, 67, 12};		
		int[] testArray = Query.array(numbers).reverse().toPrimitiveArray().intArray();
		int[] reversedArray = new int[] {12, 67, 105, 23, 55, 12, 3, 76, 34};		
		
		for (int i = 0; i < reversedArray.length; i++)
			if (testArray[i] != reversedArray[i])
				fail("Arrays do not match.");			
	}
	
	@Test
	public void test_reverse_list() {
		// test the reverse optimization for List<T>
		
		Integer[] numbers = new Integer[] {34, 76, 3, 12, 55, 23, 105, 67, 12};
		List<Integer> sourceList = Arrays.asList(numbers); 
		Iterable<Integer> testList = Query.list(sourceList).reverse();
		Integer[] reversedArray = new Integer[] {12, 67, 105, 23, 55, 12, 3, 76, 34};		
		
		// check if the ListReverseIterable is used
		Object containedIter = null;
		try {
			Field f = testList.getClass().getDeclaredField("containedIter");
			f.setAccessible(true);
			containedIter = f.get(testList);
		} catch (Exception e) {
			fail();
		} 
		
		assertTrue(containedIter instanceof ListReverseIterable);
		
		// test if the list was reversed
		
		int idx = 0;
		for(Integer number: testList) {
			if (number != reversedArray[idx])
				fail("Arrays do not match.");
			idx++;
		}		
	}	
	
	class TestPerson {
		public String firstName;
		public String lastName;
		
		public TestPerson(String _firstName, String _lastName) {
			firstName = _firstName;
			lastName = _lastName;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof TestPerson) {
				return firstName.equals(((TestPerson)o).firstName) && lastName.equals(((TestPerson)o).lastName);
			} else
				return this == o;
		}
		
		@Override
		public int hashCode() {
			return firstName.hashCode() ^ lastName.hashCode();
		}
	}
}
