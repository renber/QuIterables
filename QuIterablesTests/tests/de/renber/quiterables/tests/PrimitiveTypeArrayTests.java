package de.renber.quiterables.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.renber.quiterables.Query;

public class PrimitiveTypeArrayTests {

	@Test
	public void test_int_array() {		
		int[] d = new int[] {1, 2, 3};
		int result = Query.array(d).firstOrDefault(x -> x == 3);
		
		assertEquals(3, result);
	}
	
	@Test
	public void test_short_array() {		
		short[] d = new short[] {1, 2, 3};
		short result = Query.array(d).firstOrDefault(x -> x == 3);
		
		assertEquals(3, result);
	}
	
	@Test
	public void test_long_array() {
		// test if arrays can be queried
		long[] d = new long[] {1, 2, 3};
		long result = Query.array(d).firstOrDefault(x -> x == 3);
		
		assertEquals(3, result);
	}	
	
	@Test
	public void test_float_array() {
		// test if arrays can be queried
		float[] d = new float[] {1.5f, 2.5f, 3.5f};
		float result = Query.array(d).firstOrDefault(x -> x > 3);
		
		assertEquals(3.5f, result, 0.001);
	}	

	@Test
	public void test_double_array() {
		// test if arrays can be queried
		double[] d = new double[] {1.5d, 2.5d, 3.5d};
		double result = Query.array(d).firstOrDefault(x -> x > 3);
		
		assertEquals(3.5d, result, 0.001);
	}	
	
	@Test
	public void test_byte_array() {
		// test if arrays can be queried
		byte[] d = new byte[] {28, 90, 127};
		byte result = Query.array(d).firstOrDefault(x -> x == 90);
		
		assertEquals(90, result);
	}	
	
	@Test
	public void test_boolean_array() {
		// test if arrays can be queried
		boolean[] d = new boolean[] {false, false, true};
		boolean result = Query.array(d).firstOrDefault(x -> x);
		
		assertTrue(result);
	}	
	
	@Test
	public void test_char_array() {
		// test if arrays can be queried
		char[] d = new char[] {'H', 'e', 'l', 'l', 'o'};
		char result = Query.array(d).firstOrDefault(x -> x == 'l');
		
		assertEquals(result, 'l');
	}	
	
}
