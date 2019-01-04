package com.aleksa.syndroid.objects.server.repositories;

import android.app.Application;

import com.aleksa.syndroid.library.database.repositories.EntityRepository;
import com.aleksa.syndroid.library.database.SynDroidDatabase;
import com.aleksa.syndroid.objects.server.dao.ServerDao;
import com.aleksa.syndroid.objects.server.models.Server;


public class ServerRepository extends EntityRepository<Server, ServerDao>
{

    public ServerRepository(Application application) {
        SynDroidDatabase database = SynDroidDatabase.getDatabase(application);

        setDaoObject(database.serverDao());
    }

}
