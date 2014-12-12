package com.example.cmpe243.googlemapstest;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.gesture.GestureOverlayView;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MotionEventCompat;
import android.text.format.Time;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ActivityMain extends FragmentActivity{

    public static String stringUrl = null;


    public static final String MAINACTIVITY_TAG = "MAIN_ACTIVITY";
    private static final String startMsg = "start";
    private static final String stopMsg = "stops";
    private static final String testMsg = "tests";

    public static Double current_location_lat, current_location_long;
    public static LatLng current_location;
    public static MarkerOptions current_location_marker = null;
    Marker current_marker = null;

    static final LatLng HOME = new LatLng(37.327388, -121.898719);
    static final LatLng SJSU = new LatLng(37.335656, -121.881201);

    String dest_lat = null;
    String dest_long = null;

    public static float dest_long_float, dest_lat_float;

    MarkerOptions marker = null;

    public static GoogleMap map;
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

    public final int CLASS_ID = 0;

    GestureDetector gestureDetector;

    public static Button startStopButton, driveModeButton, speedModeButton;
    public static Button startButton, stopButton, slowButton, normalButton, turboButton;

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

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.336474,-121.878886), 15));

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_stop_button(CLASS_ID);
            }
        });

        speedModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speed_button(CLASS_ID);

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
                slow_speed_button();
            }
        });

        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normal_speed_button();
            }
        });

        turboButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turbo_speed_button(getApplicationContext());

            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              start_button(getApplicationContext());
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop_button(getApplicationContext());
                IncomingDataChecker.stop = true;
                IncomingDataChecker.start = false;
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

            }
        });

        startButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        stopButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        gestureDetector = new GestureDetector(new SwipeGestureDetector(this, DashboardActivity.class,
                FreeRunActivity.class));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if(marker != null){
                    map.clear();
                }
                dest_lat = Double.toString(latLng.latitude);
                dest_long = Double.toString(latLng.longitude);

                dest_lat_float = (float)latLng.latitude;
                dest_long_float = (float)latLng.longitude;

                marker = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude))
                        .title("Destination").title("Destination").snippet("Latitude: " + dest_lat + "\n" + "Longitide: "
                        + dest_long);

                Log.i(MAINACTIVITY_TAG, "Destination = " + dest_lat + ", " + dest_long);
                map.addMarker(marker);

            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(current_marker != null){
                            current_marker.remove();
                        }

                        if(IncomingDataChecker.latValue != null && IncomingDataChecker.longValue != null) {
                            current_location_lat = Double.parseDouble(IncomingDataChecker.latValue);
                            current_location_long = Double.parseDouble(IncomingDataChecker.longValue);

                            current_location = new LatLng(current_location_lat, current_location_long);
                            //current_marker = map.addMarker(new MarkerOptions().position(current_location)
                            //        .anchor(0.5f, 0.5f).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location)));
//                            current_location_marker = new MarkerOptions().position(current_location)
//                                    .anchor(0.5f, 0.5f).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.current_dot));
//                            map.addMarker(current_location_marker);
                            //map.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 20));
                        }
                    }
                });
            }
        },0, 200);

    }

    void blinkAnimation(Button button){

        Animation animation = new AlphaAnimation(1,0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(10);
        animation.setRepeatMode(Animation.REVERSE);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) button.getLayoutParams();
        button.setLayoutParams(params);
        button.startAnimation(animation);
        turboButton.setVisibility(View.INVISIBLE);
        normalButton.setVisibility(View.INVISIBLE);
        slowButton.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
    }

    public static void start_stop_button(int classID)
    {
        if(classID == 0) {
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


        }   else    {
            DashboardActivity.startButton.setVisibility(View.VISIBLE);
            DashboardActivity.stopButton.setVisibility(View.VISIBLE);
            DashboardActivity.startButton.setActivated(true);
            DashboardActivity.stopButton.setActivated(true);

            DashboardActivity.slowButton.setVisibility(View.INVISIBLE);
            DashboardActivity.turboButton.setVisibility(View.INVISIBLE);
            DashboardActivity.normalButton.setVisibility(View.INVISIBLE);

            DashboardActivity.normalButton.setActivated(false);
            DashboardActivity.turboButton.setActivated(false);
            DashboardActivity.slowButton.setActivated(false);
        }
        Log.d(MAINACTIVITY_TAG, "Start/Stop");

    }

    public static void start_button(Context context)
    {
        Toast.makeText(context, "START", Toast.LENGTH_SHORT).show();
        BluetoothSend sendStart = new BluetoothSend("a");
        sendStart.start();
        IncomingDataChecker.start = true;


    }

    public static void stop_button(Context context)
    {
        Toast.makeText(context, "STOP", Toast.LENGTH_SHORT).show();
        //blinkAnimation(stopButton);
        BluetoothSend sendStop = new BluetoothSend("b");
        sendStop.start();
        IncomingDataChecker.stop = true;
        Log.d(MAINACTIVITY_TAG, "Stop");

    }

    public static void speed_button(int classID)
    {
        if(classID == 0) {
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
        }   else    {
            DashboardActivity.slowButton.setVisibility(View.VISIBLE);
            DashboardActivity.normalButton.setVisibility(View.VISIBLE);
            DashboardActivity.turboButton.setVisibility(View.VISIBLE);
            DashboardActivity.normalButton.setActivated(true);
            DashboardActivity.turboButton.setActivated(true);
            DashboardActivity.slowButton.setActivated(true);

            DashboardActivity.startButton.setVisibility(View.INVISIBLE);
            DashboardActivity.stopButton.setVisibility(View.INVISIBLE);

            DashboardActivity.startButton.setActivated(false);
            DashboardActivity.stopButton.setActivated(false);
        }
        Log.d(MAINACTIVITY_TAG, "Speed Mode");

    }

    public static void slow_speed_button()
    {
        //blinkAnimation(slowButton);
        BluetoothSend sendDest = new BluetoothSend("g" + 37.334804 + ","
                + 121.880913 + "$");
        sendDest.start();
        IncomingDataChecker.carSpeed_normal = false;
        IncomingDataChecker.carSpeed_slow = true;
        IncomingDataChecker.carSpeed_turbo = false;
        Log.d(MAINACTIVITY_TAG, "Slow Mode");

    }

    public static void normal_speed_button()
    {
        //blinkAnimation(normalButton);
        Log.d(MAINACTIVITY_TAG, "Normal Mode");
        IncomingDataChecker.carSpeed_normal = true;
        IncomingDataChecker.carSpeed_slow = false;
        IncomingDataChecker.carSpeed_turbo = false;
        String send_checkpoints = "g";
        for(int i=0; i<=JsonParsing.arrayLength; i++){
            send_checkpoints += JsonParsing.latLngs[i].latitude + "," + JsonParsing.latLngs[i].longitude;
            send_checkpoints += ",";
        }
        //send_checkpoints += dest_lat + "," + dest_long + "$";
        send_checkpoints += "$";
        Log.d(MAINACTIVITY_TAG, "Checkpoints = " + send_checkpoints);

        Log.d(MAINACTIVITY_TAG, "Start");
        BluetoothSend sendCheckpoints = new BluetoothSend(send_checkpoints);
        sendCheckpoints.start();

    }

    public static void turbo_speed_button(Context context)
    {
        //blinkAnimation(turboButton);
        IncomingDataChecker.carSpeed_normal = false;
        IncomingDataChecker.carSpeed_slow = false;
        IncomingDataChecker.carSpeed_turbo = true;
        Log.d(MAINACTIVITY_TAG, "Turbo Mode");
        Toast.makeText(context, "ROUTE", Toast.LENGTH_SHORT).show();
        if(dest_lat_float != 0 && dest_long_float != 0) {
            stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin=37.336474,-121.878886" +
                    "&destination=" + dest_lat_float + "," + dest_long_float+"&sensor=false&mode=walking";
            HttpConnection httpConnection = new HttpConnection(map);
            httpConnection.execute(stringUrl);


        }
        else
            Toast.makeText(context, "Not a valid location!", Toast.LENGTH_LONG).show();

    }

    public void showToast(String text, Context context){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
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

