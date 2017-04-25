package com.example.naval_passwordsaver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup_form extends Activity implements OnClickListener{
	Button B1;
	EditText E1;
	EditText E2;
	EditText E3;
	EditText E4;
	EditText E5;
	EditText E6;
	String S1;
	String S2;
	String S3;
	String S4;
	String S5;
	String S6;
	String S7;
	TextView textView;
	DatabaseHandler db=new DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_page);
		
	     B1 = (Button) findViewById(R.id.C1);
	     E1=(EditText) findViewById(R.id.ET1);
	     E2=(EditText) findViewById(R.id.ET2);
	     E3=(EditText) findViewById(R.id.ET3);
	     E4=(EditText) findViewById(R.id.ET4);
	     E5=(EditText) findViewById(R.id.ET5);
	     E6=(EditText) findViewById(R.id.ET6);
	     TextView T1=(TextView) findViewById(R.id.textView2);
	     String string=":<font color=#cc0029>*</font>";
	     T1.append(Html.fromHtml(string));
	     
	     TextView T2=(TextView) findViewById(R.id.textView3);
	     String string1=":<font color=#cc0029>*</font>";
	     T2.append(Html.fromHtml(string1));
	     
	     TextView T3=(TextView) findViewById(R.id.textView4);
	     String string3=":<font color=#cc0029>*</font>";
	     T3.append(Html.fromHtml(string3));
	     
	     TextView T4=(TextView) findViewById(R.id.textView5);
	     String string4=":<font color=#cc0029>*</font>";
	     T4.append(Html.fromHtml(string4));
	     
	     TextView T5=(TextView) findViewById(R.id.textView6);
	     String string5=":<font color=#cc0029>*</font>";
	     T5.append(Html.fromHtml(string5));
	     
	     TextView T6=(TextView) findViewById(R.id.textView7);
	     String string6=":<font color=#cc0029>*</font>";
	     T6.append(Html.fromHtml(string6));
	    // textView.setError("Fields can't be blank");
	     B1.setOnClickListener(this);
	     
	}
	@Override
	public void onClick(View V) {
		switch (V.getId())
		{
		case (R.id.C1):
			 S1=E1.getText().toString();
	         S2=E2.getText().toString();
	         S3=E3.getText().toString();
	         S4=E4.getText().toString();
	         S5=E5.getText().toString();
	         S6=E6.getText().toString();
	         if(S1.matches("") || S2.matches("")||S3.matches("")||S4.matches("")||S5.matches("")||S6.matches(""))
	         {
	        	 Toast.makeText(this,"All Input Fields are mandatory", Toast.LENGTH_SHORT).show();
	        	 return;
	         }
	         
	         else
	        {
			if(S3.equals(S4))
			{
			S5=db.dbpasswordsend(new PwdDetails(S2,S1,S3,S5,S6));
			if(S5.equals("SUCCESS"))
			{
				new AlertDialog.Builder(this)
			    .setTitle("")
			    .setMessage("User added successfully")
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        @Override
					public void onClick(DialogInterface dialog, int which) { 
			        	Intent i=new Intent(Signup_form.this,PasswordSaver_Activity.class);
					    startActivity(i);
					    finish();
			        }
			      
			     })
			     .setCancelable(false)
			     .show();
			
			}
			else
			{
				new AlertDialog.Builder(this)
			    .setTitle("Error")
			    .setMessage("User ID Already Present.Please use any other.")
			    .setCancelable(false)
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
				new AlertDialog.Builder(this)
			    .setTitle("Error")
			    .setMessage("Password doesn't match! Retry.")
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
		}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			   Intent i=new Intent(this,MainPage.class);
		       startActivity(i);
		       finish();		    	   
		}

		return super.onKeyDown(keyCode, event);
		}
	}
