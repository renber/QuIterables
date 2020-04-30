package de.renebergelt.quiterables.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import de.renebergelt.quiterables.Query;
import de.renebergelt.quiterables.grouping.Group;
import de.renebergelt.quiterables.iterators.ListReverseIterable;

public class OrderingTest {

	@Test
	public void test_orderBy() {
		// generated using listofrandomnames.com
		List<String> testList = Arrays.asList(
				new String[] { "Lashaunda Saladino", "Rosalyn Sterling", "Jessia Suire", "Lilly Fabian", "Long Grange",
						"Mahalia Luton", "Roberta Fowler", "Luciano Vann", "Camellia Risch", "Tanya Goodpaster" });

		List<String> orderedList = Arrays.asList(
				new String[] { "Camellia Risch", "Jessia Suire", "Lashaunda Saladino", "Lilly Fabian", "Long Grange",
						"Luciano Vann", "Mahalia Luton", "Roberta Fowler", "Rosalyn Sterling", "Tanya Goodpaster" });

		List<String> resultList = Query.list(testList).orderBy(x -> x).toList();

		assertEquals(orderedList, resultList);
	}

	@Test
	public void test_orderBy_int() {
		int[] numbers = new int[] { 34, 76, 3, 12, 55, 23, 105, 67, 12 };
		int[] testArray = Query.array(numbers).orderBy(x -> x).toPrimitiveArray().intArray();
		int[] sortedArray = new int[] { 3, 12, 12, 23, 34, 55, 67, 76, 105 };

		for (int i = 0; i < sortedArray.length; i++)
			if (testArray[i] != sortedArray[i])
				fail("Arrays do not match.");
	}

	@Test
	public void test_orderByDescending_int() {
		int[] numbers = new int[] { 34, 76, 3, 12, 55, 23, 105, 67, 12 };
		int[] testArray = Query.array(numbers).orderByDescending(x -> x).toPrimitiveArray().intArray();
		int[] sortedArray = new int[] { 105, 76, 67, 55, 34, 23, 12, 12, 3 };

		for (int i = 0; i < sortedArray.length; i++)
			if (testArray[i] != sortedArray[i])
				fail("Arrays do not match.");
	}

	@Test
	public void test_thenBy() {
		List<TestPerson> testList = Arrays.asList(new TestPerson[] { new TestPerson("Gamma", "Omega"),
				new TestPerson("Beta", "Gamma"), new TestPerson("Delta", "Alpha"), new TestPerson("Delta", "Beta"),
				new TestPerson("Alpha", "Omega"), new TestPerson("Iota", "Theta") });

		List<TestPerson> orderedList = Arrays.asList(new TestPerson[] { new TestPerson("Delta", "Alpha"),
				new TestPerson("Delta", "Beta"), new TestPerson("Beta", "Gamma"), new TestPerson("Alpha", "Omega"),
				new TestPerson("Gamma", "Omega"), new TestPerson("Iota", "Theta") });

		List<TestPerson> resultList = Query.list(testList).orderBy(x -> x.lastName).thenBy(x -> x.firstName).toList();

		assertEquals(orderedList, resultList);
	}

	@Test
	public void test_reverse() {
		int[] numbers = new int[] { 34, 76, 3, 12, 55, 23, 105, 67, 12 };
		int[] testArray = Query.array(numbers).reverse().toPrimitiveArray().intArray();
		int[] reversedArray = new int[] { 12, 67, 105, 23, 55, 12, 3, 76, 34 };

		for (int i = 0; i < reversedArray.length; i++)
			if (testArray[i] != reversedArray[i])
				fail("Arrays do not match.");
	}

	@Test
	public void test_reverse_list() {
		// test the reverse optimization for List<T>

		Integer[] numbers = new Integer[] { 34, 76, 3, 12, 55, 23, 105, 67, 12 };
		List<Integer> sourceList = Arrays.asList(numbers);
		Iterable<Integer> testList = Query.list(sourceList).reverse();
		Integer[] reversedArray = new Integer[] { 12, 67, 105, 23, 55, 12, 3, 76, 34 };

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
		for (Integer number : testList) {
			if (number != reversedArray[idx])
				fail("Arrays do not match.");
			idx++;
		}
	}

