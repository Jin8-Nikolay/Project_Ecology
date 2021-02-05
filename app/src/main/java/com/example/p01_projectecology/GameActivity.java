package com.example.p01_projectecology;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity {
    public static int highestPoint;
    Integer time = 11;
    ImageView image, play;
    TextView textPoint, point10, lifes, allPoint, timer;
    List<String> results;
    String photo, file;
    Integer point, life;
    Animation pointShowAnimation, pointHiding;
    LinearLayout whiteBack;
    CountDownTimer cTimer = null;
    Boolean userActive, animate = false;
    View view;
    int mAnimationCount;
    AnimationDrawable arrowAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle arguments = getIntent().getExtras();
//        userActive = arguments.getBoolean("userActive");
        if (hasConnection(this)){
            updatePoint(true);
        }
//        if (userActive) {
            setContentView(R.layout.activity_game);
            initElement();
//        } else {
//            AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
//            alertDialog.setTitle("Статус");
//            alertDialog.setMessage("Користувач не залогінився");
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(1000, 1000);
//            setContentView(R.layout.activity_main);
//            finish();
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            getPhotos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        changePhoto();
        init();
    }

    @Override
    public void onBackPressed() {
        if (point > highestPoint) {
            updatePoint(false);
        }
        finish();
    }

    public void init() {
        life = 3;
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        point = 0;
        highestPoint = sharedPreferences.getInt("sort_point", 0);
        point10.setAlpha(0);
        lifes.setText(life.toString());
        allPoint.setText("Макс. балів: " + highestPoint);
    }

    public void initElement() {
        image = (ImageView) findViewById(R.id.imageView12);
        play = (ImageView) findViewById(R.id.imageView13);
        textPoint = (TextView) findViewById(R.id.textView22);
        point10 = (TextView) findViewById(R.id.textView26);
        lifes = (TextView) findViewById(R.id.textView29);
        allPoint = (TextView) findViewById(R.id.textView31);
        timer = (TextView) findViewById(R.id.textView32);
        whiteBack = (LinearLayout) findViewById(R.id.whiteBack);
        pointShowAnimation = AnimationUtils.loadAnimation(this, R.anim.point_show);
        pointHiding = AnimationUtils.loadAnimation(this, R.anim.point_hiding);

    }

    public void getPhotos() throws IOException {
        results = new ArrayList<String>();
        for (String res :  getAssets().list("game/sort")){
            results.add(res);
        }
    }


    public static int rnd(int max) {
        return (int) (Math.random() * ++max);
    }

    public void back(View view) {
        if (point > highestPoint) {
            updatePoint(false);
        }
        finish();
    }

    //    public void action(){
//        view.setOnTouchListener(new OnSwipeTouchListener(this) {
//            @Override
//            public void onClick() {
//                super.onClick();
//            }
//
//            @Override
//            public void onSwipeUp() {
//                super.onSwipeUp();
//
//            }
//
//            @Override
//            public void onSwipeDown() {
//                super.onSwipeDown();
//
//            }
//
//            @Override
//            public void onSwipeLeft() {
//                super.onSwipeLeft();
//
//            }
//
//            @Override
//            public void onSwipeRight() {
//                super.onSwipeRight();
//
//            }
//        });
//    }
    public void glass(View view) { actionButton("glass", view); }

    public void plastik(View view) {
        actionButton("plastic", view);
    }

    public void paper(View view) {
        actionButton("paper", view);
    }

    public void bio(View view) { actionButton("bio", view); }

    public void actionButton(String button, View view) {
        if (photo.equals(button)) {
//            view.setBackgroundResource(R.drawable.arrow);
//            view.setBackgroundResource(R.drawable.arrow_click);
//            arrowAnimation = (AnimationDrawable) view.getBackground();
//            arrowAnimation.start();
            changePhoto();
            setPoint();
        } else {
//            view.setBackgroundResource(R.drawable.arrow);
//            view.setBackgroundResource(R.drawable.arrow_error_click);
//            arrowAnimation = (AnimationDrawable) view.getBackground();
//            arrowAnimation.start();
            life--;
            if (life == 0) {
                cancelTimer();
                restart();
            } else {
                lifes.setText(life.toString());
            }
        }
    }

    public void restart() {
        if (point > highestPoint) {
            highestPoint = point;
            updatePoint(false);
        }
        recreate();
        updatePoint(false);
    }

    public void changePhoto() {
        final int max = 26; // Максимальное число для диапазона от 0 до max
        final int rnd = rnd(max);
        int i = 0;
        boolean isR = false;
        if (!TextUtils.isEmpty(file)){
            for (int count = 0; count < 1; ){
                if (!results.get(rnd).equals(file)){
                    isR = true;
                }
                if (isR){

                    count++;
                }
            }
        }
        file = results.get(rnd);
        for (String retval : file.split("_", 2)) {
            if (i < 1){
                photo = retval;
            }
            i++;
        }
        try {
            // получаем входной поток
            InputStream ims = getAssets().open("game/sort/" + file);
            // загружаем как Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // выводим картинку в ImageView
            image.setImageDrawable(d);

        } catch (IOException ex) {
            return;
        }

    }

    public void setPoint() {
        point += 10;
        time = 11;
        cancelTimer();
        startTimer();
        showPoint10();
        textPoint.setText(point.toString());
    }


    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                time--;
                timer.setText(time.toString());
            }
            public void onFinish() {
                restart();
            }
        };
        cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }


    public void showPoint10() {
        point10.setAlpha(1);

        if (!animate) {
            animatePoint(true);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    animatePoint(false);
                }
            }, 800);
        }

    }


    public void animatePoint(Boolean show) {
        if (show) {
            Animation animationst = AnimationUtils.loadAnimation(this, R.anim.point_show);
            animationst.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animationst) {
                    animate = true;
                }

                @Override
                public void onAnimationEnd(Animation animationst) {

                }

                @Override
                public void onAnimationRepeat(Animation animationst) {

                }
            });
            point10.startAnimation(animationst);
        } else {
            Animation animationend = AnimationUtils.loadAnimation(this, R.anim.point_hiding);
            animationend.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animationend) {
                }

                @Override
                public void onAnimationEnd(Animation animationend) {
                    animate = false;
                }

                @Override
                public void onAnimationRepeat(Animation animationend) {

                }
            });
            point10.startAnimation(animationend);
        }
    }

    public void play(View view) {
        play.setAlpha(0);
        allPoint.setAlpha(0);
        whiteBack.setAlpha(0);
        play.setClickable(false);
        allPoint.setClickable(false);
        whiteBack.setClickable(false);
        startTimer();
    }

    public void updatePoint(Boolean isCreate){
        if (!isCreate){
            PreferenceClass preferenceClass = new PreferenceClass(this, "");
            preferenceClass.savePoint(point);
        }
        if(hasConnection(this)){
            String type = "update_sort_point";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type);
        }
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
