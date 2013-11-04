package com.wheretoeat.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.wheretoeat.models.Restaurant;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

	}

	private void setupRestaurant(Restaurant rest) {
		TextView tvReviewCount = (TextView) findViewById(R.id.tvReviewCount);
		TextView tvCategories = (TextView) findViewById(R.id.tvCategories);
		TextView tvRestaurantName = (TextView) findViewById(R.id.tvRestaurantName);
		TextView tvDirectionsLabel = (TextView) findViewById(R.id.tvDirectionsLabel);
		TextView tvCallLabel = (TextView) findViewById(R.id.tvCallLabel);
		TextView tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
		ImageView ivCall = (ImageView) findViewById(R.id.ivCall);
		ImageView ivDirections = (ImageView) findViewById(R.id.ivDirections);
		ImageView ivRating = (ImageView) findViewById(R.id.ivRating);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

}
