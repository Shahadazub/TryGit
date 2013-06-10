package com.shahadazub.takeyourpills;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;

public class PillsSettingsActivity extends Activity {
	
	DBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pills_settings);
		
		dbHelper = new DBHelper(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pills_settings, menu);
		return true;
	}
	
	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, "myDB", null, 1);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table pills ("
					+ "id integer primary key autoincrement,"
					+ "name text"
					+ "type integer"
					+ "alarm integer"
					+ ");");
		}
		
		@Override
		public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
			
		}
	}

}
