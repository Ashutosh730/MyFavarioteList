package com.example.myfavariotelist;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.prefs.PreferenceChangeEvent;

public class Manager {

    private Context context;

    public Manager(Context context) {
        this.context = context;
    }

    public void saveCategory(Category category){

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        HashSet itemSet = new HashSet(Arrays.asList(category.getItem()));
        editor.putStringSet(category.getName(),itemSet);
        editor.apply();

    }

    public ArrayList<Category> retrive(){

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        Map<String,?> data=sharedPreferences.getAll();

        ArrayList<Category> categories=new ArrayList<>();

        for(Map.Entry<String,?> entry :data.entrySet()){

            Category category=new Category(entry.getKey(),new ArrayList<String>((HashSet)entry.getValue()));
            categories.add(category);
        }
        return categories;
    }


}
