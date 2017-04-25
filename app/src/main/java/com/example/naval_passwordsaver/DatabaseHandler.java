package com.example.naval_passwordsaver;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "password_DB";

	// Contacts table name
	private static final String TABLE_USER = "USR_USER";
	private static final String TABLE_PWD = "PWD_LINKS";

	// Contacts Table Columns names
	//private static final String KEY_SESSIONID="key";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PWD = "pwd";
	private static final String KEY_SECQUES="Ques1";
	private static final String KEY_SECQANS="Ans1";
	
	
	private static final String KEY_ID1 = "id1";
	private static final String KEY_NAME1 = "name1";
	private static final String KEY_PWD1 = "pwd1";
	private static final String KEY_NAME2 = "name2";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("inside db", "Success");
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USER + "("
				 + KEY_ID +" TEXT PRIMARY KEY,"+KEY_NAME + " TEXT,"
				+ KEY_PWD + " TEXT," + KEY_SECQUES + " TEXT," + KEY_SECQANS + " TEXT" +")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		String CREATE_PWD_TABLE = "CREATE TABLE " + TABLE_PWD + "("
				+ KEY_ID1 + " TEXT," + KEY_NAME1 + " TEXT UNIQUE,"
				+ KEY_PWD1 + " TEXT," + KEY_NAME2 + " TEXT" +",PRIMARY KEY(id1,name1)"+ ")";
		db.execSQL(CREATE_PWD_TABLE);
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER );
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PWD );
		
		// Creates tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new user
	public String dbpasswordsend(PwdDetails pwddetails) {
		SQLiteDatabase db = this.getWritableDatabase();
		String a="SUCCESS";
		ContentValues values = new ContentValues();
		values.put(KEY_ID, pwddetails.getID()); // ID
		values.put(KEY_NAME, pwddetails.getName()); //  Name
		values.put(KEY_PWD, pwddetails.getPwd());//PWD
		values.put(KEY_SECQUES, pwddetails.getSecq());//secq
		values.put(KEY_SECQANS, pwddetails.getSeca());//seca

		// Inserting Row
		try{
		db.insertOrThrow(TABLE_USER, null, values);
		}
		catch(SQLException e)
		{
			a="FAILURE";
		}
		db.close(); // Closing database connection
		return a;
	}
	
	public String dbPwdLinkCreate(PwdDetails pwdDetails) {
		SQLiteDatabase db = this.getWritableDatabase();
		String a="SUCCESS";
		ContentValues values = new ContentValues();
		values.put(KEY_ID1,pwdDetails.getSid());
		values.put(KEY_NAME1, pwdDetails.getID()); //  Name
		values.put(KEY_PWD1, pwdDetails.getName());//PWD
		values.put(KEY_NAME2, pwdDetails.getPwd());//Link User ID
		

		// Inserting Row
		try{
		db.insertOrThrow(TABLE_PWD, null, values);
		}
		catch(SQLException e)
		{
			a="FAILURE";
		}
		db.close(); // Closing database connection
		return a;
	}
	
	public List<PwdDetails> getAllPwd(String s) {
		String S1,S2,S3;
		Integer I;
		List<PwdDetails> PwdList = new ArrayList<PwdDetails>();
		// Select All Query
		String selectQuery = "SELECT  name1,pwd1,name2 FROM " + TABLE_PWD+" WHERE id1=?";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[]{s});
		I=cursor.getCount();
		// looping through all rows and adding to list
		if(I != 0)
		{
		if (cursor.moveToFirst()) {
			do {
				S1=cursor.getString(0);
				S2=cursor.getString(1);
				S3=cursor.getString(2);
				Log.d("ID", S1);
				Log.d("Name", S3);
				Log.d("PWD", S2);
				PwdDetails pwddetails = new PwdDetails();
				pwddetails.setID(cursor.getString(0));
				pwddetails.setName(cursor.getString(2));
				pwddetails.setPwd(cursor.getString(1));
				// Adding pwd to list
				PwdList.add(pwddetails);
			} while (cursor.moveToNext());
		}
		}
		else
		{
			PwdDetails pwddetails = new PwdDetails();
			pwddetails.setID("No Details Found");
			pwddetails.setName("Please add new password details");
			pwddetails.setPwd("No Password");
			// Adding pwd to list
			PwdList.add(pwddetails);
		}

		// return contact list
		return PwdList;
	}
	
	public String[] dbgetAllPwd1(String s) {
		Integer I,i=0;
		
		// Select All Query
		String selectQuery = "SELECT  name1 FROM " + TABLE_PWD+" WHERE id1 =?";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, new String[]{s});
		I=cursor.getCount();
		
		// looping through all rows and adding to list
		if(I != 0)
		{
			String[] PwdList = new String[I];
		if (cursor.moveToFirst()) {
			do {
				PwdList[i]=cursor.getString(0);
				//S2=cursor.getString(1);
				//S3=cursor.getString(2);
				Log.d("ID", PwdList[i]);
			//	Log.d("Name", S3);
			//	Log.d("PWD", S2);
				//String pwddetails = new String();
				//pwddetails.setID(cursor.getString(0));
				//pwddetails.setName(cursor.getString(2));
				//pwddetails.setPwd(cursor.getString(1));
				// Adding pwd to list
				++i;
			} while (cursor.moveToNext());
		}
		return PwdList;
		}
		else
		{
			String[] PwdList = new String[1];
			//PwdDetails pwddetails = new PwdDetails();
			//pwddetails.setID("0");
			//pwddetails.setName("No Details Found");
			//pwddetails.setPwd("Please add new password details");
			// Adding pwd to list
			//PwdList.add("No Details Found");
			PwdList[i]="No Details Found";
			Log.d("Inside else of getallpwds", PwdList[i]);
			return PwdList;
		}
		
	}
	public PwdDetails dbgetclickedpwddetails(String j,String k)
	{
		String P1;
		String P2;
		String P3;
		Integer I1;
		PwdDetails PD1;
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT id1,name1,pwd1,name2  FROM " + TABLE_PWD +" WHERE name1=? AND id1=?";
		//Cursor cursor = db.query(TABLE_PWD, new String[] {KEY_ID1,KEY_NAME1,
			//	KEY_PWD1,KEY_NAME2 }, KEY_NAME1 + "=?",
				//new String[] { String.valueOf(j)}, null, null, null,null);
		Cursor cursor = db.rawQuery(selectQuery,new String[]{j,k});
		if (cursor != null)
			cursor.moveToFirst();
		
		I1=cursor.getCount();
		Log.d("I",String.valueOf(I1));	
		if(I1 == 1)
		{
			P1=cursor.getString(1);
			P2=cursor.getString(2);
			P3=cursor.getString(3);
			Log.d("name1", P1);
			Log.d("pwd", P2);
			Log.d("name2", P3);
			
		    	PD1=new	PwdDetails(P1,P2,P3);
				return PD1;

		}
		    else
		    {
		    	PD1=new	PwdDetails(String.valueOf(j),"Record not found","Record not found");
				return PD1;
		    }
		}
	public List<PwdDetails> dbgetclickedpwddetails1(String j,String k)
	{
		String P1,P2,P3;
		Integer I1;
		PwdDetails PD1;
		List<PwdDetails> PD2=new ArrayList<PwdDetails>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT name1,pwd1,name2  FROM " + TABLE_PWD +" WHERE name1 LIKE ? AND id1=(?)";
		//Cursor cursor = db.query(TABLE_PWD, new String[] {KEY_ID1,KEY_NAME1,
			//	KEY_PWD1,KEY_NAME2 }, KEY_NAME1 + "=?",
				//new String[] { String.valueOf(j)}, null, null, null,null);
		Cursor cursor = db.rawQuery(selectQuery,new String[]{"%"+j+"%",k});
		
		I1=cursor.getCount();
		Log.d("dbclickedpwd:I",String.valueOf(I1));	
		Log.d("dbclickedpwd:j",String.valueOf(j));
		Log.d("dbclickedpwd:k",String.valueOf(k));
		if(I1 != 0)
		{
			if (cursor.moveToFirst()) {
				do {
					P1=cursor.getString(0);
					P2=cursor.getString(1);
					P3=cursor.getString(2);
					Log.d("dbclickedpwd:name1", P1);
					Log.d("dbclickedpwd:pwd", P2);
					Log.d("dbclickedpwd:name2", P3);
					
				    	PD1=new	PwdDetails(P1,P2,P3);
						PD2.add(PD1);
				} while (cursor.moveToNext());
			}
				return PD2;
		}
		    else
		    {
		    	PD1=new	PwdDetails(String.valueOf(j),"Record not found","Record not found");
		    	PD2.add(PD1);
				return PD2;
		    }
		}

	
	public String dbpasswordvalidate(String p1,String p2)
	{
		String P1;
		String P2;
		Integer I1;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID,KEY_NAME,
				KEY_PWD }, KEY_ID + "=?",
				new String[] { String.valueOf(p1)}, null, null, null,null);
		if (cursor != null)
			cursor.moveToFirst();
		
		I1=cursor.getCount();
		
		if(I1 != 1)
		{
			
			return "User Not Found";
		}
		else
		{
			P1=cursor.getString(0);
			P2=cursor.getString(2);
		    if(P1.equals(p1) && P2.equals(p2))
		    {
		    	return "Success";
		    }
		    else
		    {
		    	return "Failure";
		    }
		}
		
	}
	

	public Integer dbsecquesvalidate(String p1,String p2)
	{
		Integer I1,I2;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID,KEY_NAME,
				KEY_PWD,KEY_SECQUES,KEY_SECQANS }, KEY_SECQANS + "=?"+" AND "+KEY_ID+"=?",
				new String[] { String.valueOf(p1),String.valueOf(p2)}, null, null, null,null);
		if (cursor != null)
			cursor.moveToFirst();
		
		I1=cursor.getCount();
		
		if(I1 == 1)
		{
			I2=0;
		}
		else if(I1 == 0)
		{
		    I2=1;
		}
		else
		{
			I2=2;
		}
		return I2;
		
	}

	public String dbPwdLinkUpdate(PwdDetails PD) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PWD1, PD.getName());

		// updating row
		try
		{
		db.update(TABLE_PWD, values, KEY_NAME1 + " = ?"+" AND "+KEY_ID1+" = ?",
		new String[] { String.valueOf(PD.getID()),String.valueOf(PD.getPwd())});
		}
		catch(SQLException e)
		{
			return "FAILURE"; 
		}
		return "SUCCESS";
	}

	
	public Integer dbUserCheck()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		String countQuery = "SELECT  * FROM " + TABLE_USER;
		Cursor cursor = db.rawQuery(countQuery, null);
		//cursor.close();
		return cursor.getCount();
	}
	//Deleting User
	/*	public void deleteUser(PwdDetails pwdDetails) {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_USER, KEY_ID +" = ?"+" AND "+ KEY_PWD + " = ?",
					new String[] { String.valueOf(pwdDetails.getID()),String.valueOf(pwdDetails.getPwd())});
			db.close(); 
		}*/
	public String dbDeletePWD(String a,String b) {
		String s="SUCCESS";
		SQLiteDatabase db = this.getWritableDatabase();
		try{
		db.delete(TABLE_PWD, KEY_NAME1 +" = ?"+" AND "+KEY_ID1 + " = ?",
				new String[] { String.valueOf(a),String.valueOf(b)});
		}
		catch(SQLException e)
		{
			s="FAILURE";
		}
		db.close(); 
		return s;
	}


	public String dbdeletedetails(String str) {
		SQLiteDatabase db = this.getWritableDatabase();
		String s="SUCCESS";
		try{
			db.delete(TABLE_USER, KEY_ID + " = ? ", new String[]{String.valueOf(str)});
			db.delete(TABLE_PWD,KEY_ID1 + " = ? ",new String[] {String.valueOf(str)});

		//db.execSQL("DELETE FROM " + TABLE_USER);
		//db.execSQL("DELETE FROM " + TABLE_PWD);
		//db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_PWD+"'");
		}
		catch(SQLException e)
		{
			s="FAILURE";
		}
		db.close();
		return s;
	}

