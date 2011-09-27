package com.fbrs.zombieoutbreak.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.fbrs.zombieoutbreak.R;
import com.fbrs.zombieoutbreak.ZombieOutbreak;
import com.fbrs.zombieoutbreak.net.ServerConnection;


public class ZombieOutbreakService extends Service{
	
	public static final String TAG = "ZombieOutbreakLocationService";
	
	private Location currentBestLocation;
	 private static final int HELLO_ID = 1;
	private Handler mHandler = new Handler();

	 @Override
	  public void onCreate() {
		 //add a notification
		 String ns = Context.NOTIFICATION_SERVICE;
		 NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		 int icon = R.drawable.zombie_notification;
		 CharSequence tickerText = "Zombie Outbreak is running";
		 long when = System.currentTimeMillis();

		 Notification notification = new Notification(icon, tickerText, when);
		 notification.flags = Notification.FLAG_ONGOING_EVENT;
		 Context context = getApplicationContext();
		 CharSequence contentTitle = "Zombie Outbreak";
		 CharSequence contentText = "Zombie Outbreak is running in the background.";
		 Intent notificationIntent = new Intent(this, ZombieOutbreak.class);
		 PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		 notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		

		 mNotificationManager.notify(HELLO_ID, notification);
		 
		 // Acquire a reference to the system Location Manager
			final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

			// Define a listener that responds to location updates
			final LocationListener locationListener = new LocationListener() {
			    public void onLocationChanged(Location location) {
			      // Called when a new location is found by the network location provider.
			    	//if(GPS.isBetterLocation(location, currentBestLocation)) {
			    		Log.v(TAG, "Updateing Location");
			    		Log.v(TAG, "New Location: " + location.getLatitude() + location.getLongitude() + "  Accuracy: " + location.getAccuracy());
			    		SharedPreferences prefs = getSharedPreferences(ZombieOutbreak.TAG,1);
			    		String name = prefs.getString("Username", null);
			    		String pword = prefs.getString("Password", null);
			    		
			    	    if(name != null && pword != null)
			    	    	ServerConnection.sendRequest("/GPS/Upload/" + name + "/"+ pword + "/" + location.getLatitude() + "/" + location.getLongitude());
			    		currentBestLocation = location;
			    		
			    	//}
			    }

			    public void onStatusChanged(String provider, int status, Bundle extras) {}

			    public void onProviderEnabled(String provider) {}

			    public void onProviderDisabled(String provider) {}
			  };

			// Register the listener with the Location Manager to receive location updates
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 180000, 20, locationListener);
		 
	  }

	  @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	      return START_STICKY;
	  }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
