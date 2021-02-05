package com.example.p01_projectecology;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {

    private EditText Email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();

    }
    public void init(){
        Email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.register_password);
    }

    public void back(View view) {
        finish();
    }

    public void login(View view) {

        String email = Email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String type = "login";
        if (TextUtils.isEmpty(email) || !email.contains("@")) {
            Email.setError("E-mail неправильний");
            Email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pass) || pass.length() < 6) {
            password.setError("Пароль неправильний");
            password.requestFocus();
            return;
        }
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, email, pass);

    }

}