package com.example.cmpe243.googlemapstest;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jayanthnallapothula on 10/23/14.
 *
 */
public class BluetoothSend extends Thread{

    public static enum MSG{
        START_MSG,
        STOP_MSG,
        TURBO_MODE_MSG,
        SLOW_MODE_MSG,
        NORMAL_MODE_MSG,
        FREE_RUN_MODE,
        MAP_MODE,
        HOME_MODE,
        LAT_LONG
    }
//    public String lat_long = JsonParsing.midPoints[JsonParsing.midPoints.length-1].toString();
  //  public static String[] msg_data = {"start", "stop", "turbo", "slow", "normal", "free", "map",
    //        "home"};

    public static final String MAINACTIVITY_TAG = "Bluetooth Send";
    String sendBuffer;
    BluetoothSend(String buffer)
    {
        sendBuffer = buffer;

    }

    public void run(){

        //Log.d("Bluetooth Send: lat_long: ", lat_long);
        //String stringBuffer = ActivityMain.sendDataText.getText().toString() + "\0";
        //String stringBuffer = "5Hello World\0";      // Test String
        //char stringBuffer = 'j';      // Test String
        //byte [] buffer = stringBuffer.getBytes();
        //byte buffer = (byte)stringBuffer;
        byte [] buffer = sendBuffer.getBytes();
        try{

            BluetoothConnect.mmOutputStream.write(buffer);
            BluetoothConnect.mmOutputStream.flush();
            Log.e(MAINACTIVITY_TAG, "Data send successful");
        } catch (IOException e){
            Log.e(MAINACTIVITY_TAG, "Error data send");
        }
    }
}
