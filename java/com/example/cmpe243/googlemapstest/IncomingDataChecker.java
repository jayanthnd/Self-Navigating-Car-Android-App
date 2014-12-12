package com.example.cmpe243.googlemapstest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Jayanth Nallapothula on 10/27/14.
 *
 */
public class IncomingDataChecker extends AsyncTask<String,Void,Void> {

    public static final String MAINACTIVITY_TAG = "Incoming Data Checker";

    public static final String testString = "Test";
    public static final String speedString = "speed";
    public static final String sensorString = "s1";
    public static final String batteryString = "bat";
    public static final String lightString = "light";
    public static final String gpsString = "lat";
    public static final String startString = "start";
    public static final String stopString = "stop";
    public static final String headingString = "heading";
    public static final String driveSpeed_normal = "normal";
    public static final String driveSpeed_slow = "slow";
    public static final String driveSpeed_turbo = "turbo";

    public static final String speedDelimiter = "[=, ]+";
    public static final String sensorDelimiter = "[=, ]+";
    public static final String gpsDelimiter = "[=, ]+";
    public static final String lightDelimiter = "[=, ]+";
    public static final String batteryDelimiter = "[=, ]+";
    public static final String commonDelimiter = "[=, ]+";

    public static final String max_speed = "12";
    public String [] speedToken;
    public String [] sensorToken;
    public String [] gpsToken;
    public String [] batteryToken;
    public String [] lightToken;
    public String [] headingToken;

    public static String s1 = null, s2 = null, s3 = null, s4=null;
    public static int front_left=0, front_center=0, front_right=0, back_sensor=0;
    public static String current_location = null;

    public static String latValue = null, longValue = null;

    public static int count = 0;

    public static boolean start = false;
    public static boolean stop = true;
    public static boolean carSpeed_normal = true, carSpeed_slow = false, carSpeed_turbo = false;

    public static String headingValue = null, speedValue = null, batteryValue = null, lightValue = null;

    public enum recData{
        speed,
        sensor,
        gps,
        battery,
        light,
        test,
        start,
        stop,
        heading,
        speed_normal,
        speed_slow,
        speed_turbo,
        unrecognized
    }

