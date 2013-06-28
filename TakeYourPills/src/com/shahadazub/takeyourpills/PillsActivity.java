package com.shahadazub.takeyourpills;

import com.shahadazub.takeyourpills.PillsSettingsActivity.DBHelper;

import android.R.color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PillsActivity extends Activity implements OnClickListener {
	
	Button btnAdd, btnToMenu;
	
	DBHelper dbHelper;
	
	LinearLayout pillsScroll;
	
	Context context;
	
	int iconId, q = 1;
	
	final String L = "MyLog";
	
	
	@Override
	  protected void onStart() {
	    super.onStart();
	    Log.d(L, "MainActivity: onStart()");
	    
	    btnAdd = (Button) findViewById(R.id.pills_new_button);
		btnAdd.setOnClickListener(this);
		
		btnToMenu = (Button) findViewById(R.id.but_scheduleList_toMenu);
		btnToMenu.setOnClickListener(this);
		
		pillsScroll = (LinearLayout) findViewById(R.id.Pills_Scroll);
		pillsScroll.removeAllViews();
		
		Log.d(L, "--- Now ill try to open DB ---");
		
		dbHelper = new DBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("create table if not exists pills ("
					+ "id integer primary key autoincrement,"
					+ "name text,"
					+ "type integer,"
					+ "alarm integer,"
					+ "measure integer,"
					+ "indamedikit real,"
					+ "capacity real"
					+ ");");
		Log.d(L, "--- DB opened: ---");
		
		
		Cursor c = db.query("pills", null, null, null, null, null, null);
		Log.d(L, "--- Cursor created: ---");
		
		c.moveToFirst();
		
		Log.d(L, "--- Cursor moved to the first row and there are " + c.getCount() + "rows ---");
		
		for (int i = 1; i < (c.getCount() + 1); i++) {
			Log.d(L, "--- Iteration" + i + "---");
			
			RelativeLayout.LayoutParams rlPillsRowParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			
			RelativeLayout rlPillsRow = new RelativeLayout(this);
			rlPillsRow.setLayoutParams(rlPillsRowParams);
			rlPillsRow.setClickable(true);
			rlPillsRow.setOnClickListener(this);
			rlPillsRow.setId(i);
			
			pillsScroll.addView(rlPillsRow);
			
			
			RelativeLayout.LayoutParams tvNameParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
			tvNameParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			tvNameParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			
			TextView tvName = new TextView (this);
			tvName.setText(c.getString(c.getColumnIndex("name")) + " " + c.getString(c.getColumnIndex("capacity")) + " " + c.getString(c.getColumnIndex("measure")));
			tvName.setLayoutParams(tvNameParams);
			tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
			
			
			rlPillsRow.addView(tvName);
			
			RelativeLayout.LayoutParams tvIndamedikitParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			tvIndamedikitParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			tvIndamedikitParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			
			TextView tvIndamedikit = new TextView (this);
			tvIndamedikit.setLayoutParams(tvIndamedikitParams);
			tvIndamedikit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			tvIndamedikit.setText(getString(R.string.Pills_Availability_TextView) + " " + c.getLong(c.getColumnIndex("indamedikit")) + " " );
			
						
			rlPillsRow.addView(tvIndamedikit);
			
			
			switch (c.getInt(c.getColumnIndex("type"))) {
			case 1:
				iconId = R.drawable.ic_pills;
				break;
			case 2:
				iconId = R.drawable.ic_syringe;
				break;
			case 3:
				iconId = R.drawable.ic_elixir;	
				break;
			default:
				break;			
			}
			
			RelativeLayout.LayoutParams ivIconParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			ivIconParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
			ivIconParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
			
			ImageView ivIcon = new ImageView(this);
			ivIcon.setLayoutParams(ivIconParams);
			ivIcon.setImageResource(iconId);
			
			rlPillsRow.addView(ivIcon);
						
			
			
			
			
			c.moveToNext();
		}
		dbHelper.close();
	  }

	  @Override
	  protected void onResume() {
	    super.onResume();
	    Log.d(L, "MainActivity: onResume()");
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    Log.d(L, "MainActivity: onPause()");
	  }

	  @Override
	  protected void onStop() {
	    super.onStop();
	    Log.d(L, "MainActivity: onStop()");
	  }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pills);
		Log.d(L, "--- PillsActivity layout started ---");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pills, menu);
		
			
		return true;
	}
	
	public void onClick(View v) {
		
		Intent intent;
		switch (v.getId()){
			case R.id.pills_new_button:
				Log.d(L, "--- NewBut pushed ---");
				intent = new Intent (this, PillsSettingsActivity.class);
				intent.putExtra("string", 0);			
				break; 
			case R.id.but_scheduleList_toMenu:
				Log.d(L, "--- ToMenuBut pushed ---");
				intent = new Intent (this, MainActivity.class);
				break;
			default:
				Log.d(L, "--- LayoutRow ZERO pushed ---");
				intent = new Intent (this, PillsSettingsActivity.class);
				intent.putExtra("string", v.getId());
				break;
			}
		startActivity(intent);			
	}
	
	
	
	
	
	
	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, "myDB", null, 1);
			Log.d(L, "--- Just DBHelper (context context) ---");
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(L, "--- Creating DB with table pills: ---");
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

}
