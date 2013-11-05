package com.wheretoeat.helper;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapHelper {

	public static double[] getCurrentlocation(Context context) {
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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

	public static void markLocationOnMap(double[] coordinates, GoogleMap googleMap, String title) {

		// double[] d = GoogleMapHelper.getCurrentlocation(getBaseContext());
		LatLng currentLocation = new LatLng(coordinates[0], coordinates[1]);
		BitmapDescriptor descriptor = null;
		if (title.equalsIgnoreCase("CurrentLocation")) {
			descriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
		} else {
			descriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		}

		googleMap.addMarker(new MarkerOptions().position(currentLocation).title(title).icon(descriptor));
		// googleMap.addMarker(new
		// MarkerOptions().position(currentLocation).title(title).icon(BitmapDescriptorFactory.fromAsset(getUrl("1"))));
		// googleMap.addMarker(new
		// MarkerOptions().position(currentLocation).title(title).icon(BitmapDescriptorFactory.fromBitmap(bmp)));
	}

	public static String getUrl(String text) {
		return "https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=" + text + "|FF0000|000000";
	}

	public static Bitmap createBitmap(String text) {
		Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		Bitmap bmp = Bitmap.createBitmap(200, 50, conf);
		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint();

		canvas.drawText(text, 0, 50, paint);
		return bmp;
	}

}
