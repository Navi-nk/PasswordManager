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

public class Pwd_Delete1 extends Activity implements OnClickListener{
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
	    Intent i=new Intent(Pwd_Delete1.this,Pwd_Delete2.class);
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
	        	Intent i1=new Intent(Pwd_Delete1.this,Pwd_Delete2.class);
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

	
	/*Button B1;
	EditText E1;
	String S;
	PwdDetails PD;
	static String S1;
	static String S2;
	static String s="DUMMY";
	DatabaseHandler db5=new DatabaseHandler(this);
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pwd_change);
		E1=(EditText) findViewById(R.id.eT1);
		B1=(Button) findViewById(R.id.b1);
		B1.setOnClickListener(this);
	}
	public void onClick(View V) {
		Integer a;
		switch (V.getId())
		{
		case (R.id.b1):
		S=E1.getText().toString();
		PD=db5.dbgetclickedpwddetails(S,PasswordSaver_Activity.S1);
        a=pwd_validate(PD);
        if(a == 0)
        {
        	S1=PD.getID();
        	S2=PD.getPwd();
        	new AlertDialog.Builder(this)
    	    .setTitle("Delete "+ S1)
    	    .setMessage("Are you sure?")
    	    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
    	        @Override
    			public void onClick(DialogInterface dialog, int which) {
    	        	s=db5.dbDeletePWD(S1,PasswordSaver_Activity.S1);
    	        	Log.d("Print s", s);
    	        	if(s == "SUCCESS")
    	        	{
    	        		alertbox1();
    	        	}
    	        	else if(s == "FAILURE")
    	        	{
    	        		alertbox2();
    	        	}
    	        }
    	     })
    	     .setNegativeButton("NO",
		   new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int id) {
		   }
		   })
    	   .show();
        }	
        
        
        break;
		}
	}
	public Integer pwd_validate(PwdDetails PD)
	{
		String s1;
		s1=PD.getPwd();
		Integer I=1;
		if(s1.equals("Record not found"))
		{
		new AlertDialog.Builder(this)
	    .setTitle("Search Failed")
	    .setMessage("No Record was found that match your search")
	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        @Override
			public void onClick(DialogInterface dialog, int which) {      	
	        }
	     })
	     .show();
		}
		else
		{
			I=0;
		}
		return I;
	}
	public void alertbox1()
	{
		new AlertDialog.Builder(this)
	    .setTitle("")
	    .setMessage("Password deleted successfully.")
	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        @Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent i3=new Intent(Pwd_Delete1.this,Passwordmain_Navdrawer.class);
				startActivity(i3);
				finish(); 

	        }
	     })
	     .show();

	}
	public void alertbox2()
	{	
	//Log.d("Inside Delete1","s");
	new AlertDialog.Builder(this)
	    .setTitle("Error")
	    .setMessage("Database error")
	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        @Override
			public void onClick(DialogInterface dialog, int which) { 
	            
	        }
	     })
	     .show();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i3=new Intent(Pwd_Delete1.this,Passwordmain_Navdrawer.class);
			startActivity(i3);
			finish(); 
		}

		return super.onKeyDown(keyCode, event);
		}
*/
}

