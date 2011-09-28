package com.fbrs.zombieoutbreak;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbrs.zombieoutbreak.account.Signin;
import com.fbrs.zombieoutbreak.net.ServerConnection;
import com.fbrs.zombieoutbreak.pages.StaticContent;
import com.fbrs.zombieoutbreak.service.ZombieOutbreakService;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class ZombieOutbreak extends Activity {

	public static final String TAG = "ZombieOutbreak";

	private static boolean shown = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		 AdView adView = (AdView)this.findViewById(R.id.adView);
		    adView.loadAd(new AdRequest());


		final Activity a = this;
		HandleAccount();

		Intent intent = new Intent(this, ZombieOutbreakService.class);
		startService(intent);
		Log.v(TAG, "Service Started");
		StaticContent.setupButtons(this,0);

		ServerConnection.StartRequestInNewThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					setPlayerStats();
				}
				catch(Exception e)
				{
					Log.e(TAG, "Couldn't connect to server");
				}
			}

		}, new Runnable() {

			@Override
			public void run() {
			
				SharedPreferences prefs = getSharedPreferences(ZombieOutbreak.TAG,1);
		if(!prefs.getBoolean("isHuman", true))
		{
			TextView statustext = (TextView) findViewById(R.id.statustext);
			statustext.setText("You are a Zombie");
			ImageView statusimg = (ImageView) findViewById(R.id.statusimg);
			statusimg.setImageResource(R.drawable.urazombie);
		}

		TextView counttext = (TextView) findViewById(R.id.count);
		counttext.setText("There are " + prefs.getInt("zombieCount", 0) + " Zombies and " + prefs.getInt("humanCount",0) + " Humans");
		StaticContent.setupButtons(a,0);

			}
			
		});


		

	}

	@Override
	public void onResume()
	{
		super.onResume();
		HandleAccount();
		try {
			setPlayerStats();
		}
		catch(Exception e)
		{
			Log.e(TAG, "Couldn't connect to server");
		}
	}

	public boolean HandleAccount()
	{
		AccountManager mAccountManager = AccountManager.get(this);

		Account mAccount[] = mAccountManager.getAccountsByType(getString(R.string.ACCOUNT_TYPE));

		if(mAccount.length == 0 && !shown)
		{
			Intent signin = new Intent().setClass(this, Signin.class);
			startActivity(signin);
			shown = true;
			return false;
		}
		else if(mAccount.length > 0){
			String credentials = mAccountManager.getPassword(mAccount[0]);
			SharedPreferences ZOSP = getSharedPreferences(ZombieOutbreak.TAG,1);
			SharedPreferences.Editor editor = ZOSP.edit();
			editor.putString("Username", mAccount[0].name);
			editor.putString("Password", credentials);
			Log.v(TAG, "Logged in" + mAccount[0].name);
			editor.commit();

			return true;
		}
		return false;
	}

	public void setPlayerStats()
	{
		SharedPreferences prefs = getSharedPreferences(ZombieOutbreak.TAG,1);
		String request = ServerConnection.sendRequest("/Get/getStats/" + prefs.getString("Username", null));
		String general = ServerConnection.sendRequest("/Get/getGeneral/");
		String[] gen = general.split("/");
		String[] stats = request.split("/");
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("isHuman", Boolean.parseBoolean(stats[0]));
		editor.putLong("human_since", Long.parseLong(stats[1]));
		editor.putInt("survived", Integer.parseInt(stats[2]));
		editor.putInt("zombiesKilled", Integer.parseInt(stats[3]));
		editor.putInt("gun", Integer.parseInt(stats[4]));
		editor.putLong("zombie_since", Long.parseLong(stats[5]));
		editor.putInt("infections", Integer.parseInt(stats[6]));
		editor.putLong("killed_time", Integer.parseInt(stats[7]));
		editor.putInt("way_killed", Integer.parseInt(stats[8]));
		editor.putLong("last_zombie", Long.parseLong(gen[0]));
		editor.putInt("zombieCount", Integer.parseInt(gen[2]));
		editor.putInt("humanCount", Integer.parseInt(gen[1]));
		editor.commit();
		Log.v(TAG, "Got stats" + stats[0]);

	}


}