/*public void deletePwd(String a) {
	SQLiteDatabase db = this.getWritableDatabase();
	db.delete(TABLE_PWD, KEY_NAME1 +" = ?",
			new String[] { String.valueOf(a)});
	db.close(); 
}*/

/*public PwdDetails dbgetsavedpwd(String p1)
{
	PwdDetails PD1;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.query(TABLE_PWD, new String[] { KEY_ID1,KEY_NAME1,
			KEY_PWD1 }, KEY_NAME1 + "=?",
			new String[] { String.valueOf(p1)}, null, null, null,null);
	if (cursor != null)
		cursor.moveToFirst();
	
PD1=new	PwdDetails(cursor.getString(1),cursor.getString(2));
return PD1;
	    
	}*/

public String dbgetsecques(String s) {
	String S="NO RECORDS FOUND";
	Integer I;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID,KEY_NAME,
			KEY_PWD,KEY_SECQUES,KEY_SECQANS }, KEY_ID + "=?" ,new String[] { String.valueOf(s)}, null, null, null,null);
	if (cursor != null)
		cursor.moveToFirst();
	
	I=cursor.getCount();
	if(I==1)
	{
	S=cursor.getString(3);
	}
	return S;
}

public String dbgetusrid(String s) {
	String S="User not found";
	Integer I;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID,KEY_NAME,
			KEY_PWD,KEY_SECQUES,KEY_SECQANS }, KEY_ID + "=?" ,new String[] { String.valueOf(s)}, null, null, null,null);
	if (cursor != null)
		cursor.moveToFirst();
	
	I=cursor.getCount();
	if(I==1)
	{
	S=cursor.getString(0);
	}

	return S;
}

