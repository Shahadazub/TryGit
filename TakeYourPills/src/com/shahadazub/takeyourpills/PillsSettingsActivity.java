package com.shahadazub.takeyourpills;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PillsSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pills_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pills_settings, menu);
		return true;
	}

}
