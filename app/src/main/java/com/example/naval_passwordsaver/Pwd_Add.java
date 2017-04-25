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

public class Pwd_Add extends Activity implements OnClickListener{

	Button B1;
	EditText E1;
	EditText E2;
	EditText E3;
	EditText E4;
	String S1;
	PwdDetails PD;
	String S2;
	String S3;
	String S4;
	String S5;
	DatabaseHandler db4=new DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pwd_add_page);
		B1=(Button) findViewById(R.id.b1);
		E1=(EditText) findViewById(R.id.eT1);
		E2=(EditText) findViewById(R.id.eT2);
		E3=(EditText) findViewById(R.id.eT3);
		E4=(EditText) findViewById(R.id.eT4);
		
		TextView T1=(TextView) findViewById(R.id.textView2);
	   // String E=getText(R.id.textView2).toString();
	    String string="<font color=#cc0029>*</font>";
	    T1.append(Html.fromHtml(string));
	    
	    TextView T2=(TextView) findViewById(R.id.textView3);
	    //String E1=getText(R.id.textView3).toString();
	    String string1="<font color=#cc0029>*</font>";
	    T2.append(Html.fromHtml(string1));
	     
	    TextView T3=(TextView) findViewById(R.id.textView4);
       // String E3=getText(R.id.textView4).toString();
        String string3="<font color=#cc0029>*</font>";
        T3.append(Html.fromHtml(string3));
	     
        TextView T4=(TextView) findViewById(R.id.textView5);
        //String E4=getText(R.id.textView5).toString();
        String string4="<font color=#cc0029>*</font>";
        T4.append(Html.fromHtml(string4));
	     
		B1.setOnClickListener(this);
}
	public void onClick(View V) {
		switch (V.getId())
		{
		case (R.id.b1):
			S1=E1.getText().toString();
     	    S2=E2.getText().toString();
     	    S3=E3.getText().toString();
     	    S4=E4.getText().toString();
     	   /*if((S1.matches("")) && !(S2.matches("")) && !(S3.matches("")) && !(S4.matches("")))
	       {
	        	 Toast.makeText(this,"Link/Profile name is mandatory", Toast.LENGTH_SHORT).show();
	        	 return;
	       }
     	   else if(!(S1.matches("")) && (S2.matches("")) && !(S3.matches("")) && !(S4.matches("")))
     	   {
     		  Toast.makeText(this,"Link User ID is mandatory", Toast.LENGTH_SHORT).show();
	        	 return;
     	   }
     	   else if(!(S1.matches("")) && !(S2.matches("")) && (S3.matches("")) && !(S4.matches("")))
     	   {
     		  Toast.makeText(this,"Password is mandatory", Toast.LENGTH_SHORT).show();
	        	 return;
     	   }
     	   else if(!(S1.matches("")) && !(S2.matches("")) && !(S3.matches("")) && (S4.matches("")))
     	   {
     		  Toast.makeText(this,"Confirm Password is mandatory", Toast.LENGTH_SHORT).show();
	        	 return;
     	   }
	       else if()
	       {
	        	 Toast.makeText(this,"All Fields are mandatory", Toast.LENGTH_SHORT).show();
	        	 return;
	       }*/
     	   if(S1.matches("") || S2.matches("")||S3.matches("")||S4.matches(""))
	         {
	        	 Toast.makeText(this,"All Input Fields are mandatory", Toast.LENGTH_SHORT).show();
	        	 return;
	         }
	         
	         else
	        {

     	   if(S2.equals(S3))
			{
     		  //db4.deletePwd("Twitter");
     		  S5=db4.dbPwdLinkCreate(new PwdDetails(PasswordSaver_Activity.S1,S1,S2,S4));
     		 if(S5.equals("SUCCESS"))
 			{
 				new AlertDialog.Builder(this)
 			    .setTitle("")
 			    .setMessage("Password added successfully")
 			    .setCancelable(false)
 			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
 			        @Override
 					public void onClick(DialogInterface dialog, int which) { 
 			        	Intent i=new Intent(Pwd_Add.this,Passwordmain_Navdrawer.class);
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
 			    .setMessage("Database error")
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
			    .setMessage("Password doesn't match. Please Retry")
			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			        @Override
					public void onClick(DialogInterface dialog, int which) { 
			            
			        }
			     })
			     .show();
     	   }
      
		}
		}	   
   }
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i2=new Intent(Pwd_Add.this,Passwordmain_Navdrawer.class);
			    startActivity(i2);
			    finish(); 
		}

		return super.onKeyDown(keyCode, event);
		}
}
