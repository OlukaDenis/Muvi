package com.premar.muvi.room.favorite_movies_db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.premar.muvi.model.Movie;

@androidx.room.Database(entities = Movie.class,version = 1, exportSchema = false)
public abstract class MuviDatabase extends RoomDatabase {

    public abstract FavoritesDoa favoritesDoa();
    private static MuviDatabase instance;

    public  static  synchronized MuviDatabase getInstance(Context context) {
        if(instance ==null)
        {
            String MUVI_DB = "Muvi_Database";
            instance= Room.databaseBuilder(context.getApplicationContext(), MuviDatabase.class, MUVI_DB)
                    .addCallback(callback)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback callback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
