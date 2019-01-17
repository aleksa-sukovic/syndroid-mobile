package com.aleksa.syndroid.objects.unit_item.repositories;

import android.app.Application;

import com.aleksa.syndroid.library.database.SynDroidDatabase;
import com.aleksa.syndroid.library.database.promises.Promise;
import com.aleksa.syndroid.library.database.repositories.EntityRepository;
import com.aleksa.syndroid.objects.unit_item.dao.UnitDao;
import com.aleksa.syndroid.objects.unit_item.models.Unit;

public class UnitRepository extends EntityRepository<Unit, UnitDao>
{

    public UnitRepository(Application application)
    {
        SynDroidDatabase database = SynDroidDatabase.getDatabase(application);

        setDaoObject(database.unitDao());
    }

    public Promise getNavigationMenuUnits()
    {
        Promise promise = new Promise();

        new Thread(() -> promise.resolve(this.daoObject.navigationUnits())).start();

        return promise;
    }

    public Promise updateAll(Unit... units)
    {
        Promise promise = new Promise();

        new Thread(() -> {
           for (Unit unit : units) {
               this.update(unit);
           }

           promise.resolve(units);
        }).start();

        return promise;
    }
}
