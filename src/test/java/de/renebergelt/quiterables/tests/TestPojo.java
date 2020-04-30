package de.renebergelt.quiterables.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BasePojo {
	public String textItem;		
}

class TestPojo extends BasePojo {
	
	public int numberItem;
	
	public List<String> subElements;

	public TestPojo(String text, int number, String... children) {
		textItem = text;
		numberItem = number;
		
		subElements = new ArrayList<String>(Arrays.asList(children));
	}				
}
