# QuIterables
Queriable iterables for java

QuIterables provides a wrapper for java Iterables in order to query them with a functional approach.
It tries to stay as close as possible to the LINQ extension lambda methods from C#.

Beginning with Java 8 there is the stream functionality built into the system library with which there is some overlap.
But there are also enough differences to consider QuIterable useful:
 - There are methods which are currently not available using streams (skip, take, skipWhile, takeWhile, firstOrDefault, lastOrDefault, ...)
 - QuIterables' objects can be resused as many times as you want whereas as soon as you used a stream it cannot be used again as it has been closed
 - QuIterables can be used with Java 7 and below
 
QuIterables tries to lazy-evaluate your expressions as much as possible, so expression evaluation can be deferred up to the point where you actually request an item or
final result. In contrast to other Java query libraries it is fully type-safe.

This repository is automatically built and tested with Travis CI:<br>
![build status](https://api.travis-ci.org/renber/QuIterables.svg?branch=master)

Available on [maven central](https://search.maven.org/artifact/de.renebergelt/quiterables/1.1.0/jar)

# Usage

To use QuIterables add the following to your pom.xml if you are using Maven:
```
<dependency>
  <groupId>de.renebergelt</groupId>
  <artifactId>quiterables</artifactId>
  <version>1.1.0</version>
</dependency>
```

All samples are using the (more readable) Java 8 lambda syntax. Below Java 8 you have to resort to implementing the required interfaces which all only have one method (e.g. anonymous implementation in-place).

QuIterables can be used to query java Iterables (Lists, Collections, ...), Object arrays and even primitive array (values are automatically boxed/unboxed)

```java
List<String> list = Arrays.asList(new String[] {"Item One", "Item Two", "Item Three"});
Queriable<String> resultList = Query.list(list).where(x -> x.endsWith("e")).skip(1);
// resultList now only contains the item "Item Three"
```

using QuIterables with primitive-type arrays:

```java
int[] numbers = new int[] {34, 76, 3, 12, 55, 23, 105, 67, 12};		
int minimumValue = Query.array(numbers).min(x -> x).intValue(); // min returns java.lang.Number
int[] sortedArray = Query.array(numbers).orderBy(x -> x).toPrimitiveArray().intArray();
```

using complex types:
```java

class TestPojo {	
    public String textItem;	    
	public int numberItem;

	public TestPojo(String text, int number) {
		textItem = text;
		numberItem = number;	
	}				
}

List<TestPojo> list = Arrays.asList(new TestPojo[] {new TestPojo("One", 1), new TestPojo("Two", 2), new TestPojo("Three", 3)});
TestPojo p1 = Query.list(list).where(x -> x.numberItem > 1).firstOrDefault(); // p1 is now TestPojo("Two", 2)
TestPojo p2 = Query.list(list).skipWhile(x -> x < 2); // p2 is now TestPojo("Three", 3)


List<TestPojo> list2 = Arrays.asList(new TestPojo[] {new TestPojo("A very long entry", 1), new TestPojo("Short entry", 26), new TestPojo("Short entry", 11)});
Queriable<TestPojo> p3 = Query.list(list2).orderBy(x -> x.textItem.length()).thenBy(x -> x.numberItem); 
// the items in p3 now have this order: TestPojo("Short entry", 11), new TestPojo("Short entry", 26), new TestPojo("A very long entry", 1)
