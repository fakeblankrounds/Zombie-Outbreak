package com.fbrs.zombieoutbreak.pages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fbrs.zombieoutbreak.R;
import com.fbrs.zombieoutbreak.ZombieOutbreak;

public class StaticContent {
	
	public static void setupButtons(final Activity context, int view)
	{
		Button home = (Button)context.findViewById(R.id.homebutton);
		Button infest = (Button)context.findViewById(R.id.infestmap);
		Button radar = (Button)context.findViewById(R.id.radar);
		Button zstats = (Button)context.findViewById(R.id.buttonc);
		Button hstats = (Button)context.findViewById(R.id.buttond);
		TextView title = (TextView)context.findViewById(R.id.title);
		
		final Intent infest_intent = new Intent().setClass(context, InfestMap.class);
		final Intent home_intent = new Intent().setClass(context, ZombieOutbreak.class);
		final Intent radar_intent = new Intent().setClass(context, RadarPage.class);
		final Intent human_intent = new Intent().setClass(context, HumanStats.class);
		final Intent zombie_intent = new Intent().setClass(context, ZombieStats.class);
		
		  SharedPreferences prefs = context.getSharedPreferences(ZombieOutbreak.TAG,1);
	       if(!prefs.getBoolean("isHuman", true))
	       {
	    	   title.setBackgroundResource(R.drawable.zombiebackground);
	    	   home.setBackgroundResource(R.drawable.zombiebackground);
	       }
		
		if(view != 0)
		home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(home_intent);
				
			}
		});
		
		if(view != 1)
		infest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(infest_intent);
				
			}
		});
		
		if(view != 2)
			radar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					context.startActivity(radar_intent);
					
				}
			});
		if(view != 3)
			zstats.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					context.startActivity(zombie_intent);
					
				}
			});
		if(view != 4)
			hstats.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					context.startActivity(human_intent);
					
				}
			});
	}

}
