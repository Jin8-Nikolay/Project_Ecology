package com.example.p01_projectecology;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


public class SignUpActivity extends AppCompatActivity {
    private EditText editTextTextPersonName;
    private EditText editTextTextEmailAddress;
    private EditText editTextTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    public void init(){
        editTextTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
    }
    public void back(View view) {
        finish();
    }

    public void confidential(View view) {
        Intent confidential = new Intent(SignUpActivity.this, ConfidentialActivity.class);
        startActivity(confidential);
    }

    public void save(View view) {
        createUser();
    }

    private void createUser(){
        String username = editTextTextPersonName.getText().toString().trim();
        String email = editTextTextEmailAddress.getText().toString().trim();
        String password = editTextTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) || username.length() < 3) {
            editTextTextPersonName.setError("Логін неправильний");
            editTextTextPersonName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email) || !email.contains("@") || !email.contains(".")) {
            editTextTextEmailAddress.setError("E-mail неправильний");
            editTextTextEmailAddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            editTextTextPassword.setError("Пароль неправильний");
            editTextTextPassword.requestFocus();
            return;
        }
        String type = "register";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, email, password);
    }


}
