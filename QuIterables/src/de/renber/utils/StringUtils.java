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
package de.renber.utils;

import java.util.List;

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
	 * Join the given list to a string by using the delimiter <br/>
	 * a = {"A", "B", "C"}, delimiter = ";" <br/>
	 * result: "A;B;C"
	 * @param a
	 * @param delimiter
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String join(List a, String delimiter) {
		if (a == null || a.size() == 0) 
			return "";
		if (a.size() == 1)
			return String.valueOf(a.get(0));
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < a.size() - 1; i++) {
			sb.append(a.get(i)).append(delimiter);
		}
		
		sb.append(String.valueOf(a.get(a.size()-1)));
		
		return sb.toString();
	}
}
