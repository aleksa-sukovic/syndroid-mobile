package com.aleksa.syndroid.library.database.repositories;

import android.os.AsyncTask;
import android.util.Log;

import com.aleksa.syndroid.library.database.dao.BaseDao;
import com.aleksa.syndroid.library.database.promises.Promise;

import java.util.List;

public class EntityRepository<Entity, DaoObject extends BaseDao<Entity>>
{
    private DaoObject daoObject;

    protected EntityRepository() {
        //
    }

    public EntityRepository(DaoObject daoObject) {
        this.daoObject = daoObject;
    }

    public Promise all() {
        Promise promise = new Promise();

        new Thread(() -> {
            List<Entity> data = daoObject.all();
            promise.resolve(data);
        }).start();

        return promise;
    }

    public Promise one(int id) {
        Promise promise = new Promise();

        new Thread(() -> {
            Entity entity = daoObject.one(id);
            promise.resolve(entity);
        }).start();

        return promise;
    }

    public void insert(Entity... objects) {
        for(Entity object : objects) {
            daoObject.insert(object);
        }
    }

    public Promise delete(Entity object) {
        Promise promise = new Promise();

        new Thread(() -> {
            daoObject.delete(object);

            promise.resolve(object);
        }).start();


        return promise;
    }

    public Promise update(Entity object) {
        Promise promise = new Promise();

        new Thread(() -> {
            daoObject.update(object);

            promise.resolve(object);
        }).start();

        return promise;
    }

    protected void setDaoObject(DaoObject daoObject) {
        this.daoObject = daoObject;
    }

    private static class ExecuteTask extends AsyncTask<Promise, Void, Void> {

        @Override
        protected Void doInBackground(Promise... promises)
        {
            return null;
        }

    }
}
