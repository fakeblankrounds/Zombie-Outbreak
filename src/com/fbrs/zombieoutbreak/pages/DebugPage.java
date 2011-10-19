package com.fbrs.zombieoutbreak.pages;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fbrs.zombieoutbreak.R;


import com.fbrs.zombieoutbreak.pages.map.ParseMap;
import com.fbrs.zombieserver.map.serial.OverLayItemDescriptor;
import com.fbrs.zombieserver.map.serial.QuadTree;
import com.fbrs.zombieserver.map.serial.RectF;

public class DebugPage extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug);
		
		Button xml = (Button)findViewById(R.id.xmlMapButton);
		
		xml.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					ParseMap.getFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.v("ZombieOutbreakMap", "Map Fetch Failed" + e.getMessage());
				}
			}
		});
	Button map = (Button)findViewById(R.id.xmlTreeButton);
		
		map.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					QuadTree mp = ParseMap.getFile();
					ArrayList<OverLayItemDescriptor> t = mp.query2D(new RectF(-160000000,80000000,160000000,-80000000),100);
					for(OverLayItemDescriptor tree : t) {
							Log.v("ZombieOutbreak", tree.toString());
					}
					Log.v("ZombieOutbreak","Size: "+ t.size());
					recurse(mp.root);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.v("ZombieOutbreak", "Map Fetch Failed" + e.getMessage());
				}
			}
		});
	
	Button quit = (Button)findViewById(R.id.QuitButton);
	
	quit.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Object o = null;
			o.hashCode();
		}
	});
}
	
	public void recurse(QuadTree.Node h)
	{
		Log.v("ZombieOutbreak", h.value.toString());
		if(h.NE != null)recurse(h.NE);
		if(h.NW != null)recurse(h.NW);
		if(h.SE != null)recurse(h.SE);
		if(h.SW != null)recurse(h.SW);
	}
}
