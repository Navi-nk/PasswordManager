package com.example.naval_passwordsaver;

public class PwdDetails {
	
	//private variables
	String _id;
	String _name;
	String _pwd;
	String _SECQ;
	String _SECA;
	String _sid;
	
	// Empty constructor
	public PwdDetails(){
		
	}
	// constructor 1
	public PwdDetails(String id, String name, String pwd,String secq,String seca){
		this._id = id;
		this._name = name;
		this._pwd = pwd;
		this._SECQ = secq;
		this._SECA= seca;
	}
	//constructor 3
	public PwdDetails(String id, String name, String pwd){
		this._id = id;
		this._name = name;
		this._pwd = pwd;
	}
	// constructor 4
			public PwdDetails(String sid,String id, String name, String pwd,String secq,String seca){
				this._sid=sid;
				this._id = id;
				this._name = name;
				this._pwd = pwd;
				this._SECQ = secq;
				this._SECA= seca;
			}
			// constructor 4
			public PwdDetails(String sid,String id, String name, String pwd){
				this._sid=sid;
				this._id = id;
				this._name = name;
				this._pwd = pwd;
			}

	// constructor 2
	public PwdDetails(String id, String pwd){
		this._id = id;
		this._pwd = pwd;
	}
	
	// getting ID
	public String getID(){
		return this._id;
	}
	
	// setting id
	public void setID(String id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting pwd
	public String getPwd(){
		return this._pwd;
	}
	
	// setting pwd
	public void setPwd(String pwd){
		this._pwd = pwd;
	}
	
	//getting sec question
	public String getSecq(){
		return this._SECQ;
	}

	//setting sec question
	public void setSecq(String secq){
		this._SECQ = secq;
	}
	
	//getting sec ans
	public String getSeca(){
		return this._SECA;
	}

	//setting sec ans
	public void setSeca(String seca){
		this._SECA = seca;
	}
	//getting sid
	public String getSid(){
		return this._sid;
	}

	//setting sid
	public void setSid(String sid){
		this._sid = sid;
	}

}

