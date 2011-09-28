package com.fbrs.zombieoutbreak.pages;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.fbrs.zombieoutbreak.R;
import com.fbrs.zombieoutbreak.ZombieOutbreak;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class ZombieStats extends Activity implements Runnable{

	private Handler mHandler = new Handler();
	private TextView countdown;

	private final static String zeroOffset = ":0";
	private static String timeString;
	
	long zombieTime = System.currentTimeMillis() + 10000000;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zombiestats);
		 AdView adView = (AdView)this.findViewById(R.id.adView);
		    adView.loadAd(new AdRequest());


		StaticContent.setupButtons(this,3);

		countdown = (TextView)this.findViewById(R.id.countdown);
		
		 SharedPreferences prefs = getSharedPreferences(ZombieOutbreak.TAG,1);
	     long since = prefs.getLong("zombie_since", -1);
	     int infected = prefs.getInt("infections", 0);
	     
	     
	     zombieTime = prefs.getLong("last_zombie",System.currentTimeMillis() + 10000000);
		setTime(zombieTime - System.currentTimeMillis());
		
		TextView zombietext = (TextView)findViewById(R.id.zombietext);
		if(since > 0)
		{
			Date d = new Date(since);
			zombietext.setText("You have been a zombie since " + d.toString() +".\n\n" 
					+ "You have infected " + infected + " people." + "\n\n Countdown until all zombies expire:");
		}
		else
	
		
		
		mHandler.removeCallbacks(this);
        mHandler.postDelayed(this, 1000);
       
	}
	@Override
	public void onPause()
	{
		super.onPause();
		mHandler.removeCallbacks(this);
	}


	public void setTime(long t)
	{
		long time = t/1000;

		long seconds = time % 60;
		long min = ((time % 3600) / 60);
		long hours = (time / 3600) % 24;
		long days = time / 86400;

		timeString = "" + days +"days: \n" + hours + "hours";

		if(min < 10)
			timeString += ":0" + min;
		else
			timeString += ":" + min;

		if(seconds < 10)
			timeString += ":0" + seconds;
		else
			timeString += ":" + seconds;


		countdown.setText(timeString);
		
        mHandler.postDelayed(this, 1000); 
	}


	@Override
	public void run() {
		setTime(zombieTime - System.currentTimeMillis());
		
		
	}

}