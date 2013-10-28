package com.wheretoeat.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;

import com.wheretoeat.adapters.SectionPagerAdapter;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	SectionPagerAdapter sectionPagerAdapter;
	ViewPager viewPager;
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.viewPagerCategory);
		PagerTabStrip page = (PagerTabStrip) findViewById(R.id.pager_title_strip);

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
