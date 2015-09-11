package de.renber.quiterables.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.renber.quiterables.Predicate;
import de.renber.quiterables.Query;
import de.renber.quiterables.grouping.Group;
import de.renber.quiterables.grouping.GroupKey;
import de.renber.quiterables.grouping.GroupedList;
import de.renber.quiterables.grouping.GroupedQueriable;

public class ListGroupTests {

	List<TestPojo> sampleData;

	@Before
	public void createSampleData() {
		// generate test data for each test case
		// will be generated anew for each test
		sampleData = new ArrayList<>();

		sampleData.add(new TestPojo("Item One", 1, "Child 1"));
		sampleData.add(new TestPojo("Item Two", 1));
		sampleData.add(new TestPojo("Item Three", 2, "Child 1"));
		sampleData.add(new TestPojo("Item Four", 2, "Child 1"));
		sampleData.add(new TestPojo("Item Five", 2));
		sampleData.add(new TestPojo("Item Six", 3, "Child 1"));
		sampleData.add(new TestPojo("Demo Item", 4, "Child 1"));
		sampleData.add(new TestPojo("Demo Item", 4, "Child 1"));
	}
	
	@Test
	public void test_group() {
		// group by the number item
		GroupedList<TestPojo> list = Query.list(sampleData).group(x -> new GroupKey(x.numberItem)).toList();
		
		assertEquals(4, list.size());
		assertEquals(3, list.get(new GroupKey(2)).size());
		
	}
	
	@Test
	public void test_groupSingle() {
		// group by the number item
		GroupedList<TestPojo> list = Query.list(sampleData).groupSingle(x -> x.numberItem).toList();
		
		assertEquals(4, list.size());
		assertEquals(3, list.get(new GroupKey(2)).size());		
	}
	
	@Test
	public void test_group_complex_key() {
		// group by a complex key
		GroupedList<TestPojo> list = Query.list(sampleData).group(x -> new GroupKey(x.textItem.substring(0, 4), x.subElements.size())).toList();		
		
		assertEquals(3, list.size());	
		
		// check if the group with GroupKey ("Item", 0) contains the TestPojo "Item Two" 
		assertTrue(Query.list(list.get("Item", 0)).exists(x -> "Item Two".equals(x.textItem)));		
	}
	
	@Test
	public void test_group_chain() {
		// test a chained group call
		TestPojo result = Query.list(sampleData)
				   			.where(x -> x.subElements.size() > 0)
				   			.groupSingle(x -> x.numberItem)
				   			.get(new GroupKey(2))
				   			.firstOrDefault(x -> "Item Four".equals(x.textItem));
		
		assertNotNull(result);
		assertEquals("Item Four", result.textItem);				   
	}

}
