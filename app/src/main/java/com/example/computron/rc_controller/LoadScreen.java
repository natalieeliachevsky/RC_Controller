package com.example.computron.rc_controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by Natalie on 1/28/2016.
 */
public class LoadScreen extends AppCompatActivity {

    TextView disconnectedTextView;




    //disconnectedTextView.setVisibility(View.INVISIBLE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_screen);

        disconnectedTextView = (TextView)findViewById(R.id.noConnectionTextView);


        Button connectBtn = (Button) findViewById(R.id.connect_button);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switchToMainView(view);
            }
        });
    }



    private void switchToMainView(View thisView)
    {
        Intent myIntent = new Intent(thisView.getContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
    }


}
