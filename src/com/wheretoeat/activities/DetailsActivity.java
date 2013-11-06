package com.wheretoeat.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wheretoeat.adapters.ReviewsAdapter;
import com.wheretoeat.helper.GoogleMapHelper;
import com.wheretoeat.models.Restaurant;
import com.wheretoeat.models.Review;
import com.wheretoeat.restclients.PlacesClient;
import com.wheretoeat.restclients.RestClientApplication;

public class DetailsActivity extends Activity {

	private final String TAG = "DETAILS";
	private List<Review> reviews;
	private ReviewsAdapter adapter;
	String phoneNumber;
	String website;
	String direction;
	private RatingBar reviewRatingBar;
	private TextView tvDetailviewRatings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		String ref = getIntent().getStringExtra("ref");
		fetchDetails(ref);
		reviews = new ArrayList<Review>();
		adapter = new ReviewsAdapter(this, reviews);
		ListView lvReviewsList = (ListView) findViewById(R.id.lvReviewsList);
		lvReviewsList.setAdapter(adapter);
	}

	private void setupRestaurant(Restaurant rest) {
		if (rest == null) {
			return;
		}
		// TextView tvReviewCount = (TextView) findViewById(R.id.tvReviewCount);
		TextView tvCategories = (TextView) findViewById(R.id.tvCategories);
		TextView tvRestaurantName = (TextView) findViewById(R.id.tvRestaurantName);
		TextView tvDirectionsLabel = (TextView) findViewById(R.id.tvDirectionsLabel);
		TextView tvCallLabel = (TextView) findViewById(R.id.tvCallLabel);
		TextView tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
		TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
		ImageView ivCall = (ImageView) findViewById(R.id.ivCall);
		reviewRatingBar = (RatingBar) findViewById(R.id.ivRating);
		tvDetailviewRatings = (TextView) findViewById(R.id.tvDetailviewRatings);
		// ImageView ivDirections = (ImageView) findViewById(R.id.ivDirections);
		// ImageView ivRating = (ImageView) findViewById(R.id.ivRating);
		adapter.clear();
		adapter.addAll(rest.getReviews());

		tvRestaurantName.setText(rest.getName());
		tvCategories.setText(rest.getCategories());
		tvPhoneNumber.setText(rest.getPhoneNumber());
		phoneNumber = rest.getPhoneNumber();
		tvAddress.setText(rest.getAddress());
		direction = rest.getAddress();
		website = rest.getResUrl();
		reviewRatingBar.setRating(Float.parseFloat(rest.getRating()));
		tvDetailviewRatings.setText(rest.getRating());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	private void fetchDetails(String ref) {
		PlacesClient client = RestClientApplication.getPlacesClient();
		client.getDetails(ref, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d(TAG, "Places response = " + response);
				Restaurant r = null;
				try {
					r = Restaurant.fromJson(response);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setupRestaurant(r);
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d(TAG, "Places Failure = " + t.getMessage());
			}
		});
	}

	// phone call intent
	public void callNumber(View v) {
		Intent callIntent = new Intent(Intent.ACTION_DIAL);
		callIntent.setData(Uri.parse("tel:" + phoneNumber));
		startActivity(callIntent);
	}

	// website intent
	public void openSite(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
		startActivity(browserIntent);
	}

	// direction intent
	public void getMap(View v) {
		double[] coordinates = GoogleMapHelper.getCurrentlocation(getBaseContext());
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		String data = String.format("geo:%s,%s", 37.7764, -122.417);
		int zoomLevel = 15;

		data = String.format("%s?z=%s", data, zoomLevel);

		intent.setData(Uri.parse(data));
		startActivity(intent);
	}

}
