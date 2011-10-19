package com.fbrs.zombieoutbreak.pages.map;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import com.fbrs.zombieserver.map.serial.QuadTree;

import android.util.Log;

public class ParseMap {
	
	public static QuadTree map;

	public static QuadTree getFile() throws IOException
	{
		if(map != null)
			return map;
		
		URL u = new URL ( "https://s3.amazonaws.com/ZombieMap/Map.ser" );
		HttpURLConnection con = (HttpURLConnection) u.openConnection();
	
		/*byte[] byteArray = new byte[con.getContentLength()];
		Log.v("ZombieOutbreak", "Map Length =" + con.getContentLength());
		/*InputStream is = con.getInputStream();
		int input;
		int position = 0;
		while((input = is.read()) != -1) {
			byteArray[position] = (byte) input;
			position++;
		}
		/*
		Inflater decompresser = new Inflater();
		decompresser.setInput(byteArray, 0, byteArray.length);
		byte[] result = new byte[(int) (byteArray.length / 0.2f)];
		String outputString = null;
		try {
			int resultLength = decompresser.inflate(result);
			Log.v("ZombieOUtbreakMap" , resultLength + "  " + result.length);
			decompresser.end();
			outputString = new String(result, 0, resultLength, "UTF-8");
		} catch (DataFormatException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		String[] lines = outputString.split("\\r?\\n");
		//OverLayItemTree b = new OverLayItemTree(1,new OverLayItemDescriptor("0_0"));
		QuadTree b = new QuadTree();
		
		for(String l : lines) {
			l = l.trim();
			if(!l.equals("")) {
				OverLayItemDescriptor temp = new OverLayItemDescriptor(l,true);
				b.insert(temp.lat, temp.lng, new OverLayItemDescriptor(l,true));
			}
		}*/
		
		//ByteArrayInputStream reader = new ByteArrayInputStream(byteArray);
		ObjectInputStream in = new ObjectInputStream(con.getInputStream());
		
		
		try {
			map = (QuadTree)in.readObject();
		} catch (ClassNotFoundException e) {
			Log.e("ZombieOutbreak", null, e);
		}
		return map;
	}
}
