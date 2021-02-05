package com.example.p01_projectecology;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class ChooseGameActivity extends AppCompatActivity {
    Boolean userActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle arguments = getIntent().getExtras();
//        userActive = arguments.getBoolean("userActive");
//        if (userActive) {
            setContentView(R.layout.activity_chose_game);
//        } else {
//            AlertDialog alertDialog = new AlertDialog.Builder(ChooseGameActivity.this).create();
//            alertDialog.setTitle("Статус");
//            alertDialog.setMessage("Користувач не залогінився");
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(1000,1000);
//            setContentView(R.layout.activity_main);
//            finish();
//        }

    }


    public void back(View view) {
        finish();
    }

    public void sort(View view) {
        Intent game = new Intent(ChooseGameActivity.this, GameActivity.class);
//        game.putExtra("userActive", userActive);
        startActivity(game);
    }

    public void snake(View view) {
//        Intent game = new Intent(ChooseGameActivity.this, SnakeActivity.class);
//        game.putExtra("userActive", userActive);
//        startActivity(game);
    }
}