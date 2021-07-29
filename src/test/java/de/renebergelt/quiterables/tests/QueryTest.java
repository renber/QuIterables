package de.renebergelt.quiterables.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import de.renebergelt.quiterables.Equivalence;
import de.renebergelt.quiterables.QuIterables;
import de.renebergelt.quiterables.Queriable;
import de.renebergelt.quiterables.Query;

/**
 * Unit tests for the PoorMansLinq/ListMatcher class Using Java 8 lamdba
 * expressions
 * 
 * @author René Bergelt
 */
public class QueryTest {

	List<TestPojo> sampleData;

	@Before
	public void createSampleData() {
		// generate test data for each test case
		// will be generated anew for each test
		sampleData = new ArrayList<>();

		sampleData.add(new TestPojo("Item One", 1, "Child 1", "Child 2"));
		sampleData.add(new TestPojo("Item Two", 2, "Child 1"));
		sampleData.add(new TestPojo("Item Three", 3));
		sampleData.add(new TestPojo("Item Three b", 3, "Child 1"));
		sampleData.add(new TestPojo("Item Four", 4));
		sampleData.add(new TestPojo("Item Four b", 4, "Child 1", "Child 2", "Child 3"));
	}

	interface Action {
		public void execute();
	}

	/**
	 * Test if the given Action f throws the given Exception If not, then the
	 * test fails
	 */
	private <T extends Exception> void testForException(Class<T> exceptionClass, Action f) {
		try {
			f.execute();
			fail("Expected exception: " + exceptionClass.getName());
		} catch (Exception e) {
			if (e.getClass() != exceptionClass)
				fail("Unexpected exception: " + e.getClass().getName() + ", expected: " + exceptionClass.getName());
		}
	}

	// --------------
	// The test cases
	// --------------

	@Test
	public void test_isEmpty() {
		Queriable<String> qList = Query.list(new ArrayList<String>());
		assertTrue(qList.isEmpty());
	}
	
	@Test
	public void test_defaultIfEmpty() {
		int[] numbers = new int[] {1, 2, 3, 4, 5};
		
		assertEquals(5, Query.array(numbers).defaultIfEmpty(36).count());
		
		Queriable<Integer> qList = Query.array(new int[] {}).defaultIfEmpty(36);
		assertEquals(1, qList.count());		
		assertEquals(36, qList.first().intValue());		
	}

	@Test
	public void test_where() {
		List<TestPojo> resultList = Query.list(sampleData).where(x -> x.textItem.endsWith("e")).toList();

		assertEquals(2, resultList.size());
	}

	@Test
	public void test_exists_positive() {
		boolean result = Query.list(sampleData).exists(x -> "Item Three".equals(x.textItem));
		assertTrue(result);
	}

	@Test
	public void test_exists_negative() {
		boolean result = Query.list(sampleData).exists(x -> "Item Five".equals(x.textItem));
		assertFalse(result);
	}

	@Test
	public void test_contains_positive() {
		List<Integer> numbers = Arrays.asList(new Integer[] { 1, 2, 3, 4 });

		assertTrue(Query.list(numbers).contains(2));

		// test using a custom equivalence function
		assertTrue(Query.list(numbers).contains(2, new Equivalence<Integer>() {
			@Override
			public boolean areEqual(Integer element1, Integer element2) {
				return element1 / element2 == 2;
			}
		}));
	}

	@Test
	public void test_contains_negative() {
		List<Integer> numbers = Arrays.asList(new Integer[] { 1, 2, 3, 4 });

		assertFalse(Query.list(numbers).contains(21));

		// test using a custom equivalence function
		assertFalse(Query.list(numbers).contains(2, new Equivalence<Integer>() {
			@Override
			public boolean areEqual(Integer element1, Integer element2) {
				return element1 / element2 == 3;
			}
		}));
	}

	@Test
	public void test_first_positive() {
		// test parameterless first
		TestPojo p = Query.list(sampleData).first();
		assertNotNull(p);
		assertEquals("Item One", p.textItem);
		assertEquals(1, p.numberItem);

		// test conditional first
		p = Query.list(sampleData).first(x -> x.numberItem == 3);
		assertNotNull(p);
		assertEquals("Item Three", p.textItem);
		assertEquals(3, p.numberItem);
	}

