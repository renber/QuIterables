package de.renber.quiterables.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.renber.quiterables.Query;

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
