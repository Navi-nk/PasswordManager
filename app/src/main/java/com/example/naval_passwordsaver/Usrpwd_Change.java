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
import android.widget.Toast;


public class Usrpwd_Change extends Activity implements OnClickListener {
	
	TextView T1;
	EditText E1;
	EditText E2;
	EditText E3;
	Button B1;
	static String S1,S2,S3,S4,S5;
	Integer I,I1;
	DatabaseHandler db=new DatabaseHandler(this);
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userpwd_change);
		B1=(Button) findViewById(R.id.button1);
		E1=(EditText) findViewById(R.id.eT1);
		E2=(EditText) findViewById(R.id.eT2);
		E3=(EditText) findViewById(R.id.eT3);
		
		T1=(TextView) findViewById(R.id.T1);
		S1=db.dbgetusrid(PasswordSaver_Activity.S1);
		T1.setText(S1);
		B1.setOnClickListener(this);
		
	}
	
	public void onClick(View V) {
		switch (V.getId())
	{
	case (R.id.button1):
		S2=E1.getText().toString();
		I=db.dbvalidateoldpwd(S2,PasswordSaver_Activity.S1);
		if(I==0)
		{
			S3=E2.getText().toString();
			S4=E3.getText().toString();
			if(S3.equals(S4))
			{
				if(!(S2.equals(S3)))
				{
				S5=db.dbchangepwd(S1,S3);
				if(S5.equals("SUCCESS"))
				{
					new AlertDialog.Builder(this)
				    .setTitle("")
				    .setMessage("Password updated successfully.\nLogin again with new password")
				    .setCancelable(false)
				    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				        @Override
						public void onClick(DialogInterface dialog, int which) { 
				        	Intent i=new Intent(Usrpwd_Change.this,MainPage.class);
						    startActivity(i);
						    finish();
				        }
				     })
				     .show();
				
				}
				else
				{
				new AlertDialog.Builder(this)
			    .setTitle("Error")
			    .setMessage("Database error! Please Contact Navi!")
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        @Override
					public void onClick(DialogInterface dialog, int which) {         
			        }
			     })
			     .show();

				}
			  }
				else
				{
					Toast.makeText(this,"\tEntered and Saved Passwords are same.\t\t\t\tPlease use different Password", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				new AlertDialog.Builder(this)
			    .setTitle("Error")
			    .setMessage("Passwords doesn't match! Please Retry.")
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        @Override
					public void onClick(DialogInterface dialog, int which) { 
			        }
			     })
			     .show();
			}
		}
		else if(I == 1)
		{
			new AlertDialog.Builder(this)
		    .setTitle("Error")
		    .setMessage("Old Password entered is wrong! Please Retry.")
		    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        @Override
				public void onClick(DialogInterface dialog, int which) { 
		        }
		     })
		     .show();
		}
		else
		{
			new AlertDialog.Builder(this)
		    .setTitle("Error")
		    .setMessage("Database error! Please Contact Navi!")
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
			   Intent i=new Intent(this,Passwordmain_Navdrawer.class);
		       startActivity(i);
		       finish();		    	   
		}

		return super.onKeyDown(keyCode, event);
		}

}