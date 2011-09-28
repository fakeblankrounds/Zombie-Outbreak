package com.fbrs.zombieoutbreak.pages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.fbrs.zombieoutbreak.R;
import com.fbrs.zombieoutbreak.ZombieOutbreak;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class RadarPage extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radar);
        StaticContent.setupButtons(this,2);
        AdView adView = (AdView)this.findViewById(R.id.adView);
	    adView.loadAd(new AdRequest());


        
        TextView view = (TextView)findViewById(R.id.newstext);
        String text = "";
        try {
			URL u = new URL ( "http://www.fakeblankrounds.com/zombienews.txt" );
			HttpURLConnection con = (HttpURLConnection) u.openConnection();
			BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String temp = "";
			while((temp = input.readLine()) != null)
			{
				text += temp + "\n";
			}
			
		} catch (MalformedURLException e) {
			Log.e(ZombieOutbreak.TAG, "Failed to connect to service");
			
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(ZombieOutbreak.TAG, "Failed to connect to service");
			e.printStackTrace();
		}
        view.setText(text);
    }

}
