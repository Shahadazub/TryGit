package com.shahadazub.takeyourpills;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

public class PillsSettingsActivity extends Activity implements OnClickListener {
	
	Button btnSaveChange, btnDelete;
	EditText etName, etIndaMedikit, etCapacity;
	RadioGroup RGType;
	CheckBox selChBAlarm;
	
	Context context;
	
	int type, alarm;
	
	
	final String L = "MyLog";
	
	DBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pills_settings);
		
		btnSaveChange = (Button) findViewById(R.id.pillsSettings_saveChange_button);
		btnSaveChange.setOnClickListener(this);
		
		btnDelete = (Button) findViewById(R.id.pillsSettings_delete_button);
		btnDelete.setOnClickListener(this);
		
		etName = (EditText) findViewById(R.id.pillsSettings_Name_editText);
		etIndaMedikit = (EditText) findViewById(R.id.pills_settings_indaMedikit_editText);
		etCapacity = (EditText) findViewById(R.id.pills_settings_capacity_editText);
		
		RGType = (RadioGroup) findViewById(R.id.pillsSettings_Type_radioGroup);
		
		
		
		dbHelper = new DBHelper(this);
		Log.d(L, "--- Everything created ---");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pills_settings, menu);
		Log.d(L, "--- Pills Settings Strated ---");
		return true;
	}
	
	
	
	
	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, "myDB", null, 1);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(L, "--- Cresting DB with table pills: ---");
			db.execSQL("create table pills ("
					+ "id integer primary key autoincrement,"
					+ "name text,"
					+ "type integer,"
					+ "alarm integer,"
					+ "measure integer,"
					+ "indamedikit real,"
					+ "capacity real"
					+ ");");
		}
		
		@Override
		public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
			
		}
	}

	public void onClick(View v) {
		
		ContentValues cv = new ContentValues();
		
		
		
		String name = etName.getText().toString();
		
		int SelRBType = RGType.getCheckedRadioButtonId();
		switch (SelRBType){
		case R.id.pillsSettings_elixirType_radioButton:
			type = 3;
			break;
		case R.id.pillsSettings_pillsType_radioButton:
			type = 1;
			break;
		case R.id.pillsSettings_syringeType_radioButton:
			type = 2;
			break;
		}
		 
		selChBAlarm = (CheckBox) findViewById(R.id.pillsSettings_Alarm_checkBox);
		if (selChBAlarm.isChecked()){
			alarm = 1;
		} else {
			alarm = 0;
		}
		Log.d(L, "--- Got every data ---");
		
		
		Log.d(L, "--- Asking for writable DB ---");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		
		switch (v.getId()){
		case R.id.pillsSettings_saveChange_button:
			// Сюда надо вставить проверку на то, какая кнопка показывается, а также делать поля изменяемыми или нет.
			// Еще надо сделать проверку заполненности и всплывающее предупреждение
			
			cv.put("name", name);
			cv.put("type", type);
			cv.put("alarm", alarm);
			cv.put("indamedikit", etIndaMedikit.getText().toString());
			cv.put("capacity", etCapacity.getText().toString());
			
			long rowID = db.insert("pills", null, cv);
			Log.d(L, "--- SaveChange pushed: name=" + name + " type=" + type + " alarm=" + alarm + " indmedikit=" + etIndaMedikit.getText().toString() + " capacity=" + etCapacity.getText().toString() + " " + " ---");
			break;
		case R.id.pillsSettings_delete_button:
			Log.d(L, "--- Delete button pushed: ---");
			db.execSQL("DROP TABLE pills");
			Log.d(L, "--- Table DROPED (deleted) ---");
				etName.setText("Table deleted");
				selChBAlarm.setChecked(false);
				RGType.clearCheck();
				
				etCapacity.setText("");
				etIndaMedikit.setText("");
			
			
		
			
		
		}
		
		dbHelper.close();
		
	}

}
