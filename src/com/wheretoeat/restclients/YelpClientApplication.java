package com.wheretoeat.restclients;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class YelpClientApplication extends com.activeandroid.app.Application {

	public static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		YelpClientApplication.context = this;
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
				.build();
		ImageLoader.getInstance().init(config);
	}

	public static YelpClient getYelpClient() {
		return (YelpClient) YelpClient.getInstance(YelpClient.class, YelpClientApplication.context);
	}
}
