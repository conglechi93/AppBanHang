package com.midterm.shopapp.ulti;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnection {
    public static boolean haveNetWorkConnecttion(Context context){
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for(NetworkInfo ni: networkInfos){
            if(ni.getTypeName().equalsIgnoreCase("WIFI"))
                if(ni.isConnected())
                    haveConnectedWifi = true;

            if(ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if(ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedMobile || haveConnectedWifi;
    }

    public  static void ShowToast_Short(Context context,String tb){
        Toast.makeText(context,tb,Toast.LENGTH_SHORT).show();
    }
}
