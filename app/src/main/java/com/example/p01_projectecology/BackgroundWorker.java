package com.example.p01_projectecology;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    Boolean check, isActive;
    Activity activity;
    String type, smash, statistik, result2;
    ArrayList<String> statistic, statisticBak;
    PreferenceClass preferenceClass;

    BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        if (type.equals("login")) {
            String URL = "http://smashcan.zzz.com.ua/api.php";
            try {
                String email = params[1];
                String password = params[2];
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                return getResult(post_data, URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("register")) {
            String URL = "http://smashcan.zzz.com.ua/api.php";
            try {
                String username = params[1];
                String email = params[2];
                String password = params[3];
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&"
                        + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                return getResult(post_data, URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("smash")) {
            String URL = "http://smash.risc.com.ua/api.php";
            try {
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                smash = getResult(post_data, URL);
                String URL2 = "http://smashcan.zzz.com.ua/api.php";
                preferenceClass = new PreferenceClass(context, "");
                Integer sort_point = preferenceClass.getPoint();
                String name = preferenceClass.getName();
                String surname = preferenceClass.getSurname();
                String geo = preferenceClass.getGeo();
                String id = preferenceClass.getUser();
                String photo = preferenceClass.getPhoto();
                String type_bio = preferenceClass.getUserTypeSmash("type_bio");
                String type_paper = preferenceClass.getUserTypeSmash("type_paper");
                String type_plastic = preferenceClass.getUserTypeSmash("type_plastic");
                try {
                    String post_data2 = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&"
                            + URLEncoder.encode("sort_point", "UTF-8") + "=" + URLEncoder.encode(sort_point.toString(), "UTF-8") + "&"
                            + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                            + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                            + URLEncoder.encode("surname", "UTF-8") + "=" + URLEncoder.encode(surname, "UTF-8") + "&"
                            + URLEncoder.encode("photo", "UTF-8") + "=" + URLEncoder.encode(photo, "UTF-8") + "&"
                            + URLEncoder.encode("type_bio", "UTF-8") + "=" + URLEncoder.encode(type_bio, "UTF-8") + "&"
                            + URLEncoder.encode("type_paper", "UTF-8") + "=" + URLEncoder.encode(type_paper, "UTF-8") + "&"
                            + URLEncoder.encode("type_plastic", "UTF-8") + "=" + URLEncoder.encode(type_plastic, "UTF-8") + "&"
                            + URLEncoder.encode("geo", "UTF-8") + "=" + URLEncoder.encode(geo, "UTF-8");
                    return statistik = getResult(post_data2, URL2);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("update_bak")) {
            String URL = "http://smash.risc.com.ua/api.php";
            try {
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                smash = getResult(post_data, URL);
                return smash;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("update_sort_point")) {
            String URL = "http://smashcan.zzz.com.ua/api.php";
            try {
                preferenceClass = new PreferenceClass(context, "");
                Integer point = preferenceClass.getPoint();
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&"
                        + URLEncoder.encode("point", "UTF-8") + "=" + URLEncoder.encode(point.toString(), "UTF-8");
                return result2 = getResult(post_data, URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("update_user_photo")) {
            String URL = "http://smashcan.zzz.com.ua/api.php";
            try {
                preferenceClass = new PreferenceClass(context, "");
                String id = preferenceClass.getUser();
                String photo = preferenceClass.getPhoto();
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&"
                        + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                        + URLEncoder.encode("photo", "UTF-8") + "=" + URLEncoder.encode(photo, "UTF-8");
                return result2 = getResult(post_data, URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("update_user_info")) {
            String URL = "http://smashcan.zzz.com.ua/api.php";
            try {
                preferenceClass = new PreferenceClass(context, "");
                String id = preferenceClass.getUser();
                String name = preferenceClass.getUserInfo("name");
                String surname = preferenceClass.getUserInfo("surname");
                String email = preferenceClass.getUserInfo("email");
                String geo = preferenceClass.getUserInfo("geo");
                String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&"
                        + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                        + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("surname", "UTF-8") + "=" + URLEncoder.encode(surname, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("geo", "UTF-8") + "=" + URLEncoder.encode(geo, "UTF-8");
                return result2 = getResult(post_data, URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Статус");
    }

    @Override
    protected void onPostExecute(String result) {
        activity = new Activity();
        switch (type) {
            case "register":
                if (!result.equals("Цей логін або пошта використовуються") && !result.equals("Не вдалося створити статистику") && !result.equals("Не вдалося створити користувача")) {
                    alertDialog.dismiss();
                    PreferenceClass preferenceClass = new PreferenceClass(context, result);
                    if (preferenceClass.userIsActive()) {
                        preferenceClass.deleteUser();
                    }
                    ArrayList<String> ar = new ArrayList<String>();
                    for (String retval : result.split(" ")) {
                        ar.add(retval);
                    }
                    preferenceClass.saveUser(ar);
                    Intent avatar = new Intent(context, AvatarActivity.class);
                    avatar.putExtra("button", true);
                    context.startActivity(avatar);
                } else {
                    Toast toast = Toast.makeText(context, result, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case "login":
                if (!result.equals("Невірно набрана пошта") && !result.equals("Невірно набраний пароль") && !result.equals("Немає такого користувача")) {
                    alertDialog.dismiss();
                    ArrayList<String> ar = new ArrayList<String>();
                    for (String retval : result.split(" ")) {
                        ar.add(retval);
                    }
                    PreferenceClass preferenceClass = new PreferenceClass(context, result);
                    preferenceClass.saveUser(ar);
                    isActive = preferenceClass.userIsActive();
                    Intent login = new Intent(context, ChooseActivity.class);
                    login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(login);
                } else {
                    Toast toast = Toast.makeText(context, result, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case "smash":
                preferenceClass = new PreferenceClass(context, "");
                statistic = new ArrayList<String>();
                statisticBak = new ArrayList<String>();
                for (String retval : statistik.split(" ")) {
                    statistic.add(retval);
                }
                for (String retval : smash.split(" ")) {
                    statisticBak.add(retval);
                }
                preferenceClass.saveBakInfo(statisticBak);
                preferenceClass.savePoint(Integer.parseInt(statistic.get(0)));
                preferenceClass.saveUserInfo("type_bio", statistic.get(1));
                preferenceClass.saveUserInfo("type_paper", statistic.get(2));
                preferenceClass.saveUserInfo("type_plastic", statistic.get(3));
                Intent connect = new Intent(context, StatistikActivity.class);
                connect.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                connect.putExtra("userActive", true);
                context.startActivity(connect);
                break;
            case "update_bak":
                preferenceClass = new PreferenceClass(context, "");
                statisticBak = new ArrayList<String>();
                for (String retval : smash.split(" ")) {
                    statisticBak.add(retval);
                }
                preferenceClass.saveBakInfo(statisticBak);
                break;
            case "update_sort_point":
            case "update_user_photo":
            case "update_user_info":
                if (!TextUtils.isEmpty(result2)) {
                    Toast toast = Toast.makeText(context, result2, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            default:
                alertDialog.setMessage(result);
                alertDialog.show();
                alertDialog.getWindow().setLayout(1000, 1000);
                break;
        }
    }

    protected String getResult(String post_data, String URL) throws IOException {
        URL url = new URL(URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String result = "";
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return result;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}