package com.example.p01_projectecology;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SnakeActivity extends AppCompatActivity {
    Boolean userActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        userActive = arguments.getBoolean("userActive");
        if (userActive) {
            setContentView(R.layout.activity_chose_game);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(SnakeActivity.this).create();
            alertDialog.setTitle("Статус");
            alertDialog.setMessage("Користувач не залогінився");
            alertDialog.show();
            alertDialog.getWindow().setLayout(1000, 1000);
            setContentView(R.layout.activity_main);
            finish();
        }

    }
}
