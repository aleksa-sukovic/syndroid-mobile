package com.aleksa.syndroid.library.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.server.dao.ServerDao;


@Database(entities = {Server.class}, version = 1)
public abstract class SynDroidDatabase extends RoomDatabase
{
    // Initialization of Dao instances that are used throughout application
    public abstract ServerDao serverDao();

    // Singleton implementation of SynDroid database
    private static volatile SynDroidDatabase INSTANCE;

    public static SynDroidDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SynDroidDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        SynDroidDatabase.class, "syndroid")
                        .build();
                }
            }
        }

        return INSTANCE;
    }
}
