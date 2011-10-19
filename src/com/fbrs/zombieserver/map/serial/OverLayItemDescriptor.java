package com.fbrs.zombieserver.map.serial;

import java.io.Serializable;


public class OverLayItemDescriptor implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int lat, lng;
	public int weight;
	public boolean zombie;
	
	public OverLayItemDescriptor(int l, int n, int w, boolean z)
	{
		lat = l; lng = n; weight = w; zombie = z;
	}
	
	public OverLayItemDescriptor(String line)
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
		
	}
	//the boolean version just assumes that the line is 2 floats instead of an two ints. 
	public OverLayItemDescriptor(String line, boolean b)
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
		
	}
	
	@Override
	public String toString()
	{
		return lat + "_" + lng;
	}

}
