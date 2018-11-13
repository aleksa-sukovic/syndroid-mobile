package com.aleksa.syndroid.library.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.server.dao.ServerDao;


@Database(entities = {Server.class}, version = 1)
public abstract class SynDroidDatabase extends RoomDatabase
{
    public abstract ServerDao serverDao();

    private static volatile SynDroidDatabase INSTANCE;
    private static RoomDatabase.Callback insertCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db)
        {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    public static SynDroidDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SynDroidDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        SynDroidDatabase.class, "syndroid")
                        .addCallback(insertCallback)
                        .build();
                }
            }
        }

        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SynDroidDatabase database;

        public PopulateDbAsync(SynDroidDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            database.serverDao().deleteAll();

            Server[] servers = {
                new Server("192.168.1.101", "AleksaDTC"),
                new Server("192.168.1.102", "AleksaLPT"),
                new Server("192.168.1.103", "Aleksa MacMini"),
                new Server("192.168.1.104", "ZoranDTC"),
                new Server("192.168.1.105", "MatijaLPT"),
                new Server("192.168.1.106", "NevenaDTC"),
            };

            for (Server server: servers) {
                database.serverDao().insert(server);
            }

            return null;
        }
    }
}
