package com.example.naval_passwordsaver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class About extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i2=new Intent(About.this,MainPage.class);
		    startActivity(i2);
		    finish();
		       		    	   
		}
		return super.onKeyDown(keyCode, event);
	}
}
