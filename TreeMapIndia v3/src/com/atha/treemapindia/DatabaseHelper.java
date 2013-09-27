package com.atha.treemapindia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import com.atha.csvexport.CSVWriter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper
{
	public static final long	  SESSION_CHANGE_TIMEOUT	         = 10 * 60 * 1000;	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                // 10

	public static final String	  DATABASE_NAME	                     = "TreeMapIndia.db";

	public static final String	  TABLE_PRABHAG_MASTER	             = "prabhag";
	public static final String	  TABLE_TREE	                     = "Tree";
	public static final String	  TABLE_CLUSTER_MASTER	             = "cluster";
	public static final String	  TABLE_SURVEYOR	                 = "surveyor";
	public static final String	  TABLE_SPECIES_MASTER	             = "Species_list";
	public static final String	  TABLE_PROPERTY_MASTER	             = "property_types";
	public static final String	  TABLE_HEALTH_MASTER	             = "health_of_tree_master";
	public static final String	  TABLE_GROUND_MASTER	             = "ground_condition";
	public static final String	  TABLE_RISK_MASTER	                 = "risk_due_to_tree";
	public static final String	  TABLE_AREA_DETAILS	             = "area_details";
	public static final String	  TABLE_SESSION	                     = "session";

	public static final String	  GID[]	                             = { "gid", "integer primary key autoincrement" };
	public static final String	  PRABHAG_ID[]	                     = { "prabhagid", "integer" };
	public static final String	  OBJECT_ID[]	                     = { "objectid", "text" };
	public static final String	  CLUSTER_ID[]	                     = { "clusterid", "integer" };
	public static final String	  PROPERTY_ID[]	                     = { "propertyid", "text" };
	public static final String	  DEVICE_ID[]	                     = { "device_id", "text" };
	public static final String	  SURVEYOR_ID[]	                     = { "surveyor_id", "text" };
	public static final String	  SESSION_ID[]	                     = { "session_id", "text" };
	public static final String	  FORM_ID[]	                         = { "form_id", "text" };

	public static final String	  PRABHAG_CITY_CODE[]	             = { "citycode", "integer" };
	public static final String	  PRABHAG_DESCRIPTION[]	             = { "description", "text" };
	public static final String	  PRABHAG_AREA[]	                 = { "area", "real" };
	public static final String	  PRABHAG_PERIMETER[]	             = { "perimeter", "real" };
	public static final String	  PRABHAG_SHAPE_LENGTH[]	         = { "shape_length", "real" };
	public static final String	  PRABHAG_SHAPE_AREA[]	             = { "shape_area", "real" };
	public static final String	  PRABHAG_GEOM[]	                 = { "geom", "text" };

	public static final String	  PRABHAG_COLUMN[][]	             = { GID, PRABHAG_ID, PRABHAG_CITY_CODE, OBJECT_ID, PRABHAG_DESCRIPTION, PRABHAG_AREA, PRABHAG_PERIMETER, PRABHAG_SHAPE_LENGTH, PRABHAG_SHAPE_AREA, PRABHAG_GEOM };

	public static final String	  TREE_NUMBER[]	                     = { "tree_number", "integer" };
	public static final String	  TREE_NAME[]	                     = { "tree_name", "text" };
	public static final String	  TREE_BOTANICAL_NAME[]	             = { "botanical_name", "text" };
	public static final String	  TREE_GIRTH_M[]	                 = { "girth_m", "real" };
	public static final String	  TREE_HEIGHT_M[]	                 = { "height_m", "real" };
	public static final String	  TREE_NEST[]	                     = { "nest", "integer" };
	public static final String	  TREE_BURROWS[]	                 = { "burrows", "integer" };
	public static final String	  TREE_FLOWERS[]	                 = { "flowers", "integer" };
	public static final String	  TREE_FRUITS[]	                     = { "fruits", "integer" };
	public static final String	  TREE_NAILS[]	                     = { "nails", "integer" };
	public static final String	  TREE_POSTER[]	                     = { "poster", "integer" };
	public static final String	  TREE_WIRES[]	                     = { "wires", "integer" };
	public static final String	  TREE_GUARD[]	                     = { "tree_guard", "integer" };
	public static final String	  TREE_OTHER_NUISSANCE[]	         = { "other_nuissance", "integer" };
	public static final String	  TREE_OTHER_NUISSANCE_DESC[]	     = { "other_nuissance_desc", "text" };
	public static final String	  TREE_HEALTH_OF_TREE[]	             = { "health_of_tree", "text" };
	public static final String	  TREE_GROUND_TYPE[]	             = { "ground_type", "text" };
	public static final String	  TREE_GROUND_DESC[]	             = { "ground_desc", "text" };
	public static final String	  TREE_RISK_DUE_TO_TREE[]	         = { "risk_due_to_tree", "text" };
	public static final String	  TREE_RISK_DESC[]	                 = { "risk_desc", "text" };
	public static final String	  TREE_REFER_TO_DEPT[]	             = { "refer_to_deptt", "integer" };
	public static final String	  TREE_SPECIAL_OTHER[]	             = { "special_other", "text" };
	public static final String	  TREE_SPECIAL_OTHER_DESC[]	         = { "special_other_desc", "text" };
	public static final String	  TREE_LATTITUDE[]	                 = { "lattitude", "real" };
	public static final String	  TREE_LONGITUDE[]	                 = { "longitude", "real" };
	public static final String	  TREE_POINT[]	                     = { "Point", "text" };
	public static final String	  TREE_CREATION_DATE[]	             = { "Creation_date", "text" };
	public static final String	  TREE_CREATION_TIME[]	             = { "Creation_time", "text" };
	public static final String	  TREE_PHOTO_F_1[]	                 = { "photo_focussed1", "blob" };
	public static final String	  TREE_PHOTO_F_2[]	                 = { "photo_focussed2", "blob" };
	public static final String	  TREE_PHOTO_P_1[]	                 = { "photo_panoorama1", "blob" };
	public static final String	  TREE_PHOTO_P_2[]	                 = { "photo_panoorama2", "blob" };
	public static final String	  TREE_PHOTO_OTHER[]	             = { "photo_other", "blob" };
	public static final String	  TREE_EDIT_TRACE[]	                 = { "edit_trace", "int default 0" };
	public static final String	  TREE_DELETED[]	                 = { "deleted", "int default 0" };

	public static final String	  TREE_COLUMN[][]	                 = { GID, FORM_ID, TREE_NUMBER, PRABHAG_ID, CLUSTER_ID, PROPERTY_ID, TREE_NAME, TREE_BOTANICAL_NAME, TREE_GIRTH_M, TREE_HEIGHT_M, TREE_NEST, TREE_BURROWS, TREE_FLOWERS, TREE_FRUITS, TREE_NAILS, TREE_POSTER, TREE_WIRES, TREE_GUARD, TREE_OTHER_NUISSANCE, TREE_OTHER_NUISSANCE_DESC, TREE_HEALTH_OF_TREE, TREE_GROUND_TYPE, TREE_GROUND_DESC, TREE_RISK_DUE_TO_TREE, TREE_RISK_DESC, TREE_REFER_TO_DEPT, TREE_SPECIAL_OTHER, TREE_SPECIAL_OTHER_DESC, TREE_LATTITUDE, TREE_LONGITUDE, TREE_POINT, TREE_CREATION_DATE, TREE_CREATION_TIME, DEVICE_ID, SURVEYOR_ID, SESSION_ID, TREE_PHOTO_F_1, TREE_PHOTO_F_2, TREE_PHOTO_P_1, TREE_PHOTO_P_2, TREE_PHOTO_OTHER, TREE_EDIT_TRACE, TREE_DELETED };

	public static final String	  CLUSTER_DESC[]	                 = { "description", "text" };
	public static final String	  CLUSTER_AREA[]	                 = { "area", "real" };
	public static final String	  CLUSTER_PERIMETER[]	             = { "perimeter", "real" };
	public static final String	  CLUSTER_SHAPE_LENGTH[]	         = { "shape_length", "real" };
	public static final String	  CLUSTER_SHAPE_AREA[]	             = { "shape_area", "real" };
	public static final String	  CLUSTER_GEOM[]	                 = { "geom", "text" };
	public static final String	  CLUSTER_LOCALITIES_NEIGHBOURHOOD[]	= { "localities_neighborhood", "text" };

	public static final String	  CLUSTER_COLUMN[][]	             = { GID, CLUSTER_ID, OBJECT_ID, PRABHAG_ID, CLUSTER_DESC, CLUSTER_AREA, CLUSTER_PERIMETER, CLUSTER_SHAPE_LENGTH, CLUSTER_SHAPE_AREA, CLUSTER_GEOM, CLUSTER_LOCALITIES_NEIGHBOURHOOD };

	public static final String	  SURVEYOR_AUTO_ID[]	             = { SURVEYOR_ID[0], "integer primary key autoincrement" };
	public static final String	  SURVEYOR_NAME[]	                 = { "surveyor_name", "text" };
	public static final String	  SURVEYOR_PASSWORD[]	             = { "password", "blob" };

	public static final String	  SURVEYOR_COLUMN[][]	             = { SURVEYOR_AUTO_ID, SURVEYOR_NAME, SURVEYOR_PASSWORD };

	public static final String	  SPECIES_SCIENTIFIC_NAME[]	         = { "Scientific_Name", "text" };
	public static final String	  SPECIES_LOCAL_NAME[]	             = { "Local_Name", "text" };

	public static final String	  SPECIES_COLUMN[][]	             = { GID, SPECIES_LOCAL_NAME, SPECIES_SCIENTIFIC_NAME };

	public static final String	  PROPERTY_TYPE[]	                 = { "Property_Type", "text" };

	public static final String	  PROPERTY_COLUMN[][]	             = { PROPERTY_TYPE };

	public static final String	  HEALTH_OF_TREE[]	                 = { "health_of_tree", "text" };

	public static final String	  HEALTH_COLUMN[][]	                 = { HEALTH_OF_TREE };

	public static final String	  GROUND_TYPE[]	                     = { "ground_type", "text" };

	public static final String	  GROUND_COLUMN[][]	                 = { GROUND_TYPE };

	public static final String	  RISK_DUE_TO_TREE[]	             = { "risk_due_to_tree", "text" };

	public static final String	  RISK_COLUMN[][]	                 = { RISK_DUE_TO_TREE };

	public static final String	  AREA_DETAILS_PROPERTY_TYPE[]	     = { "property_type", "text" };
	public static final String	  AREA_DETAILS_BOUNDARY_TYPE[]	     = { "boundary_type", "text" };
	public static final String	  AREA_DETAILS_PROPERTY_DESC[]	     = { "property_desc", "text" };
	public static final String	  AREA_DETAILS_PROPERTY_AREA[]	     = { "property_area", "text" };
	public static final String	  AREA_DETAILS_PROPERTY_OWNER[]	     = { "property_owner", "text" };
	public static final String	  AREA_DETAILS_SURVEY_NUMBER[]	     = { "survey_number", "text" };
	public static final String	  AREA_DETAILS_HOUSE_NUMBER[]	     = { "house_number", "text" };
	public static final String	  AREA_DETAILS_PLACE_TYPE[]	         = { "place_type", "text" };

	public static final String	  AREA_DETAILS_COLUMN[][]	         = { GID, FORM_ID, AREA_DETAILS_BOUNDARY_TYPE, AREA_DETAILS_PROPERTY_TYPE, AREA_DETAILS_PROPERTY_OWNER, AREA_DETAILS_PROPERTY_DESC, AREA_DETAILS_SURVEY_NUMBER, AREA_DETAILS_HOUSE_NUMBER, AREA_DETAILS_PROPERTY_AREA, AREA_DETAILS_PLACE_TYPE };

	public static final String	  SESSION_TREES_SURVEYED[]	         = { "trees_surveyed", "integer" };
	public static final String	  SESSION_TIME[]	                 = { "session_time", "integer" };

	public static final String	  SESSION_COLUMN[][]	             = { GID, SESSION_ID, SESSION_TREES_SURVEYED, SURVEYOR_ID, SESSION_TIME };

	public static final String	  TABLE_ARRAY[]	                     = { TABLE_PRABHAG_MASTER, TABLE_TREE, TABLE_CLUSTER_MASTER, TABLE_SURVEYOR, TABLE_SPECIES_MASTER, TABLE_PROPERTY_MASTER, TABLE_HEALTH_MASTER, TABLE_GROUND_MASTER, TABLE_RISK_MASTER, TABLE_AREA_DETAILS, TABLE_SESSION };

	public static final String	  TABLE_COLUMN_ARRAY[][][]	         = { PRABHAG_COLUMN, TREE_COLUMN, CLUSTER_COLUMN, SURVEYOR_COLUMN, SPECIES_COLUMN, PROPERTY_COLUMN, HEALTH_COLUMN, GROUND_COLUMN, RISK_COLUMN, AREA_DETAILS_COLUMN, SESSION_COLUMN };

	public static String	      DATABASE_PATH	                     = null;
	private static SQLiteDatabase	db	                             = null;
	private Context	              context	                         = null;

	protected DatabaseHelper(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);
		this.context = context;
		DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).getPath();
		Log.i("r db", "refer db : " + DATABASE_PATH);
		getWritableDatabase();
		openDatabase();
	}

	public DatabaseHelper(Context context)
	{
		this(context, DATABASE_NAME, null, 1);
	}

	public boolean openDatabase()
	{
		close();
		try
		{
			db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
		}
		catch (SQLiteException e)
		{
			// db = getWritableDatabase();
		}
		return (db != null);
	}

	public void close()
	{
		if (db != null)
		{
			db.close();
			super.close();
		}
	}

	public Cursor getAllTreedetailCursor()
	{
		return db.query(TABLE_SPECIES_MASTER, new String[] { GID[0], SPECIES_LOCAL_NAME[0], SPECIES_SCIENTIFIC_NAME[0] }, null, null, null, null, null);
	}

	public ArrayList<String> getAllUserNames()
	{
		Cursor cursor = db.query(TABLE_SURVEYOR, new String[] { SURVEYOR_NAME[0] }, null, null, null, null, null);
		ArrayList<String> names = new ArrayList<String>();
		for (; cursor.moveToNext();)
		{
			names.add(cursor.getString(0));
		}
		return names;
	}

	public ArrayList<String> getAllPrabhagNumbers()
	{
		Cursor cursor = db.query(TABLE_PRABHAG_MASTER, new String[] { PRABHAG_ID[0] }, null, null, null, null, null);
		ArrayList<String> prabhagNumbers = new ArrayList<String>();
		for (; cursor.moveToNext();)
		{
			prabhagNumbers.add(cursor.getString(0));
		}
		return prabhagNumbers;
	}

	public String[] getPropertyTypes()
	{
		Cursor cursor = db.query(TABLE_PROPERTY_MASTER, new String[] { PROPERTY_TYPE[0] }, null, null, null, null, null);
		String str[] = new String[cursor.getCount()];
		for (int i = 0; cursor.moveToNext(); i++)
		{
			str[i] = cursor.getString(0);
		}
		return str;
	}

	public ArrayList<String> getClusterNumberAccordingToPrabhagNumber(String prabhagId)
	{
		Cursor cursor = db.query(TABLE_CLUSTER_MASTER, new String[] { CLUSTER_ID[0] }, PRABHAG_ID[0] + "=?", new String[] { prabhagId }, null, null, null);
		// cursor = db.rawQuery("select " + CLUSTER_ID[0] + " from " + TABLE_CLUSTER_MASTER +
		// " where " + PRABHAG_ID[0] + "=" + prabhagId, null);
		Log.i("database", "cluster cursor count : " + cursor.getCount());
		ArrayList<String> clusterNumbers = new ArrayList<String>();
		for (; cursor.moveToNext();)
		{

			clusterNumbers.add(cursor.getString(0));
		}
		return clusterNumbers;
	}

	public ArrayList<String> getAllClusterNumbers()
	{
		Cursor cursor = db.query(TABLE_CLUSTER_MASTER, new String[] { CLUSTER_ID[0] }, null, null, null, null, null);
		ArrayList<String> clusterNumbers = new ArrayList<String>();
		for (; cursor.moveToNext();)
		{
			clusterNumbers.add(cursor.getString(0));
		}
		return clusterNumbers;
	}

	public String[] getHealthOfTreeCursor()
	{
		Cursor cur = db.rawQuery("SELECT " + HEALTH_OF_TREE[0] + " FROM " + TABLE_HEALTH_MASTER, null);
		String str[] = new String[cur.getCount()];
		if (cur.getCount() > 0)
		{
			for (int i = 0; cur.moveToNext(); i++)
			{
				str[i] = cur.getString(0);
			}
		}
		return str;
	}

	public String[] getGroundTypeCursor()
	{
		Cursor cur = db.rawQuery("SELECT " + GROUND_TYPE[0] + " FROM " + TABLE_GROUND_MASTER, null);
		String str[] = new String[cur.getCount()];
		for (int i = 0; cur.moveToNext(); i++)
		{
			str[i] = cur.getString(0);
		}
		return str;
	}

	public String[] getRiskDueToTreeCursor()
	{
		Cursor cur = db.rawQuery("SELECT " + RISK_DUE_TO_TREE[0] + " FROM " + TABLE_RISK_MASTER, null);
		String str[] = new String[cur.getCount()];
		for (int i = 0; cur.moveToNext(); i++)
		{
			str[i] = cur.getString(0);
		}
		return str;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.i("database", "db path : " + db.getPath());
		for (int i = 0; i < TABLE_ARRAY.length; i++)
		{
			String sql = createSQLString(TABLE_ARRAY[i], TABLE_COLUMN_ARRAY[i]);
			Log.v("sql onCreate", sql);
			db.execSQL(sql);
		}
		for (int i = 1; i <= 10; i++)
		{
			db.execSQL("insert into " + TABLE_PRABHAG_MASTER + "(" + PRABHAG_ID[0] + ") values(" + i + ")");
			for (int j = 1; j <= 5; j++)
			{
				db.execSQL("insert into " + TABLE_CLUSTER_MASTER + "(" + CLUSTER_ID[0] + "," + PRABHAG_ID[0] + ") values(" + i + j + "," + i + ")");
			}
		}
		for (int i = 1; i <= 5; i++)
		{
			db.execSQL("insert into " + TABLE_SURVEYOR + "(" + SURVEYOR_COLUMN[1][0] + "," + SURVEYOR_COLUMN[2][0] + ") values('sevak" + i + "','sevak" + i + "')");
		}

		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('divider')");
		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('pavement')");
		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('apartments')");
		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('bunglow')");
		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('colony')");
		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('estate')");
		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('plot')");
		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('garden')");
		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('river bank')");
		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('pond side')");
		db.execSQL("insert into " + TABLE_PROPERTY_MASTER + "(" + PROPERTY_TYPE[0] + ") values('lake side')");

		db.execSQL("insert into " + TABLE_HEALTH_MASTER + "(" + HEALTH_OF_TREE[0] + ") values('healthy')");
		db.execSQL("insert into " + TABLE_HEALTH_MASTER + "(" + HEALTH_OF_TREE[0] + ") values('diseased')");
		db.execSQL("insert into " + TABLE_HEALTH_MASTER + "(" + HEALTH_OF_TREE[0] + ") values('pest infected')");
		db.execSQL("insert into " + TABLE_HEALTH_MASTER + "(" + HEALTH_OF_TREE[0] + ") values('dead')");

		db.execSQL("insert into " + TABLE_GROUND_MASTER + "(" + GROUND_TYPE[0] + ") values('open soil')");
		db.execSQL("insert into " + TABLE_GROUND_MASTER + "(" + GROUND_TYPE[0] + ") values('compressed soil')");
		db.execSQL("insert into " + TABLE_GROUND_MASTER + "(" + GROUND_TYPE[0] + ") values('cemented')");
		db.execSQL("insert into " + TABLE_GROUND_MASTER + "(" + GROUND_TYPE[0] + ") values('tiled')");
		db.execSQL("insert into " + TABLE_GROUND_MASTER + "(" + GROUND_TYPE[0] + ") values('foot path')");

		db.execSQL("insert into " + TABLE_RISK_MASTER + "(" + RISK_DUE_TO_TREE[0] + ") values('growing against wall')");
		db.execSQL("insert into " + TABLE_RISK_MASTER + "(" + RISK_DUE_TO_TREE[0] + ") values('growing in drain')");
		db.execSQL("insert into " + TABLE_RISK_MASTER + "(" + RISK_DUE_TO_TREE[0] + ") values('none')");
		db.execSQL("insert into " + TABLE_RISK_MASTER + "(" + RISK_DUE_TO_TREE[0] + ") values('other')");

		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('capparis grandis','Pachunda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('diospyros buxifolia','Striped Ebony')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('anogeissus acuminata','Maha Dhawda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('citrofortunella mitis','Mandarin Orange')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus benjamina','Nuda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus benjamina var. nuda','Benjamina')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('anogeissus latifolia','Dhawda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus microcarpa','Nandruk')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('grewia flavescens','Khatkhati')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('manilkara zapota','Chikkoo')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('flacourtia latifolia','Tambat')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('flacourtia montana','Tambat')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('glochidion ellipticum','Bhoma')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('annona reticulata','Sitaphal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('annona squamosa','Ramphal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ehretia laevis','Ajaan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus amplissima','Pipri')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus virens','Payar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('eriobotrya japonica','Loquat')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('mimusops elengi','Bakul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('anacardium occidentale','Kaju')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus racemosa','Umbar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cananga odorata','Ylang-Ylang')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('citrus limon','Idlimbu')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('holoptelea integrifolia','Wawal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('Myristica fragrans','Jayphal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('excoecaria bussei','Ballon Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ochna obtusata','Kanak Champa')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bridelia retusa','Asana')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pterospermum xylocarpum','Muchkunda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('artocarpus heterophyllus','Phanas')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus exasperata','Kharwat')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('alseodaphne semecarpifolia','Phudgus')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('talauma mutabilis (did not find the name)','Kavthi Chafa')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('hydnocarpus pentandra','Kadukawath')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('michelia champaca','Sonchafa')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('persea americana','Avacado')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('suregada multiflora','False Lime')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('citrus maxima','Papnas')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('elaeocarpus sphaericus','Rudraksha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus elastica','Rubber Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('terminalia bellirica','Beheda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('terminalia chebula','Beheda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('artocarpus lacucha','Bread-fruit Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('theobroma cacao','Cocoa')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('brugmansia arborea (*** Colour of brugmansia suaveolens)','Trumpet Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('manilkara sp','Zapota')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dillenia indica','Motha Karmal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('muntingia calabura','Singapore Cherry')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('jacquinia ruscifolia','Jacquinia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('trema orientalis','Gol')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('madhuca longifolia var. longifolia','Moha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('salix tetrasperma','Walunj')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('couroupita guianensis','Kailaspati')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('polyalthia longifolia','Ashok')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('polyalthia longifolia var. angustifolia','Ashok')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('emblica officinalis','Awala')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('guazuma ulmifolia','Rudrakshi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('casuarina equisetifolia','Suru')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dracaena fragrans','Dracaena')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('beaucarnea recurvata','Elephant''s foot palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('yucca aloifolia','Yucca')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('nopalea cochenillifera','Nopal Cactus')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pandanus odoratissimus','Kevada')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('euphorbia lactea','Candelabra Plant')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('euphorbia tirucalli','Sher')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bauhinia hookeri','Mountain Ebony')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bauhinia racemosa','Apta')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bauhinia tomentosa','Pivla Kanchan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bauhinia variegata','Kanchan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('jatropha curcas','Mogli Erand')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pterospermum canescens','Muchkunda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bauhinia blakeana','Kanchan Raj')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bauhinia monandra','Orchid Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bauhinia purpurea','Gulabi Kanchan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bauhinia roxburghii (Difference in name)','Roxburgh''s Bauhinia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('firmiana colorata','Kaushi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('kydia calycina','Warang')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cochlospermum religiosum','Ganer')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dombeya acutangula','Dombeya')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('hibiscus mutabilis','Bhendi Gulab')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('solanum torvum','Kutri')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('broussonetia papyrifera','Paper Mulberry')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('manihot esculenta','Tapioca')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('platanus orientalis','Chinar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ricinus communis','Erand')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pterospermum acerifolium','Karnikar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('solanum macrophyllum','Potato Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('artocarpus incisus','Neer Phanas')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sterculia urens','Kandol')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('carica papaya','Papayi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ziziphus caracutta (did not find the name)','Ghatbor')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('drypetes roxburghii','Putranjiva')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('putranjiva roxburghii','Putranjiva')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pyrus malus','Apple')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('manilkara hexandra','Khirni')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ziziphus glabrata','Bor')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ziziphus mauritiana','Bor')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cordia gharaf','Gondan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('oncoba routledge','Fried Egg Plant')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('chrysophyllum oliviforme','Tarsi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('diospyros melanoxylon','Temru')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('annona muricata','Hanuman Phal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('miliusa tomentosa','Humb')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('chrysophyllum cainito','Star Apple')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('terminalia arjuna','Arjun')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('terminalia cuneata','Arjun')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('terminalia elliptica','Arjun')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('alangium salvifolium','Ankol')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('buchanania cochinchinensis','Charoli')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('diospyros peregrina','Temburni')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('magnolia grandiflora','Magnolia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('mangifera indica','Amba')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ravenala madagascariensis','Traveler''s Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus natalensis','Triangle Leaf Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('phyllanthus acidus','Ray Awala')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('maytenus rothiana','Henkal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('maytenus senegalensis','Henkal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('populus deltoides','Poplar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cordia dichotoma','Bhokar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cordia macleodii','Bhokar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('hura crepitans','Sandbox Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('jatropha integerrima','Fiddle Head Jatropha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('jatropha pandurifolia','Fiddle Head Jatropha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('thespesia populnea','Paras Bhendi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('mallotus philippensis','Kapila')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('prunus serrulata','Cherry Blossom')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('barringtonia acutangula','Newar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bixa orellana','Shedri')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus arnottiana','Pimpal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus religiosa','Pimpal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('kleinhovia hospita','Tree Antigonon')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('coccoloba uvifera','Sea-Grape Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('morus alba','Tuti')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cordia sebestena','Scarlet Cordia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus benghalensis','Wad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus benghalensis var. krishnae','Wad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus mollis','Kallu Goli')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('grewia asiatica','Phalsa')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('careya arborea','Kumbha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pterygota alata','Buddha Coconut')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sterculia alata','Buddha Coconut')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus carica','Anjeer')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus spp','Anjeer')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('semecarpus anacardium','Bibba')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus drupacea var. pubescens','Burali Wad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus lyrata','Fiddleleaf Fig')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus mysorensis','Burali Wad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('givotia rottleriformis','Panki')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('terminalia catappa','Badam')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('barringtonia asiatica','Samudraphal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('lawsonia inermis','Mendi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('malpighia glabra','Barbados Cherry')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('memecylon umbellatum','Anjani')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('meyna laxiflora','Alu')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('santalum album','Chandan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('osmanthus fragrans','Tea Olive')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('premna obtusifolia','Agnimanth')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('strychnos nuxvomica (strychnos nux vomica)','Kajra')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tabernaemontana coronaria','Tagar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('garcinia livingstonei','African Mangosteine')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('olea dioica','Par Jambhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('garcinia indica','Kokam')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pimenta dioica','Allspice')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cassine glauca','Bhutiya')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('gardenia resinifera','Dikemali')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('citharexylum spinosum','Seetaranjan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('coffea arabica','Coffee')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('crescentia alata','Kalabash')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('crescentia cujete','Kalabash')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('lagerstroemia microcarpa','Nana')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('lagerstroemia parviflora','Nana')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('holarrhena pubescens','Kuda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('wrightia arborea','Tambada Kuda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ixora pavetta','Raykuda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('wrightia tinctoria','Kala Kuda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('Morinda pubescens','Bartondi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('syzygium jambos','Jamb')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('calophyllum inophyllum','Undi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('lagerstroemia reginae','Tamhan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('euphorbia leucocephala','Snow Bush')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('neolamarckia cadamba','Kadamb')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('olea europaea','Olive Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('punica granatum','Dalimb')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cinnamomum camphora','Dalchini')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cinnamomum verum','Dalchini')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('mesua ferrea','Nagchapha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('eucalyptus citriodora','Nilgiri')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('eucalyptus globulus','Nilgiri')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('eucalyptus tereticornis','Nilgiri')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia auriculiformis','Austalian Babhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia mangium','Austalian Babhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('alstonia macrophylla','Motha Satween')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('alstonia scholaris','Satween')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('melaleuca bracteata','Golden Bottel Brush')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('melaleuca leucadendron','Golden Bottel Brush')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('callistemon citrinus','Bottle Brush')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cascabela thevetia','Bitti')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('thevetia peruviana','Bitti')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('nerium indicum','Kanher')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pachypodium lamerei','Madagascar Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ceriscoides turgida','Phetra')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('petrea arborea','Petrea')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('psidium guajava','Peru')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('syzygium aromaticum','Jambhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('syzygium cumini','Jambhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('syzygium heyneanum','Jambhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('calotropis gigantea','mandar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('mammea suriga','Surangi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cerbera manghas','Sukanu')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('terminalia mantaly','Madagascar Almond')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('catunaregam spinosa','Gela')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('lagerstroemia indica','Gulmendi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('clerodendrum multiflorum','Iran')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('hamelia patens','Fire Bush')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('duranta erecta','Duranta')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('euphorbia cotinifolia','Copper Plant')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('mitragyna parvifolia','Kalam')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('nyctanthes arbor-tristis','Parijatak')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('eucalyptus spp (confirm name)','Nilgiri')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('mussaenda erythrophylla','Mussaenda')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('gardenia uliginosa','Pandhara')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('gmelina arborea','Shiwan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tamilnadia uliginosa','Pandhara')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('haldina cordifolia','Hedu')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('trewia nudiflora','Petari')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('clusia rosea','Pitch Apple')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('gardenia latifolia','Pendro')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ficus hispida','Kala Umber')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('plumeria pudica','Pandhra Chapha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('plumeria obtusata (plumeria obtusa)','Lal Chapha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('plumeria rubra','Lal Chapha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tectona grandis','Sag')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('clausena indica','Ran Kadhilimb')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('murraya koenigii','Kadhilimb')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ailanthus excelsa','Maharukh')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('harpullia zanguebarica','Black Pearl')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dalbergia melanoxylon','Black Wood')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('grevillea robusta','Silver Oak')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dalbergia lanceolaria var. lanceolaria','Dandus')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dalbergia lanceolaria var. paniculata','Phanshi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('filicium decipiens','Fern Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('simarouba glauca','Laxmi Taru')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dalbergia sissoo','Shisav')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('murraya paniculata','Kunti')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cassia spectabilis','Spectacular Cassia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dalbergia latifolia','Shisam')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('senna alata','Spectacular Cassia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('senna spectabilis','Spectacular Cassia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bursera delpechiana','Tree of Fragrance')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bursera penicillata','Tree of Fragrance')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('parmentiera cereifera','Candle Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('schinus terebinthifolius','Brazillian Pepper')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('spondias pinnata','Ambada')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('heterophragma quadriloculare','Waras')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('spathodea campanulata','Pichkari')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('erythrina blakei','Cockspur Coral Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('erythrina crista-galli','Cockspur Coral Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('toona hexandra','Toon')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cassia fistula','Amaltas')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('litchi chinensis','Litchi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('nephelium chinensis','Litchi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sambucus canadensis','Sambucus')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('markhamia platycalyx','Markhamia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('stereospermum colais','Padal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('stereospermum colais var. angustifolium','Padal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('canarium ovatum','Pilinut')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('schleichera oleosa','Kusumb')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('haplophragma adenophyllum','Katsagon')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('saraca asoca','Seeta Ashok')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('azadirachta indica','Kadulimb')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('khaya grandifoliola','Large-leaved Khaya')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('swietenia macrophylla','Mahogany')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('swietenia mahagoni','Mahogany')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('garuga pinnata','Kakad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('aegle marmelos','Bel')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tecoma gaudichaudi','Tecoma')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tecoma stans','Tecoma')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('brownea coccinea','Flame Bean')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sapindus emarginatus','Ritha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sapindus laurifolius','Ritha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('amherstia nobilis','Urvashi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('vitex altissima','Balage')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('vitex negundo','Balage')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tamarindus indica','Chinch')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cassia roxburghii','Red Cassia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cassia javanica var. indochinensis','Jawa Cassia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cassia javanica var. javanica','Jawa Cassia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sesbania grandiflora','Hadga')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sesbania sesban','Hadga')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cassia grandis','Horse Cassia')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('chloroxylon swietenia','Behru')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cassia renigera','Gulabi Bahawa')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cassia surattensis','Motha Tarwad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('senna siamea','Motha Tarwad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('senna surattensis','Motha Tarwad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('averrhoa bilimbi','Bimbal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pterocarpus marsupium','Beeja')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pterocarpus santalinus','Beeja')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('castanospermum australe','Australian Chestnut')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pterocarpus indicus f. echinatus','Padauk')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pterocarpus indicus f. indicus','Padauk')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('khaya anthotheca','Khaya')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('khaya senegalensis','Khaya')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('kigelia africana','Brahmadand')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('kigelia pinnata ****','Brahmadand')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('Amoora rohituka','Raktarohida')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('aphanamixis polystachya','Raktarohida')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('lannea coromandelica','Moi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('guaiacum officinale','Gum Guaiacum')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('haematoxylon campechianum','Raktachandan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('hardwickia binata','Anjan')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tecoma smithii','Dwarf Tecoma')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('balanites aegyptiaca','Hinganbet')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('feronia elephantum','Kavath')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('limonia acidissima','Kavath')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('boswellia serrata','Salai')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dolichandrone falcata','Medhshingi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('averrhoa carambola','Kamrak')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('gliricidia sepium','Giripushpa')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('chukrasia tabularis','Dalmara')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('millettia peguensis','Lal Karanj')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('crateva magna','Vaywaran')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pongamia pinnata','Karanj')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('erythrina suberosa','Buch Panagara')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('schrebera swietenioides','Mokha')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('erythrina variegata','Pangara')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ougeinia oojeinensis','Kala Palas')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('adenanthera pavonina','Palas')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('butea monosperma','Ratangunj')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('albizia lucidor (Mistake in ToP)','Potka Shirish')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('calliandra haematocephala','Powder Puff')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('calliandra portoricensis','Powder Puff')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acrocarpus fraxinifolius','Tokphal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('jacaranda mimosifolia','Neelmohor')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia planifrons','Chhatri Babhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dichrostachys cinera','Sigamkathi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia catechu','Khair')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia chundra','Khair')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('parkinsonia aculeata','Vedi Babhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia eburnea','Dev Babhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia farnesiana','Dev Babhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia leucophloea','Hivar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia nilotica','Babhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia nilotica var. cupressiformis','Babhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('caesalpinia coriaria','Dividivi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acacia ferruginea (Mistake in ToP)','Pandhra Khair')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('albizia amara','Kala Shirish')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('caesalpinia pulcherrima','Shankasur')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('colvillea racemosa','Manimohor')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('delonix regia','Gulmohor')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('leucaena latisiliqua','Subabhul')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('parkia biglandulosa','Chenduphali')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('prosopis cineraria','Shami')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('prosopis juliflora','Shami')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('albizia odoratissima','Chinchva')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('peltophorum africanum','Tambadsheng')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('peltophorum pterocarpum','Tambadsheng')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('albizia lebbeck','Shirish')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('albizia procera','Kinhai')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('caesalpinia ferrea','Brazillian Ironwood')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('moringa oleifera','Shewga')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pithecellobium dulce','Vilayati Chinch')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('samanea saman (Albizia saman)','Rain Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('melia azedarach','Bakan Neem')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('melia dubia','Bakan Neem')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('millingtonia hortensis','Buch')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('oroxylum indicum','Tetu')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('radermachera xylocarpa','Khadshingi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('adansonia digitata','Gorakh Chinch')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('joannesia princeps','Arara Nut')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ceiba pentandra','Pandhri Sawar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ceiba speciosa','Delhi Sawar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('chorisia speciosa','Delhi Sawar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('brassaia actinophylla','Umbrella Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('schefflera actinophylla','Umbrella Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pseudobombax ellipticum','Shaving Brush')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sterculia foetida','Jungli Badam')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sterculia guttata','Jungli Badam')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bombax ceiba','Sawar')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tabebuia rosea','Pink Trumpet')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tabebuia argentea','Golden Trumpet')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tabebuia pallida','Purple Trumpet')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tabebuia avellanedae','Taheboo Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tabebuia chrysantha','Yellow Trumpet Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('tabebuia chrysotricha','Yellow Trumpet Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('adonidia merrillii','Manila Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('veitchia merrillii','Manila Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('areca catechu','Supari')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('carpentaria accuminata','Carpentaria Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('caryota mitis','Bherli Mad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('caryota urens','Bherli Mad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('caryota rumphiana','Bherli Mad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('chambeyronia macrocarpa','Red Leaf Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dictyosperma album','Hurricane Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dypsis decaryi','Triangle Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('Neodypsis decaryi','Triangle Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dypsis leptocheilos','Red Neck Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('howea forsteriana','Kentia Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('hyophorbe lagenicaulis','Champagne Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('hyophorbe verschaffeltii','Champagne Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('phoenicophorium borsigianum','Thief Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ravenea rivularis','Majesty Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('roystonea oleracea','Bottle Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('roystonea regia','Bottle Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('veitchia arecina','Montgomery Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('wodyetia bifurcata','Foxtail Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('chrysalidocarpus renda','Areca Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dypsis lutescens','Areca Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dypsis madagascariensis','Areca Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cyrtostachys renda','Lipstick Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ptychosperma elegans','Mac Arthur Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('ptychosperma macarthurii','Mac Arthur Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('aiphanes erosa','Bitten Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('attalea cohune','American Oil Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('butia capitata','Jelly Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cocos nucifera','Naral')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('elaeis guineensis','Oil Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('phoenix canariensis','Canary Island Date Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('phoenix robusta','Shelu')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('phoenix roebelenii','Dwarf Date Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('phoenix rupicola','Cliff Date Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('phoenix dactylifera','Shindi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('phoenix sylvestris','Shindi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('syagrus coronata','Licuri Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('syagrus romanzoffiana','Queen Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('syagrus sancona','Queen Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('Arecastrum romanzoffianum','Queen Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('wallichia disticha','Wallich''s Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('arenga engleri','Formosa Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('chamaedorea seifrizii','Bamboo Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('brahea armata','Mexican Blue Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('coccothrinax argentata','Hispaniolan Silver Thatch')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('copernicia prunifera','Carnauba Wax Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('corypha umbraculifera','Talipot')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('latania loddigesii','Blue Latan Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('latania lontaroides','Red Latan Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('latania verschaffeltii','Yellow Latan Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('licuala grandis','Vanautu Fan Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('licuala spinosa','Vanautu Fan Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('livistona chinensis','Chinese Fam Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('livistona rotundifolia','Foot Stool Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pritchardia pacifica','Fiji Fan Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('thrinax parviflora','Thatch Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('washingtonia robusta','California Fan Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('washingtonia filifera','California Fan Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('acoelorrhaphe wrightii','Everglades Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('chamaerops humilis','European Fan Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bismarckia nobilis','Silver Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('borassus flabellifer','Tad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('hyphaene dichotoma','Ravan Tad')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sabal causiarum','Cabage Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sabal palmetto','Cabage Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('sabal mauritiiformis','Bay Palmetto')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('callitris glaucophylla','White Cypress')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pinus longifolia','Pine')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('pinus roxburghii','Pine')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cupressus glabra','Agra Suru')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cupressus sempervirens','Italian Cypress')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cupressus torulosa','Bhutan Cypress')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('juniperus chinensis','Chinese Juniper')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('afrocarpus gracilior','Fern Pine')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('podocarpus gracilior','Fern Pine')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('Platycladus orientalis','Morpankhi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('thuja orientalis (Platycladus orientalis)','Morpankhi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('agathis robusta','Kauri Pine')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('araucaria bidwillii','Bunya Pine')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('araucaria columnaris','New Caledonian Pine')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('araucaria cunninghamii','Hoop Pine')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('araucaria heterophylla','Christmas Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('araucaria hunsteinii','Klinki Pine')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('podocarpus neriifolius','Yellow Wood')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('taxodium distichum','Bald Cypress')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cycas circinalis','Queen Sago')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cycas pectinata','Thakal')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cycas revoluta','Sago Palm')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cycas rumphii','Indonesian Cycas')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cycas thouarsii','Samble')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cycas zeylanica','Andaman Cycas')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bambusa arundinacea','Thorny Bamboo')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bambusa ventricosa','Buddha''s Belly Bamboo')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('bambusa vulgaris','Golden Bamboo')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dendrocalamus giganteus','Giant Bamboo')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('dendrocalamus strictus','Velu')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('schizostachyum lumampao','Philippines Bamboo')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('cleistanthus collinus','Garari')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('commiphora stocksiana','Bayi')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('koelreuteria bipinnata','Golden Rain Tree')");
		db.execSQL("insert into " + TABLE_SPECIES_MASTER + " (" + SPECIES_SCIENTIFIC_NAME[0] + "," + SPECIES_LOCAL_NAME[0] + ") values('paraserianthes lophantha','Cape Wattle')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.v("upgrade db", "old version: " + oldVersion + " newVersion: " + newVersion);
		String[] str = getDropTableSQLString(TABLE_ARRAY);
		for (int i = 0; i < str.length; i++)
		{
			db.execSQL(str[i]);
		}
		onCreate(db);
	}

	public String[] getDropTableSQLString(String[] TABLE_ARRAY)
	{
		String str[] = new String[TABLE_ARRAY.length];
		StringBuilder sql = new StringBuilder();
		for (int i = 0; i < TABLE_ARRAY.length; i++)
		{
			sql.append("DROP TABLE IF EXISTS " + TABLE_ARRAY[i] + ";");
			Log.v("upgrade sql", sql.toString());
			str[i] = sql.toString();
			sql = new StringBuilder();
		}
		return str;
	}

	public String createSQLString(String name, String[][] KEY)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE " + name + " (");
		Log.v("", "KEY.length=" + KEY.length);
		for (int i = 0; i < KEY.length; i++)
		{
			Log.v("", "KEY[" + i + "].length=" + KEY[i].length);
			sql.append((i == 0 ? "" : ","));
			for (int j = 0; j < KEY[i].length; j++)
			{
				sql.append((j == 0 ? "" : " ") + KEY[i][j]);
			}
		}
		sql.append(");");
		return sql.toString();
	}

	public int authenticateUser(String name, String passwd)
	{
		Cursor surveyorCursor = db.query(TABLE_SURVEYOR, new String[] { SURVEYOR_ID[0], SURVEYOR_NAME[0], SURVEYOR_PASSWORD[0] }, SURVEYOR_NAME[0] + "=? and " + SURVEYOR_PASSWORD[0] + "=?", new String[] { name, passwd }, null, null, null);
		Log.i("authenticate", "cursor rows: " + surveyorCursor.getCount());
		if (surveyorCursor.getCount() > 0)
		{
			surveyorCursor.moveToNext();
			return surveyorCursor.getInt(0);
		}
		return 0;
	}

	public long insertAreadetails(Area_Details _ad)
	{
		ContentValues newArea = new ContentValues();

		// Assign value for each row

		newArea.put(AREA_DETAILS_PROPERTY_TYPE[0], _ad.get_prop_type());
		newArea.put(AREA_DETAILS_PROPERTY_OWNER[0], _ad.get_prop_owner());
		newArea.put(AREA_DETAILS_HOUSE_NUMBER[0], _ad.get_house_no());
		newArea.put(AREA_DETAILS_SURVEY_NUMBER[0], _ad.get_survey());
		newArea.put(AREA_DETAILS_PROPERTY_DESC[0], _ad.get_prop_desc());
		newArea.put(AREA_DETAILS_PROPERTY_AREA[0], _ad.get_prop_area());
		newArea.put(FORM_ID[0], _ad.get_form_key());

		// Insert the row.
		long gid = db.insert(TABLE_AREA_DETAILS, null, newArea);
		Cursor cur = db.rawQuery("SELECT " + DatabaseHelper.GID[0] + " from " + DatabaseHelper.TABLE_AREA_DETAILS + " WHERE " + DatabaseHelper.GID[0] + "=" + gid, null);
		if (cur.getCount() > 0)
		{
			// Toast.makeText(context, "Values inserted", Toast.LENGTH_LONG).show();
		}
		return gid;
	}

	public long insertSurveydetails(TreeDetails _sd)
	{
		// Create a new row of values to insert.
		ContentValues newSurveyValues = new ContentValues();
		// Assign values for each row.
		String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

		newSurveyValues.put(FORM_ID[0], _sd.getFormNumber());
		newSurveyValues.put(PROPERTY_ID[0], _sd.getPropertyId());
		newSurveyValues.put(TREE_NAME[0], _sd.getName());
		newSurveyValues.put(TREE_NUMBER[0], _sd.getNumber());
		newSurveyValues.put(TREE_BOTANICAL_NAME[0], _sd.getBotanicalName());
		newSurveyValues.put(TREE_HEIGHT_M[0], _sd.getHeight());
		newSurveyValues.put(TREE_NEST[0], _sd.isNest() ? 1 : 0);
		newSurveyValues.put(TREE_NAILS[0], _sd.isNails() ? 1 : 0);
		newSurveyValues.put(TREE_FLOWERS[0], _sd.isFlowers() ? 1 : 0);
		newSurveyValues.put(TREE_FRUITS[0], _sd.isFruits() ? 1 : 0);
		newSurveyValues.put(TREE_BURROWS[0], _sd.isBurrows() ? 1 : 0);
		newSurveyValues.put(TREE_HEALTH_OF_TREE[0], _sd.getHealth());
		newSurveyValues.put(TREE_RISK_DUE_TO_TREE[0], _sd.getRiskDueToTree());
		newSurveyValues.put(TREE_RISK_DESC[0], _sd.getRiskDesc());
		newSurveyValues.put(TREE_POSTER[0], _sd.isPoster() ? 1 : 0);
		newSurveyValues.put(TREE_WIRES[0], _sd.isWires() ? 1 : 0);
		newSurveyValues.put(TREE_GUARD[0], _sd.isTreeGuard() ? 1 : 0);
		newSurveyValues.put(TREE_OTHER_NUISSANCE[0], _sd.isOtherNuissance() ? 1 : 0);
		newSurveyValues.put(TREE_OTHER_NUISSANCE_DESC[0], _sd.getOtherNuissanceDesc());
		newSurveyValues.put(TREE_GROUND_TYPE[0], _sd.getGroundType());
		newSurveyValues.put(TREE_GROUND_DESC[0], _sd.getGroundDesc());
		newSurveyValues.put(TREE_GIRTH_M[0], _sd.getGirth());
		newSurveyValues.put(TREE_REFER_TO_DEPT[0], _sd.isReferToDept() ? 1 : 0);
		newSurveyValues.put(TREE_SPECIAL_OTHER[0], _sd.isSpecialOther() ? 1 : 0);
		newSurveyValues.put(TREE_SPECIAL_OTHER_DESC[0], _sd.getSpecialOtherDesc());
		newSurveyValues.put(TREE_LATTITUDE[0], _sd.getLattitude());
		newSurveyValues.put(TREE_LONGITUDE[0], _sd.getLongitude());
		newSurveyValues.put(TREE_CREATION_DATE[0], _sd.getDate());// .get_Created().getTime());
		newSurveyValues.put(TREE_CREATION_TIME[0], _sd.getTime());
		newSurveyValues.put(DEVICE_ID[0], androidId);
		newSurveyValues.put(SESSION_ID[0], _sd.getSessionId());
		newSurveyValues.put(SURVEYOR_ID[0], _sd.getSurveyorId());
		newSurveyValues.put(PRABHAG_ID[0], _sd.getPrabhagId());
		newSurveyValues.put(CLUSTER_ID[0], _sd.getClusterId());
		newSurveyValues.put(TREE_PHOTO_F_1[0], _sd.getImageF1());
		newSurveyValues.put(TREE_PHOTO_F_2[0], _sd.getImageF2());
		newSurveyValues.put(TREE_PHOTO_P_1[0], _sd.getImageP1());
		newSurveyValues.put(TREE_PHOTO_P_2[0], _sd.getImageP2());
		newSurveyValues.put(TREE_PHOTO_OTHER[0], _sd.getImageOther());
		newSurveyValues.put(TREE_EDIT_TRACE[0], _sd.getEditTrace());
		newSurveyValues.put(TREE_DELETED[0], _sd.isDeleted() ? 1 : 0);
		// Insert the row.
		return db.insert(TABLE_TREE, null, newSurveyValues);
	}

	public Cursor getTreeSurveyDump()
	{
		return db.rawQuery("SELECT * FROM " + TABLE_TREE + " WHERE " + TREE_DELETED[0] + "=0", null);
	}

	public Cursor getSessionDump()
	{
		return db.rawQuery("SELECT * FROM " + TABLE_SESSION, null);
	}

	// public long createNewSession()
	// {
	// long sessionId = 0;
	// Cursor cursor = db.rawQuery("SELECT " + SESSION_ID[0] + " FROM " + TABLE_SESSION, null);
	// if (cursor.getCount() == 0)
	// {
	// sessionId = 1;
	// }
	// else
	// {
	// cursor.moveToLast();
	// sessionId = cursor.getInt(0);
	// sessionId++;
	// }
	// ContentValues values = new ContentValues();
	// values.put(SESSION_ID[0], sessionId);
	// values.put(SESSION_TREES_SURVEYED[0], 0);
	// values.put(SESSION_TIME[0], System.currentTimeMillis());
	// long res = db.insert(TABLE_SESSION, null, values);
	// return (res == 1 ? sessionId : 0);
	// }

	public String createSession(String sessionId, int surveyorId)
	{
		System.out.println("Creating new session with sessionId: " + sessionId);
		ContentValues values = new ContentValues();
		values.put(SESSION_ID[0], sessionId);
		values.put(SESSION_TREES_SURVEYED[0], 0);
		values.put(SURVEYOR_ID[0], surveyorId);
		values.put(SESSION_TIME[0], System.currentTimeMillis());
		db.insert(TABLE_SESSION, null, values);
		return sessionId;
	}

	public String getSessionAccordingTo10Mins(int surveyorId)
	{
		int ses;
		Cursor cursor = db.rawQuery("SELECT " + GID[0] + "," + SESSION_ID[0] + "," + SESSION_TREES_SURVEYED[0] + "," + SESSION_TIME[0] + "," + SURVEYOR_ID[0] + " FROM " + TABLE_SESSION + " WHERE " + SURVEYOR_ID[0] + "=" + surveyorId, null);
		Calendar cal = Calendar.getInstance();
		int day = cal.get(cal.DAY_OF_MONTH);
		String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		String sessionId = androidId + "_" + day + "_" + surveyorId + "_";
		if (cursor.getCount() == 0)
		{
			ses = 1;
			sessionId += ses;
			return createSession(sessionId, surveyorId);
		}
		else
		{
			cursor.moveToLast();
			long time = cursor.getLong(3);
			long currTime = System.currentTimeMillis();
			ses = cursor.getInt(0);
			long result = currTime - time;
			Toast.makeText(context, "time lapse: " + (result / 60000) + " min(s)", Toast.LENGTH_LONG).show();
			if (result > SESSION_CHANGE_TIMEOUT)
			{
				if (cursor.getInt(2) > 0)
				{
					ses++;
					sessionId += ses;
					return createSession(sessionId, surveyorId);
				}
			}
			sessionId += ses;
		}
		return sessionId;
	}

	public boolean updateSessionTime(String sessionId, int surveyorId)
	{
		int treesSurveyed = 0;
		Cursor cursor = db.rawQuery("SELECT " + SESSION_TREES_SURVEYED[0] + " FROM " + TABLE_SESSION + " WHERE " + SESSION_ID[0] + "='" + sessionId + "' and " + SURVEYOR_ID[0] + "=" + surveyorId, null);
		Log.i("updatesession", "cursor size: " + cursor.getCount() + " session: " + sessionId);
		cursor.moveToNext();
		treesSurveyed = cursor.getInt(0);
		Log.i("update", "session id: " + sessionId + " tress surveyed: " + treesSurveyed);
		cursor.close();
		treesSurveyed++;
		ContentValues values = new ContentValues();
		values.put(SESSION_TIME[0], System.currentTimeMillis());
		values.put(SESSION_TREES_SURVEYED[0], treesSurveyed);
		int res = db.update(TABLE_SESSION, values, SESSION_ID[0] + "=?", new String[] { sessionId + "" });
		return (res == 1 ? true : false);
	}

	public void databaseDump(String androidId) throws IOException
	{
		for (int i = 0; i < TABLE_ARRAY.length; i++)
		{
			File myNewFolder = new File(Environment.getExternalStorageDirectory().toString() + "/" + androidId + "/TreeCensus");
			myNewFolder.mkdirs();
			File myFile = new File(Environment.getExternalStorageDirectory().toString() + "/" + androidId + "/TreeCensus/" + TABLE_ARRAY[i] + ".csv");
			myFile.createNewFile();
			CSVWriter csvWrite = new CSVWriter(new FileWriter(myFile));
			Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ARRAY[i], null);
			Log.i("data dump", "dumping data for " + TABLE_ARRAY[i]);
			csvWrite.writeNext(cursor.getColumnNames());
			Log.i("data dump", TABLE_ARRAY[i] + " columns: " + Arrays.toString(cursor.getColumnNames()));
			String arrStr[] = new String[cursor.getColumnCount()];
			while (cursor.moveToNext())
			{
				for (int j = 0; j < arrStr.length; j++)
				{
					arrStr[j] = cursor.getString(j);
				}
				Log.i("data dump", "data: " + Arrays.toString(arrStr));
				csvWrite.writeNext(arrStr);
			}
			csvWrite.close();
			cursor.close();
		}
	}

	public Session[] getAllSessions()
	{
		Cursor sessionCursor = db.query(TABLE_SESSION, new String[] { GID[0], SESSION_ID[0], SESSION_TREES_SURVEYED[0], SESSION_TIME[0], SURVEYOR_ID[0] }, null, null, null, null, null);
		Session sessions[] = new Session[sessionCursor.getCount()];
		for (int i = 0; i < sessions.length; i++)
		{
			sessions[i] = new Session();
			sessionCursor.moveToNext();
			sessions[i].setGid(sessionCursor.getInt(0));
			sessions[i].setId(sessionCursor.getString(1));
			sessions[i].setNumTrees(sessionCursor.getInt(2));
			sessions[i].setTime(sessionCursor.getLong(3));
			sessions[i].setSurveyorId(sessionCursor.getInt(4));
			// sessions[i].setTrees(getTreeDetailsAccordingToSessionId(sessions[i].getId()));
		}
		return sessions;
	}

	public Session[] getSessionsAccordingToSurveyorId(int surveyorId)
	{
		Cursor sessionCursor = db.query(TABLE_SESSION, new String[] { GID[0], SESSION_ID[0], SESSION_TREES_SURVEYED[0], SESSION_TIME[0], SURVEYOR_ID[0] }, SURVEYOR_ID[0] + "=" + surveyorId, null, null, null, null);
		Session sessions[] = new Session[sessionCursor.getCount()];
		for (int i = 0; i < sessions.length; i++)
		{
			sessions[i] = new Session();
			sessionCursor.moveToNext();
			sessions[i].setGid(sessionCursor.getInt(0));
			sessions[i].setId(sessionCursor.getString(1));
			sessions[i].setNumTrees(sessionCursor.getInt(2));
			sessions[i].setTime(sessionCursor.getLong(3));
			sessions[i].setSurveyorId(sessionCursor.getInt(4));
			// sessions[i].setTrees(getTreeDetailsAccordingToSessionId(sessions[i].getId()));
		}
		return sessions;
	}

	public TreeDetails[] getTreeDetailsAccordingToSessionId(String sessionGid, int surveyorId)
	{
		Cursor treeCursor = db.query(TABLE_TREE, new String[] { TREE_NUMBER[0], PRABHAG_ID[0], CLUSTER_ID[0], TREE_NAME[0], TREE_BOTANICAL_NAME[0], TREE_GIRTH_M[0], TREE_HEIGHT_M[0], TREE_NEST[0], TREE_BURROWS[0], TREE_FLOWERS[0], TREE_FRUITS[0], TREE_NAILS[0], TREE_POSTER[0], TREE_WIRES[0], TREE_GUARD[0], TREE_OTHER_NUISSANCE[0], TREE_OTHER_NUISSANCE_DESC[0], TREE_HEALTH_OF_TREE[0], TREE_GROUND_TYPE[0], TREE_GROUND_DESC[0], TREE_RISK_DUE_TO_TREE[0], TREE_RISK_DESC[0], TREE_REFER_TO_DEPT[0], TREE_SPECIAL_OTHER[0], TREE_SPECIAL_OTHER_DESC[0], TREE_LATTITUDE[0], TREE_LONGITUDE[0], TREE_POINT[0], TREE_CREATION_DATE[0], TREE_CREATION_TIME[0], DEVICE_ID[0], SURVEYOR_ID[0], SESSION_ID[0], TREE_PHOTO_F_1[0], TREE_PHOTO_F_2[0], TREE_PHOTO_P_1[0], TREE_PHOTO_P_2[0], TREE_PHOTO_OTHER[0], PROPERTY_ID[0], GID[0] }, SESSION_ID[0] + "=? and " + TREE_DELETED[0] + "=0 and " + TREE_EDIT_TRACE[0] + " like '0' and " + SURVEYOR_ID[0] + "=" + surveyorId, new String[] { sessionGid + "" }, null, null, null);
		TreeDetails trees[] = new TreeDetails[treeCursor.getCount()];
		for (int j = 0; j < trees.length; j++)
		{
			trees[j] = new TreeDetails();
			treeCursor.moveToNext();
			trees[j].setNumber(treeCursor.getInt(0));
			trees[j].setPrabhagId(treeCursor.getInt(1));
			trees[j].setClusterId(treeCursor.getInt(2));
			trees[j].setFormNumber(trees[j].getPrabhagId() + "_" + trees[j].getClusterId());
			trees[j].setName(treeCursor.getString(3));
			trees[j].setBotanicalName(treeCursor.getString(4));
			trees[j].setGirth(treeCursor.getDouble(5));
			trees[j].setHeight(treeCursor.getDouble(6));
			trees[j].setNest(treeCursor.getString(7).equals("1") ? true : false);
			trees[j].setBurrows(treeCursor.getString(8).equals("1") ? true : false);
			trees[j].setFlowers(treeCursor.getString(9).equals("1") ? true : false);
			trees[j].setFruits(treeCursor.getString(10).equals("1") ? true : false);
			trees[j].setNails(treeCursor.getString(11).equals("1") ? true : false);
			trees[j].setPoster(treeCursor.getString(12).equals("1") ? true : false);
			trees[j].setWires(treeCursor.getString(13).equals("1") ? true : false);
			trees[j].setTreeGuard(treeCursor.getString(14).equals("1") ? true : false);
			trees[j].setOtherNuissance(treeCursor.getString(15).equals("1") ? true : false);
			trees[j].setOtherNuissanceDesc(treeCursor.getString(16));
			trees[j].setHealth(treeCursor.getString(17));
			trees[j].setGroundType(treeCursor.getString(18));
			trees[j].setGroundDesc(treeCursor.getString(19));
			trees[j].setRiskDueToTree(treeCursor.getString(20));
			trees[j].setRiskDesc(treeCursor.getString(21));
			trees[j].setReferToDept(treeCursor.getString(22).equals("1") ? true : false);
			trees[j].setSpecialOther(treeCursor.getString(23).equals("1") ? true : false);
			trees[j].setSpecialOtherDesc(treeCursor.getString(24));
			trees[j].setLattitude(treeCursor.getDouble(25));
			trees[j].setLongitude(treeCursor.getDouble(26));
			trees[j].setPoint(treeCursor.getString(27));
			trees[j].setCreationDate(treeCursor.getString(28));
			trees[j].setCreationTime(treeCursor.getString(29));
			trees[j].setDeviceId(treeCursor.getString(30));
			trees[j].setSurveyorId(treeCursor.getInt(31));
			Cursor surveyorCursor = db.query(TABLE_SURVEYOR, new String[] { SURVEYOR_NAME[0] }, SURVEYOR_ID[0] + "=?", new String[] { treeCursor.getString(31) }, null, null, null);
			if (surveyorCursor.getCount() > 0)
			{
				surveyorCursor.moveToNext();
				trees[j].setSurveyorName(surveyorCursor.getString(0));
			}
			trees[j].setSessionId(treeCursor.getString(32));
			trees[j].setImageF1(treeCursor.getString(33));
			trees[j].setImageF2(treeCursor.getString(34));
			trees[j].setImageP1(treeCursor.getString(35));
			trees[j].setImageP2(treeCursor.getString(36));
			trees[j].setImageOther(treeCursor.getString(37));
			trees[j].setPropertyId(treeCursor.getString(38));
			Cursor propertyCursor = db.query(TABLE_AREA_DETAILS, new String[] { AREA_DETAILS_PROPERTY_TYPE[0] }, GID[0] + "=?", new String[] { treeCursor.getString(38) }, null, null, null);
			if (propertyCursor.getCount() > 0)
			{
				propertyCursor.moveToNext();
				trees[j].setPropertyType(propertyCursor.getString(0));
			}
			trees[j].setGid(treeCursor.getLong(39));
		}
		return trees;
	}

	public void deleteTreeEntry(long treeGid)
	{
		ContentValues values = new ContentValues();
		values.put(TREE_DELETED[0], 1);
		// Cursor cursor = db.rawQuery("UPDATE " + TABLE_TREE + " SET " + TREE_DELETED[0] +
		// "=1 WHERE " + GID[0] + "=" + treeGid, null);
		int up = db.update(TABLE_TREE, values, GID[0] + "=" + treeGid, null);
		// Toast.makeText(context, "deleted: " + up, Toast.LENGTH_SHORT).show();
	}

	public void updateTreeWithEditTrace(TreeDetails tree)
	{
		long gid = insertSurveydetails(tree);
		ContentValues values = new ContentValues();
		values.put(TREE_EDIT_TRACE[0], gid);
		db.update(TABLE_TREE, values, GID[0] + "=" + tree.getGid(), null);
	}
}
