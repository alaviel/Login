package com.example.login;

import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JWTUtils {
    private static final String TAG = "JWT";

    public static String[] decoded(String JWTEncoded) throws Exception {
        String[] parsedData = new String[0];

        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d(TAG, "Header: " + getJson(split[0]));
            Log.d("TAG", "Body: " + getJson(split[1]));;

            String str =  getJson(split[1]);
            JSONObject json = null;

            json = new JSONObject(str);

            String titular = json.getString("titular");
            String url = json.getString("url");
            String email = json.getString("email");

            parsedData = new String[]{titular, url, email};

        } catch (UnsupportedEncodingException e) {
            //Error
        }
        return parsedData;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}