public Integer dbvalidateoldpwd(String s,String s1) {
	
	Integer I1,I2;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID,KEY_NAME,
			KEY_PWD,KEY_SECQUES,KEY_SECQANS }, KEY_PWD + "=?"+ " AND "+KEY_ID + "=?",
			new String[] { String.valueOf(s), String.valueOf(s1)}, null, null, null,null);
	if (cursor != null)
		cursor.moveToFirst();
	
	I1=cursor.getCount();
	
	if(I1 == 1)
	{
		I2=0;
	}
	else if(I1 == 0)
	{
	    I2=1;
	}
	else
	{
		I2=2;
	}
	return I2;
}

public String dbchangepwd(String S1,String S2) {
	SQLiteDatabase db = this.getWritableDatabase();
    String s="SUCCESS";
	ContentValues values = new ContentValues();
	values.put(KEY_PWD, S2);

	// updating row
	try{
	 db.update(TABLE_USER, values, KEY_ID + " = ?",
			new String[] { S1 });
	}
	catch (SQLException e)
	{
		s="FAILURE";
	}
	return s;
}

public boolean dbCheckPwd(String s, String s2, String s1) {
	// TODO Auto-generated method stub
	Integer I1;
	String P1;
	SQLiteDatabase db = this.getReadableDatabase();
	String selectQuery = "SELECT pwd1  FROM " + TABLE_PWD +" WHERE name1=? AND id1=?";
	Cursor cursor = db.rawQuery(selectQuery,new String[]{s,s1});
	if (cursor != null)
		cursor.moveToFirst();
	
	I1=cursor.getCount();
	Log.d("I",String.valueOf(I1));	
		P1=cursor.getString(0);
		Log.d("pwd", P1);
		if(P1.equals(s2))
		{
			return true;
		}
		else
		{
			return false;
		}

}

/*public void SetSid(String s1,Integer i) {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_SESSIONID, i);

	// updating row
	
	db.update(TABLE_PWD, values, KEY_ID + " = ?",
	new String[] { String.valueOf(s1) });
	
	catch(SQLException e)
	{
		return "FAILURE"; 
	}
	return "SUCCESS";
	
	
}*/
	

}
/*
	// 
	 * 
	Contact getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return contact;
	}
	
	// Getting All Contacts
	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setName(cursor.getString(1));
				contact.setPhoneNumber(cursor.getString(2));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	// Updating single contact
	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, contact.getName());
		values.put(KEY_PH_NO, contact.getPhoneNumber());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}

	

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}*/
