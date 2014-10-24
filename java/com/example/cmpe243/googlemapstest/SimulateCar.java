package com.example.cmpe243.googlemapstest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by jayanthnallapothula on 10/23/14.
 *
 */
public class SimulateCar extends AsyncTask<Integer,Void,Integer> {

    public static final String MAIN_TAG = "Simulate Car";
    LatLng[] latLngs = new LatLng[7];
    int i = 0;
    int count =0;
    GoogleMap map;
    public static Marker[] markers = new Marker[7];
    Marker m;
    MarkerOptions markerOptions;

    public SimulateCar(LatLng[] latLng) {
        this.latLngs = latLng;
        Log.v(MAIN_TAG, "" + i);
        this.map = JsonParsing.map;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        map.addMarker(new MarkerOptions().position(latLngs[0]));

    }

    @Override
    protected Integer doInBackground(Integer...index) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;

        return index[0];
    }

    @Override
    protected void onPostExecute(Integer index) {
        Log.d(MAIN_TAG, "Index= " + index);
        if(index < 3) {
            markers[index] = map.addMarker(new MarkerOptions().position(latLngs[index]).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.car_clipart)));

        }
        else if(index >= 3 && index < 5){
            markers[index] = map.addMarker(new MarkerOptions().position(latLngs[index]).icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.car_clipart)).rotation(270));
        }
        else{
            markers[index] = map.addMarker(new MarkerOptions().position(latLngs[index]).icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.car_clipart)).rotation(180));
        }
        if(index > 0){

            markers[index-1].setVisible(false);
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs[index], 17));

    }
}
