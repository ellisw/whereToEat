package com.wheretoeat.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wheretoeat.activities.R;
import com.wheretoeat.adapters.RestaurantsAdpater;
import com.wheretoeat.models.Restaurant;
import com.wheretoeat.restclients.YelpClient;
import com.wheretoeat.restclients.YelpClientApplication;

public class NearbyFragment extends Fragment {

	protected static final String TAG = "NearbyFragment";
	List<Restaurant> resList;
	ListView listView;
	RestaurantsAdpater adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_nearby, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = (ListView) getActivity().findViewById(R.id.lv_res);
		resList = new ArrayList<Restaurant>();
		adapter = new RestaurantsAdpater(getActivity(), resList);
		listView.setAdapter(adapter);
		searchApi();

	}

	private void searchApi() {

		YelpClient client = YelpClientApplication.getYelpClient();
		client.search("restaurants", 37.5294083, -121.9814466, new JsonHttpResponseHandler() {

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

}
