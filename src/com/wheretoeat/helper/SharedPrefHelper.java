package com.wheretoeat.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.wheretoeat.models.Filters;

public class SharedPrefHelper {

	private static final String FILTERS_SHARED_PREF = "filters-shared-pref";
	private static final String PRICE1_KEY = "price1_key";
	private static final String PRICE2_KEY = "price2_key";
	private static final String PRICE3_KEY = "price3_key";
	private static final String PRICE4_KEY = "price4_key";
	private static final String OPEN_NOW_KEY = "open_now_key";
	private static final String SHOW_VISITED_KEY = "show_visited_key";
	private static final String CURRENT_LATITUDE_KEY = "latitude";
	private static final String CURRENT_LONGITUDE_KEY = "logitude";

	public static void AddFiltersSharedPrefs(Filters filters, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(FILTERS_SHARED_PREF, 0);
		Editor edit = prefs.edit();
		edit.putBoolean(PRICE1_KEY, filters.isPrice1());
		edit.putBoolean(PRICE2_KEY, filters.isPrice2());
		edit.putBoolean(PRICE3_KEY, filters.isPrice3());
		edit.putBoolean(PRICE4_KEY, filters.isPrice4());
		edit.putBoolean(OPEN_NOW_KEY, filters.isOpenNow());
		edit.putBoolean(SHOW_VISITED_KEY, filters.isShowVisited());
		edit.commit();
	}

	public static boolean getPrice1Pref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(FILTERS_SHARED_PREF, 0);
		return prefs.getBoolean(PRICE1_KEY, true);
	}

	public static boolean getPrice2Pref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(FILTERS_SHARED_PREF, 0);
		return prefs.getBoolean(PRICE2_KEY, true);
	}

	public static boolean getPrice3Pref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(FILTERS_SHARED_PREF, 0);
		return prefs.getBoolean(PRICE3_KEY, true);
	}

	public static boolean getPrice4Pref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(FILTERS_SHARED_PREF, 0);
		return prefs.getBoolean(PRICE4_KEY, true);
	}

	public static boolean isShowVisitedPrefs(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(FILTERS_SHARED_PREF, 0);
		return prefs.getBoolean(SHOW_VISITED_KEY, true);
	}

	public static boolean getOpenNowPref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(FILTERS_SHARED_PREF, 0);
		return prefs.getBoolean(OPEN_NOW_KEY, true);
	}

}