	@Test(expected = NoSuchElementException.class)
	public void test_first_negative() {
		Query.list(sampleData).first(x -> x.numberItem == 23);
	}

	@Test
	public void test_firstOrDefault_positive() {
		// test parameterless first
		TestPojo p = Query.list(sampleData).firstOrDefault();
		assertNotNull(p);
		assertEquals("Item One", p.textItem);
		assertEquals(1, p.numberItem);

		// test conditional first
		p = Query.list(sampleData).firstOrDefault(x -> x.numberItem == 3);
		assertNotNull(p);
		assertEquals("Item Three", p.textItem);
		assertEquals(3, p.numberItem);
	}

	@Test
	public void test_firstOrDefault_negative() {
		TestPojo p = Query.list(sampleData).firstOrDefault(x -> x.numberItem == 23);
		assertNull(p);

		// test custom default value for empty list
		p = Query.list(new ArrayList<TestPojo>()).firstOrDefault(new TestPojo("Default", 0));
		assertEquals("Default", p.textItem);
		
		// test custom default value for unsatisfiable predicate
		p = Query.list(sampleData).firstOrDefault(x -> x.numberItem == 23, new TestPojo("Default", 0));
		assertEquals("Default", p.textItem);
	}

	@Test
	public void test_lastOrDefault_positive() {
		// test parameterless lastOrDefault
		TestPojo p = Query.list(sampleData).lastOrDefault();
		assertNotNull(p);
		assertEquals("Item Four b", p.textItem);
		assertEquals(4, p.numberItem);

		// test conditional lastOrDefault
		p = Query.list(sampleData).lastOrDefault(x -> x.numberItem == 3);
		assertNotNull(p);
		assertEquals("Item Three b", p.textItem);
		assertEquals(3, p.numberItem);
	}

	@Test
	public void test_last_positive() {
		// test parameterless last
		TestPojo p = Query.list(sampleData).last();
		assertNotNull(p);
		assertEquals("Item Four b", p.textItem);
		assertEquals(4, p.numberItem);

		// test conditional last
		p = Query.list(sampleData).last(x -> x.numberItem == 3);
		assertNotNull(p);
		assertEquals("Item Three b", p.textItem);
		assertEquals(3, p.numberItem);
		
		// test custom default value for unsatisfiable predicate
		p = Query.list(sampleData).lastOrDefault(x -> x.numberItem == 23, new TestPojo("Default", 0));
		assertEquals("Default", p.textItem);
	}

	@Test
	public void test_lastOrDefault_negative() {
		TestPojo p = Query.list(sampleData).lastOrDefault(x -> x.numberItem == 23);

		assertNull(p);
	}

	@Test(expected = NoSuchElementException.class)
	public void test_last_negative() {
		Query.list(sampleData).last(x -> x.numberItem == 23);
	}

	@Test
	public void test_singleOrDefault_positive() {
		// test parameterless single
		List<String> testList = Arrays.asList(new String[] { "Item One" });
		assertEquals("Item One", Query.iterable(testList).singleOrDefault());

		// test conditional single
		testList = Arrays.asList(new String[] { "Item One", "Item Two", "Item Three" });
		assertEquals("Item Two", Query.iterable(testList).singleOrDefault(x -> "Item Two".equals(x)));
	}

	@Test
	public void test_single_positive() {
		// test parameterless single
		List<String> testList = Arrays.asList(new String[] { "Item One" });
		assertEquals("Item One", Query.iterable(testList).single());

		// test conditional single
		testList = Arrays.asList(new String[] { "Item One", "Item Two", "Item Three" });
		assertEquals("Item Two", Query.iterable(testList).single(x -> "Item Two".equals(x)));
	}

