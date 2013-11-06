package com.wheretoeat.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wheretoeat.activities.R;
import com.wheretoeat.models.Restaurant;

public class RestaurantsAdpater extends ArrayAdapter<Restaurant> {
	private static final String TAG = "RestaurantsAdpater";
	private Context context;
	List<Restaurant> restaurants;
	ImageView imgRestaurant;
	TextView tvNumbers;
	TextView tvName;
	TextView tvInfo;
	// TextView tvMiles;
	TextView tvRatings;

	public RestaurantsAdpater(Context context, List<Restaurant> restaurants) {
		super(context, 0, restaurants);
		this.context = context;
		this.restaurants = restaurants;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.fragment_restaurant_item, null);
		}
		initViews(view);

		// ImageLoader.getInstance().displayImage(restaurants.get(position).getResUrl(),
		// imgRestaurant);
		tvNumbers.setText(position + 1 + "");
		tvName.setText(restaurants.get(position).getName());
		tvInfo.setText(restaurants.get(position).getCategories());
		tvRatings.setText(restaurants.get(position).getRating() + "/5");
		Log.d(TAG, restaurants.get(position).getResId());
		view.setTag(restaurants.get(position).getResId());
		return view;
	}

	private void initViews(View view) {
		// imgRestaurant = (ImageView) view.findViewById(R.id.img_res);
		tvNumbers = (TextView) view.findViewById(R.id.tv_numbers);
		tvName = (TextView) view.findViewById(R.id.tv_name);
		// tvMiles = (TextView) view.findViewById(R.id.tv_miles);
		tvInfo = (TextView) view.findViewById(R.id.tv_body);
		tvRatings = (TextView) view.findViewById(R.id.tv_ratings);
	}

	@Override
	public int getCount() {
		return restaurants.size();
	}

	@Override
	public Restaurant getItem(int position) {
		return restaurants.get(position);
	}

}
