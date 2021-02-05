package com.example.p01_projectecology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Achievements {
    String[] stringNameAch = {"50","100", "200", "400","Бак Біо", "Бак Дикий", "Бак Скла"};
    String[] nameImg = {"sort_point_50.jpg", "sort_point_100.jpg", "sort_point_200.jpg", "sort_point_400.jpg", "type_bio.jpg", "type_paper.jpg", "type_plastic.jpg"};
    Map<String, String> map = new HashMap<String, String>();

    String getName(String value){
        for (int i = 0; i < nameImg.length; i++){
            map.put(stringNameAch[i], nameImg[i]);
        }
        return getKey(map, value);
    }
    protected <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
