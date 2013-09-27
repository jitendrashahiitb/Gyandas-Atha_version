package com.atha.treemapindia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.atha.csvexport.CSVexport;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

public class DatabaseHandler
{
	private static final String	  DATABASE_NAME	             = "Survey.db";
	private static final String	  DATABASE_TABLE	         = "survey_details";
	private static final String	  DATABASE_TABLE_TREE	     = "tree_detail";
	private static final String	  DATABASE_TABLE_AREA	     = "area_desc";
	private static final String	  DATABASE_TABLE_UNIQUE_AREA	= "unique_area_details";
	private static final int	  DATABASE_VERSION	         = 24;
	private static String	      DB_PATH	                 = "";
	private static String	      TAG	                     = "DataBaseHelper";

	String	                      androidId;
	public static final String	  KEY_ID	                 = "_id";
	public static final String	  KEY_PROP_ID	             = "prop_id";
	public static final String	  KEY_FORM_ID	             = "form_no";
	public static final String	  KEY_TREE_NO	             = "tree_no";
	public static final String	  KEY_NOT	                 = "tree_name";
	public static final String	  KEY_BN	                 = "botanical_name";
	public static final String	  KEY_TSIZEF	             = "height_ft";
	public static final String	  KEY_TSIZEM	             = "height_m";
	public static final String	  KEY_NEST	                 = "nest";
	public static final String	  KEY_BURR	                 = "burrows";
	public static final String	  KEY_NAILS	                 = "nails";
	public static final String	  KEY_FLW	                 = "flowers";
	public static final String	  KEY_FRUIT	                 = "fruits";
	public static final String	  KEY_PO	                 = "poster";
	public static final String	  KEY_WI	                 = "wires";
	public static final String	  KEY_TG	                 = "tree_guard";
	public static final String	  KEY_OM	                 = "other_nuissance";
	public static final String	  KEY_OMD	                 = "other_nuissance_desc";
	public static final String	  KEY_HOT	                 = "health_of_tree";
	public static final String	  KEY_FOG	                 = "found_on_ground";
	public static final String	  KEY_GD	                 = "ground_description";
	public static final String	  KEY_ROT	                 = "risk_on_tree";
	public static final String	  KEY_RD	                 = "risk_desc";
	public static final String	  KEY_RA	                 = "rare";
	public static final String	  KEY_EDA	                 = "endangered";
	public static final String	  KEY_VA	                 = "vulenrable";
	public static final String	  KEY_PA	                 = "pest_affected";
	public static final String	  KEY_RTD	                 = "refer_to_dept";
	public static final String	  KEY_SO	                 = "special_other";
	public static final String	  KEY_SOD	                 = "special_other_description";
	public static final String	  KEY_GIRCM	                 = "girth_cm";
	public static final String	  KEY_GIRM	                 = "girth_m";
	public static final String	  KEY_LAT	                 = "latitude";
	public static final String	  KEY_LON	                 = "longitude";
	public static final String	  KEY_CREATION_DATE	         = "creation_date";
	public static final String	  KEY_CREATION_TIME	         = "creation_time";
	public static final String	  KEY_ANDROID_ID	         = "device_id";
	// new fields
	public static final String	  KEY_POINT	                 = "point";
	public static final String	  KEY_SURVEYOR_ID	         = "surveyorId";
	public static final String	  KEY_SESSION_ID	         = "sessionId";
	public static final String	  KEY_PHOTO_F_1	             = "photoF1";
	public static final String	  KEY_PHOTO_F_2	             = "photoF2";
	public static final String	  KEY_PHOTO_P_1	             = "photoP1";
	public static final String	  KEY_PHOTO_P_2	             = "photoP2";
	public static final String	  KEY_PHOTO_OTHER	         = "photoOther";

	public static final String	  KEY_WARD_NO	             = "ward_no";
	public static final String	  KEY_POLY_NO	             = "poly_no";
	public static final String	  KEY_FORM_KEY	             = "form_key";
	public static final String	  KEY_BOUNDARY_TYPE	         = "boundary_type";
	public static final String	  KEY_PROP_TYPE	             = "property_type";
	public static final String	  KEY_PROP_DESC	             = "property_description";
	public static final String	  KEY_PROP_OWNER	         = "property_owner";
	public static final String	  KEY_HOUSE_NO	             = "house_no";
	public static final String	  KEY_SURVEY_NO	             = "survey_no";
	public static final String	  KEY_PROP_AREA	             = "property_area";
	public static final String	  KEY_PLACE_TYPE	         = "place_type";

