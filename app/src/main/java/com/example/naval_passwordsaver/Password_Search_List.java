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
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class Password_Search_List extends Activity {
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	private Integer lastExpgrposition=-1;
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
				if(groupPosition==0 && listDataHeader.get(groupPosition)=="Record not found")
				{
					new AlertDialog.Builder(Password_Search_List.this)
		    	    .setTitle("No Details Found")
		    	    .setMessage("Please add new password details")
		    	    .setCancelable(false)
		    	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    	        @Override
		    			public void onClick(DialogInterface dialog, int which) { 
		    	        	Intent i=new Intent(Password_Search_List.this,Passwordmain_Navdrawer.class);
		    			       startActivity(i);
		    			       finish();	
		    	        }
		    	     })
		    	     .show();
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
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				if(lastExpgrposition != -1 && groupPosition != lastExpgrposition)
				{
					expListView.collapseGroup(lastExpgrposition);
				}
				lastExpgrposition=groupPosition;
			}
		});
		
		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				final String S=listDataHeader.get(groupPosition);
				switch (childPosition)
				{
				case (0):
					PwdDetails PD;
					PD=db.dbgetclickedpwddetails(S,PasswordSaver_Activity.S1);
					pwdbox(PD);
				     break;
				case (1):
				LayoutInflater factory = LayoutInflater.from(Password_Search_List.this);
				final View PopuptextEntryView = factory.inflate(R.layout.pwd_change_alertview,null);
				AlertDialog.Builder Popup= new AlertDialog.Builder(Password_Search_List.this);
	    	    Popup.setTitle("Change Password:"+listDataHeader.get(groupPosition));
	    	    Popup.setView(PopuptextEntryView);
	    	    final EditText ipPwd1=(EditText)PopuptextEntryView.findViewById(R.id.eT1);
	    	    final EditText ipPwd2=(EditText)PopuptextEntryView.findViewById(R.id.eT2);
	    	    Popup.setCancelable(false);
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
				 break;
				 case (2):
					 AlertDialog.Builder Popup1 = new AlertDialog.Builder(Password_Search_List.this);
		    	     Popup1.setTitle("Delete "+listDataHeader.get(groupPosition));
		    	     Popup1.setMessage("Are you sure?");
		    	     Popup1.setCancelable(false);
		    	     Popup1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
		    	        @Override
		    			public void onClick(DialogInterface dialog, int which) {
		    	        	String s=db.dbDeletePWD(S,PasswordSaver_Activity.S1);
		    	        	if(s == "SUCCESS")
		    	        	{
		    	        		Intent i=new Intent(Password_Search_List.this, Password_Search_List.class);
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
				 break;
				}
			/*	Toast.makeText(
						getApplicationContext(),
						listDataHeader.get(groupPosition)
								+ " : "
								+ listDataChild.get(
										listDataHeader.get(groupPosition)).get(
										childPosition), Toast.LENGTH_SHORT)
										.show();*/
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
		List<PwdDetails> PD=db.dbgetclickedpwddetails1(Password_Search.S,PasswordSaver_Activity.S1);
		
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
		// Adding child data
		List<String> pwdlistoptions = new ArrayList<String>();
		pwdlistoptions.add("View Password");
		pwdlistoptions.add("Change password");
		pwdlistoptions.add("Delete password");

		
		for(int j=0;j<i;j++)
		{
		listDataChild.put(listDataHeader.get(j), pwdlistoptions); // Header, Child data
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
