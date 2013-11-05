package com.wheretoeat.helper;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
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
		// Bitmap bmp = createBitmap("1");
		// googleMap.addMarker(new
		// MarkerOptions().position(currentLocation).title(title).icon(BitmapDescriptorFactory.fromBitmap(bmp)));
	}

	/*
	 * public static Bitmap createBitmap(String text) { Bitmap.Config conf =
	 * Bitmap.Config.ARGB_8888; Bitmap bmp = Bitmap.createBitmap(200, 50, conf);
	 * Canvas canvas = new Canvas(bmp); Paint paint = new Paint();
	 * paint.setStyle(Style.FILL); paint.setColor(Color.WHITE);
	 * paint.setTypeface(tf); paint.setTextAlign(Align.CENTER);
	 * paint.setTextSize(convertToPixels(context, 11));
	 * 
	 * canvas.drawText(text, 0, 50, paint); return bmp; }
	 */
}
