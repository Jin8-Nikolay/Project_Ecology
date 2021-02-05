package com.example.p01_projectecology;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ApplicationErrorReport;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatistikActivity extends AppCompatActivity{
    ProgressBar pbc;
    TextView progress;
    ImageView profile;
    ArrayList<Integer> ar;
    PreferenceClass preferenceClass;
    ProgressTextView pb;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch wifi, clean;
    private Boolean userActive;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        preferenceClass = new PreferenceClass(this, "");
        if (hasConnection(this)){
            String type = "update_bak";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type);
        }
        ar = new ArrayList<Integer>();
//        if (userActive){
            setContentView(R.layout.activity_statistik);
            prog();
            try {
                init();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }else {
//            AlertDialog alertDialog = new AlertDialog.Builder(StatistikActivity.this).create();
//            alertDialog.setTitle("Login Status");
//            alertDialog.setMessage("Користувач не залогінився");
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(1000,1000);
//            setContentView(R.layout.activity_main);
//            finish();
//        }
    }
    public void init() throws IOException {
        wifi = (Switch) findViewById(R.id.switch1);
        clean = (Switch) findViewById(R.id.switch2);
        progress = (TextView) findViewById(R.id.textView17);
        progress.setText(preferenceClass.getBakFullness());
        setProfilePhoto();
    }

    public void prog(){
        pb = (ProgressTextView) findViewById(R.id.progressBar2);
        pbc = (ProgressBar) findViewById(R.id.progressBar);
        pbc.setProgress(Integer.parseInt(preferenceClass.getBakFullness()));
        pb.setValue(Integer.parseInt(preferenceClass.getBakBattery()));
    }

    public void game(View view) {
        Intent game = new Intent(StatistikActivity.this, ChooseGameActivity.class);
//        game.putExtra("userActive", userActive);
        startActivity(game);
    }

    public void profile(View view) {
        Intent profile = new Intent(StatistikActivity.this, ProfileActivity.class);
        startActivityForResult(profile, 1);
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
            profile = (ImageView) findViewById(R.id.imageView8);
            profile.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProfilePhoto() throws IOException {
        profile = (ImageView) findViewById(R.id.imageView8);
        PreferenceClass preferenceClass = new PreferenceClass(this, "");
        InputStream avatar = getAssets().open("avatar/" + preferenceClass.getPhoto());
        Drawable drawable = Drawable.createFromStream(avatar, null);
        profile.setImageDrawable(drawable);
    }

    public void achievements(View view) {
        Intent achievements = new Intent(this, AchievementsActivity.class);
        startActivity(achievements);
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

//    public void test(View view) {
//        Intent test = new Intent(this, ChooseActivity.class);
//        startActivity(test);
//    }
}


