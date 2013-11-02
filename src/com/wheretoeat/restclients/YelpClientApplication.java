package com.wheretoeat.restclients;

import android.content.Context;

public class YelpClientApplication extends com.activeandroid.app.Application {

	public static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		YelpClientApplication.context = this;
	}

	public static YelpClient getYelpClient() {
		return (YelpClient) YelpClient.getInstance(YelpClient.class, YelpClientApplication.context);
	}
}
