package com.fbrs.zombieoutbreak.account;

import android.accounts.Account;
import android.accounts.OperationCanceledException;
import android.app.Service;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class SyncAdapter extends Service{
	
	private static final String TAG = "ZOMBIESYNCADAPTERSERVICE";
	private static SyncAdapterImpl sSyncAdapter = null;
	private static ContentResolver mContentResolver = null;
	
	 @Override
	 public IBinder onBind(Intent intent) {
	  IBinder ret = null;
	  ret = getSyncAdapter().getSyncAdapterBinder();
	  return ret;
	 }
	 
	 private SyncAdapterImpl getSyncAdapter() {
	  if (sSyncAdapter == null)
	   sSyncAdapter = new SyncAdapterImpl(this);
	  return sSyncAdapter;
	 }
	 
	 public static void performSync(Context context, Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult)
	   throws OperationCanceledException {
	  //mContentResolver = context.getContentResolver();
	  //Log.i(TAG, "performSync: " + account.toString());
	 }

}
