package de.renebergelt.quiterables.tests;

/**
 * A class which overrides equals and hashCodefor testing with
 * the equality based methods of ListMatcher (such as distinct())
 * @author berre
 */
public class ComplexEqualsClass {

	public String strValue;
	public int intValue;
	
	public ComplexEqualsClass(String _strValue, int _intValue) {
		strValue = _strValue;
		intValue = _intValue;
	}
	
	@Override
	public int hashCode() {
		return strValue.hashCode() ^ intValue;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ComplexEqualsClass)
		{
			ComplexEqualsClass ce = (ComplexEqualsClass)o;
			return ce.strValue.equals(strValue) && ce.intValue == intValue;
		} else
			return o == this;
	}
	
}
