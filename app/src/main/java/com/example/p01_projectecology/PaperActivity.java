package com.example.p01_projectecology;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PaperActivity extends AppCompatActivity {
    private Boolean userActive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
//        userActive = arguments.getBoolean("userActive");
//        if (userActive){
            setContentView(R.layout.activity_paper);
//        }else {
//            AlertDialog alertDialog = new AlertDialog.Builder(PaperActivity.this).create();
//            alertDialog.setTitle("Login Status");
//            alertDialog.setMessage("Користувач не залогінився");
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(1000,1000);
//            setContentView(R.layout.activity_main);
//        }
    }

    public void goChoose(View view){
//        finish();
        Intent paper = new Intent(PaperActivity.this, ConnectActivity.class);
//        choose.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        choose.putExtra("userActive", userActive);
        startActivity(paper);
    }
    public void back(View view) {
        finish();
    }
}
