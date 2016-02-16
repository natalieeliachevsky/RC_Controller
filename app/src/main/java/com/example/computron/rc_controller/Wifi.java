package com.example.computron.rc_controller;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Natalie on 1/28/2016.
 */
public class Wifi extends AsyncTask {

    public Socket s;
    TextView internetView;
    private Exception exception;
    MainActivity mActivity;
    private boolean isConnected = false;
    InetAddress piIPAddress;

    public Wifi()
    {
        try {
            piIPAddress = InetAddress.getByName("192.168.2.8");
        }
        catch (UnknownHostException e) {
            piIPAddress = null;
        }
    }


    @Override
    protected Object doInBackground(Object[] params) {

       // String st;
        try {
            s = new Socket(piIPAddress,5010);//80);

            //outgoing stream redirect to socket
            // InputStream out = s.getInputStream();

            //BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

            //read line(s)
            // st = input.readLine();

            //Close connection
            isConnected = true;
          //  s.close();
        } catch (ConnectException e){
            isConnected = false;
        } catch (UnknownHostException e) {
            isConnected = false;
        } catch (IOException e) {
            isConnected = false;
        }
        return params;
    }

    public boolean IsConnected(){
        return isConnected;
    }

    public boolean Disconnect() {
        if (isConnected) {
            try {
                s.close();
                isConnected = false;
            }
            catch (IOException e) { return false; }
        }
        return true;
    }

    public boolean Connect() {

        // set up wifi connection
        try {
            execute().get();
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }

        if (isConnected) {
            return true;
        } else {
            return false;
        }

    }

}
