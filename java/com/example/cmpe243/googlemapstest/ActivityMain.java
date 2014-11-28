package com.example.cmpe243.googlemapstest;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.gesture.GestureOverlayView;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class ActivityMain extends FragmentActivity{

    //String stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin=37.335982,-121.88" +
    //        "0960&destination=37.335194,-121.878582&sensor=false&mode=walking";
    //37.335999,-121.878566
    String stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin=37.336474,-121.878886" +
            "&destination=37.335619,-121.878540&sensor=false&mode=walking";


    public static final String MAINACTIVITY_TAG = "MAIN_ACTIVITY";
    private static final String startMsg = "start";
    private static final String stopMsg = "stops";
    private static final String testMsg = "tests";

    static final LatLng HOME = new LatLng(37.327388, -121.898719);
    static final LatLng SJSU = new LatLng(37.335656, -121.881201);

    String dest_lat = null;
    String dest_long = null;

    MarkerOptions prev_marker = null, current_marker = null;

    private GoogleMap map;
    BluetoothSocket mBluetoothSocket =  null;

    Button sendButton, stopButton1, startButton1;
    Button connectButton;

    ImageView homeButton;

    boolean connected;

    public static String switchActivityString = "Successfully reached this activity!!";
    public final static String EXTRA_ACTIVITY = "Activity_Main";
    public final static String s1 = "s1";
    public final static String s2 = "s2";
    public final static String s3 = "s3";

    GestureDetector gestureDetector;

    Button startStopButton, driveModeButton, speedModeButton;
    Button startButton, stopButton, slowButton, normalButton, turboButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activity_main);

        sendButton = (Button)findViewById(R.id.sendDataButton);
        startButton1 = (Button)findViewById(R.id.startButton1);
        stopButton1 = (Button)findViewById(R.id.stopButton1);

        homeButton = (ImageView)findViewById(R.id.homeImageView);

        //Row-1 Buttons
        startStopButton = (Button)findViewById(R.id.startStopButton);
        speedModeButton = (Button)findViewById(R.id.speedModeButton);
        driveModeButton = (Button)findViewById(R.id.driveModeButton);

        //Row-2 Buttons
        startButton = (Button)findViewById(R.id.startButton);
        stopButton = (Button)findViewById(R.id.stopButton);
        slowButton = (Button)findViewById(R.id.slowSpeedButton);
        normalButton = (Button)findViewById(R.id.normalSpeedButton);
        turboButton = (Button)findViewById(R.id.turboSpeedButton);

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                startButton.setActivated(true);
                stopButton.setActivated(true);

                slowButton.setVisibility(View.INVISIBLE);
                turboButton.setVisibility(View.INVISIBLE);
                normalButton.setVisibility(View.INVISIBLE);

                normalButton.setActivated(false);
                turboButton.setActivated(false);
                slowButton.setActivated(false);

                Log.d(MAINACTIVITY_TAG, "Start/Stop");
            }
        });

        speedModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slowButton.setVisibility(View.VISIBLE);
                normalButton.setVisibility(View.VISIBLE);
                turboButton.setVisibility(View.VISIBLE);
                normalButton.setActivated(true);
                turboButton.setActivated(true);
                slowButton.setActivated(true);

                startButton.setVisibility(View.INVISIBLE);
                stopButton.setVisibility(View.INVISIBLE);

                startButton.setActivated(false);
                stopButton.setActivated(false);

                Log.d(MAINACTIVITY_TAG, "Speed Mode");

            }
        });

        driveModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        slowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blinkAnimation(slowButton);
                BluetoothSend sendDest = new BluetoothSend("g" + 37.334804 + ","
                        + 121.880913 + "$");
                sendDest.start();

                Log.d(MAINACTIVITY_TAG, "Slow Mode");
            }
        });

        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blinkAnimation(normalButton);
                BluetoothSend sendDest = new BluetoothSend("g" + "37.334633" + ","
                        + "-121.880774" + "$");
                sendDest.start();

                Log.d(MAINACTIVITY_TAG, "Normal Mode");
            }
        });

        turboButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blinkAnimation(turboButton);

                BluetoothSend sendDest = new BluetoothSend("g" + 37.334804 + ","
                        + 121.880913 + "$");
                sendDest.start();

                Log.d(MAINACTIVITY_TAG, "Turbo Mode");
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blinkAnimation(startButton);
                BluetoothSend sendStart = new BluetoothSend("a");
                sendStart.start();
                BluetoothSend sendDest = new BluetoothSend("g" + 37.335619 + ","
                        + -121.878540 + "$");
                sendDest.start();

                Log.d(MAINACTIVITY_TAG, "Start");
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blinkAnimation(stopButton);
                BluetoothSend sendStop = new BluetoothSend("b");
                sendStop.start();

                Log.d(MAINACTIVITY_TAG, "Stop");
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, DashboardActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(ActivityMain.this, SplashScreenActivity.class);
                //intent.putExtra(EXTRA_ACTIVITY, switchActivityString);
                //startActivity(intent);

                BluetoothSend sendDest = new BluetoothSend("g" + DrawLinesOnMap.finalDestination_latitude + ","
                        + DrawLinesOnMap.finalDestination_longitude + "$");
                //BluetoothSend sendDest = new BluetoothSend("g" + 37.335619 + ","
                //                + -121.878540 + "$");
                sendDest.start();
            }
        });

        startButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(connected) {
                    BluetoothSend sendData = new BluetoothSend(startMsg);
                    sendData.start();
                //}
            }
        });

        stopButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(connected) {
                    BluetoothSend sendData = new BluetoothSend(stopMsg);
                    sendData.start();
                //}
            }
        });

        connectButton = (Button)findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this, SplashScreenActivity.class);
                startActivity(intent);

            }
        });

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
        HttpConnection httpConnection = new HttpConnection(map);
        httpConnection.execute(stringUrl);

        gestureDetector = new GestureDetector(new SwipeGestureDetector(this, DashboardActivity.class,
                FreeRunActivity.class));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                MarkerOptions marker = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                        .title("Destination");
                dest_lat = Double.toString(latLng.latitude);
                dest_long = Double.toString(latLng.longitude);
                Log.i(MAINACTIVITY_TAG, "Destination = " + dest_lat + ", " + dest_long);
                map.addMarker(marker);
            }
        });

    }

    void blinkAnimation(Button button){

        Animation animation = new AlphaAnimation(1,0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(10);
        animation.setRepeatMode(Animation.REVERSE);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) button.getLayoutParams();
        button.setLayoutParams(params);
        //button.setTextSize(40f);
        //button.setTextColor(Color.BLUE);
        button.startAnimation(animation);
        //button.setTextColor(Color.RED);
        turboButton.setVisibility(View.INVISIBLE);
        normalButton.setVisibility(View.INVISIBLE);
        slowButton.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

