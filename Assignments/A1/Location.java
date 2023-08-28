/**
 * COMP 2150 Summer 2022 Assignment 1 Question 1
 */

import java.util.Scanner;

public class Location {

	private int x;
	private int y;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}
	
	public double distance(Location other) {
		double xd = x - other.x;
		double yd = y - other.y;

		return Math.sqrt(xd * xd + yd * yd);
	}

	public static Location read(Scanner in) {
		return new Location(in.nextInt(), in.nextInt());
	}
}
