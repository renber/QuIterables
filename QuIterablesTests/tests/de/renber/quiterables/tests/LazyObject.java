package de.renber.quiterables.tests;

class LazyObject
{
	public String text = "";
	public Integer value = 0;
	
	public boolean wasQueried = false;
	
	public LazyObject(String _text, int _value) {
		text = _text;
		value = _value;
	}
	
	/**
	 * Returns this object's text and marks the object as queried		 
	 */
	public String getText() {
		wasQueried = true;
		return text;
	}		
	
	/**
	 * Returns this object's int value and marks the object as queried		 
	 */
	public int getValue() {
		wasQueried = true;
		return value;
	}
}
