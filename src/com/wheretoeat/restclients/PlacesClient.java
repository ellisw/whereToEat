package com.wheretoeat.restclients;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class PlacesClient {

	private static PlacesClient instance;
	Context context;
	String apiKey;
	static String BROWSER_API_KEY = "AIzaSyBQy7xjiAd8k4RIlKkeBiQl764UC2C_Mks";
	public static final String NEARBY_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	protected static final String TAG = "PlacesClient";

	private PlacesClient(Context context) {
		this.context = context;
	}

	public static PlacesClient getInstance(Context context) {

		if (instance == null) {
			try {
				instance = new PlacesClient(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public void searchRestaurants(String type, String rankby, double latitude, double longitude, AsyncHttpResponseHandler handler) {
		String url = makeUrl(type, rankby, latitude, longitude, NEARBY_BASE_URL);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, handler);
	}
	
	public void getDetails(String ref, AsyncHttpResponseHandler handler){
		String url = "https://maps.googleapis.com/maps/api/place/details/json?reference=" + ref + "&sensor=true&key=" + BROWSER_API_KEY;
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, handler);
	}
	
	// https://maps.googleapis.com/maps/api/place/search/json?location=28.632808,77.218276&radius=500&types=atm&sensor=false&key=apikey
	private String makeUrl(String type, String rankby, double latitude, double longitude, String baseurl) {
		StringBuilder urlString = new StringBuilder(baseurl);

		if (type.equals("")) {
			urlString.append("&location=");
			urlString.append(Double.toString(latitude));
			urlString.append(",");
			urlString.append(Double.toString(longitude));
			if (rankby == null) {
				urlString.append("&radius=5000");
			} else {
				urlString.append("&rankby=" + rankby);
			}

			urlString.append("&sensor=false&key=" + BROWSER_API_KEY);
		} else {
			urlString.append("&location=");
			urlString.append(Double.toString(latitude));
			urlString.append(",");
			urlString.append(Double.toString(longitude));
			urlString.append("&types=" + type);
			if (rankby == null) {
				urlString.append("&radius=5000");
			} else {
				urlString.append("&rankby=" + rankby);
			}
			urlString.append("&sensor=false&key=" + BROWSER_API_KEY);

		}

		return urlString.toString();
	}
}
