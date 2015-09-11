package de.renber.quiterables.tests;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.renber.quiterables.Queriable;
import de.renber.quiterables.Query;

/**
 * Unit tests for the PoorMansLinq/ListMatcher class Using Java 8 lamdba
 * expressions
 * 
 * @author berre
 */
public class QueryTests {

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
	
	// --------------
	// The test cases
	// --------------	

	@Test
	public void test_where() {
		List<TestPojo> resultList = Query.list(sampleData)
				.where(x -> x.textItem.endsWith("e"))
				.toList();
		
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
	public void test_firstOrDefault_positive() {
		// test parameterless firstOrDefault
		TestPojo p = Query.list(sampleData).firstOrDefault();
		assertNotNull(p);
		assertEquals("Item One", p.textItem);
		assertEquals(1, p.numberItem);
		
		// test conditional firstOrDefault
		p = Query.list(sampleData).firstOrDefault(x -> x.numberItem == 3);
		assertNotNull(p);
		assertEquals("Item Three", p.textItem);
		assertEquals(3, p.numberItem);
	}

	@Test
	public void test_firstOrDefault_negative() {
		TestPojo p = Query.list(sampleData).firstOrDefault(x -> x.numberItem == 23);

		assertNull(p);
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
	public void test_lastOrDefault_negative() {
		TestPojo p = Query.list(sampleData).lastOrDefault(x -> x.numberItem == 23);

		assertNull(p);
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
		List<String> testList = new ArrayList<String>(Arrays.asList(new String[] {"Item", "Item", "Item", "Item2"}));
		List<String> resultList = Query.list(testList).distinct().toList();
		
		assertEquals(2, resultList.size());
	}
	
	@Test
	public void test_cast() {
		// cast the list items to their base class and return a list of the base class references
		List<BasePojo> list = Query.list(sampleData)
				.cast(BasePojo.class)
				.toList();
		
		assertEquals(sampleData.size(), list.size());
		assertTrue(Query.list(list).firstOrDefault() instanceof BasePojo);
		assertEquals("Item One", list.iterator().next().textItem);
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
		Number max = Query.list(sampleData).max(x -> x.numberItem);

		assertEquals(4, max);
	}

	@Test
	public void test_min() {
		Number min = Query.list(sampleData).min(x -> x.numberItem);

		assertEquals(1, min);
	}

	@Test
	public void test_sum() {
		Number sum = Query.list(sampleData).sum(x -> x.numberItem);

		assertEquals(17, sum);
	}
	
	@Test
	public void test_chain_single() {
		// test a more complex chained list matching function
		// which evaluates to a single value
		Number sum = Query.list(sampleData)
				.where(x -> x.textItem.startsWith("Item Three"))
				.select(x -> x.numberItem)
				.distinct()				
				.sum(x -> x);
		
		assertEquals(3, sum);
	}
	
	@Test
	public void test_chain_list() {
		// test a more complex chained list matching function
		// which evaluates to another list
		List<BasePojo> list = Query.list(sampleData)
				.where(x -> x.textItem.startsWith("Item T"))								
				.cast(BasePojo.class)
				.toList();				
		
		assertEquals(3, list.size());
		assertEquals("Item Two", list.get(0).textItem);
		assertEquals("Item Three", list.get(1).textItem);
	}	
	
	@Test
	public void test_matchableList() {		
		// test if the MatchableList-Wrapper works		
		Queriable<TestPojo> testList = Query.list(sampleData);
		TestPojo p = testList.where(x -> (x.subElements.size() > 0))
								 .distinct()
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
		Integer[] d = new Integer[] {1, 2, 3};
		int result = Query.array(d).firstOrDefault(x -> x == 3);
		
		assertEquals(3, result);
	}
}
