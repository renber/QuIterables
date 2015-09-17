/*******************************************************************************
 * This file is part of the Java QuIterables Library
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 René Bergelt
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package de.renber.quiterables.grouping;

/**
 * Represents a group key (All group key instances with the same elements
 * (value-wise) are considered equal and have the same hashCode
 * 
 * @author berre
 * 
 */
public class GroupKey {

	private Object[] elements;

	/**
	 * Create a new group key
	 * 
	 * @param parts
	 *            The parts which compose this group key (order is critical!)
	 */
	public GroupKey(Object... parts) {
		elements = new Object[parts.length];
		System.arraycopy(parts, 0, elements, 0, parts.length);
	}

	/**
	 * Compares this group key to the given one for data (!) equality (not only
	 * reference equality)
	 * 
	 * @param gk
	 * @return
	 */
	public boolean equals(GroupKey gk) {

		if (this == gk) {
			return true;
		}

		if (this.partCount() != gk.partCount()) {
			return false;
		}

		// compare the individual elements
		for (int i = 0; i < partCount(); i++) {
			if (!get(i).equals(gk.get(i))) {
				return false; // the group keys are not equal
			}
		}

		return true; // all elements match -> the keys are equal
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GroupKey)) {
			return false;
		}

		return equals((GroupKey) other);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;

		for (Object o : elements) {
			result += o.hashCode();
		}

		return result;
	}

	/**
	 * Number of key parts this group key consists of
	 * 
	 * @return
	 */
	public int partCount() {
		return elements.length;
	}

	/**
	 * Returns the key part with the given index
	 * 
	 * @param index
	 * @return
	 */
	public Object get(int index) {
		return elements[index];
	}

}
