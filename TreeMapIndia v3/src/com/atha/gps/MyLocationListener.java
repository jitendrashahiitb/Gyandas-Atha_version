package com.atha.gps;

import android.app.AlertDialog;
import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.widget.Toast;

public class MyLocationListener implements LocationListener
{

	public static double	latitude	= -1;
	public static double	longitude	= -1;
	Context	             context;

	public MyLocationListener(Context context)
	{
		this.context = context;
	}

	@Override
	public void onLocationChanged(Location loc)
	{
		String str = "lat: " + loc.getLatitude() + ", lon: " + loc.getLongitude();
		// Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("GPS")//
		.setCancelable(true)//
		.setMessage(str)//
		.show();

		latitude = loc.getLatitude();
		longitude = loc.getLongitude();
	}

	@Override
	public void onProviderDisabled(String provider)
	{
		// print "Currently GPS is Disabled";
	}

	@Override
	public void onProviderEnabled(String provider)
	{
		// print "GPS got Enabled";
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		String showStatus = null;
		if (status == LocationProvider.AVAILABLE)
		{
			showStatus = "Available";
		}
		if (status == LocationProvider.TEMPORARILY_UNAVAILABLE)
		{
			showStatus = "Temporarily Unavailable";
		}
		if (status == LocationProvider.OUT_OF_SERVICE)
		{
			showStatus = "Out of Service";
		}
		Toast.makeText(context, "status: " + showStatus, Toast.LENGTH_SHORT).show();
	}
}