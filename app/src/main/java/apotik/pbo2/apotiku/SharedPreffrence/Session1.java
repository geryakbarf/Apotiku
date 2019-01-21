package apotik.pbo2.apotiku.SharedPreffrence;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import apotik.pbo2.apotiku.Login_Register.Login;
import apotik.pbo2.apotiku.MainActivity;
import apotik.pbo2.apotiku.UserActivity;

public class Session1 {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_NAMA_PEGAWAI = "NamaPegawai";
    public static final String KEY_HAK_AKSES = "HakAkses";
    public static final String KEY_IDAPOTIK = "IdApotik";
    public static final String KEY_USERNAME = "Username";

    // Constructor
    public Session1(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     *
     * Create login session
     * */
    public void createLoginSession(String NamaPegawai, String HakAkses, String IdApotik, String Username){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing email in pref
        editor.putString(KEY_NAMA_PEGAWAI, NamaPegawai);
        editor.putString(KEY_HAK_AKSES, HakAkses);
        editor.putString(KEY_IDAPOTIK,IdApotik);
        editor.putString(KEY_USERNAME,Username);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If true it will redirect user to UserActivity Page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, UserActivity.class);
            // Closing all the Activities
            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP|i.FLAG_ACTIVITY_CLEAR_TASK|i.FLAG_ACTIVITY_NEW_TASK);

            // Staring Main Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        // user email id
        user.put(KEY_NAMA_PEGAWAI, pref.getString(KEY_NAMA_PEGAWAI, null));
        user.put(KEY_HAK_AKSES, pref.getString(KEY_HAK_AKSES, null));
        user.put(KEY_IDAPOTIK, pref.getString(KEY_IDAPOTIK, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME,null));
        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent intent = new Intent(_context, Login.class);
        // Closing all the Activities
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP|intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(intent);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
