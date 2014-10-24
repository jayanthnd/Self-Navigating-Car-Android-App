package com.example.cmpe243.googlemapstest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ActivityMain extends FragmentActivity {

    String stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin=37.335982,-121.88" +
            "0960&destination=37.333576,-121.878582&sensor=false&mode=walking";

    public static final String MAINACTIVITY_TAG = "MAIN_ACTIVITY";
    static final LatLng HOME = new LatLng(37.327388, -121.898719);
    static final LatLng SJSU = new LatLng(37.335656, -121.881201);
    private GoogleMap map;
    BluetoothConnection bluetoothConnection;
    BluetoothSocket mBluetoothSocket =  null;

    Button sendButton;
    Button connectButton;
    public static EditText sendDataText;

    boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_main);

        sendButton = (Button)findViewById(R.id.sendDataButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startThread();
                if(connected) {
                    BluetoothSend sendData = new BluetoothSend();
                    sendData.start();
                }
            }
        });

        connectButton = (Button)findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BluetoothConnect connectBluetooth = new BluetoothConnect(mBluetoothSocket,
                        ActivityMain.this, ActivityMain.this);
                //connectBluetooth.start();
                connected = connectBluetooth.getBluetoothConnection();
                if(connected){
                    BluetoothReceive bluetoothReceive = new BluetoothReceive();
                    bluetoothReceive.listenForData();
                }
            }
        });

        sendDataText = (EditText)findViewById(R.id.sendDataEditText);
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
        HttpConnection httpConnection = new HttpConnection(map);
        httpConnection.execute(stringUrl);

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

