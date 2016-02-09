package com.example.computron.rc_controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.concurrent.ExecutionException;

import static com.example.computron.rc_controller.R.*;

public class MainActivity extends AppCompatActivity {

    boolean isManual = true;
    RelativeLayout layout_joystick;
    JoyStickClass js;
    protected Button manualButton;
    protected Button disconnectButton;
    Wifi wifiController;

    private ImageView image;

    private Wifi thisWifiConnection;

    public MainActivity(Wifi wifiObject) {
        thisWifiConnection = wifiObject;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //connect wifi
        if (!connectWifi()){
            switchToLoadScreen(findViewById(android.R.id.content));
        }

        setContentView(layout.activity_main);

        manualButton = (Button) findViewById(id.manual_button);
        disconnectButton = (Button) findViewById(id.disconnect_button);
        layout_joystick = (RelativeLayout) findViewById(id.layout_joystick);
        image = (ImageView) findViewById(id.imageViewCompass);

        js = new JoyStickClass(getApplicationContext()
                , layout_joystick, drawable.image_button);

        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(90);
        js.setOffset(90);
        js.setMinimumDistance(50);



        manualButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toggleMode();
            }
        });

        layout_joystick.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                return jsOnTouchListener(arg0, arg1);
            }
        });

        moveCompassTo(180);

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switchToLoadScreen(view);
            }
        });

    }





    private void moveCompassTo(float degree) {

        float currentDegree = 0f;

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
    }




    private void toggleMode() {
        if (isManual) {
            // set Autonomous Mode

            manualButton.setText(string.manual_text);
            layout_joystick.setVisibility(View.INVISIBLE);

            isManual = false;
        } else {
            // set Manual Mode

            manualButton.setText(string.autonomous_text);
            layout_joystick.setVisibility(View.VISIBLE);

            isManual = true;
        }

    }



    private void switchToLoadScreen(View thisView) {

        if (thisWifiConnection.Disconnect()) {
            Intent myIntent = new Intent(thisView.getContext(), LoadScreen.class);
            startActivityForResult(myIntent, 0);
        }
    }



    private boolean jsOnTouchListener(View arg0, MotionEvent arg1){
        js.drawStick(arg1);
        if(arg1.getAction()==MotionEvent.ACTION_DOWN
        ||arg1.getAction()==MotionEvent.ACTION_MOVE)

        {
            //         textView1.setText("X : " + String.valueOf(js.getX()));
            //         textView2.setText("Y : " + String.valueOf(js.getY()));
            //         textView3.setText("Angle : " + String.valueOf(js.getAngle()));
            //         textView4.setText("Distance : " + String.valueOf(js.getDistance()));

            int direction = js.get8Direction();
            if (direction == JoyStickClass.STICK_UP) {
                //            textView5.setText("Direction : Up");
            } else if (direction == JoyStickClass.STICK_UPRIGHT) {
                //             textView5.setText("Direction : Up Right");
            } else if (direction == JoyStickClass.STICK_RIGHT) {
                //             textView5.setText("Direction : Right");
            } else if (direction == JoyStickClass.STICK_DOWNRIGHT) {
                //             textView5.setText("Direction : Down Right");
            } else if (direction == JoyStickClass.STICK_DOWN) {
                //             textView5.setText("Direction : Down");
            } else if (direction == JoyStickClass.STICK_DOWNLEFT) {
                //             textView5.setText("Direction : Down Left");
            } else if (direction == JoyStickClass.STICK_LEFT) {
                //             textView5.setText("Direction : Left");
            } else if (direction == JoyStickClass.STICK_UPLEFT) {
                //             textView5.setText("Direction : Up Left");
            } else if (direction == JoyStickClass.STICK_NONE) {
                //             textView5.setText("Direction : Center");
            }
        }

        else if(arg1.getAction()==MotionEvent.ACTION_UP)

        {
            //         textView1.setText("X :");
            //         textView2.setText("Y :");
            //         textView3.setText("Angle :");
            //         textView4.setText("Distance :");
            //         textView5.setText("Direction :");
        }

        return true;
    }



    private boolean connectWifi() {

        // set up wifi connection
        wifiController = new Wifi();

        try {
            wifiController.execute().get();
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }

        if (wifiController.Connected()) {
            return true;
        } else {
            return false;
        }

    }

}
