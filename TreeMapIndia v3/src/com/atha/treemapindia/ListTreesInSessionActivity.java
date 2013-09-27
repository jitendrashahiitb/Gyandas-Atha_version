package com.atha.treemapindia;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListTreesInSessionActivity extends ListActivity
{

	private Session	             session	= null;
	private ArrayList<String>	 listItems	= new ArrayList<String>();
	private ArrayAdapter<String>	adapter	= null;
	private DatabaseHelper	     db	        = null;
	private int	                 surveyorId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		db = new DatabaseHelper(this);
		setContentView(R.layout.activity_list_trees_in_session);
		Bundle bundle = getIntent().getExtras();
		session = (Session) bundle.get("session");
		surveyorId = bundle.getInt(DatabaseHelper.SURVEYOR_ID[0]);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);
		Log.i("create", "in create");
		session.setTrees(db.getTreeDetailsAccordingToSessionId(session.getId(), surveyorId));
		Log.i("sessions", "all trees : " + Arrays.toString(session.getTrees()));
		for (TreeDetails tree : session.getTrees())
		{
			if (tree != null)
			{
				listItems.add(tree.getNumber() + ", " + tree.getName());
			}
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle("Item selected").setMessage("Selected: " +
		// l.getItemAtPosition(position).toString() + "\nTree num: " +
		// session.getTrees()[position]).show();
		Intent intent = new Intent(this, ViewTreeDetailActivity.class);
		intent.putExtra("treeDetail", session.getTrees()[position]);
		intent.putExtra("session", session);
		intent.putExtra("pos", position);
		intent.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
		startActivity(intent);
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		ListTreesInSessionActivity.this.finish();
		Intent intent = new Intent(ListTreesInSessionActivity.this, ListSessionsActivity.class);
		intent.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
		startActivity(intent);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		db.close();
	}
}
