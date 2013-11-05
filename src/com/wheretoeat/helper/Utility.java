package com.wheretoeat.helper;

public class Utility {

	public static double convertMeterstoMiles(double meters) {
		double miles = 0.0d;
		// 1609.34
		miles = meters / 1609.34;
		return miles;
	}

}
