package com.wheretoeat.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wheretoeat.fragments.CategoriesFragment;
import com.wheretoeat.fragments.FavoritesFragment;
import com.wheretoeat.fragments.NearbyFragment;
import com.wheretoeat.fragments.TopRatedFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {

	public SectionPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Categories";
		case 1:
			return "Nearby";
		case 2:
			return "Top Rated";
		case 3:
			return "Favorites";
		default:
			break;
		}
		return null;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new CategoriesFragment();
			break;
		case 1:
			fragment = new NearbyFragment();
			break;
		case 2:
			fragment = new TopRatedFragment();
			break;
		case 3:
			fragment = new FavoritesFragment();
			break;
		default:
			return null;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 4;
	}

}
