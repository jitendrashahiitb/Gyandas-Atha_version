package com.atha.treemapindia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.atha.csvexport.CSVexport;
import com.atha.gps.MyLocationListener;
import com.atha.gps.MyNetworkLocationListener;
import com.google.android.apps.mytracks.content.MyTracksProviderUtils;
import com.google.android.apps.mytracks.content.Track;
import com.google.android.apps.mytracks.services.ITrackRecordingService;
import com.google.android.apps.mytracks.stats.TripStatistics;

public class MainActivity extends Activity
{

	// RadioGroup rg, rob;
	TextView	                   ftv, htv, d_id;
	// RadioButton prb, rbob, bt;
	// ViewSwitcher pvs;
	Button	                       badd_tree, cancel, exdata;
	EditText	                   etname, etpdesc, etsuno, ethno, etarea;
	Spinner	                       etpoly, etward;
	String	                       prabhagNo	     = "";
	String	                       clusterNo	     = "";

	Spinner	                       spptype;
	String	                       oname	         = null, pd = null,
	        sn = null, hn = null, area = null, androidId;
	Area_Details	               _ad;
	Unique_Area_Detail	           _uad;

	private DatabaseHandler	       _db1;
	private DatabaseHelper	       db	             = null;

	Track	                       tr	             = null;
	int	                           st	             = 0;
	// utils to access the MyTracks content provider
	private MyTracksProviderUtils	myTracksProviderUtils;

	// MyTracks service
	private ITrackRecordingService	myTracksService;

	// intent to access the MyTracks service
	private Intent	               intent;

	private int	                   surveyorId;
	private boolean	               serviceConnected	 = false;

	LocationManager	               mlocManager	     = null;
	LocationListener	           mlocListener;
	LocationListener	           mnetworklocListener;

	// connection to the MyTracks service
	private ServiceConnection	   serviceConnection	= new ServiceConnection() {
		                                                 @Override
		                                                 public void onServiceConnected(ComponentName className, IBinder service)
		                                                 {
			                                                 myTracksService = ITrackRecordingService.Stub.asInterface(service);
			                                                 serviceConnected = true;
		                                                 }

		                                                 @Override
		                                                 public void onServiceDisconnected(ComponentName className)
		                                                 {
			                                                 myTracksService = null;
			                                                 System.out.println("mytracks service disconnected");
		                                                 }
	                                                 };

