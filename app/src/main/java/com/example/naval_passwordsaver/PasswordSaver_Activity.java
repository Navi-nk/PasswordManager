package com.example.naval_passwordsaver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordSaver_Activity extends Activity implements OnClickListener{
	EditText E1;
	EditText E2;
	Button B1;
	static String S1;
	String S2;
	String S3;
	String val;
	DatabaseHandler db1=new DatabaseHandler(this);
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_saver);
		E1 = (EditText) findViewById(R.id.Usr);
		 E2 = (EditText) findViewById(R.id.Pwd);
		 TextView T1=(TextView) findViewById(R.id.textView1);
	     String string="<font color=#cc0029>*</font>";
	     T1.append(Html.fromHtml(string));
	     TextView T2=(TextView) findViewById(R.id.textView2);
	     String string1="<font color=#cc0029>*</font>";
	     T2.append(Html.fromHtml(string1));
	     B1 = (Button) findViewById(R.id.b1);
	     B1.setOnClickListener(this);
	}
	@Override
	public void onClick(View V) {
		switch (V.getId())
		{
		case (R.id.b1):
			S1=E1.getText().toString();
	     	S2=E2.getText().toString();
	     	if(S1.equals("")||S2.equals(""))
	     	{
	     		Toast.makeText(this,"\tInput Fields cannot be empty", Toast.LENGTH_SHORT).show();
	     	}
	     	else
	     	{
	     	val=db1.dbpasswordvalidate(S1,S2);
	     	if(val == "Success")
	     	{  // db1.SetSid(S1,1);
	     		Intent i=new Intent(PasswordSaver_Activity.this,Passwordmain_Navdrawer.class);
			    startActivity(i);
			    finish();
	     	}
	     	else if(val == "Failure")
	     	{
	     		new AlertDialog.Builder(this)
			    .setTitle("Error")
			    .setMessage("\tInvalid User ID or Password! \n\t\t\t\t\t  Please retry.")
			    .setCancelable(false)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        @Override
					public void onClick(DialogInterface dialog, int which) { 
			        	Intent i=new Intent(PasswordSaver_Activity.this,PasswordSaver_Activity.class);
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
			    .setMessage("\t\t\tUser Id doesn't exist.")
			    .setCancelable(false)
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        @Override
					public void onClick(DialogInterface dialog, int which) { 
			        	Intent i=new Intent(PasswordSaver_Activity.this,PasswordSaver_Activity.class);
					       startActivity(i);
					       finish();
			        }
			     })
			     .show();
	     	}
			break;
		}   
	  }
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.password_saver_, menu);
		return true;
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