	public static final int	      ID_COLUMN	                 = 0;
	public static final int	      FORM_COLUMN	             = 1;
	public static final int	      TREE_NO_COLUMN	         = 2;
	public static final int	      NOT_COLUMN	             = 3;
	public static final int	      BN_COLUMN	                 = 4;
	public static final int	      GIRCM_COLUMN	             = 5;
	public static final int	      GIRM_COLUMN	             = 6;
	public static final int	      TSIZEF_COLUMN	             = 7;
	public static final int	      TSIZEM_COLUMN	             = 8;
	public static final int	      NEST_COLUMN	             = 9;
	public static final int	      BURR_COLUMN	             = 10;
	public static final int	      FLW_COLUMN	             = 11;
	public static final int	      FRUIT_COLUMN	             = 12;
	public static final int	      NAILS_COLUMN	             = 13;
	public static final int	      PO_COLUMN	                 = 14;
	public static final int	      WI_COLUMN	                 = 15;
	public static final int	      HOT_COLUMN	             = 16;
	public static final int	      FOG_COLUMN	             = 17;
	public static final int	      GD_COLUMN	                 = 18;
	public static final int	      ROT_COLUMN	             = 19;
	public static final int	      RD_COLUMN	                 = 20;
	public static final int	      RA_COLUMN	                 = 21;
	public static final int	      EDA_COLUMN	             = 22;
	public static final int	      VA_COLUMN	                 = 23;
	public static final int	      PA_COLUMN	                 = 24;
	public static final int	      RTD_COLUMN	             = 25;
	public static final int	      SO_COLUMN	                 = 26;
	public static final int	      SOD_COLUMN	             = 27;
	public static final int	      LAT_COLUMN	             = 28;
	public static final int	      LON_COLUMN	             = 29;
	public static final int	      CREATION_DATE_COLUMN	     = 30;
	public static final int	      CREATION_TIME_COLUMN	     = 31;
	public static final int	      CREATION_AID_COLUMN	     = 32;

	private static SQLiteDatabase	_db;
	public static Context	      context;
	private static DBOpenHelper	  dbHelper;

	public DatabaseHandler(Context _context) throws IOException
	{
		context = _context;

		dbHelper = new DBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		dbHelper.createDataBase();
	}

	/** Close the database */
	public void close()
	{
		_db.close();
	}

	/** Open the database */
	public void open() throws SQLiteException
	{
		try
		{
			dbHelper.openDataBase();
			dbHelper.close();
			_db = dbHelper.getWritableDatabase();
		}
		catch (SQLiteException ex)
		{
			Log.e(TAG, "open >>" + ex.toString());
			_db = dbHelper.getWritableDatabase();
		}
	}

	/** Insert a Unique area details **/
	public long insertuniqearedetails(Unique_Area_Detail _uad)
	{
		ContentValues uArea = new ContentValues();
		uArea.put(KEY_WARD_NO, _uad.get_ward());
		uArea.put(KEY_POLY_NO, _uad.get_poly());
		uArea.put(KEY_FORM_KEY, _uad.get_form_key());

		return _db.insert(DATABASE_TABLE_UNIQUE_AREA, null, uArea);
	}

	/** Insert a new area details **/
	public long insertAreadetails(Area_Details _ad)
	{
		ContentValues newArea = new ContentValues();

		// Assign value for each row

		newArea.put(KEY_PROP_TYPE, _ad.get_prop_type());
		newArea.put(KEY_PROP_OWNER, _ad.get_prop_owner());
		newArea.put(KEY_HOUSE_NO, _ad.get_house_no());
		newArea.put(KEY_SURVEY_NO, _ad.get_survey());
		newArea.put(KEY_PROP_DESC, _ad.get_prop_desc());
		newArea.put(KEY_PROP_AREA, _ad.get_prop_area());
		newArea.put(KEY_FORM_KEY, _ad.get_form_key());

		// Insert the row.
		return _db.insert(DATABASE_TABLE_AREA, null, newArea);
	}

