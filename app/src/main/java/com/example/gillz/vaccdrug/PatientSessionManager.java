package com.example.gillz.vaccdrug;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class PatientSessionManager {

	// Shared Preferences reference
	SharedPreferences pref;

	// Editor reference for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREFER_NAME_PATIENT = "Patient_Login";

	// All Shared Preferences Keys
	private static final String IS_PATIENT_LOGIN = "IspatientLoggedIn";

	// User name (make variable public to access from outside)
	public static final String KEY_PATIENT_ID_NUMBER = "patient_id_number";

	// Email address (make variable public to access from outside)
	//public static final String KEY_FARMER_LOCATION = "farmer_location";

	// Constructor
	public PatientSessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREFER_NAME_PATIENT, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	//Create login session
	public void createUserLoginSession(String patient_id_number){
		// Storing login value as TRUE
		editor.putBoolean(IS_PATIENT_LOGIN, true);
		
		// Storing name in pref
		editor.putString(KEY_PATIENT_ID_NUMBER, patient_id_number);
		
		// Storing email in pref
		//editor.putString(KEY_FARMER_LOCATION, farmer_location);
		
		// commit changes
		editor.commit();
	}	
	
	/**
	 * Check login method will check user login status
	 * If false it will redirect user to login page
	 * Else do anything
	 * */
	public boolean checkLogin(){
		// Check login status
		if(!this.isUserLoggedIn()){
			
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, Plogin.class);
			
			// Closing all the Activities from stack
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			// Staring Login Activity
			_context.startActivity(i);
			
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		
		//Use hashmap to store user credentials
		HashMap<String, String> user = new HashMap<String, String>();
		
		// user name
		user.put(KEY_PATIENT_ID_NUMBER, pref.getString(KEY_PATIENT_ID_NUMBER, null));
		
		// user email id
		//user.put(KEY_FARMER_LOCATION, pref.getString(KEY_FARMER_LOCATION, null));
		
		// return user
		return user;
	}
	
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		
		// Clearing all user data from Shared Preferences
		editor.clear();
		editor.commit();
		
		// After logout redirect user to Login Activity
		Intent i = new Intent(_context, Plogin.class);
		
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Staring Login Activity
		_context.startActivity(i);
	}
	
	
	// Check for login
	public boolean isUserLoggedIn(){
		return pref.getBoolean(IS_PATIENT_LOGIN, false);
	}
}
