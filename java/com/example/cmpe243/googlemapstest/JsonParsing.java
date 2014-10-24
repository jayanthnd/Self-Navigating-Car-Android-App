package com.example.cmpe243.googlemapstest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jayanthnallapothula on 10/23/14.
 *
 */
public class JsonParsing extends AsyncTask<String,Void,Void>{

    public static final String MAIN_TAG = "JSON Parsing";
    static int arrayLength;
    static LatLng[] latLngs = new LatLng[4];
    static LatLng [] midPoints = new LatLng[4];

    public static GoogleMap map;

    JsonParsing(GoogleMap map)
    {
        this.map = map;
    }

    @Override
    protected Void doInBackground(String... strings) {

        try {
            JSONObject jsonObject = new JSONObject(strings[0]);
            JSONArray jsonArray = jsonObject.getJSONArray("routes");
            JSONObject route = jsonArray.getJSONObject(0);
            JSONArray jsonArray1 = route.getJSONArray("legs");
            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
            JSONArray jsonArray2 = jsonObject1.getJSONArray("steps");
            arrayLength = jsonArray2.length();
            String logs = jsonArray2.toString();
            Log.v("2nd Log: " + MAIN_TAG, logs);
            for (int i = 0; i < arrayLength; i++) {
                JSONObject step = jsonArray2.getJSONObject(i);
                JSONObject start = step.getJSONObject("start_location");
                Double startLat = start.getDouble("lat");
                Double startLng = start.getDouble("lng");
                latLngs[i] = new LatLng(startLat, startLng);
                Log.v("Start Location: \n" + "Lat: " + startLat + "\nLng: " + startLng, "");
            }
            for (int i = 0; i < latLngs.length - 1; i++) {
                Log.v(MAIN_TAG, "Array Length: " + latLngs.length);
                double startLat = latLngs[i].latitude;
                Log.v(MAIN_TAG, "" + startLat);
                double startLong = latLngs[i].longitude;
                double endLat = latLngs[i + 1].latitude;
                double endLong = latLngs[i + 1].longitude;
                midPoints[i] = new LatLng((startLat + endLat) / 2, (startLong + endLong) / 2);
                Log.v(MAIN_TAG, "" + midPoints[i].latitude + " " + midPoints[i].longitude);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < midPoints.length - 1; i++) {
            Log.d(MAIN_TAG, "" + midPoints[i].latitude + " " + midPoints[i].longitude);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        DrawLinesOnMap drawLines = new DrawLinesOnMap();
        drawLines.drawConnectingMarkers();
    }
}
