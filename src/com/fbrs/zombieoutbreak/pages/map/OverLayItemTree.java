package com.fbrs.zombieoutbreak.pages.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import android.util.Log;

public class OverLayItemTree {

	public int depth;
	public HashMap<String, OverLayItemTree> nodes = new HashMap<String, OverLayItemTree>();
	public OverLayItemDescriptor2 thisPoint;
	public ArrayList<OverLayItemDescriptor2> thisNodes = new ArrayList<OverLayItemDescriptor2>();

	public final static float depthBase = 1f;
	public final static int MAX_DEPTH = 8;

	public OverLayItemTree(int depth, OverLayItemDescriptor2 desc)
	{
		this.depth = depth;
		if(dp() == 0)
			Log.v("test","zero");
		float lat = (float) (desc.lat - desc.lat%dp());
		float lng = (float) (desc.lng - desc.lng%dp());
		if(depth == MAX_DEPTH)
			thisPoint = desc;
		else {
			thisPoint = new OverLayItemDescriptor2((desc.lat+dp()/2) + "_" + (desc.lng + dp()/2));
			//Log.v("ZombieOutbreak" , "New Tree: " + desc.lat + "_" + desc.lng);
		}
	}

	public void AddItem(OverLayItemDescriptor2 desc){

		float _lat = (float) (desc.lat - desc.lat%dp());
		float _lng = (float) (desc.lng - desc.lng%dp());
		//thisNodes.add(new OverLayItemDescriptor(_lat+"_"+_lng));
		if(nodes.containsKey(_lat+"_"+_lng))
			nodes.get(_lat+"_"+_lng).AddItem(desc);
		else {
			if(depth < MAX_DEPTH) {
				nodes.put(_lat+"_"+_lng, new OverLayItemTree(depth+1, desc));
				nodes.get(_lat+"_"+_lng).AddItem(desc);
				
				}
		}
	}

	public boolean printed2 = false;
	
	public Collection<OverLayItemTree> getDepth(int dep, OverLayItemDescriptor2 desc) {
		
		if(dep == depth)
			return nodes.values();
		float _lat = (float) (desc.lat - desc.lat%dp());
		float _lng = (float) (desc.lng - desc.lng%dp());
		if(nodes.get(_lat+"_"+_lng) == null) {
			if(depth == 1)
				return this.nodes.values();
			else
				return null;
		}
		else {
			Collection<OverLayItemTree> check = nodes.get(_lat+"_"+_lng).getDepth(dep, desc);
			if(check == null) {
				
				return this.nodes.values();
			}
			else
				return check;
		}
	}

	public int dp()
	{
		return (int) Math.round((depthBase * Math.pow(10, MAX_DEPTH - depth)));
	}
}


