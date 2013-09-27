package com.atha.csvexport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.atha.treemapindia.DatabaseHandler;
import com.atha.treemapindia.DatabaseHelper;

public class CSVexport
{
	Context	               _context;
	DatabaseHandler	       _db;
	SQLiteDatabase	       _sld;

	Cursor	               cur_uad, cur_ad, cur_sd;
	File	               exportDir, myNewFolder, exportDir1, exportDir2;
	private DatabaseHelper	db;

	public CSVexport(Context _context, String androidId, int old) throws IOException
	{
		this._context = _context;
		// _sld=_context.openOrCreateDatabase("Survey.db", Context.MODE_PRIVATE, null);
		// _db=new DatabaseHandler(_context);
		// _db.open();
		db = new DatabaseHelper(_context);

		myNewFolder = new File(Environment.getExternalStorageDirectory().toString() + "/" + androidId + "/TreeCensus");
		myNewFolder.mkdir();
		if (old == 1)
		{
			exportDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + androidId + "/TreeCensus/", "OLD_Unique_area_details.csv");
			exportDir1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + androidId + "/TreeCensus/", "OLD_area_details.csv");
			exportDir2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + androidId + "/TreeCensus/", "OLD_survey_details.csv");
		}
		else
		{
			exportDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + androidId + "/TreeCensus/", "Unique_area_details.csv");
			exportDir1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + androidId + "/TreeCensus/", "session_details.csv");
			exportDir2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + androidId + "/TreeCensus/", "survey_details.csv");
		}

		exportDir.createNewFile();
		CSVWriter csvWrite = new CSVWriter(new FileWriter(exportDir));
		cur_uad = _sld.rawQuery("Select * from unique_area_details", null);
		csvWrite.writeNext(cur_uad.getColumnNames());
		while (cur_uad.moveToNext())
		{

			String arrStr[] = { cur_uad.getString(0), cur_uad.getString(1), cur_uad.getString(2), cur_uad.getString(3) };
			csvWrite.writeNext(arrStr);
		}
		csvWrite.close();
		cur_uad.close();

		csvWrite = new CSVWriter(new FileWriter(exportDir1));
		cur_ad = _sld.rawQuery("Select * from " + DatabaseHelper.TABLE_SESSION, null);
		csvWrite.writeNext(cur_ad.getColumnNames());
		while (cur_ad.moveToNext())
		{

			String arrStr[] = { cur_ad.getString(0), cur_ad.getString(1), cur_ad.getString(2), cur_ad.getString(3), cur_ad.getString(4), cur_ad.getString(5), cur_ad.getString(6), cur_ad.getString(7), cur_ad.getString(8), cur_ad.getString(9) };
			csvWrite.writeNext(arrStr);
		}
		csvWrite.close();
		cur_ad.close();

		csvWrite = new CSVWriter(new FileWriter(exportDir2));
		// cur_sd = _sld.rawQuery("Select * from survey_details", null);
		// csvWrite.writeNext(cur_sd.getColumnNames());
		// while (cur_sd.moveToNext())
		// {
		//
		// String arrStr[] = { cur_sd.getString(0), cur_sd.getString(1), cur_sd.getString(2),//
		// cur_sd.getString(3), cur_sd.getString(4), cur_sd.getString(5), cur_sd.getString(6),//
		// cur_sd.getString(7), cur_sd.getString(8), cur_sd.getString(9), cur_sd.getString(10),//
		// cur_sd.getString(11), cur_sd.getString(12), cur_sd.getString(13), cur_sd.getString(14),//
		// cur_sd.getString(15), cur_sd.getString(16), cur_sd.getString(17), cur_sd.getString(18),//
		// cur_sd.getString(19), cur_sd.getString(20), cur_sd.getString(21), cur_sd.getString(22),//
		// cur_sd.getString(23), cur_sd.getString(24), cur_sd.getString(25), cur_sd.getString(26),//
		// cur_sd.getString(27), cur_sd.getString(28), cur_sd.getString(29), cur_sd.getString(30),//
		// cur_sd.getString(31), cur_sd.getString(32), cur_sd.getString(33), cur_sd.getString(34),//
		// cur_sd.getString(35), cur_sd.getString(36) };
		// csvWrite.writeNext(arrStr);
		// }
		// csvWrite.close();
		// _db.close();
		// cur_sd.close();
		cur_sd = db.getTreeSurveyDump();
		Log.i("Exporting", "cursor rows: " + cur_sd.getCount() + " cursor column: " + cur_sd.getColumnCount());
		csvWrite.writeNext(cur_sd.getColumnNames());
		String arrStr[] = new String[cur_sd.getColumnCount()];
		while (cur_sd.moveToNext())
		{
			for (int i = 0; i < arrStr.length; i++)
			{
				arrStr[i] = cur_sd.getString(i);
			}
			csvWrite.writeNext(arrStr);
		}
		csvWrite.close();
		db.close();
		cur_sd.close();
	}

}
