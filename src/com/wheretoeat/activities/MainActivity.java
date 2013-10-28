package com.wheretoeat.activities;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.wheretoeat.adapters.SectionPagerAdapter;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	private static final String TAG = "MainActivity";
	SectionPagerAdapter sectionPagerAdapter;
	ViewPager viewPager;
	ActionBar actionBar;
	GoogleMap googleMap;
	MapFragment mapFragment;
	PagerTabStrip page;

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClickZoomOutIn(View v) {

		int mainLayoutHeight = (findViewById(R.id.main_layout)).getHeight();
		int id = v.getId();
		ImageButton imgBtn = null;
		FrameLayout pagerFrameLayout = (FrameLayout) findViewById(R.id.pager_container);
		FrameLayout mapFrameLayout = (FrameLayout) findViewById(R.id.map_fragment_container);
		int titleHeight = page.getHeight();
		String tag = "out";
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		switch (id) {
		case R.id.imgBtnMap:
			imgBtn = (ImageButton) v.findViewById(R.id.imgBtnMap);
			tag = getBtnTag(imgBtn);
			if (tag.equals("out")) {
				params.height = mainLayoutHeight - titleHeight;
				imgBtn.setTag("in");
			} else {
				params.height = mainLayoutHeight / 2;
				imgBtn.setTag("out");
			}
			mapFrameLayout.setLayoutParams(params);

			break;
		case R.id.imgBtnPager:
			imgBtn = (ImageButton) v.findViewById(R.id.imgBtnPager);
			tag = getBtnTag(imgBtn);
			if (tag.equals("out")) {
				params.height = mainLayoutHeight;
				imgBtn.setTag("in");
			} else {
				params.height = mainLayoutHeight / 2;
				imgBtn.setTag("out");
			}
			pagerFrameLayout.setLayoutParams(params);
			break;
		default:
			break;
		}

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
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
