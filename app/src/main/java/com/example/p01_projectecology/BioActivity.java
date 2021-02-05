package com.example.p01_projectecology;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BioActivity extends AppCompatActivity {
    private Boolean userActive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
//        userActive = arguments.getBoolean("userActive");
//        if (userActive){
            setContentView(R.layout.activity_bio);
//        }else {
//            AlertDialog alertDialog = new AlertDialog.Builder(BioActivity.this).create();
//            alertDialog.setTitle("Login Status");
//            alertDialog.setMessage("Користувач не залогінився");
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(1000,1000);
//            setContentView(R.layout.activity_main);
//        }
    }

    public void goPaper(View view){
//        finish();
        Intent bio = new Intent(BioActivity.this, ConnectActivity.class);
//        paper.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        paper.putExtra("userActive", userActive);
        startActivity(bio);
    }
    public void back(View view) {
        finish();
    }
}
