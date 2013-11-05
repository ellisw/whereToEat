package com.wheretoeat.activities;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.wheretoeat.adapters.SectionPagerAdapter;
import com.wheretoeat.fragments.NearbyFragment.OnMapUpdateListener;
import com.wheretoeat.helper.GoogleMapHelper;
import com.wheretoeat.helper.SharedPrefHelper;
import com.wheretoeat.models.Filters;
import com.wheretoeat.models.Restaurant;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener, OnMapUpdateListener {

	private static final String TAG = "MainFragmentActivity";
	private static int DETAILS_REQUEST_CODE = 1;
	final static int ZOOM_LEVEL = 17;
	SectionPagerAdapter sectionPagerAdapter;
	ViewPager viewPager;
	ActionBar actionBar;
	GoogleMap googleMap;
	SupportMapFragment supportMapFragment;
	PagerTabStrip page;
	View dialogView;
	// Dialog Views
	ToggleButton price1;
	ToggleButton price2;
	ToggleButton price3;
	ToggleButton price4;
	Switch swtchShowVisited;
	Switch swtchOpenNow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// create Pager Adapter.
		sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
		// get ViewPager.
		viewPager = (ViewPager) findViewById(R.id.viewPagerCategory);
		page = (PagerTabStrip) findViewById(R.id.pager_title_strip);
		// Set Listener for ViewPager
		viewPager.setOnPageChangeListener(pageChangeListener);
		// Set Adapter on ViewPager
		viewPager.setAdapter(sectionPagerAdapter);
		viewPager.setCurrentItem(1);

		supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		googleMap = supportMapFragment.getMap();

		double[] coordinates = GoogleMapHelper.getCurrentlocation(getBaseContext());

		LatLng currentLocation = new LatLng(coordinates[0], coordinates[1]);
		GoogleMapHelper.markLocationOnMap(coordinates, googleMap, "CurrentLocation");
		googleMap.setMyLocationEnabled(true);
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, ZOOM_LEVEL));
		googleMap.getUiSettings().setZoomControlsEnabled(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_filter) {
			showFilterDialog();
		}
		return true;
	}

	// display dialog box
	private void showFilterDialog() {

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dialogView = inflater.inflate(R.layout.dialog_filter, null, true);
		initDialogView();
		dialogBuilder.setView(dialogView);
		dialogBuilder.setTitle("Set the Filter");
		dialogBuilder.setPositiveButton(R.string.save, dialogOnClickListener);
		dialogBuilder.setNegativeButton(R.string.cancle, dialogOnClickListener);
		dialogBuilder.create().show();
	}

	// Initialize the views of dialog
	private void initDialogView() {

		price1 = (ToggleButton) dialogView.findViewById(R.id.price1);
		price2 = (ToggleButton) dialogView.findViewById(R.id.price2);
		price3 = (ToggleButton) dialogView.findViewById(R.id.price3);
		price4 = (ToggleButton) dialogView.findViewById(R.id.price4);
		swtchShowVisited = (Switch) dialogView.findViewById(R.id.swtch_show_visited);
		swtchOpenNow = (Switch) dialogView.findViewById(R.id.swtch_open_now);
		populateDialotValues();
	}

	// Populate the values in dialogbox stored from SharedPrefs
	private void populateDialotValues() {
		price1.setChecked(SharedPrefHelper.getPrice1Pref(MainActivity.this));
		price2.setChecked(SharedPrefHelper.getPrice2Pref(MainActivity.this));
		price3.setChecked(SharedPrefHelper.getPrice3Pref(MainActivity.this));
		price4.setChecked(SharedPrefHelper.getPrice4Pref(MainActivity.this));
		swtchShowVisited.setChecked(SharedPrefHelper.isShowVisitedPrefs(MainActivity.this));
		swtchOpenNow.setChecked(SharedPrefHelper.getOpenNowPref(MainActivity.this));

	}

	// Dialog button click listener
	OnClickListener dialogOnClickListener = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {

			switch (which) {
			// Save button
			case -1:
				Filters filters = new Filters();

				filters.setPrice1(price1.isChecked());
				filters.setPrice2(price2.isChecked());
				filters.setPrice3(price3.isChecked());
				filters.setPrice4(price4.isChecked());
				filters.setOpenNow(swtchOpenNow.isChecked());
				filters.setShowVisited(swtchShowVisited.isChecked());

				SharedPrefHelper.AddFiltersSharedPrefs(filters, MainActivity.this);

				break;
			// Cancel Button
			case -2:
				break;
			default:
				break;
			}
		}
	};

	public void onClickZoomOutIn(View v) {

		int mainLayoutHeight = (findViewById(R.id.main_layout)).getHeight();
		int id = v.getId();
		ImageButton imgBtn = null;
		FrameLayout pagerFrameLayout = (FrameLayout) findViewById(R.id.pager_container);
		FrameLayout mapFrameLayout = (FrameLayout) findViewById(R.id.map_fragment_container);
		int titleHeight = page.getHeight();
		String tag = "out";

		LinearLayout.LayoutParams pageParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LinearLayout.LayoutParams mapParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		switch (id) {
		case R.id.imgBtnMap:
			imgBtn = (ImageButton) v.findViewById(R.id.imgBtnMap);
			tag = getBtnTag(imgBtn);
			if (tag.equals("out")) {
				pageParam.height = titleHeight;
				mapParam.height = mainLayoutHeight - titleHeight;
				((ImageButton) findViewById(R.id.imgBtnPager)).setVisibility(Button.INVISIBLE);
				imgBtn.setTag("in");
				imgBtn.setImageResource(R.drawable.ic_zoom_in);
			} else {
				((ImageButton) findViewById(R.id.imgBtnPager)).setVisibility(Button.VISIBLE);
				mapParam.height = mainLayoutHeight / 2;
				pageParam.height = mainLayoutHeight / 2;
				imgBtn.setImageResource(R.drawable.ic_zoom_out);
				imgBtn.setTag("out");
			}
			break;
		case R.id.imgBtnPager:
			imgBtn = (ImageButton) v.findViewById(R.id.imgBtnPager);
			tag = getBtnTag(imgBtn);
			if (tag.equals("out")) {
				pageParam.height = mainLayoutHeight;
				mapParam.height = 0;
				imgBtn.setTag("in");
				imgBtn.setImageResource(R.drawable.ic_zoom_in);
			} else {
				mapParam.height = mainLayoutHeight / 2;
				pageParam.height = mainLayoutHeight / 2;
				imgBtn.setImageResource(R.drawable.ic_zoom_out);
				imgBtn.setTag("out");
			}
			break;
		default:
			break;
		}

		pagerFrameLayout.setLayoutParams(pageParam);
		mapFrameLayout.setLayoutParams(mapParam);

	}

	private String getBtnTag(ImageButton btn) {

		if (btn != null && btn.getTag() != null) {
			return btn.getTag().toString();
		}
		return "out";

	}

	OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			try {
				getActionBar().setSelectedNavigationItem(position);
			} catch (Exception e) {
				Log.d("Exception", "" + e);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

	};

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
		Log.d(TAG, "onTabSelected : " + tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onMapUpdate(List<Restaurant> resList) {
		if (resList != null && resList.size() > 0) {
			for (Restaurant restaurant : resList) {
				double[] coordinates = restaurant.getLocation();
				GoogleMapHelper.markLocationOnMap(coordinates, googleMap, restaurant.getName());
			}

		}
	}

	@Override
	public void onDetailSelected(String ref) {
		Intent i = new Intent(this, DetailsActivity.class);
		i.putExtra("ref", ref);
		startActivityForResult(i, DETAILS_REQUEST_CODE);	
	}

}
