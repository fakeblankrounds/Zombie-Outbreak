package com.fbrs.zombieoutbreak.pages;

import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.fbrs.zombieoutbreak.R;
import com.fbrs.zombieoutbreak.ZombieOutbreak;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class HumanStats extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.humanstats);
        StaticContent.setupButtons(this,4);
        AdView adView = (AdView)this.findViewById(R.id.adView);
	    adView.loadAd(new AdRequest());


        TextView stats = (TextView)findViewById(R.id.humantext);
        SharedPreferences prefs = getSharedPreferences(ZombieOutbreak.TAG,1);
        long time = prefs.getLong("human_since", -1);
        int survive = prefs.getInt("survived", 0);
        int zombieskilled = prefs.getInt("zombiesKilled", 0);
        int gun = prefs.getInt("gun", 0);
        Date hsince = new Date(time);
        
        stats.setText("You have been a human since: " + hsince.toString() + "\n\n" +
        		"You have survived " + survive + " encounters with a zombie" + "\n\n"+
        		"You have killed " + zombieskilled + " zombies");
    }

}