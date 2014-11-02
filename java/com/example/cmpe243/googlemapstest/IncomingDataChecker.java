package com.example.cmpe243.googlemapstest;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by jayanthnallapothula on 10/27/14.
 *
 */
public class IncomingDataChecker extends AsyncTask<String,Void,Void> {

    public static final String MAINACTIVITY_TAG = "Incoming Data Checker";

    public static final String testString = "Test";
    public static final String speedString = "speed";
    public static final String sensorString = "s1";
    public static final String gpsString = "lat";

    public enum recData{
        speed,
        sensor,
        gps,
        test,
        unrecognized
    }


    @Override
    protected Void doInBackground(String... data) {

        recData incomingData = recData.unrecognized;
        Log.d(MAINACTIVITY_TAG, data[0]);
        try {
            if(data[0].substring(0, testString.length()).equals(testString)){
                incomingData = recData.test;
            }   else if(data[0].substring(0, sensorString.length()).equals(sensorString)){
                incomingData = recData.sensor;
            }   else if(data[0].substring(0, speedString.length()).equals(speedString)){
                incomingData = recData.speed;
            }   else if(data[0].substring(0, gpsString.length()).equals(gpsString)){
                incomingData = recData.gps;
            }   else {
                incomingData = recData.unrecognized;
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        switch (incomingData)
        {
            case test:
                Log.d(MAINACTIVITY_TAG, "data is: " + testString);
                break;

            case speed:
                Log.d(MAINACTIVITY_TAG, "data is: " + "Speed!");
                break;

            case gps:
                Log.d(MAINACTIVITY_TAG, "data is: " + "GPS!");
                break;

            case sensor:
                Log.d(MAINACTIVITY_TAG, "data is: " + "Sensor!");
                break;

            default:
                Log.d(MAINACTIVITY_TAG, "Un-recognized data");
                break;

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }
}
