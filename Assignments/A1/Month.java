/**
 * COMP 2150 Summer 2022 Assignment 1 Question 1
 */

public enum Month {
	// this should probably use the Java Calendar class instead
	
	JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC;

	private static final String[] NAMES = {
		"January",
		"February",
		"March",
		"April",
		"May",
		"June",
		"July",
		"August",
		"September",
		"October",
		"November",
		"December"
	};

	public String toString() {
		return NAMES[ordinal()];
	}
	
	public static Month fromInt(int i) {
		assert i >= 1 && i <= 12;
		return Month.values()[i-1];
	}
}
