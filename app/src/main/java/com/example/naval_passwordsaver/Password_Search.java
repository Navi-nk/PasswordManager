package com.example.naval_passwordsaver;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Password_Search extends Activity implements OnClickListener{
	Button B1;
	EditText E1;
	static String S;
	static List<PwdDetails> PD=new ArrayList<PwdDetails>();
	DatabaseHandler db=new DatabaseHandler(this);
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pwd_change);
		E1=(EditText) findViewById(R.id.eT1);
		B1=(Button) findViewById(R.id.b1);
		B1.setOnClickListener(this);
	}
	@Override
	public void onClick(View V) {
		// TODO Auto-generated method stub
		switch (V.getId())
		{
		case (R.id.b1):
		S=E1.getText().toString();
		PD=db.dbgetclickedpwddetails1(S,PasswordSaver_Activity.S1);
		Integer a=pwd_validate(PD);
		if(a == 0)
        {
	    Intent i=new Intent(Password_Search.this,Password_Search_List.class);
     	startActivity(i);
     	finish();
        }
		break;
		}
	}
	public Integer pwd_validate(List<PwdDetails> PD)
	{
		Integer I;
		I=PD.size();
		Log.d("Inside validate:I", String.valueOf(I));
		Log.d("pd", PD.get(0).getName());
		if(I==1 && PD.get(0).getName()=="Record not found")
		{
		new AlertDialog.Builder(this)
	    .setTitle("Search Failed")
	    .setMessage("\t\t\tNo Record was found\n\t\t that match your search")
	    .setCancelable(false)
	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        @Override
			public void onClick(DialogInterface dialog, int which) {   
	        	Intent i1=new Intent(Password_Search.this,Password_Search.class);
	         	startActivity(i1);
	         	finish();	
	        }
	     })
	     .show();
		}
		else
		{
			I=0;
		}
		Log.d("Inside validate before return:I", String.valueOf(I));
		return I;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i3=new Intent(this,Passwordmain_Navdrawer.class);
			startActivity(i3);
			finish(); 
		}

		return super.onKeyDown(keyCode, event);
		}

}
