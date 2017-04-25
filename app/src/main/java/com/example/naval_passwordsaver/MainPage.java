package com.example.naval_passwordsaver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainPage extends Activity implements OnClickListener{
	
	Button B1;
	Button B2;
	Button B3;
	Integer I1;
	Integer I2=0;
	DatabaseHandler db1=new DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.passwordsaver_secondpage);
	
	     B1 = (Button) findViewById(R.id.signin);
	     B2=(Button) findViewById(R.id.signup);
	     B3=(Button) findViewById(R.id.button1);
	     B1.setOnClickListener(this);
	     B2.setOnClickListener(this);
	     B3.setOnClickListener(this);
	}
	@Override
	public void onClick(View V) {
		switch (V.getId())
		{
		case(R.id.button1):
			Intent i2=new Intent(MainPage.this,About.class);
	    startActivity(i2);
	    finish();
			break;
		case (R.id.signin):
			Intent i=new Intent(MainPage.this,PasswordSaver_Activity.class);
		    startActivity(i);
		    finish();
			break;
		case (R.id.signup):
			//I1=db1.dbUserCheck();
		//if(I1 == I2)
		//{
			Intent i1=new Intent(MainPage.this,Signup_form.class);
	        startActivity(i1);
	        finish();
		//}
	/*	else
		{
			new AlertDialog.Builder(this)
		    .setTitle("Error")
		    .setMessage("The device is already registered by a user.Please Login with correct credentials")
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        @Override
				public void onClick(DialogInterface dialog, int which) { 
		        	Intent i2=new Intent(MainPage.this,PasswordSaver_Activity.class);
				    startActivity(i2);
				    finish();
		        }
		     })
		     .show();
			
		}*/    
			break;
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			new AlertDialog.Builder(this)
		    .setTitle("Exit")
		    .setMessage("Are you sure?")
		    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
		        @Override
				public void onClick(DialogInterface dialog, int which) { 
		        	finish();
		        }
		     })
		     .setNegativeButton("NO",
        		   new DialogInterface.OnClickListener() {
        		   public void onClick(DialogInterface dialog, int id) {
        		   }
        		   })
		     .show();
		       		    	   
		}

		return super.onKeyDown(keyCode, event);
		}
}