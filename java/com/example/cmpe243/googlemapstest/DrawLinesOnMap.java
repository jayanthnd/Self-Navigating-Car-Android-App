package com.example.cmpe243.googlemapstest;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by jayanthnallapothula on 10/23/14.
 *
 */
public class DrawLinesOnMap {

    GoogleMap map;
    int arrayLength;
    static LatLng[] carPositions;

    DrawLinesOnMap()
    {
        this.map = JsonParsing.map;
        this.arrayLength = JsonParsing.arrayLength;
    }

    void drawConnectingMarkers()
    {
        if(map != null){
            for(int i=0; i<arrayLength; i++) {
                map.addMarker(new MarkerOptions().position(JsonParsing.latLngs[i]).title("START POINT").snippet("This is the start point"));

                if(i <= arrayLength-2){
                    try {
                        Polyline line = map.addPolyline(new PolylineOptions().add(JsonParsing.latLngs[i], JsonParsing.latLngs[i+1])
                                .color(Color.RED).width(5));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }

            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(JsonParsing.latLngs[0], 15));
            LatLng[] carPositions = {JsonParsing.latLngs[0], JsonParsing.midPoints[0], JsonParsing.latLngs[1], JsonParsing.midPoints[1],
                    JsonParsing.latLngs[2], JsonParsing.midPoints[2], JsonParsing.latLngs[3]};

/*
            for(int i=0; i<carPositions.length; i++) {
                SimulateCar simulateCarPosition = new SimulateCar(carPositions);
            //SimulateCar simulateCarPosition = new SimulateCar(carPositions, 1);
                simulateCarPosition.execute(i);

            }*/
        }
    }
}
