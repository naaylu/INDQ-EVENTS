package com.example.indq_events.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

public class LocalStorage {

    /* TODO: Make methods "iteratables" */

    Context context;

    @Inject
    public LocalStorage(Context context) {
        this.context = context;
    }

    public void setValue(String fileName, String key, String value) {
        SharedPreferences.Editor shpEditor = context.getSharedPreferences(fileName, MODE_PRIVATE).edit();
        shpEditor.putString(key, value);
        shpEditor.apply();
    }

    public String getValue(String fileName, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, MODE_PRIVATE);
        return sharedPreferences.getString(key, "0");
    }

    public void deleteFile(String fileName) {
        SharedPreferences removePassengersFile = context.getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor editorPassengersFile = removePassengersFile.edit();
        editorPassengersFile.clear().apply();
    }

}

