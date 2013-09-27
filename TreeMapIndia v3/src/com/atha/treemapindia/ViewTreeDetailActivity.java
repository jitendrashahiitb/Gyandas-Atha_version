package com.atha.treemapindia;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTreeDetailActivity extends Activity
{

	EditText	   txtNumber	         = null;
	EditText	   txtName	             = null;
	EditText	   txtBotanicalName	     = null;
	EditText	   txtPrabhagId	         = null;
	EditText	   txtClusterId	         = null;
	EditText	   txtPropertyType	     = null;
	EditText	   txtGirth	             = null;
	EditText	   txtHeight	         = null;
	EditText	   txtNest	             = null;
	EditText	   txtBurrows	         = null;
	EditText	   txtFlowers	         = null;
	EditText	   txtFruits	         = null;
	EditText	   txtNails	             = null;
	EditText	   txtPoster	         = null;
	EditText	   txtWires	             = null;
	EditText	   txtTreeGuard	         = null;
	EditText	   txtOtherNuissanceDesc	= null;
	EditText	   txtHealthOfTree	     = null;
	EditText	   txtGroundType	     = null;
	EditText	   txtGroundDesc	     = null;
	EditText	   txtRisk	             = null;
	EditText	   txtRiskDesc	         = null;
	EditText	   txtReferToDept	     = null;
	EditText	   txtSpecialDesc	     = null;
	EditText	   txtLatitude	         = null;
	EditText	   txtLongitude	         = null;
	EditText	   txtPoint	             = null;
	EditText	   txtCreationDate	     = null;
	EditText	   txtCreationTime	     = null;
	EditText	   txtSurveyorName	     = null;

	Spinner	       spinnerHealthOfTree	 = null;
	Spinner	       spinnerGroundType	 = null;
	Spinner	       spinnerGroundDesc	 = null;
	Spinner	       spinnerRisk	         = null;
	Spinner	       spinnerReferToDept	 = null;

	// array of all edit texts so that they are easier to manipulate
	EditText	   txtArr[]	             = { /*
											 * txtNumber, txtName, txtBotanicalName, txtPrabhagId,
											 * txtClusterId, txtPropertyType ,
											 */txtGirth, txtHeight, /*
																	 * txtNest , txtBurrows ,
																	 * txtFlowers , txtFruits ,
																	 * txtNails , txtPoster ,
																	 * txtWires , txtTreeGuard ,
																	 * txtOtherNuissanceDesc ,
																	 */txtHealthOfTree, txtGroundType, txtGroundDesc, txtRisk, txtRiskDesc, txtReferToDept, txtSpecialDesc, /*
																																											 * txtLatitude
																																											 * ,
																																											 * txtLongitude
																																											 * ,
																																											 * txtPoint
																																											 * ,
																																											 * txtCreationDate
																																											 * ,
																																											 * txtCreationTime
																																											 * ,
																																											 * txtSurveyorName
																																											 */};

	DatabaseHelper	db	                 = null;
	TreeDetails	   treeDetail	         = null;
	Session	       session	             = null;
	int	           pos	                 = -1;
	int	           surveyorId;
	boolean	       showGroundDesc	     = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_tree_detail);

		txtNumber = (EditText) findViewById(R.id.txtNumber);
		txtName = (EditText) findViewById(R.id.txtName);
		txtBotanicalName = (EditText) findViewById(R.id.txtBotanicalName);
		txtPrabhagId = (EditText) findViewById(R.id.txtPrabhagId);
		txtClusterId = (EditText) findViewById(R.id.txtClusterId);
		txtPropertyType = (EditText) findViewById(R.id.txtPropertyType);
		txtGirth = (EditText) findViewById(R.id.txtGirth);
		txtHeight = (EditText) findViewById(R.id.txtHeight);
		txtNest = (EditText) findViewById(R.id.txtNest);
		txtBurrows = (EditText) findViewById(R.id.txtBurrows);
		txtFlowers = (EditText) findViewById(R.id.txtFlowers);
		txtFruits = (EditText) findViewById(R.id.txtFruits);
		txtNails = (EditText) findViewById(R.id.txtNails);
		txtPoster = (EditText) findViewById(R.id.txtPoster);
		txtWires = (EditText) findViewById(R.id.txtWires);
		txtTreeGuard = (EditText) findViewById(R.id.txtTreeGuard);
		txtOtherNuissanceDesc = (EditText) findViewById(R.id.txtOtherNuissanceDesc);
		txtHealthOfTree = (EditText) findViewById(R.id.txtHealthOfTree);
		txtGroundType = (EditText) findViewById(R.id.txtGroundType);
		txtGroundDesc = (EditText) findViewById(R.id.txtGroundDesc);
		txtRisk = (EditText) findViewById(R.id.txtRisk);
		txtRiskDesc = (EditText) findViewById(R.id.txtRiskDesc);
		txtReferToDept = (EditText) findViewById(R.id.txtReferToDept);
		txtSpecialDesc = (EditText) findViewById(R.id.txtSpecialDesc);
		txtLatitude = (EditText) findViewById(R.id.txtLatitude);
		txtLongitude = (EditText) findViewById(R.id.txtLongitude);
		txtPoint = (EditText) findViewById(R.id.txtPoint);
		txtCreationDate = (EditText) findViewById(R.id.txtCreationDate);
		txtCreationTime = (EditText) findViewById(R.id.txtCreationTime);
		txtSurveyorName = (EditText) findViewById(R.id.txtSurveyorName);

		spinnerGroundDesc = (Spinner) findViewById(R.id.spinnerGroundDesc);
		spinnerGroundType = (Spinner) findViewById(R.id.spinnerGroundType);
		spinnerHealthOfTree = (Spinner) findViewById(R.id.spinnerHealthOfTree);
		spinnerReferToDept = (Spinner) findViewById(R.id.spinnerReferToDept);
		spinnerRisk = (Spinner) findViewById(R.id.spinnerRisk);

		txtArr[0] = txtGirth;
		txtArr[1] = txtHeight;
		txtArr[2] = txtHealthOfTree;
		txtArr[3] = txtGroundType;
		txtArr[4] = txtGroundDesc;
		txtArr[5] = txtRisk;
		txtArr[6] = txtRiskDesc;
		txtArr[7] = txtReferToDept;
		txtArr[8] = txtSpecialDesc;

		LinearLayout layoutFoundOnTree = (LinearLayout) findViewById(R.id.layoutFoundOnTree);
		LinearLayout layoutRisk = (LinearLayout) findViewById(R.id.layoutRisk);
		LinearLayout layoutRiskDesc = (LinearLayout) findViewById(R.id.layoutRiskDesc);
		LinearLayout layoutSpecial = (LinearLayout) findViewById(R.id.layoutSpecialDesc);
		LinearLayout layoutPoint = (LinearLayout) findViewById(R.id.layoutPoint);

		boolean boolFoundOnTreeShouldBeVisible = false;

		Bundle bundle = getIntent().getExtras();
		treeDetail = (TreeDetails) bundle.get("treeDetail");
		session = (Session) bundle.get("session");
		pos = (Integer) bundle.get("pos");
		surveyorId = bundle.getInt(DatabaseHelper.SURVEYOR_ID[0]);

		txtNumber.setText(treeDetail.getNumber() + "");
		txtName.setText(treeDetail.getName());
		txtBotanicalName.setText(treeDetail.getBotanicalName());
		txtPrabhagId.setText(treeDetail.getPrabhagId() + "");
		txtClusterId.setText(treeDetail.getClusterId() + "");
		txtPropertyType.setText(treeDetail.getPropertyType());
		txtGirth.setText(treeDetail.getGirth() + "");
		txtHeight.setText(treeDetail.getHeight() + "");

		if (treeDetail.isNest())
		{
			txtNest.setVisibility(View.VISIBLE);
			boolFoundOnTreeShouldBeVisible = true;
		}
		else
		{
			txtNest.setVisibility(View.GONE);
		}

		if (treeDetail.isBurrows())
		{
			txtBurrows.setVisibility(View.VISIBLE);
			boolFoundOnTreeShouldBeVisible = true;
		}
		else
		{
			txtBurrows.setVisibility(View.GONE);
		}

		if (treeDetail.isFlowers())
		{
			txtFlowers.setVisibility(View.VISIBLE);
			boolFoundOnTreeShouldBeVisible = true;
		}
		else
		{
			txtFlowers.setVisibility(View.GONE);
		}

		if (treeDetail.isFruits())
		{
			txtFruits.setVisibility(View.VISIBLE);
			boolFoundOnTreeShouldBeVisible = true;
		}
		else
		{
			txtFruits.setVisibility(View.GONE);
		}

		if (treeDetail.isNails())
		{
			txtNails.setVisibility(View.VISIBLE);
			boolFoundOnTreeShouldBeVisible = true;
		}
		else
		{
			txtNails.setVisibility(View.GONE);
		}

		if (treeDetail.isPoster())
		{
			txtPoster.setVisibility(View.VISIBLE);
			boolFoundOnTreeShouldBeVisible = true;
		}
		else
		{
			txtPoster.setVisibility(View.GONE);
		}

		if (treeDetail.isWires())
		{
			txtWires.setVisibility(View.VISIBLE);
			boolFoundOnTreeShouldBeVisible = true;
		}
		else
		{
			txtWires.setVisibility(View.GONE);
		}

		if (treeDetail.isTreeGuard())
		{
			txtTreeGuard.setVisibility(View.VISIBLE);
			boolFoundOnTreeShouldBeVisible = true;
		}
		else
		{
			txtTreeGuard.setVisibility(View.GONE);
		}

		if (treeDetail.isOtherNuissance())
		{
			txtOtherNuissanceDesc.setVisibility(View.VISIBLE);
			txtOtherNuissanceDesc.setText(treeDetail.getOtherNuissanceDesc());
			boolFoundOnTreeShouldBeVisible = true;
		}
		else
		{
			txtOtherNuissanceDesc.setVisibility(View.GONE);
		}

		txtHealthOfTree.setText(treeDetail.getHealth());
		txtGroundType.setText(treeDetail.getGroundType());
		if (treeDetail.getGroundDesc().equals("") || treeDetail.getGroundDesc().equals("nil"))
		{
			txtGroundDesc.setVisibility(View.GONE);
		}
		else
		{
			txtGroundDesc.setText(treeDetail.getGroundDesc());
		}
		if (treeDetail.getRiskDueToTree().equals("") || treeDetail.getRiskDueToTree().equals("nil"))
		{
			layoutRisk.setVisibility(View.GONE);
		}
		else
		{
			txtRisk.setText(treeDetail.getRiskDueToTree());
			layoutRisk.setVisibility(View.VISIBLE);
		}
		if (treeDetail.getRiskDesc() == null || treeDetail.getRiskDesc().equals("") || treeDetail.getRiskDesc().equals("nil"))
		{
			layoutRiskDesc.setVisibility(View.GONE);
		}
		else
		{
			txtRiskDesc.setText(treeDetail.getRiskDesc());
		}
		txtReferToDept.setText(treeDetail.isReferToDept() ? "Yes" : "No");
		if (treeDetail.isSpecialOther())
		{
			txtSpecialDesc.setText(treeDetail.getSpecialOtherDesc());
		}
		else
		{
			layoutSpecial.setVisibility(View.GONE);
		}
		txtLatitude.setText(treeDetail.getLattitude() + "");
		txtLongitude.setText(treeDetail.getLongitude() + "");
		if (treeDetail.getPoint() == null || treeDetail.getPoint().equals("") || treeDetail.getPoint().equals("nil"))
		{
			layoutPoint.setVisibility(View.GONE);
		}
		else
		{
			txtPoint.setText(treeDetail.getPoint());
		}
		txtCreationDate.setText(treeDetail.getCreationDate());
		txtCreationTime.setText(treeDetail.getCreationTime());
		txtSurveyorName.setText(treeDetail.getSurveyorName());

		if (!boolFoundOnTreeShouldBeVisible)
		{
			layoutFoundOnTree.setVisibility(View.GONE);
		}

		// Toast.makeText(ViewTreeDetailActivity.this, boolFoundOnTreeShouldBeVisible + "",
		// Toast.LENGTH_SHORT).show();

		final Button btnEdit = (Button) findViewById(R.id.btnEdit);
		final Button btnDelete = (Button) findViewById(R.id.btnDelete);

		btnEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view)
			{
				if (btnEdit.getText().equals(getApplication().getString(R.string.txtEdit)))
				{
					if (db == null)
					{
						db = new DatabaseHelper(ViewTreeDetailActivity.this);
					}
					else
					{
						db.openDatabase();
					}
					// set up spinners

					// set up health spinner
					if (spinnerHealthOfTree.getAdapter() == null)
					{
						String healthStr[] = db.getHealthOfTreeCursor();
						ArrayAdapter<String> healthAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, healthStr);
						spinnerHealthOfTree.setAdapter(healthAdapter);
						spinnerHealthOfTree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
							{

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0)
							{

							}
						});
						for (int i = 0; i < healthStr.length; i++)
						{
							if (spinnerHealthOfTree.getAdapter().getItem(i).toString().equals(txtHealthOfTree.getText().toString()))
							{
								spinnerHealthOfTree.setSelection(i);
								break;
							}
						}
					}

					// set up ground type spinner
					if (spinnerGroundType.getAdapter() == null)
					{
						String groundTypeStr[] = db.getGroundTypeCursor();
						ArrayAdapter<String> groundTypeAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, groundTypeStr);
						spinnerGroundType.setAdapter(groundTypeAdapter);
						spinnerGroundType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> adapter, View view, int pos, long arg3)
							{
								if (adapter.getItemAtPosition(pos).equals("open soil"))
								{
									showGroundDesc = true;
									spinnerGroundDesc.setVisibility(View.VISIBLE);
								}
								else
								{
									showGroundDesc = false;
									spinnerGroundDesc.setVisibility(View.GONE);
								}
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0)
							{

							}
						});
						for (int i = 0; i < groundTypeStr.length; i++)
						{
							if (spinnerGroundType.getAdapter().getItem(i).toString().equals(txtGroundType.getText().toString()))
							{
								spinnerGroundType.setSelection(i);
								break;
							}
						}
					}

					// set up ground desc spinner
					if (spinnerGroundDesc.getAdapter() == null)
					{
						String groundDescStr[] = new String[] { "0 to 2ft", "2 to 4ft", "4 to 6ft", "6ft and above" };
						ArrayAdapter<String> groundDescAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, groundDescStr);
						spinnerGroundDesc.setAdapter(groundDescAdapter);
						for (int i = 0; i < groundDescStr.length; i++)
						{
							if (spinnerGroundDesc.getAdapter().getItem(i).toString().equals(txtGroundDesc.getText().toString()))
							{
								spinnerGroundDesc.setSelection(i);
								break;
							}
						}
						spinnerGroundDesc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
							{

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0)
							{

							}
						});
					}

					// set up risk due to tree spinner
					if (spinnerRisk.getAdapter() == null)
					{
						String riskStr[] = db.getRiskDueToTreeCursor();
						ArrayAdapter<String> riskAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, riskStr);
						spinnerRisk.setAdapter(riskAdapter);
						spinnerRisk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> adapter, View view, int pos, long arg3)
							{
								if (adapter.getItemAtPosition(pos).equals("other"))
								{
									txtRiskDesc.setVisibility(View.VISIBLE);
								}
								else
								{
									txtRiskDesc.setVisibility(View.GONE);
								}
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0)
							{

							}
						});
						for (int i = 0; i < riskStr.length; i++)
						{
							if (spinnerRisk.getAdapter().getItem(i).toString().equals(txtRisk.getText().toString()))
							{
								spinnerRisk.setSelection(i);
								break;
							}
						}
					}

					// set up refer to dept spinner
					if (spinnerReferToDept.getAdapter() == null)
					{
						String referToDeptStr[] = new String[] { "Yes", "No" };
						ArrayAdapter<String> referToDeptAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, referToDeptStr);
						spinnerReferToDept.setAdapter(referToDeptAdapter);
						for (int i = 0; i < referToDeptStr.length; i++)
						{
							if (spinnerReferToDept.getAdapter().getItem(i).toString().equals(txtReferToDept.getText().toString()))
							{
								spinnerReferToDept.setSelection(i);
								break;
							}
						}
						spinnerReferToDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
							{

							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0)
							{

							}
						});
					}

					// make spinners visible and edittexts gone

					// make health spinner visible
					if (txtHealthOfTree.getVisibility() == View.VISIBLE)
					{
						txtHealthOfTree.setVisibility(View.GONE);
						spinnerHealthOfTree.setVisibility(View.VISIBLE);
					}

					// make ground type spinner visible
					if (txtGroundType.getVisibility() == View.VISIBLE)
					{
						txtGroundType.setVisibility(View.GONE);
						spinnerGroundType.setVisibility(View.VISIBLE);
					}

					// make ground desc spinner visible
					if (txtGroundDesc.getVisibility() == View.VISIBLE)
					{
						txtGroundDesc.setVisibility(View.GONE);
						spinnerGroundDesc.setVisibility(View.VISIBLE);
					}

					// make risk spinner visible
					if (txtRisk.getVisibility() == View.VISIBLE)
					{
						txtRisk.setVisibility(View.GONE);
						spinnerRisk.setVisibility(View.VISIBLE);
					}

					// make refer to dept spinner visible
					if (txtReferToDept.getVisibility() == View.VISIBLE)
					{
						txtReferToDept.setVisibility(View.GONE);
						spinnerReferToDept.setVisibility(View.VISIBLE);
					}

					for (int i = 0; i < txtArr.length; i++)
					{
						txtArr[i].setEnabled(true);
					}
					btnEdit.setText(getApplication().getString(R.string.txtSave));
					btnDelete.setText(getApplication().getString(R.string.txtCancel));
					// btnDelete.setEnabled(false);
				}
				else
				{
					// make spinners gone and edittexts visible

					// make health spinner gone
					if (spinnerHealthOfTree.getVisibility() == View.VISIBLE)
					{
						txtHealthOfTree.setVisibility(View.VISIBLE);
						txtHealthOfTree.setText(spinnerHealthOfTree.getSelectedItem().toString());
						spinnerHealthOfTree.setVisibility(View.GONE);
					}

					// make ground type spinner gone
					if (spinnerGroundType.getVisibility() == View.VISIBLE)
					{
						txtGroundType.setVisibility(View.VISIBLE);
						txtGroundType.setText(spinnerGroundType.getSelectedItem().toString());
						spinnerGroundType.setVisibility(View.GONE);
					}

					// make ground desc spinner gone
					if (spinnerGroundDesc.getVisibility() == View.VISIBLE)
					{
						txtGroundDesc.setVisibility(View.VISIBLE);
						txtGroundDesc.setText(spinnerGroundDesc.getSelectedItem().toString());
						spinnerGroundDesc.setVisibility(View.GONE);
					}

					// make risk spinner gone
					if (spinnerRisk.getVisibility() == View.VISIBLE)
					{
						txtRisk.setVisibility(View.VISIBLE);
						txtRisk.setText(spinnerRisk.getSelectedItem().toString());
						spinnerRisk.setVisibility(View.GONE);
					}

					// make refer to dept spinner gone
					if (spinnerReferToDept.getVisibility() == View.VISIBLE)
					{
						txtReferToDept.setVisibility(View.VISIBLE);
						txtReferToDept.setText(spinnerReferToDept.getSelectedItem().toString());
						spinnerReferToDept.setVisibility(View.GONE);
					}

					for (int i = 0; i < txtArr.length; i++)
					{
						txtArr[i].setEnabled(false);
					}

					btnEdit.setText(getApplication().getString(R.string.txtEdit));
					btnDelete.setText(getApplication().getString(R.string.txtDelete));
					// btnDelete.setEnabled(true);

					treeDetail.setGirth(Double.parseDouble(txtGirth.getText().toString()));
					treeDetail.setHeight(Double.parseDouble(txtHeight.getText().toString()));
					treeDetail.setHealth(txtHealthOfTree.getText().toString());
					treeDetail.setGroundType(txtGroundType.getText().toString());
					String groundDesc = txtGroundDesc.getText().toString();
					groundDesc = groundDesc.equals("") ? "nil" : groundDesc;
					treeDetail.setGroundDesc(groundDesc);
					treeDetail.setRiskDueToTree(txtRisk.getText().toString());
					String riskDesc = txtRiskDesc.getText().toString();
					riskDesc = riskDesc.equals("") ? "nil" : riskDesc;
					treeDetail.setRiskDesc(riskDesc);
					treeDetail.setReferToDept(txtReferToDept.getText().toString().equals("Yes") ? true : false);
					String specialDesc = txtSpecialDesc.getText().toString();
					specialDesc = specialDesc.equals("") ? "nil" : specialDesc;
					treeDetail.setSpecialOtherDesc(specialDesc);
					treeDetail.setLattitude(Double.parseDouble(txtLatitude.getText().toString()));
					treeDetail.setLongitude(Double.parseDouble(txtLongitude.getText().toString()));
					treeDetail.setPoint(txtPoint.getText().toString());

					if (db == null)
					{
						db = new DatabaseHelper(ViewTreeDetailActivity.this);
					}
					else
					{
						db.openDatabase();
					}
					db.updateTreeWithEditTrace(treeDetail);
				}
			}
		});

		btnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				if (btnDelete.getText().equals(getApplication().getString(R.string.txtDelete)))
				{
					AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ViewTreeDetailActivity.this);
					alertBuilder.setTitle("Important!")//
					.setCancelable(false)//
					.setMessage("Are you sure you want to delete this entry from this device?")//
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1)
						{
							db = new DatabaseHelper(ViewTreeDetailActivity.this);
							db.deleteTreeEntry(treeDetail.getGid());
							finish();
							treeDetail = null;
							Intent intent = new Intent(ViewTreeDetailActivity.this, ListTreesInSessionActivity.class);
							session.getTrees()[pos] = null;
							intent.putExtra("session", session);
							intent.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
							startActivity(intent);
						}
					})//
					.setNegativeButton("No", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1)
						{
							arg0.dismiss();
						}
					})//
					.show();
				}
				else
				{
					// make spinners gone and edittexts visible

					// make health spinner gone
					if (spinnerHealthOfTree.getVisibility() == View.VISIBLE)
					{
						txtHealthOfTree.setVisibility(View.VISIBLE);
						txtHealthOfTree.setText(spinnerHealthOfTree.getSelectedItem().toString());
						spinnerHealthOfTree.setVisibility(View.GONE);
					}

					// make ground type spinner gone
					if (spinnerGroundType.getVisibility() == View.VISIBLE)
					{
						txtGroundType.setVisibility(View.VISIBLE);
						txtGroundType.setText(spinnerGroundType.getSelectedItem().toString());
						spinnerGroundType.setVisibility(View.GONE);
					}

					// make ground desc spinner gone
					if (spinnerGroundDesc.getVisibility() == View.VISIBLE)
					{
						txtGroundDesc.setVisibility(View.VISIBLE);
						txtGroundDesc.setText(spinnerGroundDesc.getSelectedItem().toString());
						spinnerGroundDesc.setVisibility(View.GONE);
					}

					// make risk spinner gone
					if (spinnerRisk.getVisibility() == View.VISIBLE)
					{
						txtRisk.setVisibility(View.VISIBLE);
						txtRisk.setText(spinnerRisk.getSelectedItem().toString());
						spinnerRisk.setVisibility(View.GONE);
					}

					// make refer to dept spinner gone
					if (spinnerReferToDept.getVisibility() == View.VISIBLE)
					{
						txtReferToDept.setVisibility(View.VISIBLE);
						txtReferToDept.setText(spinnerReferToDept.getSelectedItem().toString());
						spinnerReferToDept.setVisibility(View.GONE);
					}

					for (int i = 0; i < txtArr.length; i++)
					{
						txtArr[i].setEnabled(false);
					}

					btnEdit.setText(getApplication().getString(R.string.txtEdit));
					btnDelete.setText(getApplication().getString(R.string.txtDelete));
				}
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		ViewTreeDetailActivity.this.finish();
		Intent intent = new Intent(ViewTreeDetailActivity.this, ListTreesInSessionActivity.class);
		intent.putExtra(DatabaseHelper.SURVEYOR_ID[0], surveyorId);
		intent.putExtra("session", session);
		startActivity(intent);
	}
}
