package com.example.ainshamsuniversity.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Gpa.class,version = 1,exportSchema = false)
public abstract class GpaDataBase extends RoomDatabase {
    private static GpaDataBase instance;
    public abstract GpaDao GpaDao();


    public static synchronized GpaDataBase  getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    GpaDataBase.class
                    ,"gpa_database")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;
    }



}
