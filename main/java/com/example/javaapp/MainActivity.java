package com.example.javaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static  final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "com.example.javaapp.extra.MESSAGE";
    private EditText mMessagePort, mMessageIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessagePort = (EditText) findViewById(R.id.textPort_main);
        mMessageIP = (EditText) findViewById(R.id.textIP_main);
    }

    public void launchJoyStick(View view) {
        Log.d(LOG_TAG, "Connecting");
        Bundle extras = new Bundle();
        Intent intent = new Intent(this, JoyStick.class);
        String port = mMessagePort.getText().toString();
        String ip = mMessageIP.getText().toString();
        intent.putExtra("port", port);
        intent.putExtra("ip", ip);

        startActivity(intent);
    }
}
