package com.example.p01_projectecology;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PlastukActivity extends AppCompatActivity {
    private Boolean userActive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
//        userActive = intent.getBooleanExtra("userActive", false);
//        if (userActive){
            setContentView(R.layout.activity_plastuk);
//        }else {
//            AlertDialog alertDialog = new AlertDialog.Builder(PlastukActivity.this).create();
//            alertDialog.setTitle("Login Status");
//            alertDialog.setMessage("Користувач не залогінився");
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(1000,1000);
//            setContentView(R.layout.activity_main);
//        }
    }


    public void goBio(View view) {
//        finish();
        Intent bio = new Intent(PlastukActivity.this, ConnectActivity.class);
//        bio.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        bio.putExtra("userActive", userActive);
        startActivity(bio);
    }

    public void back(View view) {
        finish();
    }
}
