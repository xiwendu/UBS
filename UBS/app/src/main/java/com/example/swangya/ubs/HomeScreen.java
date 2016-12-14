package com.example.swangya.ubs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void openRes(View view){
        Intent startWriteActivity = new Intent(this, ClubAndOrganization.class);
        startActivity(startWriteActivity);
    }
}
