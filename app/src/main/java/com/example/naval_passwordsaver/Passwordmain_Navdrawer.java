package com.example.naval_passwordsaver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class Passwordmain_Navdrawer extends Activity {
	private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    Integer i=0,j=0;
    DatabaseHandler db5=new DatabaseHandler(this);
    private ExpandableListAdapter listAdapter;
    private ExpandableListView mDrawerList;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    DatabaseHandler db=new DatabaseHandler(this);
 
  // private static String[] mPwdTitles={"View Passwords List","Manage Passwords","Settings","Sign Out"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordmain_navdrawer);
       
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
       // mPwdTitles= db5.getAllPwd1();
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
 
    
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        mDrawerList.setAdapter(listAdapter);
        
        // set up the drawer's list view with items and click listener
      
      //  mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,mPwdTitles));
        
        
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        mDrawerList.setOnGroupClickListener(new OnGroupClickListener() {

    		@Override
    		public boolean onGroupClick(ExpandableListView parent, View v,
    				int groupPosition, long id) {
    			switch(groupPosition)
    			{
    			case (0):
    			Intent i1=new Intent(Passwordmain_Navdrawer.this,Pwd_List_Expandlist.class);
			    startActivity(i1);
			    finish();
    			break;
    			case (1):
        		Intent i2=new Intent(Passwordmain_Navdrawer.this,Password_Search.class);
    			startActivity(i2);
    			finish();
    			break;
    			case(4):
    				logoutbutton();
    			break;
    			}
    			return false;
    		}
    	});
        mDrawerList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				if(groupPosition==2)
				{
				switch(childPosition)
    			{
    			case (0):
    			Intent i2=new Intent(Passwordmain_Navdrawer.this,Pwd_Add.class);
			    startActivity(i2);
			    finish();
    			break;
    			case(1):
    			Intent i3=new Intent(Passwordmain_Navdrawer.this,Pwd_Change1.class);
			    startActivity(i3);
			    finish();
    			break;
    			case (2):
        			Intent i4=new Intent(Passwordmain_Navdrawer.this,Pwd_Delete1.class);
    			    startActivity(i4);
    			    finish();
        			break;
    			}
				}
				else if(groupPosition==3)
				{
					switch(childPosition)
	    			{
	    			case (0):
	    			Intent i5=new Intent(Passwordmain_Navdrawer.this,Secques_Validate.class);
				    startActivity(i5);
				    finish();
	    			break;
	    			case(1):
	    			new AlertDialog.Builder(Passwordmain_Navdrawer.this)
	    		    .setTitle("Delete Profile")
	    		    .setMessage("Are you sure?")
	    		    .setCancelable(false)
	    		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    		        @Override
	    				public void onClick(DialogInterface dialog, int which) { 
	    		        	String s=db.dbdeletedetails(PasswordSaver_Activity.S1);
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

	    			break;
				}
				}
				return false;
			
			}
		});
        }

   // @Override
   /* public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        /*switch(item.getItemId()) {
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }*/
        return super.onOptionsItemSelected(item);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ExpandableListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        	
        }
    }
   

    private void selectItem(int position) {
        // update the main content by replacing fragments
      /*  Fragment fragment = new PwdFragment();
        Bundle args = new Bundle();
        args.putInt(PwdFragment.ARG_PWD_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();*/

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        //setTitle(mPwdTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
   /* public static class PwdFragment extends Fragment {
        public static final String ARG_PWD_NUMBER = "pwd_number";

        public PwdFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.test, container, false);
            ((TextView) rootView.findViewById(R.id.textView1)).setText("Password Manager V1.2");
            int i = getArguments().getInt(ARG_PWD_NUMBER);
            Log.e("I", String.valueOf(i));
            switch (i)
            {
            case(0):
            	Intent i1=new Intent(Passwordmain_Navdrawer.this,Pwd_List_Expandlist.class);
			    startActivity(i1);
			    finish();
            break;
            case(1):
            	
            break;
            case(2):
            	
            break;
            case(3):
            	
            break;               
            }
         //   String planet = getResources().getStringArray(R.array.planets_array)[i];

         // int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
           //                 "drawable", getActivity().getPackageName());
           // ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
           // getActivity().setTitle(planet);
           // if(i==1)
            //{
           
            //}
            return rootView;
        }
    }*/

    public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			  logoutbutton();	    	   
		}
		return super.onKeyDown(keyCode, event);
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        
        // Adding child data
        listDataHeader.add("View Passwords List");
        listDataHeader.add("Search Password");
        listDataHeader.add("Manage Passwords");
        listDataHeader.add("Settings");
        listDataHeader.add("Sign Out");
 
        // Adding child data
        List<String> MP = new ArrayList<String>();
        MP.add("ADD");
        MP.add("Change");
        MP.add("Delete");
       
 
        List<String> STNG = new ArrayList<String>();
        STNG.add("Change User Password");
        STNG.add("Delete Profile");
 
 
        listDataChild.put(listDataHeader.get(0), null); // Header, Child data
        listDataChild.put(listDataHeader.get(1), null);        
        listDataChild.put(listDataHeader.get(2), MP);
        listDataChild.put(listDataHeader.get(3), STNG);
        listDataChild.put(listDataHeader.get(4), null);
    }
    public void logoutbutton()
	{
		new AlertDialog.Builder(this)
	    .setTitle("Sign out")
	    .setMessage("Are you sure?")
	    .setCancelable(false)
	    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
	        @Override
			public void onClick(DialogInterface dialog, int which) {
	        	//db3.SetSid(PasswordSaver_Activity.S1,0);
	        	Intent i=new Intent(Passwordmain_Navdrawer.this,MainPage.class);
			    startActivity(i);
			    finish();
	        }
	     })
	      .setNegativeButton("NO",
		   new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int id) {
		   }
		   })
	     .show();
	}
    public void alertbox1()
    {
    	new AlertDialog.Builder(this)
        .setTitle("")
        .setMessage("Profile deleted successfully.")
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
    		public void onClick(DialogInterface dialog, int which) {
            	Intent i3=new Intent(Passwordmain_Navdrawer.this,MainPage.class);
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

}
