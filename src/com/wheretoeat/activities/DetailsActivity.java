package com.wheretoeat.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wheretoeat.adapters.ReviewsAdapter;
import com.wheretoeat.models.Restaurant;
import com.wheretoeat.models.Review;
import com.wheretoeat.restclients.PlacesClient;
import com.wheretoeat.restclients.RestClientApplication;

public class DetailsActivity extends Activity {

	private final String TAG = "DETAILS";
	private List<Review> reviews;
	private ReviewsAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		String ref = getIntent().getStringExtra("ref");
		fetchDetails(ref);
		reviews = new ArrayList<Review>();
		adapter = new ReviewsAdapter(this,reviews);
		ListView lvReviewsList = (ListView) findViewById(R.id.lvReviewsList);
		lvReviewsList.setAdapter(adapter);
	}

	private void setupRestaurant(Restaurant rest) {
		if(rest == null) {
			return;
		}
//		TextView tvReviewCount = (TextView) findViewById(R.id.tvReviewCount);
		TextView tvCategories = (TextView) findViewById(R.id.tvCategories);
		TextView tvRestaurantName = (TextView) findViewById(R.id.tvRestaurantName);
		TextView tvDirectionsLabel = (TextView) findViewById(R.id.tvDirectionsLabel);
		TextView tvCallLabel = (TextView) findViewById(R.id.tvCallLabel);
		TextView tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
		TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
		ImageView ivCall = (ImageView) findViewById(R.id.ivCall);
//		ImageView ivDirections = (ImageView) findViewById(R.id.ivDirections);
//		ImageView ivRating = (ImageView) findViewById(R.id.ivRating);
		adapter.clear();
		adapter.addAll(rest.getReviews());
		
		tvRestaurantName.setText(rest.getName());
		tvCategories.setText(rest.getCategories());
		tvPhoneNumber.setText(rest.getPhoneNumber());
		tvAddress.setText(rest.getAddress());
		

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
	

}
