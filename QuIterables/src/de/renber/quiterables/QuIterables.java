package de.renber.quiterables;

import de.renber.quiterables.iterators.ArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.BooleanArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.ByteArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.CharArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.DoubleArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.FloatArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.IntArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.LongArrayIterable;
import de.renber.quiterables.iterators.primitivetypes.ShortArrayIterable;

/**
 * Convenience class of the QuIterables library 
 * for static importing the query(..) method
 * @author René Bergelt
 *
 */
public class QuIterables {

	/** 
	 * "static" class
	 */
	private QuIterables() {
		// --
	}
	
	/**
	 * Return a queriable object for the given iterable	
	 */
	public static <T> Queriable<T> query(Iterable<T> it) {
		return new QueriableImpl<T>(it);
	}
	
	/**
	 * Return a queriable object for the given iterable	
	 */
	public static <T> Queriable<T> query(T[] array) {
		return new QueriableImpl<T>(new ArrayIterable<T>(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type int-array
	 */
	public static Queriable<Integer> query(int[] array) {
		return new QueriableImpl<Integer>(new IntArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type short-array
	 */
	public static Queriable<Short> query(short[] array) {
		return new QueriableImpl<Short>(new ShortArrayIterable(array));
	}

	/**
	 * Return a Queriable which wraps the given primitive-type long-array
	 */
	public static Queriable<Long> query(long[] array) {
		return new QueriableImpl<Long>(new LongArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type float-array
	 */
	public static Queriable<Float> query(float[] array) {
		return new QueriableImpl<Float>(new FloatArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type double-array
	 */
	public static Queriable<Double> query(double[] array) {
		return new QueriableImpl<Double>(new DoubleArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type byte-array
	 */
	public static Queriable<Byte> query(byte[] array) {
		return new QueriableImpl<Byte>(new ByteArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type boolean-array
	 */
	public static Queriable<Boolean> query(boolean[] array) {
		return new QueriableImpl<Boolean>(new BooleanArrayIterable(array));
	}
	
	/**
	 * Return a Queriable which wraps the given primitive-type char-array
	 */
	public static Queriable<Character> query(char[] array) {
		return new QueriableImpl<Character>(new CharArrayIterable(array));
	}	
	
}
