package com.fbrs.zombieoutbreak.account;

import com.fbrs.zombieoutbreak.R;
import com.fbrs.zombieoutbreak.net.ServerConnection;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signin extends Activity{

	private static String username;
	private static String password;
	private static Signin activity;

	private static EditText UsernameView;
	private static EditText PasswordView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);

		activity = this;

		final Button signin = (Button)this.findViewById(R.id.signin);
		signin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				UsernameView = (EditText)activity.findViewById(R.id.email);
				PasswordView = (EditText)activity.findViewById(R.id.password);
				username = UsernameView.getText().toString();
				password = PasswordView.getText().toString();

				if(username.equals("") || password.equals(""))
				{
					signin.setText("Username and password cannot be empty");
				}
				else {

					String s = ServerConnection.sendRequest("/Get/newUser/" + username + "/" + password + "/");
					if(s == null)
						signin.setText("Not connected to the internet, Try again.");
					else
					{
						if(s.equals("300"))
						{
							Account account = new Account(username, getString(R.string.ACCOUNT_TYPE));
							AccountManager am = AccountManager.get(activity);
							boolean accountCreated = am.addAccountExplicitly(account, password, null);

							Bundle extras = activity.getIntent().getExtras();
							if (extras != null) {
								if (accountCreated) {  //Pass the new account back to the account manager
									AccountAuthenticatorResponse response = extras.getParcelable(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
									Bundle result = new Bundle();
									result.putString(AccountManager.KEY_ACCOUNT_NAME, username);
									result.putString(AccountManager.KEY_ACCOUNT_TYPE, getString(R.string.ACCOUNT_TYPE));
									response.onResult(result);
								}
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								finish();
							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							finish();
						}
						else
							signin.setText(s);
					}
				}
			}

		});
	}


}
