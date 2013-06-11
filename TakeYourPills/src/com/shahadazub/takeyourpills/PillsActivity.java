package com.shahadazub.takeyourpills;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PillsActivity extends Activity implements OnClickListener {
	
	Button btnAdd;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pills);
		
		btnAdd = (Button) findViewById(R.id.pills_new_button);
		btnAdd.setOnClickListener(this);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pills, menu);
		return true;
	}
	
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.pills_new_button:
			Intent intent = new Intent (this, PillsSettingsActivity.class);
			startActivity(intent);
			break;
		}
	}

}
