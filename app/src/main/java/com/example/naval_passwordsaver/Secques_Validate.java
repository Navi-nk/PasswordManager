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
import android.widget.EditText;
import android.widget.TextView;

public class Secques_Validate extends Activity implements OnClickListener {
	Button B1;
	EditText E1;
	String S,S1;
	Integer I;
	TextView T1;
	DatabaseHandler db=new DatabaseHandler(this);
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pwd_secques);
		B1=(Button) findViewById(R.id.B);
		E1=(EditText) findViewById(R.id.Et1);
		T1=(TextView) findViewById(R.id.T1);
		S1=db.dbgetsecques(PasswordSaver_Activity.S1);
		T1.setText(S1);
		B1.setOnClickListener(this);
		
	}
	public void onClick(View V) {
		switch (V.getId())
	{
	case (R.id.B):
		S=E1.getText().toString();
	    I=db.dbsecquesvalidate(S,PasswordSaver_Activity.S1);
	    if(I == 0)
	    {
	    	new AlertDialog.Builder(this)
		    .setTitle("Success")
		    .setMessage("Answer given is correct")
		    .setCancelable(false)
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        @Override
				public void onClick(DialogInterface dialog, int which) { 
		        	Intent i2=new Intent(Secques_Validate.this,Usrpwd_Change.class);
				    startActivity(i2);
				    finish();
		        }
		     })
		     .show();
	    }
	    else if(I == 1)
	    {
	    	new AlertDialog.Builder(this)
		    .setTitle("Error")
		    .setMessage("Answer given is Incorrect.Please Retry")
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        @Override
				public void onClick(DialogInterface dialog, int which) { 
		        }
		     })
		     .show();
	    }
	    else if(I == 2)
	    {
	    	new AlertDialog.Builder(this)
		    .setTitle("Error")
		    .setMessage("Database Error")
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        @Override
				public void onClick(DialogInterface dialog, int which) { 
		        }
		     })
		     .show();
	    }
	break;
	}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i=new Intent(Secques_Validate.this,Passwordmain_Navdrawer.class);
			startActivity(i);
			finish(); 
		}

		return super.onKeyDown(keyCode, event);
		}

}