	private boolean	               prabhagSelected	 = false;
	private boolean	               clusterSelected	 = false;
	private boolean	               propertySelected	 = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_form);

		mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mlocListener = new MyLocationListener(MainActivity.this);
		mnetworklocListener = new MyNetworkLocationListener(MainActivity.this);
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, mlocListener);
		mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, mnetworklocListener);

		androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
		d_id = (TextView) findViewById(R.id.devid);
		d_id.setText("Device ID   :  " + androidId);
		myTracksProviderUtils = MyTracksProviderUtils.Factory.get(this);
		// for creating a folder
		String newFolder1 = "/" + androidId;
		String extStorageDirectory1 = Environment.getExternalStorageDirectory().toString();
		File myNewFolder1 = new File(extStorageDirectory1 + newFolder1);
		myNewFolder1.mkdir();
		String newFolder = "/" + androidId + "/EMAGPS";
		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		File myNewFolder = new File(extStorageDirectory + newFolder);
		myNewFolder.mkdir();

		st = getIntent().getIntExtra("track", 0);
		surveyorId = getIntent().getIntExtra(DatabaseHelper.SURVEYOR_ID[0], 0);
		// Intent for start service
		intent = new Intent();
		ComponentName componentName = new ComponentName(getString(R.string.mytracks_service_package), getString(R.string.mytracks_service_class));
		intent.setComponent(componentName);

		// Start tracking
		Button startRecordingButton = (Button) findViewById(R.id.stt);
		startRecordingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{

				// if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
				// {
				// if (MyLocationListener.latitude != -1)
				// {
				// Toast.makeText(MainActivity.this, "Latitude:- " + MyLocationListener.latitude,
				// Toast.LENGTH_SHORT).show();
				// Toast.makeText(MainActivity.this, "Longitude:- " + MyLocationListener.longitude,
				// Toast.LENGTH_SHORT).show();
				// }
				// else
				// {
				// AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
				// alert.setTitle("Wait");
				// alert.setMessage("GPS in progress, please wait.");
				// alert.setPositiveButton("OK", null);
				// alert.show();
				// }
				// return;
				// }
				// else
				// {
				// Toast.makeText(MainActivity.this, "GPS is not turned on...",
				// Toast.LENGTH_SHORT).show();
				// }

				if (!prabhagSelected)
				{
					Toast.makeText(MainActivity.this, "Please select Prabhag number", Toast.LENGTH_LONG).show();
					return;
				}
				if (!clusterSelected)
				{
					Toast.makeText(MainActivity.this, "Please select Cluster number", Toast.LENGTH_LONG).show();
					return;
				}
				if (!propertySelected)
				{
					Toast.makeText(MainActivity.this, "Please select Property type", Toast.LENGTH_LONG).show();
					return;
				}
				if (myTracksService != null)
				{
					try
					{
						Log.i("mytracksservice", "obj " + (myTracksService == null ? "null" : myTracksService.toString()));
						Log.i("Service Connection", "Service conncted: " + serviceConnected);
						long trackId = myTracksService.startNewTrack();
						// tr =
						// myTracksProviderUtils.getTrack(myTracksService.getRecordingTrackId());
						Log.i("myTracksService", "startnewtrack: " + trackId);
						// Toast.makeText(MainActivity.this, "Track id: " + trackId,
						// Toast.LENGTH_LONG).show();
						tr = myTracksProviderUtils.getLastTrack();
						// if (trackId == 0)
						// {
						// if (tr != null)
						// {
						// trackId = tr.getId() + 1;
						// }
						// else
						// {
						// trackId = 1;
						// }
						// Track track = new Track();
						// track.setId(trackId);
						// track.setModifiedTime(System.currentTimeMillis());
						// track.setName(prabhagNo + "_" + clusterNo);
						// myTracksProviderUtils.insertTrack(track);
						// }
						// tr =
						// myTracksProviderUtils.getTrack(myTracksService.getRecordingTrackId());
						Log.i("is track", "track : " + (tr == null ? "null" : tr.toString()));
						st = 1;
					}
					catch (RemoteException e)
					{
						e.printStackTrace();
					}
				}
				v.setEnabled(false);
			}
		});

		try
		{
			_db1 = new DatabaseHandler(this);
			db = new DatabaseHelper(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		_db1.open();
		exdata = (Button) findViewById(R.id.exda);
		// pvs = (ViewSwitcher) findViewById(R.id.viewSwitcher1);
		badd_tree = (Button) findViewById(R.id.add_tree);
		cancel = (Button) findViewById(R.id.cancel);
		etname = (EditText) findViewById(R.id.et_oname);
		etpdesc = (EditText) findViewById(R.id.et_pdesc);
		etsuno = (EditText) findViewById(R.id.et_suno);
		ethno = (EditText) findViewById(R.id.et_hno);
		etarea = (EditText) findViewById(R.id.et_area);
		etpoly = (Spinner) findViewById(R.id.et_poly);
		etward = (Spinner) findViewById(R.id.et_ward);
		spptype = (Spinner) findViewById(R.id.sp_ptype);
		// rob = (RadioGroup) findViewById(R.id.radio_roadw);
		// bt = (RadioButton) findViewById(R.id.radioout);
		// rg = (RadioGroup) findViewById(R.id.radio_w);

		ArrayList<String> prabhagNumbers = db.getAllPrabhagNumbers();
		prabhagNumbers.add(0, "Select Prabhag No.");

		ArrayAdapter<String> prabhagAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, prabhagNumbers);

		etward.setAdapter(prabhagAdapter);

		etward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				String str = arg0.getItemAtPosition(arg2).toString();
				if (!str.contains("Select Prabhag"))
				{
					prabhagSelected = true;
				}
				else
				{
					prabhagSelected = false;
				}
				prabhagNo = etward.getSelectedItem().toString();
				Log.i("selection", "prabhag number : " + prabhagNo);
				db.openDatabase();
				ArrayList<String> clusterNumbers = db.getClusterNumberAccordingToPrabhagNumber(prabhagNo);
				clusterNumbers.add(0, "Select Cluster No.");
				ArrayAdapter<String> clusterAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, clusterNumbers);
				etpoly.setAdapter(clusterAdapter);
				Log.i("item selection", clusterNumbers.toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{

			}
		});

		etpoly.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				String str = arg0.getItemAtPosition(arg2).toString();
				if (!str.contains("Select Cluster"))
				{
					clusterSelected = true;
				}
				else
				{
					clusterSelected = false;
				}
				clusterNo = etpoly.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{

			}
		});

		exdata.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{

				final ProgressDialog myPd_ring = ProgressDialog.show(MainActivity.this, "Please wait", "Data is Exporting...", true);
				myPd_ring.setCancelable(true);
				new Thread(new Runnable() {
					@Override
					public void run()
					{
						try
						{
							try
							{
								/* CSVexport ex= */// new CSVexport(MainActivity.this, androidId,
								                   // 0);
								Log.i("data dump", "begin");
								db.openDatabase();
								db.databaseDump(androidId);
								Log.i("data dump", "end");
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
							// Thread.sleep(5000);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						myPd_ring.dismiss();
					}
				}).start();
			}
		});

		String property[] = db.getPropertyTypes();
		String propertyTypes[] = new String[property.length + 1];
		propertyTypes[0] = "Select Property type";
		for (int i = 0; i < property.length; i++)
		{
			propertyTypes[i + 1] = property[i];
		}
		spptype.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, propertyTypes));

		spptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				String item = arg0.getItemAtPosition(arg2).toString();
				LinearLayout layoutPrivateProperty = (LinearLayout) findViewById(R.id.layoutPrivateProperty);
				if (item.equalsIgnoreCase("divider") || item.equalsIgnoreCase("pavement") || item.contains("Select"))
				{
					layoutPrivateProperty.setVisibility(View.GONE);
				}
				else
				{
					layoutPrivateProperty.setVisibility(View.VISIBLE);
				}
				if (!item.contains("Select"))
				{
					propertySelected = true;
				}
				else
				{
					propertySelected = false;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{

			}
		});

		// rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId)
		// {
		// prb = (RadioButton) findViewById(checkedId);
		//
		// if (prb.getText().toString().contains("Outside Boundary"))
		// {
		// pvs.showPrevious();
		// }
		// else if (prb.getText().toString().contains("Enclosed Boundary"))
		// {
		// pvs.showNext();
		// }
		//
		// }
		// });

		// rob.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId)
		// {
		// rbob = (RadioButton) findViewById(checkedId);
		// }
		// });

		badd_tree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				Location loc = myTracksProviderUtils.getLastValidTrackPoint();
				if (loc != null)
				{
					// Toast.makeText(MainActivity.this, "lon: " + loc.getLongitude() + ", lat: " +
					// loc.getLatitude() + ", loc: " + loc.toString(), Toast.LENGTH_LONG).show();
				}
				else
				{
					// Toast.makeText(MainActivity.this, "location is null",
					// Toast.LENGTH_LONG).show();
				}
				_db1.open();
				if (st == 1)
				{
					// if (((RadioButton)
					// findViewById(rg.getCheckedRadioButtonId())).getText().toString().contains("Outside Boundary"))
					// {
					// Log.e("yahi ", "ediot");
					// if (/*
					// * etward.getText().toString().length() > 0 &&
					// * etpoly.getText().toString().length() > 0 &&
					// */((RadioButton)
					// findViewById(rg.getCheckedRadioButtonId())).getText().toString().length() >
					// 0)
					// {
					// // place_type=rbob.getText().toString();
					// String place_type = ((RadioButton)
					// findViewById(rob.getCheckedRadioButtonId())).getText().toString();
					// _uad = new Unique_Area_Detail(prabhagNo, clusterNo, prabhagNo + "_" +
					// clusterNo);
					// _ad = new Area_Details(place_type, prabhagNo + "_" + clusterNo);
					//
					// // _db1.insertuniqearedetails(_uad);
					// // Long id = _db1.insertAreadetails(_ad);
					// Long id = db.insertAreadetails(_ad);
					// Log.d("row_no", "" + id);
					// Intent in = new Intent(MainActivity.this, Survey_form.class);
					// in.putExtra("formno", prabhagNo + "_" + clusterNo);
					// in.putExtra(DatabaseHelper.PRABHAG_ID[0], prabhagNo);
					// in.putExtra(DatabaseHelper.CLUSTER_ID[0], clusterNo);
					// in.putExtra("prop_id", "" + id);
					// in.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
					// Toast.makeText(MainActivity.this, "surveyorId: " + surveyorId,
					// Toast.LENGTH_LONG).show();
					// startActivity(in);
					// _db1.close();
					// db.close();
					// finish();
					// }
					// else
					// {
					// Toast.makeText(MainActivity.this, "Please Complete above Details",
					// Toast.LENGTH_LONG).show();
					// }
					// }
					// else if (prb.getText().toString().contains("Enclosed Boundary"))
					// {
					String pty = null;
					if (prabhagNo.length() > 0 && clusterNo.length() > 0 && propertySelected)
					{
						double area_d = 0.0;
						if (etname.getText().toString().length() > 0)
						{
							oname = etname.getText().toString();
						}
						else
						{
							oname = "nil";
						}
						if (etpdesc.getText().toString().length() > 0)
						{
							pd = etpdesc.getText().toString();
						}
						else
						{
							pd = "nil";
						}
						if (etsuno.getText().toString().length() > 0)
						{
							sn = etsuno.getText().toString();
						}
						else
						{
							sn = "nil";
						}
						if (ethno.getText().toString().length() > 0)
						{
							hn = ethno.getText().toString();
						}
						else
						{
							hn = "nil";
						}

						if (etarea.getText().toString().length() > 0)
						{

							area = etarea.getText().toString();

							area_d = Double.parseDouble(area);

						}
						else
						{

							area_d = 0.0;
						}
						Log.e("---", "err5");
						if (String.valueOf(spptype.getSelectedItem()).contains("Select Property Type"))
						{
							pty = "nil";
						}
						else
						{
							pty = String.valueOf(spptype.getSelectedItem());
						}
						_uad = new Unique_Area_Detail(prabhagNo, clusterNo, prabhagNo + "_" + clusterNo);
						_ad = new Area_Details(pty, pd, oname, hn, sn, area_d, prabhagNo + "_" + clusterNo);
						// _db1.insertuniqearedetails(_uad);
						// Long id = _db1.insertAreadetails(_ad);
						db.openDatabase();
						Long id = db.insertAreadetails(_ad);
						Log.d("row_no", "" + id);
						Intent in = new Intent(MainActivity.this, Survey_form.class);
						in.putExtra("formno", prabhagNo + "_" + clusterNo);
						in.putExtra("prop_id", "" + id);
						in.putExtra(DatabaseHelper.PRABHAG_ID[0], prabhagNo);
						in.putExtra(DatabaseHelper.CLUSTER_ID[0], clusterNo);
						in.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
						startActivity(in);
						finish();
					}
					else
					{
						Toast.makeText(MainActivity.this, "Please Complete above Details", Toast.LENGTH_LONG).show();
					}
					// }
				}
				else
				{
					AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(MainActivity.this);
					alertDialogBuilder1.setMessage("Your tracking is Not started \n You have to start Tracking first with pressing button start tracking???").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id)
						{
							// if this button is clicked, close
							// current activity
							dialog.cancel();

						}
					}).show();
				}
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				exit();
			}
		});
		Button btnList = (Button) findViewById(R.id.listSessions);
		btnList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this, ListSessionsActivity.class);
				intent.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
				startActivity(intent);
			}
		});

		// ImageView iv = (ImageView) findViewById(R.id.imageView1);
		// iv.post(new Runnable() {
		// public void run()
		// {
		// ImageView iv = (ImageView) findViewById(R.id.imageView1);
		// LinearLayout ll = (LinearLayout) findViewById(R.id.layoutButtons);
		// Display display = getWindowManager().getDefaultDisplay();
		// Point size = new Point();
		// display.getSize(size);
		// int width = size.x;
		// int height = size.y;
		// int ivWidth = iv.getWidth();
		// if (width - ivWidth > 650)
		// {
		// ll.setOrientation(0);
		// }
		// else
		// {
		// ll.setOrientation(1);
		// }
		// // Toast.makeText(getApplication(), "W:" + width + " H:" + height + " IV W:" +
		// // ivWidth + " LL W:" + ll.getWidth(), Toast.LENGTH_LONG).show();
		// }
		// });

	}

	@Override
	protected void onStart()
	{
		super.onStart();

		startService(intent);
		bindService(intent, serviceConnection, BIND_AUTO_CREATE);
		db.openDatabase();
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		// unbind and stop the MyTracks service
		if (myTracksService != null)
		{
			unbindService(serviceConnection);
			System.out.println("unbinding service");
		}
		else
		{
			System.out.println("mytraksService null so not unbinding");
		}
		stopService(intent);
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		// exit();
	}

	public void exit()
	{
		// Toast.makeText(MainActivity.this, "exit called", Toast.LENGTH_SHORT).show();
		AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(MainActivity.this);
		alertDialogBuilder2.setMessage("Tracking will stop \n Do you want to continue?").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id)
			{
				// if this button is clicked, close
				// current activity
				dialog.cancel();
				if (myTracksService != null)
				{
					try
					{
						if (prabhagNo.length() > 0 && clusterNo.length() > 0)
						{
							// int
							// i=myTracksProviderUtils.getTrack(myTracksProviderUtils.getLastTrack().getId()).getNumberOfPoints();
							if (tr != null)
							{
								myTracksService.endCurrentTrack();
								tr.setName(prabhagNo + "_" + clusterNo);
								myTracksProviderUtils.updateTrack(tr);
							}
						}
						else
						{
							myTracksService.endCurrentTrack();
						}

						// Toast.makeText(getApplicationContext(),
						// ""+myTracksProviderUtils.getLastTrack().getId(),
						// Toast.LENGTH_SHORT).show();
					}
					catch (RemoteException e)
					{
						e.printStackTrace();
					}
				}
				MainActivity.this.finish();

			}
		}).setNegativeButton("NO", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		}).show();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mlocManager.removeUpdates(mlocListener);
		mlocManager.removeUpdates(mnetworklocListener);
		// db.close();
	}
}