package com.CarApp.car.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


public class SessionManager {
	// LogCat tag
	private static String TAG = SessionManager.class.getSimpleName();
	// Shared Preferences
	SharedPreferences
			pref;
	Editor
			editor;
	Context
			_context;
	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Shared preferences file name
	private static final String PREF_NAME = "MyLoginPref";
	// Pref Index name
	private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
	private static final String KEY_USERNAME = "name";
	private static final String KEY_USEREMAIL = "email";
	private static final String KEY_ROLE = "role";
	private static final String KEY_IMAGE = "image";
	private static final String KEY_WID = "worker_id";
	private static final String KEY_Id = "id";
	private static final String KEY_LOCATION_IDS = "location_id";
	private static final String KEY_SEET_ID = "sheet_id";
	private static final String KEY_BOOKING_ID = "origin_id";
	private static final String KEY_DESTINATION = "destination_id";
	private static final String packetid = "waypoints_id";
//	private static final  int   image = 1;

	// 1 arg constructer
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		editor = pref.edit();
	}
	public void setWorkerid(String string){
		editor.putString(KEY_WID, string);
		editor.commit();
	}
	public void setKeyOrigin(String string){
		editor.putString(KEY_BOOKING_ID, string);
		editor.commit();
	}
	public void setKeyDestination(String string){
		editor.putString(KEY_DESTINATION, string);
		editor.commit();
	}
	public void setKeyPacketid(String string){
		editor.putString(packetid, string);
		editor.commit();
	}
	public String getWorkerid() {
		return pref.getString(KEY_WID, "");
	}
	public String getKeyOrigin() {
		return pref.getString(KEY_BOOKING_ID, "");
	}
	public String getKeyDestination() {
		return pref.getString(KEY_DESTINATION, "");
	}
	public String getKeyPacketid() {
		return pref.getString(packetid, "");
	}
	public String getSheetID() {
		return pref.getString(KEY_SEET_ID, "");
	}
	public void setSheetId(String id){
		editor.putString(KEY_SEET_ID,id);
		editor.commit();
	}
	public void setId(String id){
		editor.putString(KEY_Id,id);
		editor.commit();
	}
	public String getId(){
		return  pref.getString("id","");
	}
	public String getKeyLocationIds(){
		return  pref.getString(KEY_LOCATION_IDS,"");
	}

	public void setRole(String role){
		editor.putString(KEY_ROLE,role);
		editor.commit();
	}
	public void setKeyLocationIDs(String role){
		editor.putString(KEY_LOCATION_IDS,role);
		editor.commit();
	}
	public String getRole(){
		return  pref.getString(KEY_ROLE,"");
	}
	public void setLogin(boolean isLoggedIn,String username,String uid) {
		editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
		editor.putString(KEY_USEREMAIL,username);
		editor.putString(KEY_Id, uid);
		// commit changes
		editor.commit();
		Log.d(TAG, "User login session modified!");
	}

	public void setLogin(boolean isLoggedIn) {

		editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
		// commit changes
		editor.commit();
		Log.d(TAG, "User login session modified!");
	}
	public void setEmail(String email) {
		editor.putString("email", email);
		editor.commit();
	}
	public void setName(String name) {
		editor.putString("name", name);
		editor.commit();
	}
	public String getEmail(){
		return pref.getString("email","");
	}


	public boolean isLoggedIn() {
		return pref.getBoolean(KEY_IS_LOGGED_IN, false);
	}
	public String getKeyUsername()
	{
		return pref.getString(KEY_USERNAME,"");
	}

	public String getKeyUserId() {
		return pref.getString(KEY_Id,"");
	}
	public void setlat()
	{

	}
	public void getlat()
	{

	}
	public void setlon()
	{

	}
	public void getlon()
	{

	}
}
