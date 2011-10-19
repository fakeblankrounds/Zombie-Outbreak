package com.fbrs.zombieoutbreak.pages.map;

import android.util.Log;

public class OverLayItemDescriptor2{
	
	int lat, lng;
	int weight;
	boolean zombie;
	
	public OverLayItemDescriptor2(String line)
	{		
		String[] subline = line.split(":");
		String[] coords;
		if(subline.length > 0) {
			 coords = subline[0].split("_");
			 float _lat = Float.parseFloat(coords[1]);
			 float _long = Float.parseFloat(coords[0]);
			 lat = (int) (_lat);
			 lng = (int) (_long);
		}
		else
			return;
		if(subline.length == 1)
		{
			weight = 1;
			zombie = false;
		}
		if(subline.length == 2) {
			if(Character.isDigit(subline[1].charAt(0))) {
				weight = Integer.parseInt(subline[1]);
				zombie = false;
			}
			else if(subline[1].equals("t")) {
				zombie = true;
				weight = 1;
			}
		}
		if(subline.length == 3) {
			weight = Integer.parseInt(subline[1]);
			zombie = true;
		}
		
		
		if(zombie)
			Log.v("ZombieOutbreak" , "FoundZombie");
	}
	//the boolean version just assumes that the line is 2 floats instead of an two ints. 
	public OverLayItemDescriptor2(String line, boolean b)
	{
		String[] subline = line.split(":");
		String[] coords;
		if(subline.length > 0) {
			 coords = subline[0].split("_");
			 float _lat = Float.parseFloat(coords[0]);
			 float _long = Float.parseFloat(coords[1]);
			 lat = (int) (_lat * 1000000);
			 lng = (int) (_long * 1000000);
		}
		else
			return;
		if(subline.length == 1)
		{
			weight = 1;
			zombie = false;
		}
		if(subline.length == 2) {
			if(Character.isDigit(subline[1].charAt(0))) {
				weight = Integer.parseInt(subline[1]);
				zombie = false;
			}
			else if(subline[1].equals("t")) {
				zombie = true;
				weight = 1;
			}
		}
		if(subline.length == 3) {
			weight = Integer.parseInt(subline[1]);
			zombie = true;
		}
		
		if(zombie)
			Log.v("ZombieOutbreak" , "FoundZombie");
	}
	
	@Override
	public String toString()
	{
		return lat + "_" + lng;
	}

}
