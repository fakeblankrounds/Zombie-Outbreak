package com.fbrs.zombieoutbreak.account;

import android.accounts.Account;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

public class SyncAdapterImpl extends AbstractThreadedSyncAdapter{

	private Context mContext;

	public SyncAdapterImpl(Context context) {
		super(context, false);
		mContext = context;
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult)
	{
		try {
			SyncAdapter.performSync(mContext, account, extras, authority, provider, syncResult);
		} catch (OperationCanceledException e) {
		}
	}




}
