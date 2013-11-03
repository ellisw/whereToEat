package com.wheretoeat.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Restaurant {

	private List<Review> reviews;
	private String address;
	private String phoneNumber;
	private String rating;
	private Category category;
	private List<String> photoUrl;
	private String resUrl;
	private String name;
	private String categories;
	private String distanceMiles;

	public static List<Restaurant> fromJSON(JSONObject jsonObject) {
		List<Restaurant> resList = new ArrayList<Restaurant>();
		JSONArray array = null;
		try {
			array = jsonObject.getJSONArray("businesses");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (array != null && array.length() > 0) {
			for (int i = 0; i < array.length(); i++) {
				Restaurant res = new Restaurant();
				JSONObject busiJsonObj;
				try {
					busiJsonObj = (JSONObject) array.getJSONObject(i);
					JSONArray catArray = busiJsonObj.getJSONArray("categories");
					res.setResUrl(busiJsonObj.getString("image_url"));
					res.setName(busiJsonObj.getString("name"));
					res.setRating(busiJsonObj.getString("rating"));
					res.setCategories(catArray.toString());
					res.setDistanceMiles(busiJsonObj.getString("distance"));
					resList.add(res);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		return resList;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<String> getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(List<String> photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDistanceMiles() {
		return distanceMiles;
	}

	public void setDistanceMiles(String distanceMiles) {
		this.distanceMiles = distanceMiles;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

}