    @Override
    protected Void doInBackground(String... data) {

        count++;
        recData incomingData = recData.unrecognized;
        int i = 0;
        Log.d(MAINACTIVITY_TAG, data[0]);
        try {
            if(data[0].substring(0, testString.length()).equals(testString)){
                incomingData = recData.test;
            }   else if(data[0].substring(0, sensorString.length()).equals(sensorString)){
                incomingData = recData.sensor;
                sensorToken = data[0].split(sensorDelimiter);
            }   else if(data[0].substring(0, speedString.length()).equals(speedString)){
                incomingData = recData.speed;
                speedToken = data[0].split(speedDelimiter);
            }   else if(data[0].substring(0, gpsString.length()).equals(gpsString)){
                incomingData = recData.gps;
                gpsToken = data[0].split(gpsDelimiter);
            }   else if (data[0].substring(0, startString.length()).equals(startString)){
                incomingData = recData.start;
            }   else if (data[0].substring(0, stopString.length()).equals(stopString)){
                incomingData = recData.stop;
            }   else if (data[0].substring(0, batteryString.length()).equals(batteryString)){
                incomingData = recData.battery;
                batteryToken = data[0].split(batteryDelimiter);
            }   else if (data[0].substring(0, headingString.length()).equals(headingString)){
                incomingData = recData.heading;
                headingToken = data[0].split(commonDelimiter);
            }   else if (data[0].substring(0, lightString.length()).equals(lightString)){
                incomingData = recData.light;
                lightToken = data[0].split(commonDelimiter);
            }   else if (data[0].substring(0, driveSpeed_normal.length()).equals(driveSpeed_normal)){
                incomingData = recData.speed_normal;
            }   else if (data[0].substring(0, driveSpeed_slow.length()).equals(driveSpeed_slow)){
                incomingData = recData.speed_slow;
            }   else if (data[0].substring(0, driveSpeed_turbo.length()).equals(driveSpeed_turbo)){
                incomingData = recData.speed_turbo;
            }
            else {
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
                for(i=0; i<speedToken.length; i++){
                    if(speedToken[i].equals("speed")) {
                        i++;
                        break;
                    }
                }
                speedValue = speedToken[i];
                Log.d(MAINACTIVITY_TAG, "Speed is: " + speedValue);
                break;

            case gps:
                Log.d(MAINACTIVITY_TAG, "data is: " + "GPS!");
                int count = 0;
                for(i=0; i<gpsToken.length; i++) {
                    if (gpsToken[i].equals("lat")) {
                        i++;
                        count++;
                        Log.d(MAINACTIVITY_TAG, "Token = " + gpsToken[i] + " Count = " + count);
                        break;

                    }
                }
                latValue = gpsToken[i];
                for(i=count;i<gpsToken.length; i++){
                    if(gpsToken[i].equals("long")){
                        i++;
                        Log.d(MAINACTIVITY_TAG, "Token = " + gpsToken[i] + " Count = " + count);
                        break;
                    }
                }
                longValue = gpsToken[i];
                Log.d(MAINACTIVITY_TAG, "Latitude is: " + latValue + " , Longitude is: " + longValue);

                break;

            case sensor:

                Log.d(MAINACTIVITY_TAG, "data is: " + "Sensor!");

                for(i=0;i<sensorToken.length;i++){
                    try {
                        if(sensorToken[i].equals("s1")){
                            if(sensorToken.length > 1) {
                                i++;
                                s1 = sensorToken[i++];
                                front_left = Integer.parseInt(s1);
                            }
                            if(sensorToken.length > 2) {
                                s2 = sensorToken[i++];
                                front_center = Integer.parseInt(s2);
                            }
                            if(sensorToken.length > 3) {
                                s3 = sensorToken[i++];
                                front_right = Integer.parseInt(s3);
                            }
                            if(sensorToken.length > 4) {
                                s4 = sensorToken[i];
                                back_sensor = Integer.parseInt(s4);
                            }
                            break;
                        }
                    } catch (NumberFormatException e) {
                        s1 = s2 = s3 = s4 = null;
                    }
                }
                Log.d(MAINACTIVITY_TAG, "Sensor values: s1="+s1+" s2="+s2+" s3="+s3+"s4="+s4);

                break;

            case start:
                Log.d(MAINACTIVITY_TAG, "data is: START!");
                start = true;
                stop = false;
                break;

            case stop:
                Log.d(MAINACTIVITY_TAG, "data is: STOP!");
                stop = true;
                start = false;
                break;

            case speed_normal:
                Log.d(MAINACTIVITY_TAG, "data is: Mode ----> NORMAL!");
                carSpeed_normal = true;
                break;

            case speed_slow:
                Log.d(MAINACTIVITY_TAG, "data is: Mode ----> SLOW!");
                carSpeed_slow = true;
                break;

            case speed_turbo:
                Log.d(MAINACTIVITY_TAG, "data is: Mode ----> TURBO!");
                carSpeed_turbo = true;
                break;

            case heading:
                Log.d(MAINACTIVITY_TAG, "data is: " + "Heading!");
                for(i=0; i<headingToken.length; i++){
                    if(headingToken[i].equals(headingString)) {
                        i++;
                        break;
                    }
                }
                headingValue = headingToken[i];
                Log.d(MAINACTIVITY_TAG, "Heading is: " + headingValue);
                break;

            case battery:
                Log.d(MAINACTIVITY_TAG, "data is: " + "Battery!");
                for(i=0; i<batteryToken.length; i++){
                    if(batteryToken[i].equals(batteryString)) {
                        i++;
                        break;
                    }
                }
                batteryValue = batteryToken[i];
                Log.d(MAINACTIVITY_TAG, "Battery is: " + batteryValue);
                break;

            case light:
                Log.d(MAINACTIVITY_TAG, "data is: " + "Light!");
                for(i=0; i<lightToken.length; i++){
                    if(lightToken[i].equals(lightString)) {
                        i++;
                        break;
                    }
                }
                lightValue = lightToken[i];
                Log.d(MAINACTIVITY_TAG, "Light is: " + lightValue);
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
