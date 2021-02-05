package com.example.p01_projectecology;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {
    EditText name, surname, email, geo, flag;
    PreferenceClass preferenceClass;
    String typeEdit;
    ImageView profile;
    EditText edit;
    Boolean editAction = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        preferenceClass = new PreferenceClass(this, "");
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("ResourceAsColor")
    public void init() throws IOException {
        geo = (EditText) findViewById(R.id.editTextTextPersonName2);
        name = (EditText) findViewById(R.id.editTextTextPersonName4);
        surname = (EditText) findViewById(R.id.editTextTextPersonName5);
        email = (EditText) findViewById(R.id.editTextTextPersonName3);
        geo.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
        name.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
        surname.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
        email.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
        geo.setText(preferenceClass.getGeo());
        name.setText(preferenceClass.getName());
        surname.setText(preferenceClass.getSurname());
        email.setText(preferenceClass.getEmail());
        geo.setFocusable(false);
        geo.setLongClickable(false);
        geo.setCursorVisible(false);
        name.setFocusable(false);
        name.setLongClickable(false);
        name.setCursorVisible(false);
        surname.setFocusable(false);
        surname.setLongClickable(false);
        surname.setCursorVisible(false);
        email.setFocusable(false);
        email.setLongClickable(false);
        email.setCursorVisible(false);
        setProfilePhoto();
    }


    public void name(View view) {
        typeEdit = "name";
        setEditText(typeEdit, name);
        setEdit(name);
    }

    public void geo(View view) {
        typeEdit = "geo";
        setEditText(typeEdit, geo);
        setEdit(geo);

    }

    public void surname(View view) {
        typeEdit = "surname";
        setEditText(typeEdit, surname);
        setEdit(surname);
    }

    public void setEditText(String type, EditText edit) {
        if (preferenceClass.getUserInfo(typeEdit).equals("Пусто")) {
            edit.setText("");
        } else {
            edit.setText(preferenceClass.getUserInfo(typeEdit));
        }
    }


    @SuppressLint("ResourceAsColor")
    public void setEdit(final EditText editor) {
        editAction = true;
        edit = editor;
        edit.setFocusableInTouchMode(true);
        edit.setFocusable(true);
        edit.setLongClickable(true);
        edit.setCursorVisible(true);
        edit.requestFocus();
        edit.getBackground().clearColorFilter();
        flag = edit;
        showKeyboardFrom(ProfileActivity.this, edit);
        edit.setSelection(edit.getText().length());
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                preferenceClass.saveUserInfo(typeEdit, flag.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        edit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            if (!edit.getText().toString().equals("")) {
                                editAction(false);
                            } else {
                                editAction(true);
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (editAction){
            editAction(false);
        }
        profile = findViewById(R.id.imageView16);
        Intent intent = new Intent();
        intent.putExtra("photo", profile.getTag().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyboardFrom(Context context, EditText edit) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, InputMethodManager.SHOW_FORCED);
    }

    public void setProfilePhoto() throws IOException {
        profile = (ImageView) findViewById(R.id.imageView16);
        PreferenceClass preferenceClass = new PreferenceClass(this, "");
        InputStream avatar = getAssets().open("avatar/" + preferenceClass.getPhoto());
        Drawable drawable = Drawable.createFromStream(avatar, null);
        profile.setImageDrawable(drawable);
        profile.setTag(preferenceClass.getPhoto());
    }

    public void profile(View view) {
        Intent profile = new Intent(ProfileActivity.this, AvatarActivity.class);
        profile.putExtra("profile", true);
        startActivityForResult(profile, 1);
    }

    public void back(View view) {
        profile = (ImageView) findViewById(R.id.imageView16);
        Intent intent = new Intent();
        intent.putExtra("photo", profile.getTag().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        InputStream avatar = null;
        try {
            avatar = getAssets().open("avatar/" + data.getStringExtra("photo"));
            Drawable drawable = Drawable.createFromStream(avatar, null);
            profile = (ImageView) findViewById(R.id.imageView16);
            profile.setImageDrawable(drawable);
            profile.setTag(data.getStringExtra("photo"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUserInfo() {
        String type = "update_user_info";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type);
    }

    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void addBak(View view) {
        Intent intent = new Intent(this, ChooseActivity.class);
        startActivity(intent);
    }

    public void editAction(Boolean text) {
        edit.setFocusableInTouchMode(false);
        edit.setFocusable(false);
        edit.setLongClickable(false);
        edit.setCursorVisible(false);
        if (text) {
            edit.setText("Пусто");
        }
        edit.getBackground().setColorFilter(android.R.color.transparent, PorterDuff.Mode.SRC_IN);
        hideKeyboardFrom(ProfileActivity.this, edit);
        if (hasConnection(ProfileActivity.this)) {
            saveUserInfo();
        }
    }
}
