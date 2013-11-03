package com.wheretoeat.helper;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

	public static void markLocationOnMap(double[] coordinates, GoogleMap googleMap) {

		// double[] d = GoogleMapHelper.getCurrentlocation(getBaseContext());
		LatLng currentLocation = new LatLng(coordinates[0], coordinates[1]);

		googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));
		googleMap.setMyLocationEnabled(true);
		googleMap.getUiSettings().setZoomControlsEnabled(false);
	}

}
