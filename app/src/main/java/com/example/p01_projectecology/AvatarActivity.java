package com.example.p01_projectecology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;

public class AvatarActivity extends AppCompatActivity {
    GridLayout glMain;
    LinearLayout linearLayout;
    ImageView imageView;
    Boolean buttonIs, profileIs;
    Button button;
    int count = 1;
    int currentAvatar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        buttonIs = intent.getBooleanExtra("button", false);
        profileIs = intent.getBooleanExtra("profile", false);
        setContentView(R.layout.activity_avatar);
        if (buttonIs){
            createButton();
        }
        try {
            createAvatars();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){
        PreferenceClass preferenceClass = new PreferenceClass(AvatarActivity.this, "");
        preferenceClass.saveUserInfo("photo", preferenceClass.getPhoto());
        if (hasConnection(this)){
            String type = "update_user_photo";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type);
        }
        imageView = (ImageView) findViewById(currentAvatar);
        Intent intent = new Intent();
        intent.putExtra("photo", imageView.getTag().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() != currentAvatar && currentAvatar != 0){
                imageView = (ImageView) findViewById(currentAvatar);
                imageView.setBackgroundResource(R.drawable.avatar_noactive);
                setBackgroundResource(view);
                setButtonBack();
                savePhoto(view);
            } else if (currentAvatar == 0){
                setBackgroundResource(view);
                setButtonBack();
                savePhoto(view);
            } else {
                setBackgroundResource(view);
                setButtonBack();
                savePhoto(view);
            }
        }
    };

    View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent go = new Intent(AvatarActivity.this, ChooseActivity.class);
            go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            go.putExtra("userActive", true);
            startActivity(go);
        }
    };


    public void createButton(){
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        ViewGroup.MarginLayoutParams buttonParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT);
        buttonParams.setMargins(110,50,110,30);
        button = new Button(this);
        button.setText("Далі");
        button.setEnabled(false);
        button.setClickable(false);
        button.setTextColor(Color.parseColor("#ffffff"));
        button.setOnClickListener(buttonClick);
        button.setBackgroundResource(R.drawable.button_unanaible);
        button.setLayoutParams(buttonParams);
        linearLayout.addView(button);
    }

    public void setButtonBack(){
        if (buttonIs){
            button.setBackgroundResource(R.drawable.button_green);
            button.setEnabled(true);
            button.setClickable(true);
        }
    }

    public void createAvatars() throws IOException {
        glMain = (GridLayout) findViewById(R.id.gridLayout);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(250, 250);
        layoutParams.setMargins(56,80,0,20);
        for (String res :  getAssets().list("avatar")){
            if (!res.equals("default.png")){
                InputStream avatar = getAssets().open("avatar/" + res);
                Drawable drawable = Drawable.createFromStream(avatar, null);
                ImageView view = new ImageView(this);
                view.setId(count++);
                if (profileIs){
                    PreferenceClass preferenceClass = new PreferenceClass(this, "");
                    if (res.equals(preferenceClass.getPhoto()))
                        setBackgroundResource(view);
                }
                view.setImageDrawable(drawable);
                view.setLayoutParams(layoutParams);
                view.setTag(res);
                view.setOnClickListener(clickListener);
                glMain.addView(view);
            }
        }
    }

    public void back(View view) {
        imageView = (ImageView) findViewById(currentAvatar);
        Intent intent = new Intent();
        intent.putExtra("photo", imageView.getTag().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void savePhoto(View view){
        PreferenceClass preferenceClass = new PreferenceClass(AvatarActivity.this, "");
        preferenceClass.saveUserInfo("photo", view.getTag().toString());
        if (hasConnection(this)){
            String type = "update_user_photo";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type);
        }
    }


    public void setBackgroundResource(View view){
        currentAvatar = view.getId();
        view.setBackgroundResource(R.drawable.avatar_active);
    }

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }
}