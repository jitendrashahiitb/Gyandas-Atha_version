package com.atha.treemapindia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.atha.treemapindia.R;
import com.google.android.apps.mytracks.content.MyTracksProviderUtils;
import com.google.android.apps.mytracks.content.Track;
import com.google.android.apps.mytracks.content.Waypoint;
import com.google.android.apps.mytracks.services.ITrackRecordingService;

public class Survey_form extends Activity
{

	AutoCompleteTextView	       trn_value;
	TextView	                   thot, tfot, tfog, trot, tman, tspa;
	RadioGroup	                   r_hot, r_fog, r_rot, rd_fog, rd_g, rd_w;
	RadioButton	                   rb_hot, rb_fog, rb_rot, rb_g, rb_w, rb_himt,
	        rb_gimt;
	EditText	                   et_rotv, et_hft, et_hmt, et_gcm, et_gmt,
	        et_mot, et_boto, et_tno, et_s_oth_desc;
	CheckBox	                   chk_m_others, chk_nest, chk_pf, chk_flw,
	        chk_bur, chk_none, chk_nails, chk_poster, chk_wire, chk_tg,
	        chk_another, chk_m_none, chk_vola, chk_id, chk_pa, chk_ref_to_dept,
	        chk_s_oth, chk_s_oth_desc, chk_s_non;
	TextWatcher	                   hft, hmt, gcm, gmt;
	Button	                       ch_loc, can, exdata, sav, tap;
	int	                           nest	               = -1, burrows = -1,
	        flowers = -1, fruits = -1, nails = -1, poster = -1, wires = -1,
	        tree_guard = -1, men_other = -1, s_other = -1, ref_to_dept = -1,
	        s_none = -1, f_none = -1, n_none = -1;
	String	                       prop_id	           = null, fno = null,
	        tree_no = null, tree_name = null, botanical_name = null,
	        men_other_desc = null, health_tree = null, found_ground = null,
	        ground_condition = null, risk_on_tree = null, risk_desc = null,
	        pest_affected = null, s_other_desc = null, androidId;
	double	                       height_ft, height_m, girth_cm, girth_m, lat,
	        lon;

	private Button	               btnTreeKey	       = null;

	private String	               str	               = "";

	private String[]	           imageNames	       = new String[5];

	// private DatabaseHandler _db;
	private DatabaseHelper	       dbHelper;
	private Cursor	               TreeCursor;
	Treelist	                   tl;
	String[]	                   tl_value;
	ArrayList<Treelist>	           list;
	Surveydetail	               _sd;
	TreeDetails	                   td;

	private static int	           TAKE_PICTURE	       = 1;
	private Uri	                   outputFileUri;
	Track	                       tr;
	int	                           flag	               = 0;
	// utils to access the MyTracks content provider
	private MyTracksProviderUtils	myTracksProviderUtils;
	private ITrackRecordingService	myTracksService;

	// intent to access the MyTracks service
	private Intent	               intent, intent_cam;

	// connection to the MyTracks service
	private ServiceConnection	   serviceConnection	= new ServiceConnection() {
		                                                   @Override
		                                                   public void onServiceConnected(ComponentName className, IBinder service)
		                                                   {
			                                                   myTracksService = ITrackRecordingService.Stub.asInterface(service);
		                                                   }

		                                                   @Override
		                                                   public void onServiceDisconnected(ComponentName className)
		                                                   {
			                                                   myTracksService = null;
		                                                   }
	                                                   };
	private AlertDialog.Builder	   builder	           = null;
	private int	                   surveyorId;
	private String	               prabhagId;
	private String	               clusterId;

	private boolean	               foundOnTreeDone	   = false;
	private boolean	               specialDone	       = false;
	private boolean	               nuissanceOnTreeDone	= false;
	private boolean	               healthOfTreeDone	   = false;
	private boolean	               groundConditionDone	= false;
	private boolean	               riskDueToTreeDone	= false;

	private ListView	           listView	           = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		for (int i = 0; i < 5; i++)
		{
			imageNames[i] = "";
		}
		setContentView(R.layout.activity_main);
		androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
		myTracksProviderUtils = MyTracksProviderUtils.Factory.get(this);

		// Intent for start service
		intent = new Intent();
		ComponentName componentName = new ComponentName(getString(R.string.mytracks_service_package), getString(R.string.mytracks_service_class));
		intent.setComponent(componentName);

		Bundle b = getIntent().getExtras();
		fno = b.getString("formno");
		prop_id = b.getString("prop_id");
		prabhagId = b.getString(DatabaseHelper.PRABHAG_ID[0]);
		clusterId = b.getString(DatabaseHelper.CLUSTER_ID[0]);
		surveyorId = b.getInt(DatabaseHelper.SURVEYOR_ID[0]);
		// try
		{
			// _db = new DatabaseHandler(this);
			dbHelper = new DatabaseHelper(this);
		}
		// catch (IOException e)
		{
			// e.printStackTrace();
		}
		// _db.open();
		trn_value = (AutoCompleteTextView) findViewById(R.id.trn);
		tfot = (TextView) findViewById(R.id.textView3);
		thot = (TextView) findViewById(R.id.textView5);
		tfog = (TextView) findViewById(R.id.textView10);
		trot = (TextView) findViewById(R.id.textView11);
		tman = (TextView) findViewById(R.id.tv1);
		tspa = (TextView) findViewById(R.id.tv3);
		et_tno = (EditText) findViewById(R.id.serial);
		et_boto = (EditText) findViewById(R.id.etspec);
		et_hft = (EditText) findViewById(R.id.etft);
		et_hmt = (EditText) findViewById(R.id.etmt);
		et_gcm = (EditText) findViewById(R.id.etgcm);
		et_gmt = (EditText) findViewById(R.id.etgmt);
		rd_g = (RadioGroup) findViewById(R.id.radio_g);
		rd_w = (RadioGroup) findViewById(R.id.radio_w);
		rb_himt = (RadioButton) findViewById(R.id.radio_imt);
		rb_gimt = (RadioButton) findViewById(R.id.radio_gimt);
		// add_tree=(Button)findViewById(R.id.button3);
		ch_loc = (Button) findViewById(R.id.button1);
		can = (Button) findViewById(R.id.button2);
		// exdata=(Button)findViewById(R.id.button4);
		sav = (Button) findViewById(R.id.button5);
		tap = (Button) findViewById(R.id.button6);
		btnTreeKey = (Button) findViewById(R.id.treeKey);