	@Test
	public void test_singleOrDefault_negative() {
		// test parameterless singleOrDefault
		List<String> testList = Arrays.asList(new String[] { "Item One", "Item Two" });
		assertNull(Query.iterable(testList).singleOrDefault());

		List<String> emptyList = new ArrayList<String>();
		assertNull(Query.iterable(emptyList).singleOrDefault());

		// test conditional singleOrDefault
		List<String> testListTwo = Arrays
				.asList(new String[] { "Item One", "Item Two", "Element Three", "Element Four" });
		assertNull(Query.iterable(testListTwo).singleOrDefault(x -> x.startsWith("Element")));
		assertNull(Query.iterable(testListTwo).singleOrDefault(x -> x.startsWith("Dummy")));
	}

	@Test
	public void test_single_negative() {
		// test parameterless single
		final List<String> testList = Arrays.asList(new String[] { "Item One", "Item Two" });
		testForException(NoSuchElementException.class, () -> Query.iterable(testList).single());

		final List<String> emptyList = new ArrayList<String>();
		testForException(NoSuchElementException.class, () -> Query.iterable(emptyList).single());

		// test conditional single
		final List<String> testListTwo = Arrays
				.asList(new String[] { "Item One", "Item Two", "Element Three", "Element Four" });
		testForException(NoSuchElementException.class,
				() -> Query.iterable(testListTwo).single(x -> x.startsWith("Element")));
		testForException(NoSuchElementException.class,
				() -> Query.iterable(testListTwo).single(x -> x.startsWith("Dummy")));
	}

	@Test
	public void test_sequenceEquals() {
		Queriable<Integer> numbers1 = Query.array(new int[] {1, 2, 3, 4, 5});
		Queriable<Integer> numbers2 = Query.array(new int[] {1, 2, 36, 4, 5});
		Queriable<Integer> numbers3 = Query.array(new int[] {1, 2, 36, 4});
		Queriable<Integer> numbers4 = Query.array(new int[] {1, 2, 36, 4});
		
		assertTrue(numbers1.sequenceEquals(numbers1));
		assertFalse(numbers1.sequenceEquals(numbers2));
		assertFalse(numbers2.sequenceEquals(numbers3));
		assertTrue(numbers3.sequenceEquals(numbers4));
		
		// define equality to be "same number of letters"
		Queriable<String> texts1 = Query.array(new String[] {"One", "Two", "Three"});
		Queriable<String> texts2 = Query.array(new String[] {"Dfu", "gjh", "idksd"});
		Queriable<String> texts3 = Query.array(new String[] {"awe", "dsfdsfdsf", "aaaa"});
		
		assertTrue(texts1.sequenceEquals(texts1, (item1, item2) -> item1.length() == item2.length()));
		assertTrue(texts1.sequenceEquals(texts2, (item1, item2) -> item1.length() == item2.length()));
		assertFalse(texts2.sequenceEquals(texts3, (item1, item2) -> item1.length() == item2.length()));
	}
	
	@Test
	public void test_all_positive() {
		// test the all() method for elements which satisfy the condition
		boolean result = Query.list(sampleData).all(x -> x.textItem.startsWith("Item"));

		assertTrue(result);
	}

	@Test
	public void test_all_negative() {
		// test the all() method with a non-satisfiable condition
		boolean result = Query.list(sampleData).all(x -> x.textItem.startsWith("Item") && x.numberItem < 0);

		assertFalse(result);
	}

	@Test
	public void test_select() {
		// test a select which selects the string item of each element and
		// appends _sel
		List<String> resultList = Query.list(sampleData).select(x -> x.textItem + "_sel").toList();

		List<String> expectedList = new ArrayList<String>(Arrays.asList(new String[] { "Item One_sel", "Item Two_sel",
				"Item Three_sel", "Item Three b_sel", "Item Four_sel", "Item Four b_sel" }));

		assertEquals(6, resultList.size());
		assertEquals(expectedList, resultList);
	}

	@Test
	public void test_selectMany() {
		// get the children of all elements and put them in one list
		List<String> list = Query.list(sampleData).selectMany(x -> x.subElements).toList();
		assertEquals(7, list.size());
	}

