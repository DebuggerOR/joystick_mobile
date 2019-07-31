package com.example.javaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;



public class JoyStick extends AppCompatActivity implements JoyStickView.JoyStickListner {
    private static String port, ip;
    private static DataOutputStream mBufferOut;
    public static final String elevatorPath = "/controls/flight/elevator";
    public static final String aileronPath = "/controls/flight/aileron";
    TcpClient mTcpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        JoyStickView joyStickView = new JoyStickView(this);
        
        ip = intent.getStringExtra("ip");
        port = intent.getStringExtra("port");

        new ConnectTask().execute("");

        setContentView(joyStickView);
    }

    @Override
    public void onJoyStickMoved(float xPercent, float yPercent, int id) {
        final String xMsg = "set " + aileronPath + " " + xPercent + "\r\n";
        final String yMsg = "set " + elevatorPath + " " + (-yPercent) + "\r\n";

        Log.d("Main method", "" + xMsg + " " + yMsg);

        if (mTcpClient != null) {
            mTcpClient.sendMessage(xMsg);
            mTcpClient.sendMessage(yMsg);
        }
    }

    public class ConnectTask extends AsyncTask<String, String, TcpClient> {
        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            }, ip, port);
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            //process server response here....

        }
    }
}
