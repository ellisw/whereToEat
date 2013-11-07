package com.wheretoeat.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wheretoeat.activities.R;
import com.wheretoeat.adapters.RestaurantsAdpater;
import com.wheretoeat.helper.GoogleMapHelper;
import com.wheretoeat.models.Restaurant;
import com.wheretoeat.restclients.PlacesClient;
import com.wheretoeat.restclients.RestClientApplication;
import com.wheretoeat.restclients.YelpClient;

public class NearbyFragment extends Fragment {

	protected static final String TAG = "NearbyFragment";

	List<Restaurant> resList;
	ListView listView;
	RestaurantsAdpater adapter;
	OnMapUpdateListener callBackHandler;

	public interface OnMapUpdateListener {
		public void onMapUpdate(List<Restaurant> resList);

		public void onDetailSelected(String ref);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnMapUpdateListener) {
			callBackHandler = (OnMapUpdateListener) activity;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = (ListView) getActivity().findViewById(R.id.lv_nearby_res);
		resList = new ArrayList<Restaurant>();
		adapter = new RestaurantsAdpater(getActivity(), resList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
				Log.d("TESTING", view.getTag().toString());
				callBackHandler.onDetailSelected(view.getTag().toString());
				// fetchDetails(view.getTag().toString());
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_nearby, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		// searchYelpApi();
		searchPlacesApi();
	}

	private void searchYelpApi() {
		double[] coords = GoogleMapHelper.getCurrentlocation(getActivity());
		YelpClient client = RestClientApplication.getYelpClient();
		client.searchRestaurants("restaurants", "1", coords[0], coords[1], new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject res) {
				Log.d(TAG, "JSONObject: " + res.toString());
				List<Restaurant> restauList = Restaurant.fromJSON(res);
				Log.d(TAG, "resList size: " + resList.size());
				adapter.addAll(restauList);
			}

			@Override
			public void onFailure(Throwable t) {
				Log.e(TAG, "Erros - " + t.getMessage());
			}
		});
	}

	private void searchPlacesApi() {
		double[] coords = GoogleMapHelper.getCurrentlocation(getActivity());
		PlacesClient client = RestClientApplication.getPlacesClient();
		client.searchRestaurants("restaurant", "distance", coords[0], coords[1], new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d(TAG, "Places response = " + response);
				resList = Restaurant.fromPlacesJSON(response);
				adapter.addAll(resList);
				callBackHandler.onMapUpdate(resList);
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d(TAG, "Places Failure = " + t.getMessage());
			}
		});
	}
}
