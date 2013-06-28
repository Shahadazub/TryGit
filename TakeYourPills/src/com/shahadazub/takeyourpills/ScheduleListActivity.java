package com.shahadazub.takeyourpills;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScheduleListActivity extends Activity implements OnClickListener {
	
	Button btnAdd;
	
	DBHelper dbHelper;
	
	LinearLayout scrollList;
	
	Context context;
	
	final String L = "MyLog";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_list);
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
	    Log.d(L, "ScheduleListActivity: onStart()");
	    
	    btnAdd = (Button) findViewById(R.id.but_schedule_new);
	    btnAdd.setOnClickListener(this);
	    
	    Button btnToMenu = (Button) findViewById(R.id.but_scheduleList_toMenu);
	    btnToMenu.setOnClickListener(this);
	    
	    dbHelper = new DBHelper(this);
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    db.execSQL("create table if not exists schedules ("
					+ "id integer primary key autoincrement,"
					+ "name text,"
					+ "enfermedadNumber integer,"
					+ "enfermedadName text"
					+ ");");
	    Log.d(L, "--- DB opened: ---");
	    
	    
	    Cursor c = db.query("schedules", null, null, null, null, null, null);
	    Log.d(L, "--- Cursor created: ---");
	    
	    c.moveToFirst();	    
	    Log.d(L, "--- Cursor moved to the first row and there are " + c.getCount() + "rows ---");
	    
	    
	    for (int i = 0; i < (c.getCount()); i++) {
			Log.d(L, "--- Iteration" + i + "---");
	    
	    
	    
		    RelativeLayout.LayoutParams rlRowParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			
			RelativeLayout rlRow = new RelativeLayout(this);
			rlRow.setLayoutParams(rlRowParams);
			rlRow.setClickable(true);
			rlRow.setOnClickListener(this);
			rlRow.setId(i);
			
			scrollList.addView(rlRow);
			
			
			RelativeLayout.LayoutParams tvNameParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
			tvNameParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			tvNameParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			
			TextView tvName = new TextView (this);
			tvName.setText(c.getString(c.getColumnIndex("name")));
			tvName.setLayoutParams(tvNameParams);
			tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
			
			
			rlRow.addView(tvName);
			
			RelativeLayout.LayoutParams tvUnderNameParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			tvUnderNameParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			tvUnderNameParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			
			TextView tvIndamedikit = new TextView (this);
			tvIndamedikit.setLayoutParams(tvUnderNameParams);
			tvIndamedikit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			tvIndamedikit.setText(getString(R.string.Pills_Availability_TextView) + " " + c.getLong(c.getColumnIndex("indamedikit")) + " " );
			
						
			rlRow.addView(tvIndamedikit);
				
			RelativeLayout.LayoutParams ivIconParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			ivIconParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
			ivIconParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
			
			ImageView ivIcon = new ImageView(this);
			ivIcon.setLayoutParams(ivIconParams);
						
			rlRow.addView(ivIcon);
						
			
			c.moveToNext();
		}
		dbHelper.close();   
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule_list, menu);
		return true;
	}

	
	
	
	
	
	
	
	
	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, "myDB", null, 1);
			Log.d(L, "--- Just DBHelper (context context) ---");
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(L, "--- Creating DB with table pills: ---");
			db.execSQL("create table schedules ("
					+ "id integer primary key autoincrement,"
					+ "name text,"
					+ "enfermedadNumber integer,"
					+ "enfermedadName text"
					+ ");");
		}
		
		
		@Override
		public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
			
		}
		
		
	}









	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
			case R.id.but_schedule_new:
				
				break;
			case R.id.but_scheduleList_toMenu:
				intent = new Intent(this, MainActivity.class);
				break;
	
			default:
				break;
		}
		
		startActivity(intent);
		
	}

}