	@Test
	public void test_distinct() {
		List<String> testList = new ArrayList<String>(Arrays.asList(new String[] { "Item", "Item", "Item", "Item2" }));
		List<String> resultList = Query.list(testList).distinct().toList();

		assertEquals(2, resultList.size());
	}

	@Test
	public void test_distinct_with_equivalence() {
		List<String> testList = new ArrayList<String>(Arrays.asList(new String[] { "One", "Two", "Three", "Four" }));
		// we consider equality now to be "same number of letters
		// ergo One and Two are now considered equal and Two is skipped
		List<String> resultList = Query.list(testList).distinct((x, y) -> x.length() == y.length()).toList();

		assertEquals(3, resultList.size());
	}

	@Test
	public void test_cast() {
		// cast the list items to their base class and return a list of the base
		// class references
		List<BasePojo> list = Query.list(sampleData).cast(BasePojo.class).toList();

		assertEquals(sampleData.size(), list.size());
		assertTrue(Query.list(list).firstOrDefault() instanceof BasePojo);
		assertEquals("Item One", list.iterator().next().textItem);
	}

	@Test
	public void test_ofType() {
		List<Object> testList = Arrays.asList(new Object[] { new ParentTestClass(), new ParentTestClass(),
				new ChildTestClass(), null, new UnrelatedTestClass() });

		// ChildTestClass is derived from ParentTestClass
		assertEquals(3, Query.list(testList).ofType(ParentTestClass.class).count());

		assertEquals(1, Query.list(testList).ofType(ChildTestClass.class).count());
		assertTrue(Query.list(testList).ofType(LazyObject.class).isEmpty());
	}

	@Test
	public void test_concat() {
		List<String> listOne = Arrays.asList(new String[] { "One", "Two", "Three", "Four" });
		List<String> listTwo = Arrays.asList(new String[] { "Three", "Four", "Five" });

		Queriable<String> q = Query.list(listOne).concat(listTwo);

		assertEquals(7, q.count());
	}

	@Test
	public void test_union() {
		List<String> listOne = Arrays.asList(new String[] { "One", "Two", "Three", "Four" });
		List<String> listTwo = Arrays.asList(new String[] { "Three", "Four", "Five" });

		Queriable<String> q = Query.list(listOne).union(listTwo);
		assertEquals(5, q.count());

		// define equality to be "same number of letters"
		q = Query.list(listOne).union(listTwo, (item1, item2) -> item1.length() == item2.length());
		assertEquals(3, q.count());
	}

	@Test
	public void test_intersect() {
		List<String> listOne = Arrays.asList(new String[] { "One", "Two", "Three", "Four" });
		List<String> listTwo = Arrays.asList(new String[] { "Three", "Four", "Five" });

		Queriable<String> q = Query.list(listOne).intersect(listTwo);
		assertEquals(2, q.count());

		// define equality to be "start with the same letter"
		q = Query.list(listOne).intersect(listTwo, (item1, item2) -> item1.charAt(0) == item2.charAt(0));
		assertEquals(3, q.count());
	}

	@Test
	public void test_except() {
		List<String> listOne = Arrays.asList(new String[] { "One", "Two", "Three", "Four" });
		List<String> listTwo = Arrays.asList(new String[] { "Three", "Four", "Five" });

		Queriable<String> q = Query.list(listOne).except(listTwo);
		assertEquals(2, q.count());

		// define equality to be "start with the same letter"
		q = Query.list(listOne).except(listTwo, (item1, item2) -> item1.charAt(0) == item2.charAt(0));
		assertEquals(1, q.count());
	}

	@Test
	public void test_count() {
		assertEquals(2, Query.list(sampleData).where(x -> x.textItem.endsWith("e")).count());
	}

	@Test
	public void test_conditional_count() {
		assertEquals(2, Query.list(sampleData).count(x -> x.textItem.endsWith("e")));
	}

	@Test
	public void test_max() {
		// test parameterless function
		int[] numbers = new int[] { 1, 2, 3, 4, 5 };
		assertEquals(5, Query.array(numbers).max().intValue());

		// test version with parameters
		assertEquals(4, Query.list(sampleData).max(x -> x.numberItem));
	}
	
