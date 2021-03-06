package com.shahadazub.takeyourpills;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class PillsSettingsActivity extends Activity implements OnClickListener {
	
	Button btnSaveChange, btnDelete;
	EditText etName, etIndaMedikit, etCapacity;
	TextView tvIndamedikit, tvCapacity;
	RadioGroup RGType;
	CheckBox chBAlarm;
	Cursor c;
	Spinner spMeasure;
	Context context;
	
	int type, alarm, rowNumber;
	
	String name;
	
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
		
		tvIndamedikit = (TextView) findViewById(R.id.pills_settings_indamedikit_textView);
		tvCapacity = (TextView) findViewById(R.id.pills_settings_capacity_textView);
		
		spMeasure = (Spinner) findViewById(R.id.spinner1);
		spMeasure.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				changeAllTexts();				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}			
		});
		
		
		RGType = (RadioGroup) findViewById(R.id.pillsSettings_Type_radioGroup);
		RGType.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				changeAllTexts();
			}
		});
		
		chBAlarm = (CheckBox) findViewById(R.id.pillsSettings_Alarm_checkBox);
		
		
		changeAllTexts();
		
		dbHelper = new DBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Log.d(L, "--- Everything created ---");
		
		//db.execSQL("drop table pills");
		
		Intent intent = getIntent();
		rowNumber = intent.getExtras().getInt("string");
		Log.d(L, "--- String from intent is: " + rowNumber + " ---");
		
		if (rowNumber == 0) {
			btnSaveChange.setText(R.string.save_activity_pills_settings);
			btnDelete.setText(R.string.cancel_activity_pills_settings);
		} else {
			btnSaveChange.setText(R.string.save_activity_pills_settings);
			btnDelete.setText(R.string.delete_activity_pills_settings);
			
			c = db.query("pills", null, null, null, null, null, null);
			c.moveToPosition(rowNumber - 1);
			
			etName.setText(c.getString(c.getColumnIndex("name")));
			etIndaMedikit.setText(c.getString(c.getColumnIndex("indamedikit")));
			etCapacity.setText(c.getString(c.getColumnIndex("capacity")));
			
			if (c.getInt(c.getColumnIndex("alarm")) == 1) {
				chBAlarm.setChecked(true);
			}else {
				chBAlarm.setChecked(false);
			}
			
			switch (c.getInt(c.getColumnIndex("type"))) {			
				case 1:
					RadioButton rg1 = (RadioButton) findViewById(R.id.pillsSettings_pillsType_radioButton);
					rg1.setChecked(true);
					break;
				case 2:
					RadioButton rg2 = (RadioButton) findViewById(R.id.pillsSettings_syringeType_radioButton);
					rg2.setChecked(true);
					break;
				case 3:
					RadioButton rg3 = (RadioButton) findViewById(R.id.pillsSettings_elixirType_radioButton);
					rg3.setChecked(true);
					break;
				default:
					break;			
			}
		}
		
		
		
		
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
					+ "measure text,"
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
		name = etName.getText().toString();
				
		if (chBAlarm.isChecked()){
			alarm = 1;
		} else {
			alarm = 0;
		}
		Log.d(L, "--- Got every data ---");
		
		
		Log.d(L, "--- Asking for writable DB ---");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Log.d(L, "--- DB geted ---");
		Intent newIntent = new Intent(this, PillsActivity.class);
		Log.d(L, "--- Intent seted ---");
		switch (v.getId()){
			case R.id.pillsSettings_saveChange_button:			
				cv.put("name", name);
				cv.put("type", type);
				cv.put("alarm", alarm);
				cv.put("indamedikit", etIndaMedikit.getText().toString());
				cv.put("capacity", etCapacity.getText().toString());
				cv.put("measure", spMeasure.getSelectedItem().toString());
				Log.d(L, "--- Every data puted into CV ---");
				
				if (rowNumber == 0) {	
					long rowID = db.insert("pills", null, cv);
					Log.d(L, "--- Save pushed: name=" + name + " type=" + type + " alarm=" + alarm + " indmedikit=" + etIndaMedikit.getText().toString() + " capacity=" + etCapacity.getText().toString() + " " + " ---");
				} else {
					db.update("pills", cv, "name = '" + c.getString(c.getColumnIndex("name")) + "'", null);
					Log.d(L, "--- Change pushed: name=" + name + " type=" + type + " alarm=" + alarm + " indmedikit=" + etIndaMedikit.getText().toString() + " capacity=" + etCapacity.getText().toString() + " " + " ---");
				}
				break;
				
			case R.id.pillsSettings_delete_button:
				
				
				if (rowNumber == 0) {
					Log.d(L, "--- Cancel button pushed: ---");
				}else {
					Log.d(L, "--- Delete button pushed: ---");
					db.delete("pills", "name = '" + c.getString(c.getColumnIndex("name")) + "'", null);
				}
				
				break;		
		}
		
		dbHelper.close();
		
		startActivity(newIntent);
		
	}
	
	public void changeAllTexts(){
		int SelRBType = RGType.getCheckedRadioButtonId();
		switch (SelRBType){
		case R.id.pillsSettings_pillsType_radioButton:
			type = 1;
			tvIndamedikit.setText(getString(R.string.inda_medikit_pills_settings) + " " + getString(R.string.pillsSettings_indamedikitPills) + " ");
			tvCapacity.setText(getString(R.string.pills_settings_capacity) + " " + getString(R.string.pillsSettings_capacityPills) + " " + spMeasure.getSelectedItem().toString() + " ");
			break;
		case R.id.pillsSettings_syringeType_radioButton:
			type = 2;
			tvIndamedikit.setText(getString(R.string.inda_medikit_pills_settings) + " " + getString(R.string.pillsSettings_indamedikitSyringeAndElixir) + " ");
			tvCapacity.setText(getString(R.string.pills_settings_capacity) + " " + getString(R.string.pillsSettings_capacitySyringeAndElixir) + " " + spMeasure.getSelectedItem().toString() + " ");
			break;
		case R.id.pillsSettings_elixirType_radioButton:
			type = 3;
			tvIndamedikit.setText(getString(R.string.inda_medikit_pills_settings) + " " + getString(R.string.pillsSettings_indamedikitSyringeAndElixir) + " ");
			tvCapacity.setText(getString(R.string.pills_settings_capacity) + " " + getString(R.string.pillsSettings_capacitySyringeAndElixir) + " " + spMeasure.getSelectedItem().toString() + " ");
			break;
		}
	}

}


