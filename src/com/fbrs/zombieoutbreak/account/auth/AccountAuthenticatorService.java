package com.fbrs.zombieoutbreak.account.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountAuthenticatorService extends Service{

	private static final String TAG = "AccountAuthService";
	private static AccountAuthenticator sAccAuth;

	public AccountAuthenticatorService()
	{
		super();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		IBinder r_value = null;
		if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT))
			r_value = getAuth().getIBinder();
		return r_value;
	}

	private AccountAuthenticator getAuth()
	{
		if(sAccAuth == null)
		{
			sAccAuth = new AccountAuthenticator(this);
		}
		return sAccAuth;
	}

}
