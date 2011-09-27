package com.fbrs.zombieoutbreak.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import android.util.Log;

import com.fbrs.zombieoutbreak.ZombieOutbreak;

public class ServerConnection {
	
	
public static String EC2Server = null;
	
	public static void getServer()
	{
		EC2Server = "www.thedailynerd.com";
	}
	
	
	public static String sendRequest(String request)
	{		
		 try {
			 if(EC2Server == null)
				 getServer();
			Socket s = new Socket(EC2Server ,8888);
			OutputStream out = s.getOutputStream();
			
			PrintWriter send = new PrintWriter(out);
			send.println(request);
			send.println();
			send.flush();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String t = "";
			String st = input.readLine();
			while(st != null)
			{
				t += st;
				st = input.readLine();
			}
			s.close();
			return t;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			Log.e("ZombieOutbreak", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("ZombieOutbreak", e.getMessage());
			e.printStackTrace();
		}
		return null;
			 
	}
	
	public static void StartRequestInNewThread(final Runnable request, final Runnable result)
	{
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run() {
				request.run();
				result.run();
			}
		});
		thread.start();
	}

}
