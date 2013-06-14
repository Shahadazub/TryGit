package com.shahadazub.takeyourpills;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button btnPills, btnAid, btnSchedule;
	Intent intent;
	
	final String L = "MyLog";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnPills = (Button) findViewById(R.id.Main_Pills_Button);
		btnPills.setOnClickListener(this);
	}
	
	
	public void onClick(View v){
		Log.d(L, "--- Button pushed ---");
		switch (v.getId()){
		case R.id.Main_Pills_Button:
			intent = new Intent (this, PillsActivity.class);
			startActivity(intent);
			break;
		default:
			break;
			
		
		}
		
	}

}
