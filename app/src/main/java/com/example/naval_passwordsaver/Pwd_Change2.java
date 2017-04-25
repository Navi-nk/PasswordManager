package com.example.naval_passwordsaver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

public class Pwd_Change2 extends Activity {
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	DatabaseHandler db=new DatabaseHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pwd_expand_list);

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lE);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				final String S=listDataHeader.get(groupPosition);
				if(groupPosition==0 && listDataHeader.get(groupPosition)=="Record not found")
				{
					new AlertDialog.Builder(Pwd_Change2.this)
		    	    .setTitle("No Details Found")
		    	    .setMessage("Please add new password details")
		    	    .setCancelable(false)
		    	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    	        @Override
		    			public void onClick(DialogInterface dialog, int which) { 
		    	        	Intent i=new Intent(Pwd_Change2.this,Passwordmain_Navdrawer.class);
		    			       startActivity(i);
		    			       finish();	
		    	        }
		    	     })
		    	     .show();
				}
				else
				{
					LayoutInflater factory = LayoutInflater.from(Pwd_Change2.this);
					final View PopuptextEntryView = factory.inflate(R.layout.pwd_change_alertview,null);
					AlertDialog.Builder Popup= new AlertDialog.Builder(Pwd_Change2.this);
		    	    Popup.setTitle("Change Password:"+listDataHeader.get(groupPosition));
		    	    Popup.setView(PopuptextEntryView);
		    	    Popup.setCancelable(false);
		    	    final EditText ipPwd1=(EditText)PopuptextEntryView.findViewById(R.id.eT1);
		    	    final EditText ipPwd2=(EditText)PopuptextEntryView.findViewById(R.id.eT2);
		    	    Popup.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    	        @Override
		    			public void onClick(DialogInterface dialog, int which) { 
		    	        	String S1=ipPwd1.getText().toString();
		    	        	String S2=ipPwd2.getText().toString();
		    	        	if(S1.equals("") || S2.equals(""))
		    	        	 {
		    	        			showToast("Password Fields can't be null");
		    			    }
		    	        	else
		    	        	{
		    	        		if(!(S1.equals(S2)))
		    	        		{
		    	        			showToast("Password Fields doesn't match");
		    	        		}
		    	        		else
		    	        		{
		    	        			if(db.dbCheckPwd(S, S2,PasswordSaver_Activity.S1))
		    	        			{
		    	        				showToast("\tEntered and Saved Passwords are same.\t\t\t\tPlease use different Password");
		    	        			}
		    	        			else
		    	        			{
		    	        			String S3=db.dbPwdLinkUpdate(new PwdDetails(S, S2,PasswordSaver_Activity.S1));
		    	        			if(S3.equals("SUCCESS"))
		    	        			{
		    	        				showToast("Password updated successfully");
		    	        			}
		    	        			else
		    	        			{
		    	        				showToast("Database Error!!Contact Naval");
		    	        			}
		    	        				
		    	        		    }
		    	        	     }
		    	        	}
		    	        }
		    	     });
		    	    Popup.setNegativeButton("Cancel",
		 				   new DialogInterface.OnClickListener() {
		 				   public void onClick(DialogInterface dialog, int id) {
		 				   }
		 				   });
		    	     Popup.show();
				}
				return false;
			}
		});

		// Listview Group expanded listener
		/*expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
			Toast.makeText(getApplicationContext(),
					listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});*/

		/*// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});*/
		
        //Listview on group click listener to collapse other expanded groups
	/*	expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				
			}
		});*/
		
		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
               return false;
			}
		});
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		
		Integer i=0,k=0;
		List<PwdDetails> PD=db.dbgetclickedpwddetails1(Pwd_Change1.S,PasswordSaver_Activity.S1);
		
		// Adding child data
		k=PD.size();
		String[] listdataval=new String[k];
		Log.d("preparelist:k", String.valueOf(k));
		Log.d("preparelist:PD",PD.get(0).getName() );
		if(k==1 && PD.get(0).getName()=="Record not found")
		{
			listDataHeader.add(PD.get(0).getName());
			listDataChild.put(listDataHeader.get(0), null);
		}
		
		else
		{
		for(int m=0;m<k;m++)
		{
			listdataval[m]=PD.get(m).getID();
		}
		i=listdataval.length;
		for(int j=0;j<i;j++)
		{
		listDataHeader.add(listdataval[j]);
		}

		
		for(int j=0;j<i;j++)
		{
		listDataChild.put(listDataHeader.get(j), null); // Header, Child data
		}
		
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
	public void pwdbox(PwdDetails PD)
	{
    	String s1;
    	s1=PD.getID();
    	Log.d("Inside pwdbox", s1);
    	if(s1 == "No Details Found")
    	{
    		new AlertDialog.Builder(this)
    	    .setTitle(s1)
    	    .setMessage("Please add new password details")
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
	    .setTitle("Details for " + PD.getID())
	    .setMessage("User ID : " + PD.getPwd() + "\nPassword : " + PD.getName())
	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        @Override
			public void onClick(DialogInterface dialog, int which) { 
	        	
	        }
	     })
	     .show();
    	}
	}
	public void showToast(String s)
	{
		Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
		 return;
	}

	/*
	TextView T1;
	TextView T2;
	EditText E1;
	EditText E2;
	String S1;
	String S2;
	Button B1;
	String S4;
	DatabaseHandler db6=new DatabaseHandler(this);
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pwd_change2);
		T1=(TextView) findViewById(R.id.T2);
		T2=(TextView) findViewById(R.id.T4);
		T1.setText(Pwd_Change1.S1);
		T2.setText(Pwd_Change1.S2);
		E1=(EditText) findViewById(R.id.eT1);
		E2=(EditText) findViewById(R.id.eT2);
		B1=(Button) findViewById(R.id.b1);
		B1.setOnClickListener(this);
	}
	
	public void onClick(View V) {
		switch (V.getId())
		{
		case (R.id.b1):
			S1=Pwd_Change1.S1;
			S2=E1.getText().toString();
		    String S3=E2.getText().toString();
		    if(S2.equals("")||S3.equals(""))
		    {
		    	showToast();

		    }
		    else
		    {
		    if(S2.equals(S3))
			{
     		  S4=db6.dbPwdLinkUpdate(new PwdDetails( S1, S2,PasswordSaver_Activity.S1));
     		 if(S4.equals("SUCCESS"))
 			{
 				new AlertDialog.Builder(this)
 			    .setTitle("")
 			    .setMessage("Password updated successfully")
 			    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
 			        @Override
 					public void onClick(DialogInterface dialog, int which) { 
 			        	Intent i=new Intent(Pwd_Change2.this,Passwordmain_Navdrawer.class);
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
		    break;
	}
}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i=new Intent(Pwd_Change2.this,Passwordmain_Navdrawer.class);
			startActivity(i);
			finish(); 
		}

		return super.onKeyDown(keyCode, event);
		}
public void showToast()
{
	Toast.makeText(this,"Password Fields can't be null", Toast.LENGTH_SHORT).show();
	 return;
}*/
}

