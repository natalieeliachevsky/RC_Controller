package com.example.computron.rc_controller;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    final String IP_ADDRESS = "192.168.2.6"; // my room
   // final String IP_ADDRESS = "192.168.0.106"; // Andrew's Router
    final String PORT = "5000";



    boolean isManual = true;
    RelativeLayout layout_joystick;
    JoyStickClass js;
    protected Button manualButton;
    protected Button disconnectButton;


    private MjpegView mv;

    private ImageView image;
    WebView test;

    MediaController mediaController;

    VideoView cameraDisplay;

    private Wifi thisWifiConnection;
    MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //connect wifi
        thisWifiConnection = new Wifi(IP_ADDRESS);

        if (!thisWifiConnection.Connect()){
            switchToLoadScreen(findViewById(android.R.id.content));
        }

        setContentView(R.layout.activity_main);

        manualButton = (Button) findViewById(R.id.manual_button);
        disconnectButton = (Button) findViewById(R.id.disconnect_button);
        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);
        image = (ImageView) findViewById(R.id.imageViewCompass);

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


        mv = (MjpegView) findViewById(R.id.mv);




        new DoRead().execute( IP_ADDRESS, PORT);
        // my room
      //  playStream(IP_ADDRESS + ":" + PORT);

    }

 //   public native String callNative();
  //  static {
  //      System.loadLibrary("callnative");
   // }


    @Override
    protected void onDestroy() {
        cameraDisplay.stopPlayback();
        super.onDestroy();
    }

    private void playStream(String src){
        Uri UriSrc = Uri.parse(src);
        if(UriSrc == null){
            Toast.makeText(MainActivity.this,
                    "UriSrc == null", Toast.LENGTH_LONG).show();
        }else{
            cameraDisplay.setVideoURI(UriSrc);
            mediaController = new MediaController(this);
            cameraDisplay.setMediaController(mediaController);
            Toast.makeText(MainActivity.this,
                    "Connect: " + src,
                    Toast.LENGTH_LONG).show();
            cameraDisplay.start();
        }
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

            manualButton.setText(R.string.manual_text);
            layout_joystick.setVisibility(View.INVISIBLE);

            isManual = false;
        } else {
            // set Manual Mode

            manualButton.setText(R.string.autonomous_text);
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


    public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
    	protected MjpegInputStream doInBackground( String... params){
    		Socket socket = null;
    		try {
				socket = new Socket( params[0], Integer.valueOf( params[1]));
	    		return (new MjpegInputStream(socket.getInputStream()));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return null;
    	}

        protected void onPostExecute(MjpegInputStream result) {
            try {
                mv.setSource(result);
            }
            catch (Exception e){
                e.getMessage();
            }
            if(result!=null) result.setSkip(1);
            mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
            mv.showFps(true);
        }
    }


}


