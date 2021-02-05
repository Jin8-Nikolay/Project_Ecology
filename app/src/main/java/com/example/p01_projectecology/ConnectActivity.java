package com.example.p01_projectecology;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ConnectActivity extends AppCompatActivity {
    private Boolean userActive;
    private String choose;
    private String smashLvl;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent arguments = getIntent();
//        userActive = arguments.getBooleanExtra("userActive", false);
//        if (userActive){
            setContentView(R.layout.activity_connect_smash);
//        }else {
//            AlertDialog alertDialog = new AlertDialog.Builder(ConnectActivity.this).create();
//            alertDialog.setTitle("Login Status");
//            alertDialog.setMessage("Користувач не залогінився");
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(1000,1000);
//            setContentView(R.layout.activity_main);
//            finish();
//        }
    }

    public void go(View view) {
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        String type = "smash";
        backgroundWorker.execute(type);
    }
}
