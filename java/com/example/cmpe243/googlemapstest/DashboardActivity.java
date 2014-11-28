package com.example.cmpe243.googlemapstest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class DashboardActivity extends Activity{

    public static final String DASHBOARD_TAG = "DASHBOARD_TAG";

    TextView textView, s1TextView, s2TextView, s3TextView;
    GestureDetector gestureDetector;

    Button startStopButton, driveModeButton, speedModeButton;
    Button startButton, stopButton, slowButton, normalButton, turboButton;

    ImageView car, background, speedGauge, speedNeedle;
    ImageView l1, l2, l3, l4, r1, r2, r3, r4, c1, c2, c3, c4, b1, b2, b3, b4;
    ImageView infoButton, navigationButton;

    public static final int TEXT_SIZE = 60;

    int sensor_front_left, sensor_front_center, sensor_front_right, sensor_back;

    private int count = 0;

    AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        textView = (TextView) findViewById(R.id.dashboardTextView);
        textView.setTextSize(60);

        s1TextView = (TextView) findViewById(R.id.s1TextView);
        //s2TextView = (TextView) findViewById(R.id.s2TestView);
        s3TextView = (TextView) findViewById(R.id.s3TextView);

        s1TextView.setTextSize(TEXT_SIZE);
        //s2TextView.setTextSize(TEXT_SIZE);
        s3TextView.setTextSize(TEXT_SIZE);

        //LEFT
        l1 = (ImageView)findViewById(R.id.sensorGL1ImageView);
        l2 = (ImageView)findViewById(R.id.sensorGL2ImageView);
        l3 = (ImageView)findViewById(R.id.sensorGL3ImageView);
        l4 = (ImageView)findViewById(R.id.sensorGL4ImageView);

        //RIGHT
        r1 = (ImageView)findViewById(R.id.sensorGR1ImageView);
        r2 = (ImageView)findViewById(R.id.sensorGR2ImageView);
        r3 = (ImageView)findViewById(R.id.sensorGR3ImageView);
        r4 = (ImageView)findViewById(R.id.sensorGR4ImageView);

        //CENTER
        c1 = (ImageView)findViewById(R.id.sensorGC1ImageView);
        c2 = (ImageView)findViewById(R.id.sensorGC2ImageView);
        c3 = (ImageView)findViewById(R.id.sensorGC3ImageView);
        c4 = (ImageView)findViewById(R.id.sensorGC4ImageView);

        //BACK
        b1 = (ImageView)findViewById(R.id.sensorBC1ImageView);
        b2 = (ImageView)findViewById(R.id.sensorBC2ImageView);
        b3 = (ImageView)findViewById(R.id.sensorBC3ImageView);
        b4 = (ImageView)findViewById(R.id.sensorBC4ImageView);

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

        car = (ImageView)findViewById(R.id.carSensor);
        background = (ImageView)findViewById(R.id.background_animation);
        speedGauge = (ImageView)findViewById(R.id.speedImageView);
        speedNeedle = (ImageView)findViewById(R.id.needleImageView);
        infoButton = (ImageView)findViewById(R.id.infoImageView);
        navigationButton = (ImageView)findViewById(R.id.compassImageView);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(DashboardActivity.this, "You just clicked me!", Toast.LENGTH_LONG).show();

            }
        });

        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ActivityMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });

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
            }
        });

        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blinkAnimation(normalButton);
                BluetoothSend sendDest = new BluetoothSend("g" + "37.334633" + ","
                        + "-121.880774" + "$");
                sendDest.start();
            }
        });

        turboButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blinkAnimation(turboButton);

                BluetoothSend sendDest = new BluetoothSend("g" + 37.334804 + ","
                        + 121.880913 + "$");
                sendDest.start();
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
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blinkAnimation(stopButton);
                BluetoothSend sendStop = new BluetoothSend("b");
                sendStop.start();
            }
        });

        speedGauge.setMaxHeight(600);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)car.getLayoutParams();
        params.width = 400;
        params.height = 400;

        Intent intent = getIntent();
        String message = intent.getStringExtra(ActivityMain.EXTRA_ACTIVITY);

        gestureDetector = new GestureDetector(new SwipeGestureDetector(this, MovingCarActivity.class,
                FreeRunActivity.class));
        background.setBackgroundResource(R.drawable.dashboard_background_normal);

        animation = (AnimationDrawable)background.getBackground();
        animation.start();

        s1TextView.setVisibility(View.INVISIBLE);
        s3TextView.setVisibility(View.INVISIBLE);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count = IncomingDataChecker.count;
                        s1TextView.setText(""+count);
                        count +=10;
                        //s2TextView.setText(""+count);
                        count +=1;
                        s3TextView.setText(""+count);

                        sensor_front_left = IncomingDataChecker.front_left;
                        sensor_front_center = IncomingDataChecker.front_center;
                        sensor_front_right = IncomingDataChecker.front_right;
                        sensor_back = IncomingDataChecker.back_sensor;

                        Log.d(DASHBOARD_TAG, ""+sensor_front_left+ " " +sensor_front_center+ " "+ sensor_front_right + " "+sensor_back );


                        if(sensor_front_left <= 2)
                        {
                            l1.setVisibility(View.VISIBLE);
                            l2.setVisibility(View.VISIBLE);
                            l3.setVisibility(View.VISIBLE);
                            l4.setVisibility(View.VISIBLE);
                        }
                        else if(sensor_front_left <=5 && sensor_front_left >= 3)
                        {
                            l1.setVisibility(View.VISIBLE);
                            l2.setVisibility(View.VISIBLE);
                            l3.setVisibility(View.VISIBLE);
                            l4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_left <= 8 && sensor_front_left >= 6)
                        {
                            l1.setVisibility(View.VISIBLE);
                            l2.setVisibility(View.VISIBLE);
                            l3.setVisibility(View.INVISIBLE);
                            l4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_left <= 11 && sensor_front_left >=9)
                        {
                            l1.setVisibility(View.VISIBLE);
                            l2.setVisibility(View.INVISIBLE);
                            l3.setVisibility(View.INVISIBLE);
                            l4.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            l1.setVisibility(View.INVISIBLE);
                            l2.setVisibility(View.INVISIBLE);
                            l3.setVisibility(View.INVISIBLE);
                            l4.setVisibility(View.INVISIBLE);
                        }

                        if(sensor_front_center <= 2)
                        {
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.VISIBLE);
                            c4.setVisibility(View.VISIBLE);
                        }
                        else if(sensor_front_center <=5 && sensor_front_center >= 3)
                        {
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.VISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_center <= 8 && sensor_front_center >= 6)
                        {
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_center <= 11 && sensor_front_center >=9)
                        {
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.INVISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                        }

                        if(sensor_front_right <= 2)
                        {
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.VISIBLE);
                            r4.setVisibility(View.VISIBLE);
                        }
                        else if(sensor_front_right <=5 && sensor_front_right >= 3)
                        {
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.VISIBLE);
                            r4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_right <= 8 && sensor_front_right >= 6)
                        {
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.INVISIBLE);
                            r4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_right <= 11 && sensor_front_right >=9)
                        {
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.INVISIBLE);
                            r3.setVisibility(View.INVISIBLE);
                            r4.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            r1.setVisibility(View.INVISIBLE);
                            r2.setVisibility(View.INVISIBLE);
                            r3.setVisibility(View.INVISIBLE);
                            r4.setVisibility(View.INVISIBLE);
                        }

                        if(sensor_back <= 2)
                        {
                            b1.setVisibility(View.VISIBLE);
                            b2.setVisibility(View.VISIBLE);
                            b3.setVisibility(View.VISIBLE);
                            b4.setVisibility(View.VISIBLE);
                        }
                        else if(sensor_back <=5 && sensor_back >= 3)
                        {
                            b1.setVisibility(View.VISIBLE);
                            b2.setVisibility(View.VISIBLE);
                            b3.setVisibility(View.VISIBLE);
                            b4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_back <= 8 && sensor_back >= 6)
                        {
                            b1.setVisibility(View.VISIBLE);
                            b2.setVisibility(View.VISIBLE);
                            b3.setVisibility(View.INVISIBLE);
                            b4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_back <= 11 && sensor_back >=9)
                        {
                            b1.setVisibility(View.VISIBLE);
                            b2.setVisibility(View.INVISIBLE);
                            b3.setVisibility(View.INVISIBLE);
                            b4.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            b1.setVisibility(View.INVISIBLE);
                            b2.setVisibility(View.INVISIBLE);
                            b3.setVisibility(View.INVISIBLE);
                            b4.setVisibility(View.INVISIBLE);
                        }


                    }
                });

            }
        },0,100);

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

    class MyAnimationRoutine extends TimerTask{
        MyAnimationRoutine(){

        }

        @Override
        public void run() {
            animation.start();

        }
    }

    class MyAnimationRoutine2 extends TimerTask{
        MyAnimationRoutine2(){

        }
        @Override
        public void run() {
            animation.stop();
        }
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
        getMenuInflater().inflate(R.menu.dashboard, menu);
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
