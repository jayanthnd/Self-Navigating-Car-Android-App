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
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;


public class DashboardActivity extends Activity{

    public static final String DASHBOARD_TAG = "DASHBOARD_TAG";

    TextView textView, s1TextView, s2TextView, s3TextView;
    GestureDetector gestureDetector;

    public static Button startStopButton, driveModeButton, speedModeButton;
    public static Button startButton, stopButton, slowButton, normalButton, turboButton;

    ImageView car, background, speedGauge, speedNeedle;
    ImageView l1, l2, l3, l4, r1, r2, r3, r4, c1, c2, c3, c4, b1, b2, b3, b4;
    ImageView infoButton, navigationButton;

    public static final int TEXT_SIZE = 60;
    RotateAnimation rotateAnimation = null;

    int sensor_front_left, sensor_front_center, sensor_front_right, sensor_back;

    private int count = 0;

    AnimationDrawable animation;

    public static final int zone1_lower = 0;
    public static final int zone1_upper = 2;
    public static final int zone2_lower = 3;
    public static final int zone2_upper = 5;

    public static final int zone3_lower = 6;
    public static final int zone3_upper = 7;
    public static final int zone4_lower = 8;
    public static final int zone4_upper = 9;

    public final int CLASS_ID = 1;

    float previous_heading = 0f;
    float current_heading = 0f;
    float [] heading_array;
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

        Log.d(DASHBOARD_TAG, getBaseContext().toString());

        s1TextView.setTextSize(TEXT_SIZE);
        //s2TextView.setTextSize(TEXT_SIZE);
        s3TextView.setTextSize(TEXT_SIZE);

        heading_array = new float[2];
        heading_array[0] = 0;
        heading_array[1] = 0;

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
                ActivityMain.start_stop_button(CLASS_ID);

            }
        });

        speedModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityMain.speed_button(CLASS_ID);
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
                ActivityMain.slow_speed_button();
            }
        });

        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityMain.normal_speed_button();
            }
        });

        turboButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityMain.turbo_speed_button(getApplicationContext());
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityMain.start_button(getApplicationContext());
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityMain.stop_button(getApplicationContext());
            }
        });

        speedGauge.setMaxHeight(600);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)car.getLayoutParams();
        params.width = 400;
        params.height = 400;

        Intent intent = getIntent();
        String message = intent.getStringExtra(ActivityMain.EXTRA_ACTIVITY);

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

                        //Log.d(DASHBOARD_TAG, ""+sensor_front_left+ " " +sensor_front_center+ " "+ sensor_front_right + " "+sensor_back );

                        if(IncomingDataChecker.carSpeed_normal)
                            background.setBackgroundResource(R.drawable.dashboard_background_normal);
                        else if(IncomingDataChecker.carSpeed_slow)
                            background.setBackgroundResource(R.drawable.dashboard_background_slow);
                        else if(IncomingDataChecker.carSpeed_turbo)
                            background.setBackgroundResource(R.drawable.dashboard_background_turbo);

                        animation = (AnimationDrawable)background.getBackground();


                        if(IncomingDataChecker.start)
                            animation.start();
                        else
                            animation.stop();

                        if(IncomingDataChecker.headingValue == null) {
                            rotateAnimation = new RotateAnimation(heading_array[0], heading_array[1],
                                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                        }
                        else {
                            if(heading_array[1] != (float)Integer.parseInt(IncomingDataChecker.headingValue) * 20) {
                                heading_array[0] = heading_array[1];
                                heading_array[1] = (float)Integer.parseInt(IncomingDataChecker.headingValue) * 20;
                            }   else    {
                                heading_array[0] = heading_array[1];
                            }
                            //heading_array[0] = (float)Integer.parseInt(IncomingDataChecker.headingValue) * 20;
                            rotateAnimation = new RotateAnimation(heading_array[0], heading_array[1],
                                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                        }

                        rotateAnimation.setInterpolator(new LinearInterpolator());
                        rotateAnimation.setRepeatCount(Animation.INFINITE);
                        //rotateAnimation.setDuration(2500);
                        speedNeedle.startAnimation(rotateAnimation);


                        if(sensor_front_left <= zone1_upper)
                        {
                            l1.setVisibility(View.VISIBLE);
                            l2.setVisibility(View.VISIBLE);
                            l3.setVisibility(View.VISIBLE);
                            l4.setVisibility(View.VISIBLE);
                        }
                        else if(sensor_front_left <= zone2_upper && sensor_front_left >= zone2_lower)
                        {
                            l1.setVisibility(View.VISIBLE);
                            l2.setVisibility(View.VISIBLE);
                            l3.setVisibility(View.VISIBLE);
                            l4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_left <= zone3_upper && sensor_front_left >= zone3_lower)
                        {
                            l1.setVisibility(View.VISIBLE);
                            l2.setVisibility(View.VISIBLE);
                            l3.setVisibility(View.INVISIBLE);
                            l4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_left <= zone4_upper && sensor_front_left >= zone4_lower)
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

                        if(sensor_front_center <= zone1_upper)
                        {
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.VISIBLE);
                            c4.setVisibility(View.VISIBLE);
                        }
                        else if(sensor_front_center <= zone2_upper && sensor_front_center >= zone2_lower)
                        {
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.VISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_center <= zone3_upper && sensor_front_center >= zone3_lower)
                        {
                            c1.setVisibility(View.VISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            c4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_center <= zone4_upper && sensor_front_center >= zone4_lower)
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

                        if(sensor_front_right <= zone1_upper)
                        {
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.VISIBLE);
                            r4.setVisibility(View.VISIBLE);
                        }
                        else if(sensor_front_right <=zone2_upper && sensor_front_right >= zone2_lower)
                        {
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.VISIBLE);
                            r4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_right <= zone3_upper && sensor_front_right >= zone3_lower)
                        {
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.INVISIBLE);
                            r4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_front_right <= zone4_upper && sensor_front_right >= zone4_lower)
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

                        if(sensor_back <= zone1_upper)
                        {
                            b1.setVisibility(View.VISIBLE);
                            b2.setVisibility(View.VISIBLE);
                            b3.setVisibility(View.VISIBLE);
                            b4.setVisibility(View.VISIBLE);
                        }
                        else if(sensor_back <= zone2_upper && sensor_back >= zone2_lower)
                        {
                            b1.setVisibility(View.VISIBLE);
                            b2.setVisibility(View.VISIBLE);
                            b3.setVisibility(View.VISIBLE);
                            b4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_back <= zone3_upper && sensor_back >= zone3_lower)
                        {
                            b1.setVisibility(View.VISIBLE);
                            b2.setVisibility(View.VISIBLE);
                            b3.setVisibility(View.INVISIBLE);
                            b4.setVisibility(View.INVISIBLE);
                        }
                        else if(sensor_back <= zone4_upper && sensor_back >= zone4_lower)
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
