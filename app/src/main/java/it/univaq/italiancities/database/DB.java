package it.univaq.italiancities.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import it.univaq.italiancities.model.ItalianCity;

@Database(version=3, entities = {ItalianCity.class})
public abstract class DB extends RoomDatabase {

    private volatile static DB instance = null;

    public abstract CityDao cityDao();

    public static synchronized DB getInstance(Context context) {
        if (instance == null) {
            synchronized (DB.class) {
                if (instance == null) instance = Room.databaseBuilder(
                        context, DB.class, "database.db"
                ).build();
            }
        }
        return instance;
    }

}