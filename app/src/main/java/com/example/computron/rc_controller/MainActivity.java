package com.example.computron.rc_controller;

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

public class MainActivity extends AppCompatActivity {

    boolean isManual = true;
    RelativeLayout layout_joystick;
    JoyStickClass js;
    protected Button manualButton;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manualButton = (Button)findViewById(R.id.manual_button);

        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);

        js = new JoyStickClass(getApplicationContext()
                , layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(90);
        js.setOffset(90);
        js.setMinimumDistance(50);


        manualButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isManual) {
                    // set Autonomous Mode

                    manualButton.setText(R.string.autonomous_text);
                    layout_joystick.setVisibility(View.INVISIBLE);

                    isManual = false;
                } else {
                    // set Manual Mode

                    manualButton.setText(R.string.manual_text);
                    layout_joystick.setVisibility(View.VISIBLE);

                    isManual = true;
                }
            }
        });


        layout_joystick.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
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
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    //         textView1.setText("X :");
                    //         textView2.setText("Y :");
                    //         textView3.setText("Angle :");
                    //         textView4.setText("Distance :");
                    //         textView5.setText("Direction :");
                }
                return true;
            }
        });

        image = (ImageView) findViewById(R.id.imageViewCompass);

        // TextView that will tell the user what degree is he heading
        //tvHeading = (TextView) findViewById(R.id.tvHeading);

        moveCompassTo(180);
    }


    private void moveCompassTo(float degree) {

        float currentDegree = 0f;
        //tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

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
        currentDegree = -degree;

    }

}