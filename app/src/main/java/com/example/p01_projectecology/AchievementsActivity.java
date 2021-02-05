package com.example.p01_projectecology;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AchievementsActivity extends AppCompatActivity {
    ImageView profile;
    TextView fio, achievements;
    ProgressTextView progressTextView;
    GridLayout glMain;
    Achievements achievementsNames;
    Integer count = 0, active = 0;
    PreferenceClass preferenceClass;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init() throws IOException {
        profile = (ImageView) findViewById(R.id.profile);
        preferenceClass = new PreferenceClass(this, "");
        InputStream avatar = getAssets().open("avatar/" + preferenceClass.getPhoto());
        Drawable drawable = Drawable.createFromStream(avatar, null);
        profile.setImageDrawable(drawable);
        fio = (TextView) findViewById(R.id.fio);
        fio.setText(preferenceClass.getName() + " " + preferenceClass.getSurname());
        progressTextView = (ProgressTextView) findViewById(R.id.progress);
        createAvatars();
        achievements = (TextView) findViewById(R.id.achievements);
        achievements.setText("Отримано " + active.toString() + " з " + count.toString() + " нагород");
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createAvatars() throws IOException {
        glMain = (GridLayout) findViewById(R.id.gridLayout);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.bottomMargin = 0;
        layoutParams.leftMargin = 80;
        layoutParams.rightMargin = 30;
        layoutParams.topMargin = 40;
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.bottomMargin = 0;
//        layoutParams.leftMargin = 80;
//        layoutParams.rightMargin = 30;
//        layoutParams.topMargin = 40;
//        layoutParams.gravity = Gravity.CENTER;
        RelativeLayout.LayoutParams layoutParamsI = new RelativeLayout.LayoutParams(200,200);
//        layoutParamsI.addRule(RelativeLayout.CENTER_IN_PARENT);
//        layoutParamsI.alignWithParent = true;
        LinearLayout.LayoutParams layoutParamsT = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,100);
        layoutParamsT.gravity = Gravity.CENTER;
        for (String res : getAssets().list("achievements")) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
//            linearLayout.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);
//            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(layoutParams);

            InputStream achievnement = getAssets().open("achievements/" + res);
            Drawable drawable = Drawable.createFromStream(achievnement, null);
            TextView textView = new TextView(this);
            achievementsNames = new Achievements();
            textView.setText(achievementsNames.getName(res));
//            textView.setBackgroundResource(R.drawable.tet_bacground);
//            textView.setMinimumHeight(100);
//            textView.setMinimumWidth(200);
            textView.setLayoutParams(layoutParamsT);
            ImageView view = new ImageView(this);
//            view.setMinimumWidth(200);
//            view.setMinimumHeight(200);
            view.setId(count++);
            view.setImageDrawable(drawable);
            ViewCompat.setElevation(view, 10);
            view.setLayoutParams(layoutParamsI);
            view.setTag(res);
            view.setElevation(10);
            if (!checkAch(res)) {
                view.setAlpha((float) 0.5);
                view.setClickable(false);
            } else {
                active++;
            }
            glMain.addView(linearLayout);
            linearLayout.addView(view);
            linearLayout.addView(textView);
//            view.setBackground(ContextCompat.getDrawable(this, R.drawable.achievnement));
        }
        Float fCount = Float.valueOf(count);
        Float fActive = Float.valueOf(active);
        float progress = fActive / fCount * 100;
        progressTextView.setValue((int) progress);
    }

    protected boolean checkAch(String res) {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        for (String retval : res.split("\\.")) {
            if (!retval.equals("jpg")){
                arrayList.add(retval);
            }
        }
        for (String retval2 : arrayList){
            if (retval2.contains("sort_point")) {
                for (String retval3 : retval2.split("_")) {
                    if (!retval3.equals("sort") && !retval3.equals("point")){
                        return Integer.parseInt(retval3) <= preferenceClass.getPoint();
                    }
                }
            } else if (retval2.contains("type")){
                return !preferenceClass.getUserTypeSmash(retval2).equals("0");
            }
        }
        return false;
    }

    public void back(View view) {
        finish();
    }
}