package com.fbrs.zombieoutbreak.pages.map;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.fbrs.zombieoutbreak.R;
import com.fbrs.zombieoutbreak.pages.InfestMap;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class ZombieOverlay extends Overlay{
	
	private Bitmap bmp = getBMP();
	
	public ZombieOverlay()
	{
		
	}
	
	
	@Override
	public boolean draw(Canvas canvas, MapView mapv, boolean shadow, long when)
	{
		super.draw(canvas,mapv, shadow);
		Point newPoint = new Point();
		newPoint = mapv.getProjection().toPixels(new GeoPoint(90000000, 180000000), newPoint);
		Point maxPoint = new Point();
		maxPoint = mapv.getProjection().toPixels(new GeoPoint(0,0), maxPoint);
		Paint paint = new Paint();
		Point testpoint = new Point();
		mapv.getProjection().toPixels(new GeoPoint(42433740,-71185210), testpoint);
		
		Rect scale = new Rect(newPoint.x, newPoint.y, maxPoint.x + (maxPoint.x - newPoint.x), maxPoint.y + (maxPoint.y - newPoint.y));
		//Log.v("ZombieOverlay", testpoint.x + " _ " + testpoint.y + "|" + scale.width() + " _ " + scale.height());
		
		// Converts lat/lng-Point to OUR coordinates on the screen.
		paint.setStrokeWidth(1);
		paint.setARGB(255, 255, 255, 255);
		paint.setStyle(Paint.Style.STROKE);
		Bitmap icon = BitmapFactory.decodeResource(InfestMap.thisactivity.getResources(), R.drawable.icon);
		canvas.drawBitmap(bmp, null, scale, paint);
	//	canvas.drawBitmap(icon, testpoint.x, testpoint.y, paint);
		return true;
	}
	
	public Bitmap getBMP()
	{
		try {
			URL url = new URL("https://s3.amazonaws.com/ZombieMap/map.png");
			BufferedInputStream bis = new BufferedInputStream(url.openConnection().getInputStream());
			return bmp = BitmapFactory.decodeStream(bis);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
}
