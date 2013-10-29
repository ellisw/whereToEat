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
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wheretoeat.adapters.SectionPagerAdapter;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	private static final String TAG = "MainActivity";
	SectionPagerAdapter sectionPagerAdapter;
	ViewPager viewPager;
	ActionBar actionBar;
	GoogleMap googleMap;
	SupportMapFragment supportMapFragment;
	PagerTabStrip page;
	View dialogView;
	ToggleButton price1;
	ToggleButton price2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.viewPagerCategory);
		page = (PagerTabStrip) findViewById(R.id.pager_title_strip);

		viewPager.setOnPageChangeListener(pageChangeListener);
		viewPager.setAdapter(sectionPagerAdapter);
		viewPager.setCurrentItem(1);

		supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		googleMap = supportMapFragment.getMap();

		markCurrentLocation();

	}

	private void markCurrentLocation() {

		double[] d = getCurrentlocation();
		LatLng currentLocation = new LatLng(d[0], d[1]);

		googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));
		googleMap.setMyLocationEnabled(true);
		googleMap.getUiSettings().setZoomControlsEnabled(false);
	}

	public double[] getCurrentlocation() {
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);

		Location l = null;
		for (int i = 0; i < providers.size(); i++) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}
		double[] gps = new double[2];

		if (l != null) {
			gps[0] = l.getLatitude();
			gps[1] = l.getLongitude();
		}
		return gps;
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

	private void showFilterDialog() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dialogView = inflater.inflate(R.layout.setttings_dialog, null, true);
		price1 = (ToggleButton) dialogView.findViewById(R.id.price1);
		price2 = (ToggleButton) dialogView.findViewById(R.id.price2);
		
		dialogBuilder.setView(dialogView);
		dialogBuilder.setTitle("Set the Filter");
		dialogBuilder.setPositiveButton("Save", dialogOnClickListener);
		dialogBuilder.setNegativeButton("Cancel", dialogOnClickListener);
		dialogBuilder.create().show();
	}

	OnClickListener dialogOnClickListener = new OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			switch (which) {
			// Save button
			case -1:
				Log.d(TAG, "Saved!");
				Log.d(TAG, "Price1 :" + price1.isChecked());
				Log.d(TAG, "Price2 :" + price2.isChecked());
				break;
			// Cancel Button
			case -2:
				Log.d(TAG, "Cancel!");
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

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

}