	/** Insert a new task */
	public long insertSurveydetails(Surveydetail _sd)
	{
		// Create a new row of values to insert.
		ContentValues newSurveyValues = new ContentValues();
		androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		// Assign values for each row.
		newSurveyValues.put(KEY_FORM_ID, _sd.get_fno());
		newSurveyValues.put(KEY_PROP_ID, _sd.get_prop_id());
		newSurveyValues.put(KEY_NOT, _sd.get_tree_name());
		newSurveyValues.put(KEY_TREE_NO, _sd.get_tree_no());
		newSurveyValues.put(KEY_BN, _sd.get_botanical_name());
		newSurveyValues.put(KEY_TSIZEF, _sd.get_height_ft());
		newSurveyValues.put(KEY_TSIZEM, _sd.get_height_m());
		newSurveyValues.put(KEY_NEST, _sd.get_nest());
		newSurveyValues.put(KEY_NAILS, _sd.get_nails());
		newSurveyValues.put(KEY_FLW, _sd.get_flowers());
		newSurveyValues.put(KEY_FRUIT, _sd.get_fruits());
		newSurveyValues.put(KEY_BURR, _sd.get_burrows());
		newSurveyValues.put(KEY_HOT, _sd.get_health_tree());
		newSurveyValues.put(KEY_ROT, _sd.get_risk_on_tree());
		newSurveyValues.put(KEY_RD, _sd.get_risk_desc());
		newSurveyValues.put(KEY_PO, _sd.get_poster());
		newSurveyValues.put(KEY_WI, _sd.get_wires());
		newSurveyValues.put(KEY_TG, _sd.get_tree_guards());
		newSurveyValues.put(KEY_OM, _sd.get_other_nuissance());
		newSurveyValues.put(KEY_OMD, _sd.get_other_nuissance_desc());
		newSurveyValues.put(KEY_FOG, _sd.get_ground_type());
		newSurveyValues.put(KEY_GD, _sd.get_ground_desc());
		newSurveyValues.put(KEY_GIRCM, _sd.get_girth_cm());
		newSurveyValues.put(KEY_GIRM, _sd.get_girth_m());
		// newSurveyValues.put(KEY_RA, _sd.get_rare());
		// newSurveyValues.put(KEY_EDA, _sd.get_endangered());
		// newSurveyValues.put(KEY_VA, _sd.get_vulnerable());
		newSurveyValues.put(KEY_PA, _sd.get_pest_affected());
		newSurveyValues.put(KEY_RTD, _sd.get_ref_to_dept());
		newSurveyValues.put(KEY_SO, _sd.get_s_other());
		newSurveyValues.put(KEY_SOD, _sd.get_s_other_desc());
		newSurveyValues.put(KEY_LAT, _sd.get_lat());
		newSurveyValues.put(KEY_LON, _sd.get_lon());
		newSurveyValues.put(KEY_CREATION_DATE, _sd.get_dt());// .get_Created().getTime());
		newSurveyValues.put(KEY_CREATION_TIME, _sd.get_tm());
		newSurveyValues.put(KEY_ANDROID_ID, androidId);

		// Insert the row.
		return _db.insert(DATABASE_TABLE, null, newSurveyValues);
	}

