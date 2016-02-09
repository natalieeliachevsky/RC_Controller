package com.example.computron.rc_controller;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
            piIPAddress = InetAddress.getByName("172.23.175.186");//192.168.2.5");
        }
        catch (UnknownHostException e) {
            piIPAddress = null;
        }
    }


    @Override
    protected Object doInBackground(Object[] params) {

       // String st;
        try {
            s = new Socket(piIPAddress,8000);//80);

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

    public boolean Connected(){
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

}