	private Integer getRankOfTitle(String title) {
		// assign a number to the given title
		// prof = 0, dr = 1, msc = 2, student = 3, else = 4
		title = title.toLowerCase();

		if ("prof.".equals(title))
			return 0;
		if ("dr.".equals(title))
			return 1;
		if ("m.sc.".equals(title))
			return 2;
		if ("b.sc.".equals(title))
			return 3;
		return 4;
	}

	@Test
	public void test_orderBy_UsingComparator() {
		// generated using listofrandomnames.com
		List<TestPerson> testList = Arrays.asList(new TestPerson[] { new TestPerson("B.Sc.", "Bessie", "Delp"),
				new TestPerson("Prof.", "Sue", "Pixler"), new TestPerson("M.Sc.", "Antonia", "Whitehair"),
				new TestPerson("Mohammad", "Hulse"), new TestPerson("Dr.", "Katrina", "Alverson") });

		List<TestPerson> orderedList = Arrays.asList(new TestPerson[] { new TestPerson("Prof.", "Sue", "Pixler"),
				new TestPerson("Dr.", "Katrina", "Alverson"), new TestPerson("M.Sc.", "Antonia", "Whitehair"),
				new TestPerson("B.Sc.", "Bessie", "Delp"), new TestPerson("Mohammad", "Hulse") });

		Comparator<String> academicTitleComparator = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return getRankOfTitle(o1).compareTo(getRankOfTitle(o2));
			}
		};

		// sort list by academic title using the custom comparator
		List<TestPerson> resultList = Query.list(testList).orderBy(x -> x.title, academicTitleComparator).toList();
		assertEquals(orderedList, resultList);
	}

	@Test
	public void test_group_and_order() {
		List<ComplexElement> testList = Arrays.asList(new ComplexElement[] {
				new ComplexElement("Group 1", 14),
				new ComplexElement("Group 3", 5),
				new ComplexElement("Group 2", 14),
				new ComplexElement("Group 2", 14),
				new ComplexElement("Group 1", 14),
				new ComplexElement("Group 1", 14)
			});

		// group by text and order by group size
		List<Group<ComplexElement>> lst = Query.list(testList)
				.groupSingle(x -> x.value)
				.orderBy(x -> ((ComplexValue)x.getKey().first()).number)
				.thenBy(x -> x.size())
				.toList();

		assertEquals("Group 3", ((ComplexValue)lst.get(0).getKey().first()).text);
		assertEquals("Group 2", ((ComplexValue)lst.get(1).getKey().first()).text);
		assertEquals("Group 1", ((ComplexValue)lst.get(2).getKey().first()).text);
	}
}

class ComplexElement {
	public ComplexValue value;
	
	public ComplexElement(String text, int number) {
		value = new ComplexValue(text, number);
	}
}

class ComplexValue {
	public String text;
	public int number;
	
	public ComplexValue(String _text, int _number) {
		text = _text;
		number = _number;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ComplexValue) {
			return text.equals(((ComplexValue) o).text) && number == ((ComplexValue) o).number;
		} else
			return this == o;
	}

	@Override
	public int hashCode() {
		return text.hashCode() ^ number;
	}
}

class TestPerson {
	public String title;
	public String firstName;
	public String lastName;

	public TestPerson(String _title, String _firstName, String _lastName) {
		title = _title;
		firstName = _firstName;
		lastName = _lastName;
	}

	public TestPerson(String _firstName, String _lastName) {
		title = "";
		firstName = _firstName;
		lastName = _lastName;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof TestPerson) {
			return firstName.equals(((TestPerson) o).firstName) && lastName.equals(((TestPerson) o).lastName)
					&& title.equals(((TestPerson) o).title);
		} else
			return this == o;
	}

	@Override
	public int hashCode() {
		return firstName.hashCode() ^ lastName.hashCode() ^ title.hashCode();
	}
}
