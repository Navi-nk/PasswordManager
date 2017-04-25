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
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

public class Pwd_Delete2 extends Activity {
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
					new AlertDialog.Builder(Pwd_Delete2.this)
		    	    .setTitle("No Details Found")
		    	    .setMessage("Please add new password details")
		    	    .setCancelable(false)
		    	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    	        @Override
		    			public void onClick(DialogInterface dialog, int which) { 
		    	        	Intent i=new Intent(Pwd_Delete2.this,Passwordmain_Navdrawer.class);
		    			       startActivity(i);
		    			       finish();	
		    	        }
		    	     })
		    	     .show();
				}
				else
				{
					 AlertDialog.Builder Popup1 = new AlertDialog.Builder(Pwd_Delete2.this);
		    	     Popup1.setTitle("Delete "+listDataHeader.get(groupPosition));
		    	     Popup1.setMessage("Are you sure?");
		    	     Popup1.setCancelable(false);
		    	     Popup1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
		    	        @Override
		    			public void onClick(DialogInterface dialog, int which) {
		    	        	String s=db.dbDeletePWD(S,PasswordSaver_Activity.S1);
		    	        	if(s == "SUCCESS")
		    	        	{
		    	        		Intent i=new Intent(Pwd_Delete2.this, Pwd_Delete2.class);
		    	        		startActivity(i);
		    	        		finish();
		    	        		showToast("Password deleted successfully.");
		    	        	}
		    	        	else if(s == "FAILURE")
		    	        	{
		    	        		showToast("Database Error!!Contact Naval");
		    	        	}
		    	        }
		    	     });
		    	   Popup1.setNegativeButton("NO",
				   new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
				   }
				   });
		    	   Popup1.show();
				}
				return false;
			}
		});
		
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
		List<PwdDetails> PD=db.dbgetclickedpwddetails1(Pwd_Delete1.S,PasswordSaver_Activity.S1);
		
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


}
