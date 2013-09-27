package com.atha.treemapindia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.android.apps.mytracks.content.MyTracksProviderUtils;
import com.google.android.apps.mytracks.content.Track;
import com.google.android.apps.mytracks.services.ITrackRecordingService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ListSessionsActivity extends ListActivity
{
	private ArrayList<String>	 listItems	= new ArrayList<String>();
	private ArrayAdapter<String>	adapter;

	private DatabaseHelper	     db;

	private Session	             sessions[]	= null;
	private int	                 surveyorId;

	public ListSessionsActivity()
	{
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		db = new DatabaseHelper(ListSessionsActivity.this);
		setContentView(R.layout.listsessions);
		Bundle bundle = getIntent().getExtras();
		surveyorId = bundle.getInt(DatabaseHelper.SURVEYOR_ID[0]);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);
		Log.i("create", "in create");
		sessions = db.getSessionsAccordingToSurveyorId(surveyorId);
		Log.i("sessions", "all sessions : " + Arrays.toString(sessions));
		for (Session session : sessions)
		{
			listItems.add(session.getGid() + ", " + new Date(session.getTime()).toString());
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		// AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle("Item selected").setMessage("Selected: " +
		// l.getItemAtPosition(position).toString() + "\nSession id: " +
		// sessions[position].getId()).show();
		Intent intent = new Intent(ListSessionsActivity.this, ListTreesInSessionActivity.class);
		intent.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
		intent.putExtra("session", sessions[position]);
		startActivity(intent);
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		ListSessionsActivity.this.finish();
		Intent intent = new Intent(ListSessionsActivity.this, MainActivity.class);
		intent.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	protected void onStart()
	{
		super.onStart();
		Log.i("start", "in start");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		db.close();
	}
}
