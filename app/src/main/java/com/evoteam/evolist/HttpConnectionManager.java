package com.evoteam.evolist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 7/23/2017.
 */
public class HttpConnectionManager {

    static HttpURLConnection urlConnection = null;
    static  String serverUrl = "http://198.143.181.24:3004/api/";

    public HttpConnectionManager (String serverUrl)
    {
        this.serverUrl = serverUrl;
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
            return true;
        else
            return false ;
    }

    public static String inputStreamToString(InputStream inputstream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getDataHttpUrlConnection() {
        try {
            URL url = new URL(serverUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();
            String response = inputStreamToString(is);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            urlConnection.disconnect();
            return null;
        }
    }

    public static String postData(String json) {
        Log.d("***", "hewe aaa");
        try {

            URL url = new URL(serverUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = json;

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
            urlConnection.connect();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            OutputStreamWriter dStream = new OutputStreamWriter(
                    urlConnection.getOutputStream(), "UTF-8");
            dStream.write(urlParameters);
            dStream.flush();
            dStream.close();
            InputStream is = urlConnection.getInputStream();
            String response = inputStreamToString(is);
            Log.d("***", "here");
            SignUpActivity.response = response;
            return response;


        } catch (MalformedURLException e) {
            Log.d("****", "1");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("****", "2");
            return null;
        }

    }

}


