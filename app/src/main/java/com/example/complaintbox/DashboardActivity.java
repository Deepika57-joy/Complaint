package com.example.complaintbox;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    TextView sucess ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        sucess = (TextView)findViewById(R.id.text);
        //LogOUT = (Button)findViewById(R.id.button1);

        Intent intent = getIntent();



    }
}