package com.example.complaintbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {
    String UsernameHolder;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        username = (TextView)findViewById(R.id.text);
        Intent intent = getIntent();
        username.setText(username.getText().toString()+ UsernameHolder);
    }
}