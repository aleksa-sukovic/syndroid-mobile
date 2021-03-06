package com.aleksa.syndroid.library.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.aleksa.syndroid.R;
import com.aleksa.syndroid.objects.server.models.Server;
import com.aleksa.syndroid.objects.server.dao.ServerDao;
import com.aleksa.syndroid.objects.unit_item.dao.UnitDao;
import com.aleksa.syndroid.objects.unit_item.models.Unit;

@Database(entities = {Server.class, Unit.class}, version = 2, exportSchema = false)
public abstract class SynDroidDatabase extends RoomDatabase
{
    // Initialization of Dao instances that are used throughout application
    public abstract ServerDao serverDao();
    public abstract UnitDao unitDao();

    // Singleton implementation of SynDroid database
    private static volatile SynDroidDatabase INSTANCE;

    public static SynDroidDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null) {
            synchronized (SynDroidDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SynDroidDatabase.class, "syndroid")
                        .addCallback(new Callback()
                        {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db)
                            {
                                super.onCreate(db);

                                new PopulateUnitsAsync(INSTANCE).execute();
                            }
                        }).build();
                }
            }
        }

        return INSTANCE;
    }

    private static class PopulateUnitsAsync extends AsyncTask<Void, Void, Void>
    {
        private UnitDao unitDao;

        PopulateUnitsAsync(SynDroidDatabase database)
        {
            unitDao = database.unitDao();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            unitDao.deleteAll();

            unitDao.insert(new Unit("mouse","Mouse", 0, "Control PC mouse.",R.drawable.ic_mouse));
            unitDao.insert(new Unit( "media", "Media", 1, "Control PC media.", R.drawable.ic_media));
            unitDao.insert(new Unit("state", "State", 2, "Change PC state.", R.drawable.ic_power_settings));
            unitDao.insert(new Unit("keyboard", "Keyboard", 3, "Use your PC keyboard.", R.drawable.ic_keyboard_navigation));

            return null;
        }
    }
}
