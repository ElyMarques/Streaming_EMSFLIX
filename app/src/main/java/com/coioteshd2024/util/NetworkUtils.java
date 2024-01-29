package com.coioteshd2024.util;

import static android.content.Context.WIFI_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


public class NetworkUtils {


    private NetworkUtils(){


    }


    @SuppressLint("WrongConstant")
  public static boolean isWifiConnected(Context context) {

    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

   if (manager == null) return true;

   Network network = manager.getActiveNetwork();

    NetworkCapabilities capabilities = manager.getNetworkCapabilities(network);

        return !capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI | NetworkCapabilities.TRANSPORT_CELLULAR);
  }



    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable();
        }
        return false;
    }


    public static String getMacAdress(Context context) {

        WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }


}
