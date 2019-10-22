package com.springMay.sumbooks.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class General
{

    public static void goToActivity(Context c, Class destination)
    {
        Intent i=new Intent(c,destination);
        c.startActivity(i);
    }
    //
    public static void addToSharedPreference(Context c, String key, String value)
    {
        SharedPreferences userSettings= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor pen=userSettings.edit();
        pen.putString(key,value);
        pen.commit();

    }
    //
    public static void addToSharedPreference(Context c, String key, int value)
    {
        SharedPreferences userSettings= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor pen=userSettings.edit();
        pen.putInt(key,value);
        pen.commit();

    }
    //
    public static void addToSharedPreference(Context c, String key, long value)
    {
        SharedPreferences userSettings= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor pen=userSettings.edit();
        pen.putLong(key,value);
        pen.commit();

    }
    //
    public static void addToSharedPreference(Context c, String key, float value)
    {
        SharedPreferences userSettings= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor pen=userSettings.edit();
        pen.putFloat(key,value);
        pen.commit();

    }
    //
    public static void addToSharedPreference(Context c, String key, boolean value)
    {
        SharedPreferences userSettings= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor pen=userSettings.edit();
        pen.putBoolean(key,value);
        pen.commit();

    }
    //
    public static String getFromSharedPreference(Context c, String key)
    {
        SharedPreferences userSettings= PreferenceManager.getDefaultSharedPreferences(c);
        return userSettings.getString(key,"");
    }
    //
    public static int getIntegerFromSharedPreference(Context c, String key)
    {
        SharedPreferences userSettings= PreferenceManager.getDefaultSharedPreferences(c);
        return userSettings.getInt(key,0);
    }
    //
    public static boolean getBooleanFromSharedPreference(Context c, String key)
    {
        SharedPreferences userSettings= PreferenceManager.getDefaultSharedPreferences(c);
        return userSettings.getBoolean(key,false);
    }
    //
    public static void clearPreference(Context c)
    {
        SharedPreferences userSettings= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor pen=userSettings.edit();
        pen.clear();
        pen.commit();
    }
    //
    public static String convertToString(Bitmap bitmap)
    {
        // convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte imageInByte[] = stream.toByteArray();
        String encodedString = Base64.encodeToString(imageInByte, 0);
        return encodedString;
    }
    //
    public static boolean checkLength(String data,int checkWhat)
    {
        boolean accepted=false;

        if (checkWhat==Constants.CHECK_NAME) //name
        {
            if(data.length()>=Constants.NAME_MIN_LENGTH)
            {
                accepted=true;
            }
        }
        else if (checkWhat==Constants.CHECK_PASS)
        {
            if(data.length()>=Constants.PASS_MIN_LENGTH)
            {
                accepted=true;
            }
        }



        return accepted ;
    }



}
