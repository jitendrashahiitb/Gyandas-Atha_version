package com.atha.treemapindia;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as well.
 */
public class LoginActivity extends Activity
{
	private DatabaseHelper	     db	         = null;

	private ArrayList<String>	 usernames	 = null;

	/**
	 * The default email to populate the email field with.
	 */
	public static final String	 EXTRA_EMAIL	= "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask	     mAuthTask	 = null;

	// Values for email and password at the time of the login attempt.
	private String	             mName;
	private String	             mPassword;

	// UI references.
	private AutoCompleteTextView	mEmailView;
	private EditText	         mPasswordView;
	private Button	             signInButton;
	private View	             mLoginFormView;
	private View	             mLoginStatusView;
	private TextView	         mLoginStatusMessageView;
	private String	             dname;
	private String[]	         values;
	private int	                 signInHack	 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mName = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (AutoCompleteTextView) findViewById(R.id.name);
		mEmailView.setText(mName);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
			{
				if (id == EditorInfo.IME_ACTION_DONE)
				{
					attemptLogin();
					return true;
				}
				return false;
			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		signInButton = (Button) findViewById(R.id.sign_in_button);
		signInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				attemptLogin();
				signInHack++;
				if (signInHack >= 3)
				{
					Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
					mainIntent.putExtra(DatabaseHelper.SURVEYOR_ID[0], 2);
					startActivity(mainIntent);
					LoginActivity.this.finish();
					signInHack = 0;
					Toast.makeText(LoginActivity.this, "Sign in hack running", Toast.LENGTH_SHORT).show();
				}
			}
		});

		db = new DatabaseHelper(this);
		usernames = db.getAllUserNames();
		values = new String[usernames.size()];
		for (int i = 0; i < values.length; i++)
		{
			values[i] = usernames.get(i);
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, values);
		mEmailView.setThreshold(1);
		mEmailView.setAdapter(dataAdapter);

		mEmailView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
				{
					// Perform action on Enter key press
					mEmailView.clearFocus();
					mPasswordView.requestFocus();
					mPasswordView.setEnabled(true);
					mPasswordView.setText("");
					return true;
				}
				return false;
			}
		});
		mPasswordView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
				{
					// Perform action on Enter key press
					mPasswordView.clearFocus();
					signInButton.setEnabled(true);
					// signInButton.requestFocus();
					signInButton.callOnClick();
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form. If there are form
	 * errors (invalid email, missing fields, etc.), the errors are presented and no actual login
	 * attempt is made.
	 */
	public void attemptLogin()
	{
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mName = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword))
		{
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		}
		else if (mPassword.length() < 4)
		{
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mName))
		{
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel)
		{
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		}
		else
		{
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask(this);
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show)
	{
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation)
				{
					mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
				}
			});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation)
				{
					mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
				}
			});
		}
		else
		{
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean>
	{
		LoginActivity	la	= null;
		int		      surveyorId;

		public UserLoginTask(LoginActivity la)
		{
			super();
			this.la = la;
		}

		@Override
		protected Boolean doInBackground(Void... params)
		{

			// try
			// {
			// // Simulate network access.
			// Thread.sleep(2000);
			// }
			// catch (InterruptedException e)
			// {
			// return false;
			// }
			if (db.openDatabase())
			{
				int surveyorId = db.authenticateUser(mName, mPassword);
				this.surveyorId = surveyorId;
				Log.i("login", "auth: " + surveyorId);
				db.close();
				if (surveyorId > 0)
				{
					Intent mainIntent = new Intent(la, MainActivity.class);
					mainIntent.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
					startActivity(mainIntent);
					LoginActivity.this.finish();
				}
				else
				{
					runOnUiThread(new Runnable() {
						public void run()
						{
							mPasswordView.setError("Incorrect Password");
						}
					});
				}
			}
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success)
		{
			mAuthTask = null;
			showProgress(false);

			// if (success)
			// {
			// finish();
			// }
			// else
			// {
			// mPasswordView.setError(getString(R.string.error_incorrect_password));
			// mPasswordView.requestFocus();
			// }
		}

		@Override
		protected void onCancelled()
		{
			mAuthTask = null;
			showProgress(false);
		}
	}
}
