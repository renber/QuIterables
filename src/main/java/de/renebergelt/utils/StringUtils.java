/*******************************************************************************
 * This file is part of the Java QuIterables Library
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2016 René Bergelt
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
package de.renebergelt.utils;

import java.util.Iterator;

public class StringUtils {

	private StringUtils() {		
	}
	
	/**
	 * Join the given array to a string by using the delimiter <br/>
	 * a = {"A", "B", "C"}, delimiter = ";" <br/>
	 * result: "A;B;C"
	 * @param a
	 * @param delimiter
	 * @return
	 */
	public static String join(String[] a, String delimiter) {
		if (a.length == 0) 
			return "";
		if (a.length == 1)
			return a[0];
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < a.length - 1; i++) {
			sb.append(a[i]).append(delimiter);
		}
		
		sb.append(a[a.length-1]);
		
		return sb.toString();
	}
	
	/**
	 * Join the elements in the iterable (by using their toString() method) to a string by using the delimiter <br/>
	 * a = {"A", "B", "C"}, delimiter = ";" <br/>
	 * result: "A;B;C"
	 * @param a
	 * @param delimiter
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String join(Iterable elements, String delimiter) {
		
		Iterator it = elements.iterator();
		
		if (elements == null || !it.hasNext()) 
			return "";
		
		StringBuilder sb = new StringBuilder();
		
		for(Object element = it.next(); it.hasNext(); element = it.next()) {
			sb.append(element.toString());
			if (it.hasNext())
				sb.append(delimiter);
		}
		
		return sb.toString();
	}
}