	@Test
	public void test_empty_max() {
		testForException(IllegalStateException.class, () -> QuIterables.empty().max());
	}

	@Test
	public void test_min() {
		// test parameterless function
		int[] numbers = new int[] { 1, 2, 3, 4, 5 };
		assertEquals(1, Query.array(numbers).min().intValue());

		// test version with parameters
		assertEquals(1, Query.list(sampleData).min(x -> x.numberItem));
	}
	
	@Test
	public void test_empty_min() {
		testForException(IllegalStateException.class, () -> QuIterables.empty().min());
	}
	
	@Test
	public void test_average() {
		int[] numbers = new int[] { 2, 4, 3, 6 };
		double avg = Query.array(numbers).average().doubleValue();

		assertEquals(3.75d, avg, 0.000001);
	}
	
	@Test
	public void test_empty_average() {
		testForException(IllegalStateException.class, () -> QuIterables.empty().average());
	}

	@Test
	public void test_sum_pojo() {
		Number sum = Query.list(sampleData).sum(x -> x.numberItem);

		assertEquals(17, sum);
	}
	
	@Test
	public void test_sum() {
		int[] numbers = new int[] { 2, 4, 4, 6 };
		Number sum = Query.array(numbers).sum();

		assertEquals(16, sum);
	}
	
	@Test
	public void test_empty_sum() {
		assertEquals(0, QuIterables.empty().sum());
	}

	@Test
	public void test_chain_single() {
		// test a more complex chained list matching function
		// which evaluates to a single value
		Number sum = Query.list(sampleData).where(x -> x.textItem.startsWith("Item Three")).select(x -> x.numberItem)
				.distinct().sum(x -> x);

		assertEquals(3, sum);
	}

	@Test
	public void test_chain_list() {
		// test a more complex chained list matching function
		// which evaluates to another list
		List<BasePojo> list = Query.list(sampleData).where(x -> x.textItem.startsWith("Item T")).cast(BasePojo.class)
				.toList();

		assertEquals(3, list.size());
		assertEquals("Item Two", list.get(0).textItem);
		assertEquals("Item Three", list.get(1).textItem);
	}

	@Test
	public void test_matchableList() {
		// test if the MatchableList-Wrapper works
		Queriable<TestPojo> testList = Query.list(sampleData);
		TestPojo p = testList.where(x -> (x.subElements.size() > 0)).distinct()
				.firstOrDefault(x -> x.textItem.startsWith("Item Th"));

		assertNotNull(p);
		assertEquals("Item Three b", p.textItem);
	}

	@Test
	public void test_take() {
		List<TestPojo> list = Query.list(sampleData).take(3).toList();
		assertEquals(3, list.size());

		list = Query.list(sampleData).take(26).toList();
		assertEquals(sampleData.size(), list.size());
	}

	@Test
	public void test_takeWhile() {
		List<TestPojo> list = Query.list(sampleData).takeWhile(x -> x.textItem.startsWith("Item")).toList();
		assertEquals(sampleData.size(), list.size());

		list = Query.list(sampleData).takeWhile(x -> x.numberItem < 4).toList();
		assertEquals(4, list.size());
	}

	@Test
	public void test_skip() {
		List<TestPojo> list = Query.list(sampleData).skip(2).toList();
		assertEquals(4, list.size());

		list = Query.list(sampleData).skip(26).toList();
		assertEquals(0, list.size());
	}

	@Test
	public void test_skipWhile() {
		List<TestPojo> list = Query.list(sampleData).skipWhile(x -> x.numberItem < 4).toList();
		assertEquals(2, list.size());

		list = Query.list(sampleData).skipWhile(x -> x.numberItem < 20).toList();
		assertEquals(0, list.size());
	}

	@Test
	public void test_array() {
		// test if arrays can be queried
		Integer[] d = new Integer[] { 1, 2, 3 };
		int result = Query.array(d).firstOrDefault(x -> x == 3);

		assertEquals(3, result);
	}
}