		// FOR AUTO COMPLETE
		list = new ArrayList<Treelist>();
		Log.d("tname", "hello2");
		// TreeCursor = _db.getAllTreedetailCursor();
		TreeCursor = dbHelper.getAllTreedetailCursor();
		Log.d("Survey form", "TreeCursor: " + TreeCursor.getCount());
		if (TreeCursor.moveToFirst())
			do
			{

				String tname = TreeCursor.getString(1);
				String bname = TreeCursor.getString(2);
				tl = new Treelist(tname, bname);
				// Log.d("tname", tname);
				list.add(tl);
			}
			while (TreeCursor.moveToNext());
		tl_value = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			tl = list.get(i);
			tl_value[i] = tl.get_tree_name();
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, tl_value);
		// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		trn_value.setThreshold(1);
		trn_value.setAdapter(dataAdapter);
		Log.i("Survey form", "trees: " + tl_value.length + ", list: " + list.size());
		trn_value.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				String selection = (String) arg0.getItemAtPosition(arg2);
				tree_name = selection;
				int pos = 0;
				for (int i = 0; i < tl_value.length; i++)
				{
					Log.i("Comparing", "tl_value: " + tl_value[i] + " = " + selection);
					if (tl_value[i].equals(selection))
					{
						pos = i;
						break;
					}
				}
				System.out.println("Position " + pos);
				System.out.println("pos: " + arg2 + ", selected : " + selection);
				tl = list.get(pos);
				et_boto.setText(tl.get_botonical_name());
				trn_value.clearFocus();
				rb_himt.setChecked(true);
				et_hmt.requestFocus();
				// rb_gimt.setChecked(true);
				// et_gmt.requestFocus();
			}
		});
		// this is for girth

		rd_g.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// Toast.makeText(Survey_form.this, "here", Toast.LENGTH_SHORT).show();
				rb_g = (RadioButton) findViewById(checkedId);
				if (rb_g.getId() == R.id.radio_gicm)
				// if (rb_g.getText().toString().equalsIgnoreCase("CentiMeter"))
				{
					et_gcm.setEnabled(true);
					et_gmt.setEnabled(false);
					et_gcm.requestFocus();
					et_gmt.removeTextChangedListener(gmt);
					et_gcm.addTextChangedListener(gcm);
					// et_gcm.setText("");
				}
				else
				{
					// Toast.makeText(Survey_form.this, "here1", Toast.LENGTH_SHORT).show();
					et_gcm.setEnabled(false);
					et_gmt.setEnabled(true);
					et_gmt.requestFocus();
					et_gcm.removeTextChangedListener(gcm);
					et_gmt.addTextChangedListener(gmt);
					// et_gmt.setText("");
				}
			}
		});

		// text watcher for girth cm to meter
		gcm = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{
				int i = s.length();
				if (i > 0)
				{
					String etgcm = et_gcm.getText().toString();
					double etgcm_i = Double.parseDouble(etgcm);
					double etg_m = etgcm_i / 100;
					et_gmt.setText("" + etg_m);
				}
				else
				{
					et_gmt.setText("");
				}
			}
		};

		// text watcher for girth mt to cm
		gmt = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				int i = s.length();
				if (i > 0)
				{
					String etgmt = et_gmt.getText().toString();
					double etgmt_i = Double.parseDouble(etgmt);
					double etg_cm = etgmt_i * 100;
					et_gcm.setText("" + etg_cm);
				}
				else
				{
					et_gcm.setText("");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		};

		// this is for height

		rd_w.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				rb_w = (RadioButton) findViewById(checkedId);
				if (rb_w.getText().toString().equalsIgnoreCase("Feet"))
				{
					et_hft.setEnabled(true);
					et_hmt.setEnabled(false);
					et_hft.requestFocus();
					et_hmt.removeTextChangedListener(hmt);
					et_hft.addTextChangedListener(hft);
					et_hft.setText("");
				}

				else
				{
					et_hft.setEnabled(false);
					et_hmt.setEnabled(true);
					et_hmt.requestFocus();
					et_hft.removeTextChangedListener(hft);
					et_hmt.addTextChangedListener(hmt);
					et_hmt.setText("");
				}
			}
		});

		// text watcher for height ft to mt
		hft = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				int i = s.length();
				Log.d("length", "" + i);
				if (i > 0)
				{
					String ethft = et_hft.getText().toString();
					double ethft_i = Double.parseDouble(ethft);
					double ethf_m = ethft_i * 0.305;
					et_hmt.setText("" + ethf_m);
				}
				else
				{
					et_hmt.setText("");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		};

		// text watcher for height mt to ft
		hmt = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				int i = s.length();
				Log.d("length", "" + i);
				if (i > 0)
				{
					String etm = et_hmt.getText().toString();
					double etm_i = Double.parseDouble(etm);
					double et_ft = etm_i / 0.305;
					et_hft.setText("" + et_ft);
				}
				else
				{
					et_hft.setText("");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		};

		et_hft.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
				{
					// Perform action on Enter key press
					et_hft.clearFocus();
					rb_gimt.setChecked(true);
					et_gmt.requestFocus();
					return true;
				}
				return false;
			}
		});
		et_hmt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int id, KeyEvent key)
			{
				if (id == EditorInfo.IME_ACTION_NEXT)
				{
					et_hmt.clearFocus();
					rb_gimt.setChecked(true);
					et_gmt.setEnabled(true);
					et_gmt.requestFocus();
					return true;
				}
				return false;
			}
		});
		et_hmt.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
				{
					// Perform action on Enter key press
					et_hmt.clearFocus();
					// et_hft.setEnabled(true);
					// et_hft.setFocusable(true);
					// et_hft.requestFocus();
					rb_gimt.setChecked(true);
					et_gmt.setEnabled(true);
					et_gmt.requestFocus();
					// et_gmt.setText("");

					// rb_himt.setChecked(true);
					// et_hmt.requestFocus();

					// InputMethodManager imm = (InputMethodManager)
					// getSystemService(Context.INPUT_METHOD_SERVICE);
					// imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
					return true;
				}
				return false;
			}
		});
		et_gmt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int id, KeyEvent key)
			{
				if (id == EditorInfo.IME_ACTION_DONE)
				{
					et_gmt.clearFocus();
					return true;
				}
				return false;
			}
		});
		et_gmt.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
				{
					// Perform action on Enter key press
					et_gmt.clearFocus();
					// rb_gimt.setChecked(true);
					// et_gmt.setEnabled(true);
					// et_gmt.requestFocus();
					// et_gmt.setText("");

					// rb_himt.setChecked(true);
					// rd_w.check(R.id.radio_imt);
					// et_hmt.requestFocus();

					// InputMethodManager imm = (InputMethodManager)
					// getSystemService(Context.INPUT_METHOD_SERVICE);
					// imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
					return true;
				}
				return false;
			}
		});

		btnTreeKey.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(Survey_form.this);
				builder.setTitle("Tree Help Key").setMessage("This Tree Key will help you to identify the Tree, in case you are not sure.\n" + "It is still under development at this time.").setPositiveButton("Understood", null).show();
			}
		});

		sav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				tree_no = et_tno.getText().toString();
				botanical_name = et_boto.getText().toString();
				String str = ("fno: " + fno + ", prop_id: " + prop_id + ", tree_no: " + tree_no + ", tree_name: " + tree_name + ", botanical: " + botanical_name + ", burrows: " + burrows + ", nest: " + nest + ", friuts: " + fruits + ", flowers: " + flowers + ", nails: " + nails + ", poster: " + poster + ", wires: " + wires + ", men_o: " + men_other + ", men_o_d: " + men_other_desc + ", health: " + health_tree + ", found_grnd: " + found_ground + ", grnd_cond: " + ground_condition + ", risk: " + risk_on_tree + ", risk_d: " + risk_desc + ", pest: " + pest_affected + ", refer: " + ref_to_dept + ", spl: " + s_other + ", spl_d: " + s_other_desc + ", gcm: " + et_gcm.getText().toString() + ", gm: " + et_gmt.getText().toString() + ", hf: " + et_hft.getText().toString() + ", hm: " + et_hmt.getText().toString());
				Log.d("values", str);
				AlertDialog.Builder builder = new AlertDialog.Builder(Survey_form.this);
				builder.setTitle("Values").setMessage(str).setCancelable(true).show();
				if (flag == 1)
				{
					if (pest_affected == null)
					{
						pest_affected = "nil";
					}

					// if (prop_id != null && fno != null && tree_no.length() > 0 && tree_name !=
					// null && botanical_name != null && burrows != -1 && nest != -1 && fruits != -1
					// && flowers != -1 && nails != -1 && poster != -1 && wires != -1 && tree_guard
					// != -1 && men_other != -1 && men_other_desc != null && health_tree != null &&
					// found_ground != null && ground_condition != null && risk_on_tree != null &&
					// risk_desc != null && pest_affected != null && ref_to_dept != -1 && s_other !=
					// -1 && s_other_desc != null && et_gcm.getText().toString().length() > 0 &&
					// et_gmt.getText().toString().length() > 0 &&
					// et_hft.getText().toString().length() > 0 &&
					// et_hmt.getText().toString().length() > 0
					if (prop_id != null && fno != null && tree_no.length() > 0 && tree_name != null && botanical_name != null && foundOnTreeDone && nuissanceOnTreeDone && healthOfTreeDone && groundConditionDone && riskDueToTreeDone && specialDone && et_gcm.getText().toString().length() > 0 && et_gmt.getText().toString().length() > 0 && et_hft.getText().toString().length() > 0 && et_hmt.getText().toString().length() > 0
					/*
					 * && lat > 0 && lon > 0
					 */)
					{

						height_ft = Double.parseDouble(et_hft.getText().toString());
						height_m = Double.parseDouble(et_hmt.getText().toString());
						girth_cm = Double.parseDouble(et_gcm.getText().toString());
						girth_m = Double.parseDouble(et_gmt.getText().toString());
						_sd = new Surveydetail(fno, prop_id, tree_no, tree_name, botanical_name, burrows, nest, fruits, flowers, nails, poster, wires, tree_guard, men_other, men_other_desc, health_tree, found_ground, ground_condition, risk_on_tree, risk_desc, pest_affected, ref_to_dept, s_other, s_other_desc, height_ft, height_m, girth_cm, girth_m, lat, lon);
						td = new TreeDetails();
						td.setFormNumber(fno);
						td.setPropertyId(prop_id);
						td.setNumber(Integer.parseInt(tree_no));
						td.setName(tree_name);
						td.setBotanicalName(botanical_name);
						td.setBurrows(burrows == 1 ? true : false);
						td.setNest(nest == 1 ? true : false);
						td.setFruits(fruits == 1 ? true : false);
						td.setFlowers(flowers == 1 ? true : false);
						td.setNails(nails == 1 ? true : false);
						td.setPoster(poster == 1 ? true : false);
						td.setWires(wires == 1 ? true : false);
						td.setTreeGuard(tree_guard == 1 ? true : false);
						td.setOtherNuissance(men_other == 1 ? true : false);
						td.setOtherNuissanceDesc(men_other_desc);
						td.setHealth(health_tree);
						td.setGroundType(found_ground);
						td.setGroundDesc(ground_condition);
						td.setRiskDueToTree(risk_on_tree);
						td.setRiskDesc(risk_desc);
						td.setReferToDept(ref_to_dept == 1 ? true : false);
						td.setSpecialOther(s_other == 1 ? true : false);
						td.setSpecialOtherDesc(s_other_desc);
						td.setHeight(height_m);
						td.setGirth(girth_m);
						td.setLattitude(lat);
						td.setLongitude(lon);
						String sessionId = dbHelper.getSessionAccordingTo10Mins(surveyorId);
						td.setSessionId(sessionId);
						td.setPrabhagId(Integer.parseInt(prabhagId));
						td.setClusterId(Integer.parseInt(clusterId));
						td.setImageF1(imageNames[0]);
						td.setImageF2(imageNames[1]);
						td.setSurveyorId(surveyorId);
						td.setImageP1(imageNames[2]);
						td.setImageP2(imageNames[3]);
						td.setImageOther(imageNames[4]);
						// _sd.setSessionId(sessionId);
						_sd.setPrabhagId(prabhagId);
						_sd.setClusterId(clusterId);
						// Toast.makeText(Survey_form.this, "surveyorId: " + surveyorId,
						// Toast.LENGTH_LONG).show();
						_sd.setSurveyorId(surveyorId);
						_sd.setImages(imageNames);
						// _db.insertSurveydetails(_sd);
						// dbHelper.insertSurveydetails(_sd);
						dbHelper.insertSurveydetails(td);
						dbHelper.updateSessionTime(sessionId, surveyorId);
						// dbHelper insert survey details method
						AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(Survey_form.this);
						alertDialogBuilder1.setMessage("Do you Want to add More Trees")//
						.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id)
							{
								// if this button is clicked, close
								// current activity
								dialog.cancel();
								Intent i = new Intent(Survey_form.this, Survey_form.class);
								i.putExtra("formno", fno);
								i.putExtra("prop_id", prop_id);
								i.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
								i.putExtra(DatabaseHelper.PRABHAG_ID[0], prabhagId);
								i.putExtra(DatabaseHelper.CLUSTER_ID[0], clusterId);
								startActivity(i);
								Survey_form.this.finish();
							}
						}).setNegativeButton("NO", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								dialog.cancel();
								Intent i = new Intent(Survey_form.this, MainActivity.class);
								i.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
								startActivity(i);
								Survey_form.this.finish();

							}
						}).show();

					}
					else
					{
						Toast.makeText(getApplicationContext(), "Please Enter full Details", Toast.LENGTH_LONG).show();
					}
				}
				else
				{

					Toast.makeText(getApplicationContext(), "Please Click Photo", Toast.LENGTH_LONG).show();
				}

			}
		});

		ch_loc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				if (fno != null || tree_no != null || tree_name != null || botanical_name != null || burrows != -1 || nest != -1 || fruits != -1 || flowers != -1 || nails != -1 || poster != -1 || wires != -1 || tree_guard != -1 || men_other != -1 || men_other_desc != null || health_tree != null || found_ground != null || ground_condition != null || risk_on_tree != null || risk_desc != null || pest_affected != null || et_gcm.getText().toString() != null || et_gmt.getText().toString() != null || et_hft.getText().toString() != null || et_hmt.getText().toString() != null)
				{
					AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(Survey_form.this);
					alertDialogBuilder2.setMessage("Data Not Saved!!! \n Do you Want to save???").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id)
						{
							// if this button is clicked, close
							// current activity
							dialog.cancel();

						}
					}).setNegativeButton("NO", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.cancel();
							Intent i = new Intent(Survey_form.this, MainActivity.class);
							i.putExtra("track", 1);
							startActivity(i);
							Survey_form.this.finish();
						}
					}).show();
				}
				else
				{
					Intent i = new Intent(Survey_form.this, MainActivity.class);
					startActivity(i);
					Survey_form.this.finish();
				}
			}
		});

		can.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(Survey_form.this);
				alertDialogBuilder2.setMessage("Tracking will stop \n Do you want to continue?").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id)
					{
						// if this button is clicked, close
						// current activity
						dialog.cancel();

						try
						{
							myTracksService.endCurrentTrack();
							tr = myTracksProviderUtils.getLastTrack();
							// int
							// i=myTracksProviderUtils.getTrack(myTracksProviderUtils.getLastTrack().getId()).getNumberOfPoints();
							Log.i("Survey form", "form number " + fno);
							tr.setName(fno);
							myTracksProviderUtils.updateTrack(tr);

							// Toast.makeText(getApplicationContext(),
							// ""+myTracksProviderUtils.getLastTrack().getId(),
							// Toast.LENGTH_SHORT).show();
						}
						catch (RemoteException e)
						{
							e.printStackTrace();
						}

						finish();

					}
				}).setNegativeButton("NO", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.cancel();

					}
				}).show();

			}
		});

		tap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{

				intent_cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				tr = myTracksProviderUtils.getLastTrack();
				if (tr == null)
				{
					tr = new Track();
					tr.setId(1);
				}
				if (et_tno.getText().toString().length() > 0 && tree_name != null)
				{
					Toast.makeText(getApplicationContext(), "press only save button when you reached near to tree", Toast.LENGTH_LONG).show();
					Waypoint waypoint = new Waypoint();
					waypoint.setTrackId(tr.getId());
					waypoint.setName(fno + "_" + et_tno.getText().toString());// .toLocaleString());
					waypoint.setDescription(tree_name);// .toLocaleString());
					waypoint.setCategory("Tree");
					waypoint.setLocation(myTracksProviderUtils.getLastValidTrackPoint());
					str = (System.currentTimeMillis() + "");
					str = str.substring(str.length() - 4, str.length());
					File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + androidId + "/EMAGPS/", fno + "_" + et_tno.getText().toString() + "_" + str + ".jpg");
					// Toast.makeText(getApplicationContext(),imagename+"1",
					// Toast.LENGTH_SHORT).show();
					outputFileUri = Uri.fromFile(file);
					intent_cam.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
					startActivityForResult(intent_cam, TAKE_PICTURE);
					// Toast.makeText(getApplicationContext(), ""+tr.getId(),
					// Toast.LENGTH_SHORT).show();

					myTracksProviderUtils.insertWaypoint(waypoint);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Enter tree No. Or tree name", Toast.LENGTH_LONG).show();
				}
			}
		});

		tfot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				final String foundOnTreeStr[] = new String[] { getResources().getString(R.string.txtNest), getResources().getString(R.string.txtBurrows), getResources().getString(R.string.txtFlowers), getResources().getString(R.string.txtFruits), getResources().getString(R.string.none) };
				builder = new AlertDialog.Builder(Survey_form.this);
				builder.setTitle(R.string.fot)//
				.setMultiChoiceItems(foundOnTreeStr, null, new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int pos, boolean selected)
					{
						ListView listView = ((AlertDialog) dialog).getListView();
						// Toast.makeText(getBaseContext(), pos + " " + selected,
						// Toast.LENGTH_SHORT).show();
						switch (pos)
						{
							case 0:
								nest = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(4, false);
									f_none = -1;
								}
								break;
							case 1:
								burrows = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(4, false);
									f_none = -1;
								}
								break;
							case 2:
								flowers = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(4, false);
									f_none = -1;
								}
								break;
							case 3:
								fruits = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(4, false);
									f_none = -1;
								}
								break;
							case 4:
								f_none = selected ? 1 : -1;
								if (selected)
								{
									for (int i = 0; i < foundOnTreeStr.length; i++)
									{
										if (i != 4)
										{
											listView.setItemChecked(i, false);
											listView.setSelection(i);
										}
									}
									nest = -1;
									burrows = -1;
									fruits = -1;
									flowers = -1;
								}
								break;
						}
					}
				})//
				.setCancelable(false)//
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						if (f_none == 1)
						{
							nest = -1;
							burrows = -1;
							flowers = -1;
							fruits = -1;
						}
						if (nest == -1 && burrows == -1 && flowers == -1 && fruits == -1 && f_none == -1)
						{
							showNothingSelectedAlertDialog();
						}
						else
						{
							foundOnTreeDone = true;
							tfot.setTextColor(Color.parseColor("#FFFF7F"));
							dialog.dismiss();
						}
					}
				})//
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						dialog.dismiss();
					}
				});

				builder.show();
			}
		});

		thot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
				final View popupView = layoutInflater.inflate(R.layout.hot, null);
				final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				popupWindow.setFocusable(true);
				final String[] healthStr = dbHelper.getHealthOfTreeCursor();
				builder = new AlertDialog.Builder(Survey_form.this);
				builder.setTitle(R.string.hot)//
				.setSingleChoiceItems(healthStr, -1, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int pos)
					{
						health_tree = healthStr[pos];
						// Toast.makeText(Survey_form.this, "pos: " + pos + " " + health_tree,
						// Toast.LENGTH_SHORT).show();
					}
				})//
				  // .setView(popupView)//
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						if (health_tree != null)
						{
							healthOfTreeDone = true;
							thot.setTextColor(Color.parseColor("#FFFF7F"));
							// Toast.makeText(Survey_form.this, "selected: " + health_tree,
							// Toast.LENGTH_LONG).show();
							dialog.cancel();
						}
						else
						{
							showNothingSelectedAlertDialog();
						}
					}
				})//
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						// clear selection, if made any
						health_tree = null;
						dialog.cancel();
					}
				})//
				.setCancelable(false);
				builder.show();
			}
		});

		tfog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				final String[] groundStr = dbHelper.getGroundTypeCursor();
				builder = new AlertDialog.Builder(Survey_form.this);
				builder.setTitle(R.string.fog)//
				.setSingleChoiceItems(groundStr, -1, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, int pos)
					{
						found_ground = groundStr[pos];
						if (found_ground.contains("open"))
						{
							runOnUiThread(new Runnable() {
								public void run()
								{
									final String openSoilStr[] = new String[] { "0 to 2ft", "2 to 4ft", "4 to 6ft", "6ft and above" };
									final AlertDialog.Builder foundOnGroundBuilder = new AlertDialog.Builder(Survey_form.this);
									foundOnGroundBuilder.setTitle("Select open soil type")//
									.setCancelable(false)//
									.setSingleChoiceItems(openSoilStr, -1, new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface arg0, int pos)
										{
											ground_condition = openSoilStr[pos];
										}
									})//
									.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface arg0, int arg1)
										{
											if (ground_condition == null)
											{
												runOnUiThread(new Runnable() {
													public void run()
													{
														showCustomDialogWhenValuesAreLeftEmpty(foundOnGroundBuilder);
													}
												});
											}
											else
											{
												groundConditionDone = true;
												tfog.setTextColor(Color.parseColor("#FFFF7F"));
												dialog.dismiss();
											}
										}
									})//
									.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface arg0, int arg1)
										{
											found_ground = null;
											arg0.dismiss();
										}
									}).show();
								}
							});
						}
					}
				})//
				.setCancelable(false)//
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						if (found_ground != null && !found_ground.equals(""))
						{
							if (found_ground.contains("open"))
							{
								if (ground_condition == null || ground_condition.equals(""))
								{
									showCustomDialogWhenValuesAreLeftEmpty(builder);
								}
							}
							else
							{
								groundConditionDone = true;
								ground_condition = "nil";
								tfog.setTextColor(Color.parseColor("#FFFF7F"));
							}
						}
						else
						{
							showNothingSelectedAlertDialog();
						}
					}
				})//
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						dialog.dismiss();
					}
				});

				builder.show();
			}
		});

		trot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				final String riskStr[] = dbHelper.getRiskDueToTreeCursor();
				builder = new AlertDialog.Builder(Survey_form.this);
				builder.setTitle(R.string.rot)//
				.setSingleChoiceItems(riskStr, -1, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int pos)
					{
						listView = ((AlertDialog) dialog).getListView();
						risk_on_tree = riskStr[pos];
						if (risk_on_tree.contains("none"))
						{
							risk_on_tree = "nil";
						}
						if (risk_on_tree.contains("other"))
						{
							Toast.makeText(Survey_form.this, risk_on_tree + "", Toast.LENGTH_SHORT).show();
							final AlertDialog.Builder txtBuilder = new AlertDialog.Builder(Survey_form.this);
							final EditText input = new EditText(Survey_form.this);
							input.setInputType(InputType.TYPE_CLASS_TEXT);
							txtBuilder.setTitle("Enter the risk details")//
							.setView(input)//
							.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									risk_desc = input.getText().toString();
									if (risk_desc == null || risk_desc.equals(""))
									{
										AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Survey_form.this);
										alertBuilder.setTitle("Alert")//
										.setMessage("Please enter the risk details")//
										.setPositiveButton("Let me try again", new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface arg0, int arg1)
											{
												risk_on_tree = null;
												int i = 0;
												for (i = 0; i < riskStr.length; i++)
												{
													if (risk_on_tree.equals(riskStr[i]))
													{
														break;
													}
												}
												listView.setItemChecked(i, false);
											}
										})//
										.show();
									}
									else
									{
										riskDueToTreeDone = true;
										trot.setTextColor(Color.parseColor("#FFFF7F"));
										dialog.dismiss();
									}
								}
							})//
							.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									dialog.dismiss();
								}
							})//
							.show();
						}
						else
						{
							risk_desc = "nil";
						}
					}
				})//
				  // .setView(popupView)//
				.setCancelable(false)//
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						if (risk_on_tree == null || risk_on_tree.equals("") || (risk_on_tree.equals("other") && (risk_desc == null || risk_desc.equals(""))))
						{
							showNothingSelectedAlertDialog();
						}
						else
						{
							riskDueToTreeDone = true;
							trot.setTextColor(Color.parseColor("#FFFF7F"));
							dialog.dismiss();
						}
					}
				})//
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						dialog.dismiss();
					}
				});

				builder.show();
			}
		});

		tman.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				final String nuissanceStr[] = new String[] { getResources().getString(R.string.txtNails), getResources().getString(R.string.txtPoster), getResources().getString(R.string.txtWires), getResources().getString(R.string.txtTreeGuard), getResources().getString(R.string.none), getResources().getString(R.string.txtOtherNuissanceDesc) };
				builder = new AlertDialog.Builder(Survey_form.this);
				builder.setTitle(R.string.man_type).setCancelable(false)//
				.setMultiChoiceItems(nuissanceStr, null, new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, int pos, boolean selected)
					{
						listView = ((AlertDialog) dialog).getListView();
						switch (pos)
						{
							case 0:
								nails = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(4, false);
									n_none = -1;
								}
								break;
							case 1:
								poster = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(4, false);
									n_none = -1;
								}
								break;
							case 2:
								wires = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(4, false);
									n_none = -1;
								}
								break;
							case 3:
								tree_guard = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(4, false);
									n_none = -1;
								}
								break;
							case 4:
								n_none = selected ? 1 : -1;
								if (selected)
								{
									nails = -1;
									poster = -1;
									wires = -1;
									tree_guard = -1;
									men_other = -1;
									men_other_desc = "nil";
									for (int i = 0; i < nuissanceStr.length; i++)
									{
										if (i != 4)
										{
											listView.setItemChecked(i, false);
										}
									}
								}
								break;
							case 5:
								men_other = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(4, false);
									n_none = -1;
								}
								break;
						}
						if (men_other == 1)
						{
							final AlertDialog.Builder txtBuilder = new AlertDialog.Builder(Survey_form.this);
							final EditText input = new EditText(Survey_form.this);
							input.setInputType(InputType.TYPE_CLASS_TEXT);
							txtBuilder.setTitle("Enter details")//
							.setView(input)//
							.setCancelable(false)//
							.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1)
								{
									men_other_desc = input.getText().toString();
									if (men_other_desc == null || men_other_desc.equals(""))
									{
										runOnUiThread(new Runnable() {
											public void run()
											{
												AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Survey_form.this);
												alertBuilder.setTitle("Alert")//
												.setMessage("Please enter the details properly")//
												.setPositiveButton("Let me try again", new DialogInterface.OnClickListener() {

													@Override
													public void onClick(DialogInterface arg0, int arg1)
													{
														men_other = -1;
														listView.setItemChecked(5, false);
														// Survey_form.this.builder.show();
													}
												}).show();
											}
										});
									}
									else
									{
										nuissanceOnTreeDone = true;
										tman.setTextColor(Color.parseColor("#FFFF7F"));
										dialog.dismiss();
									}
								}
							})//
							.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1)
								{
									men_other = -1;
									arg0.dismiss();
								}
							})//
							.show();
						}
					}
				})//
				  // .setView(popupView)//
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						if (nails == -1 && poster == -1 && wires == -1 && tree_guard == -1 && men_other == -1 && n_none == -1)
						{
							showNothingSelectedAlertDialog();
						}
						else
						{
							nuissanceOnTreeDone = true;
							tman.setTextColor(Color.parseColor("#FFFF7F"));
							dialog.dismiss();
						}
					}
				})//
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						dialog.dismiss();
					}
				});

				builder.show();
			}
		});

		tspa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				s_none = -1;
				final AlertDialog.Builder txtBuilder = new AlertDialog.Builder(Survey_form.this);
				final String specialStr[] = new String[] { getResources().getString(R.string.txtReferToDept), getResources().getString(R.string.none), getResources().getString(R.string.ot_or) };
				builder = new AlertDialog.Builder(Survey_form.this);
				builder.setTitle(R.string.spa)//
				.setCancelable(false)//
				.setMultiChoiceItems(specialStr, null, new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, int pos, boolean selected)
					{
						listView = ((AlertDialog) dialog).getListView();
						switch (pos)
						{
							case 0:
								ref_to_dept = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(1, false);
									s_none = -1;
								}
								break;
							case 1:
								if (selected)
								{
									ref_to_dept = -1;
									s_other = -1;
									for (int i = 0; i < specialStr.length; i++)
									{
										if (i != 1)
										{
											listView.setItemChecked(i, false);
										}
									}
								}
								s_none = selected ? 1 : -1;
								break;
							case 2:
								s_other = selected ? 1 : -1;
								if (selected)
								{
									listView.setItemChecked(1, false);
									s_none = -1;
								}
								break;
						}
						if (s_other == 1)
						{
							final EditText input = new EditText(Survey_form.this);
							input.setInputType(InputType.TYPE_CLASS_TEXT);
							txtBuilder.setTitle("Enter details")//
							.setCancelable(false)//
							.setView(input)//
							.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg, int arg1)
								{
									s_other_desc = input.getText().toString();
									if (s_other_desc == null || s_other_desc.equals(""))
									{
										AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Survey_form.this);
										alertBuilder.setTitle("Alert")//
										.setMessage("Please do not leave the details blank")//
										.setCancelable(false)//
										.setPositiveButton("Let me try again", new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface arg0, int arg1)
											{
												listView.setItemChecked(2, false);
												s_other = -1;
											}
										})//
										.show();
									}
									else
									{
										arg.dismiss();
										specialDone = true;
										tspa.setTextColor(Color.parseColor("#FFFF7F"));
										dialog.dismiss();
									}
								}
							})//
							.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1)
								{
									s_other = -1;
									arg0.dismiss();
								}
							})//
							.show();
						}
					}
				})//
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int pos)
					{
						if (s_none == 1)
						{
							ref_to_dept = -1;
							s_other = -1;
							s_other_desc = "nil";
						}

						if (s_other == 1 && (s_other_desc == null || s_other_desc.equals("")))
						{
							AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Survey_form.this);
							alertBuilder.setTitle("Alert")//
							.setCancelable(false)//
							.setMessage("You cannot leave the other details blank if other is selected")//
							.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1)
								{
									ref_to_dept = -1;
									s_none = -1;
									s_other = -1;
									s_other_desc = null;
								}
							})//
							.show();
						}

						if (ref_to_dept == -1 && s_other == -1 && s_none == -1)
						{
							showNothingSelectedAlertDialog();
						}
						else
						{
							dialog.dismiss();
							specialDone = true;
							tspa.setTextColor(Color.parseColor("#FFFF7F"));
						}
					}
				})//
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1)
					{
						dialog.dismiss();
					}
				});

				builder.show();
			}
		});

		// layout specific
		LinearLayout l1 = (LinearLayout) findViewById(R.id.mainAcLayout1);
		LinearLayout l2 = (LinearLayout) findViewById(R.id.mainAcLayout2);
		LinearLayout l3 = (LinearLayout) findViewById(R.id.mainAcLayout3);
		LinearLayout l4 = (LinearLayout) findViewById(R.id.mainAcLayout4);
		LinearLayout l5 = (LinearLayout) findViewById(R.id.mainAcLayout5);
		LinearLayout l6 = (LinearLayout) findViewById(R.id.mainAcLayout6);
		if (getResources().getConfiguration().screenWidthDp >= 600)
		{
			l1.setOrientation(0);
			l3.setOrientation(0);
			l4.setOrientation(0);
			l5.setOrientation(0);
			l6.setOrientation(0);
		}
		else
		{
			l1.setOrientation(1);
			l3.setOrientation(1);
			l4.setOrientation(1);
			l5.setOrientation(1);
			l6.setOrientation(1);
		}
		if (getResources().getConfiguration().screenWidthDp > 850)
		{
			l2.setOrientation(0);
		}
		else
		{
			l2.setOrientation(1);
		}
	}

	private void showNothingSelectedAlertDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Survey_form.this);
		builder.setTitle("Alert")//
		.setCancelable(false)//
		.setMessage("Please select the appropriate option")//
		.setPositiveButton("Let me try again", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				Survey_form.this.builder.show();
			}
		})//
		.show();
	}

	private void showCustomDialogWhenValuesAreLeftEmpty(final AlertDialog.Builder dialog)
	{
		AlertDialog.Builder openSoilBuilder = new AlertDialog.Builder(Survey_form.this);
		openSoilBuilder.setTitle("Alert")//
		.setCancelable(false)//
		.setMessage("Please select the appropriate type")//
		.setPositiveButton("Let me try again", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int pos)
			{
				dialog.show();
			}
		}).show();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		bindService(intent, serviceConnection, BIND_AUTO_CREATE);
		dbHelper.openDatabase();
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		// unbind and stop the MyTracks service
		if (myTracksService != null)
		{
			unbindService(serviceConnection);
		}
		// stopService(intent);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		// dbHelper.close();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == TAKE_PICTURE)
		{
			// \n is for new line
			// Toast.makeText(getApplicationContext(), Toast.LENGTH_SHORT).show();
			// String filePath = data.getData().toString();
			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + androidId + "/EMAGPS/", fno + "_" + et_tno.getText().toString() + "_" + str + ".jpg");
			// File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/" +
			// androidId + "/EMAGPS/" + fno + "_" + et_tno.getText().toString() + ".jpg");
			Toast.makeText(getApplicationContext(), "" + file.toString(), Toast.LENGTH_LONG).show();
			// System.out.println(f.getAbsolutePath());
			double latitude;
			double longitude;
			try
			{
				latitude = myTracksProviderUtils.getLastValidTrackPoint().getLatitude();
				longitude = myTracksProviderUtils.getLastValidTrackPoint().getLongitude();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				latitude = 0.0;
				longitude = 0.0;
			}
			Toast.makeText(getApplicationContext(), latitude + "----" + longitude, Toast.LENGTH_SHORT).show();
			geoTag(file.getAbsolutePath(), latitude, longitude);
			/*
			 * Intent intent= new Intent(Survey_form.this, com.gestell.GPS.MapAct.class);
			 * startActivity(intent);
			 */

		}

	}

	public void geoTag(String absolutePath, double latitude, double longitude)
	{

		ExifInterface exif;

		try
		{
			exif = new ExifInterface(absolutePath);
			boolean panorama = false;
			double height = exif.getAttributeDouble(ExifInterface.TAG_IMAGE_LENGTH, 0);
			double width = exif.getAttributeDouble(ExifInterface.TAG_IMAGE_WIDTH, 0);
			if (width - height > 1000 || height - width > 1000)
			{
				panorama = true;
			}
			if (panorama)
			{
				if (imageNames[2].equals(""))
				{
					imageNames[2] = absolutePath;
				}
				else
				{
					imageNames[3] = absolutePath;
				}
			}
			else
			{
				if (imageNames[0].equals(""))
				{
					imageNames[0] = absolutePath;
				}
				else if (imageNames[1].equals(""))
				{
					imageNames[1] = absolutePath;
				}
				else
				{
					imageNames[4] = absolutePath;
				}
			}
			int num1Lat = (int) Math.floor(latitude);
			int num2Lat = (int) Math.floor((latitude - num1Lat) * 60);
			double num3Lat = (latitude - ((double) num1Lat + ((double) num2Lat / 60))) * 3600000;

			int num1Lon = (int) Math.floor(longitude);
			int num2Lon = (int) Math.floor((longitude - num1Lon) * 60);
			double num3Lon = (longitude - ((double) num1Lon + ((double) num2Lon / 60))) * 3600000;

			exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, num1Lat + "/1," + num2Lat + "/1," + num3Lat + "/1000");
			exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, num1Lon + "/1," + num2Lon + "/1," + num3Lon + "/1000");

			if (latitude > 0)
			{
				exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N");
			}
			else
			{
				exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "S");
			}

			if (longitude > 0)
			{
				exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "E");
			}
			else
			{
				exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "W");
			}

			exif.saveAttributes();
			lat = latitude;
			lon = longitude;
			flag = 1;
		}
		catch (IOException e)
		{
			Log.e("PictureActivity", e.getLocalizedMessage());
		}
	}
}
