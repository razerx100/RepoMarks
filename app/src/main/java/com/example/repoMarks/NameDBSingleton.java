package com.example.repoMarks;

import android.content.Context;

import androidx.room.Room;

public class NameDBSingleton {
    private static NameDB s_db =  null;

    public static void InitDB(Context appContext) {
        if(s_db == null)
            s_db = Room.databaseBuilder(appContext, NameDB.class, "RepoNameDB").build();
    }

    public static NameDB GetDB() {
        return s_db;
    }
}