	/** Return a Cursor to all the Survey Details */
	public Cursor getAllSurveydetailCursor()
	{
		return _db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_FORM_ID, KEY_TREE_NO, KEY_NOT, KEY_BN, KEY_GIRCM, KEY_GIRM, KEY_TSIZEF, KEY_TSIZEM, KEY_NEST, KEY_BURR, KEY_FLW, KEY_FRUIT, KEY_NAILS, KEY_PO, KEY_WI, KEY_TG, KEY_OM, KEY_OMD, KEY_HOT, KEY_FOG, KEY_GD, KEY_ROT, KEY_RD, KEY_RA, KEY_EDA, KEY_VA, KEY_PA, KEY_RTD, KEY_SO, KEY_SOD, KEY_LAT, KEY_LON, KEY_CREATION_DATE }, null, null, null, null, null);
	}

	/** Return a Cursor to all the Tree details */
	public Cursor getAllTreedetailCursor()
	{
		return _db.query(DATABASE_TABLE_TREE, new String[] { KEY_ID, KEY_NOT, KEY_BN }, null, null, null, null, null);
	}

	/** Return a Todo Item based on its row index */
	public Treelist getTreeBotoName(long _rowIndex) throws SQLException
	{
		Cursor cursor = _db.query(true, DATABASE_TABLE_TREE, new String[] { KEY_ID, KEY_NOT, KEY_BN }, KEY_ID + "=" + _rowIndex, null, null, null, null, null);
		if ((cursor.getCount() == 0) || !cursor.moveToFirst())
		{
			throw new SQLException("No to do item found for row: " + _rowIndex);
		}

		String treename = cursor.getString(FORM_COLUMN);
		String botoname = cursor.getString(TREE_NO_COLUMN);

		Treelist result = new Treelist(treename, botoname);
		return result;
	}

	// Database open helper class

	private static class DBOpenHelper extends SQLiteOpenHelper
	{

		public DBOpenHelper(Context context, String name, CursorFactory factory, int version)
		{
			super(context, name, factory, version);

			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}

		/** SQL Statement to create a new database */
		private static final String	DATABASE_SURVEY_TABLE_CREATE		= "create table " + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_FORM_ID + " text not null, " + KEY_PROP_ID + " text not null, " + KEY_TREE_NO + " text not null, " + KEY_NOT + " text not null, " + KEY_BN + " text not null, " + KEY_GIRCM + " real not null, " + KEY_GIRM + " real not null, " + KEY_TSIZEF + " real not null, " + KEY_TSIZEM + " real not null, " + KEY_NEST + " text not null, " + KEY_BURR + " text not null, " + KEY_FLW + " text not null, " + KEY_FRUIT + " text not null, " + KEY_NAILS + " text not null, " + KEY_PO + " text not null, " + KEY_WI + " text not null, " + KEY_TG + " text not null, " + KEY_OM + " text not null, " + KEY_OMD + " text not null, " + KEY_HOT + " text not null, " + KEY_FOG + " text not null, " + KEY_GD + " text not null, " + KEY_ROT + " text not null, " + KEY_RD + " text not null, " + KEY_RA + " text not null, " + KEY_EDA + " text not null, " + KEY_VA + " text not null, " + KEY_PA + " text not null, " + KEY_RTD + " text not null, " + KEY_SO + " text not null, " + KEY_SOD + " text not null, " + KEY_LAT + " text not null, " + KEY_LON + " text not null, " + KEY_CREATION_DATE + " integer not null, " + KEY_CREATION_TIME + " integer not null, " + KEY_ANDROID_ID + " TEXT not null);";

		private static final String	DATABASE_AREA_UNIQUE_TABLE_CREATE	= "create table " + DATABASE_TABLE_UNIQUE_AREA + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_WARD_NO + " text not null, " + KEY_POLY_NO + " text not null, " + KEY_FORM_KEY + " text unique not null);";
		private static final String	DATABASE_AREA_TABLE_CREATE		  = "create table " + DATABASE_TABLE_AREA + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_FORM_KEY + " text not null, " + KEY_BOUNDARY_TYPE + " text not null, " + KEY_PROP_TYPE + " text not null, " + KEY_PROP_DESC + " text not null, " + KEY_PROP_AREA + " real not null, " + KEY_PROP_OWNER + " text not null, " + KEY_HOUSE_NO + " text not null, " + KEY_SURVEY_NO + " text not null, " + KEY_PLACE_TYPE + " text not null);";

		public void createDataBase() throws IOException
		{
			// If database not exists copy it from the assets

			boolean mDataBaseExist = checkDataBase();
			if (!mDataBaseExist)
			{
				this.getReadableDatabase();
				this.close();
				try
				{
					// Copy the database from assests
					copyDataBase();
					Log.e(TAG, "createDatabase database created");
				}
				catch (IOException mIOException)
				{
					throw new Error("ErrorCopyingDataBase");
				}
			}
			else
			{

			}
		}

		// Check that the database exists here: /data/data/your package/databases/Da Name
		private boolean checkDataBase()
		{
			File dbFile = new File(DB_PATH + DATABASE_NAME);
			Log.v("dbFile", dbFile + "   " + dbFile.exists());
			return dbFile.exists();
		}

		// Copy the database from assets
		private void copyDataBase() throws IOException
		{
			InputStream mInput = context.getAssets().open(DATABASE_NAME);
			String outFileName = DB_PATH + DATABASE_NAME;
			OutputStream mOutput = new FileOutputStream(outFileName);
			byte[] mBuffer = new byte[1024];
			int mLength;
			while ((mLength = mInput.read(mBuffer)) > 0)
			{
				mOutput.write(mBuffer, 0, mLength);
			}
			mOutput.flush();
			mOutput.close();
			mInput.close();
		}

		// Open the database, so we can query it
		public boolean openDataBase() throws SQLException
		{
			String mPath = DB_PATH + DATABASE_NAME;
			// Log.v("mPath", mPath);

			_db = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
			// mDataBase = SQLiteDatabase.openDatabase(mPath, null,
			// SQLiteDatabase.NO_LOCALIZED_COLLATORS);
			return _db != null;
		}

		@Override
		public synchronized void close()
		{
			if (_db != null)
				_db.close();
			super.close();
		}

		@Override
		public void onCreate(SQLiteDatabase _db)
		{
			Log.w("TaskDBAdapteroncfreate", "Upgrading from version ");
			try
			{

				dbHelper.createDataBase();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_db.execSQL(DATABASE_SURVEY_TABLE_CREATE);
			_db.execSQL(DATABASE_AREA_UNIQUE_TABLE_CREATE);
			_db.execSQL(DATABASE_AREA_TABLE_CREATE);
		}

		@Override
		public void onUpgrade(final SQLiteDatabase _db, int _oldVersion, int _newVersion)
		{
			Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to " + _newVersion + ", which will destroy all old data");

			// Drop the old table.

			// _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TREE);
			// try {
			// dbHelper.createDataBase();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// Create a new one.
			// onCreate(_db);

			if (_oldVersion < _newVersion)
			{
				AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(context);
				alertDialogBuilder1.setMessage("Version is Upgrading Your Data is Exported ").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id)
					{
						// if this button is clicked, close
						// current activity
						dialog.cancel();
						try
						{
							new CSVexport(context, Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID), 1);
						}
						catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.d("01", "---");
						_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
						_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TREE);
						_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_AREA);
						_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_UNIQUE_AREA);
						Log.d("02", "---");
						context.deleteDatabase(DATABASE_NAME);
						Log.d("03", "---");
						// _db.close();
						// dbHelper.close();
						try
						{
							Log.d("04", "---");
							dbHelper.createDataBase();
						}
						catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.d("05", "---");
						_db.execSQL(DATABASE_SURVEY_TABLE_CREATE);
						_db.execSQL(DATABASE_AREA_UNIQUE_TABLE_CREATE);
						_db.execSQL(DATABASE_AREA_TABLE_CREATE);
						_db.close();
					}
				}).show();

				// String ALTER_TABLE="ALTER TABLE "+ DATABASE_TABLE +" ADD COLUMN "+ KEY_LAT
				// +" TEXT DEFAULT NULL;";
				// String ALTER_TABLE1="ALTER TABLE "+ DATABASE_TABLE +" ADD COLUMN "+ KEY_LON
				// +" TEXT DEFAULT NULL;";
				// String ALTER_TABLE2="ALTER TABLE "+ DATABASE_TABLE +" ADD COLUMN "+
				// KEY_CREATION_TIME +" INTEGER DEFAULT NULL;";
				// String ALTER_TABLE3="ALTER TABLE "+ DATABASE_TABLE +" ADD COLUMN "+
				// KEY_ANDROID_ID +" TEXT DEFAULT NULL;";
				// _db.execSQL(ALTER_TABLE);
				// _db.execSQL(ALTER_TABLE1);
				// _db.execSQL(ALTER_TABLE2);
				// _db.execSQL(ALTER_TABLE3);

			}

		}
	}

}
