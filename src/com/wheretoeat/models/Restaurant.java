package com.wheretoeat.models;

import java.util.List;

public class Restaurant {

	private List<Review> reviews;
	private String address;
	private String phoneNumber;
	private double rating;
	private Category category;
	private List<String> photoUrl;

	
	public List<Review> getReviews() {
		return reviews;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public double getRating() {
		return rating;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public List<String> getPhotoUrl() {
		return photoUrl;
	}
	
}
