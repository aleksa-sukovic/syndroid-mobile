package com.aleksa.syndroid.library.database.repositories;

import com.aleksa.syndroid.library.database.dao.BaseDao;
import com.aleksa.syndroid.library.database.promises.Promise;

import java.util.List;

public class EntityRepository<Entity, DaoObject extends BaseDao<Entity>>
{
    protected DaoObject daoObject;

    protected EntityRepository()
    {
        //
    }

    public EntityRepository(DaoObject daoObject)
    {
        this.daoObject = daoObject;
    }

    public Promise all()
    {
        Promise promise = new Promise();

        new Thread(() -> {
            List<Entity> data = daoObject.all();
            promise.resolve(data);
        }).start();

        return promise;
    }

    public Promise one(long id)
    {
        Promise promise = new Promise();

        new Thread(() -> {
            Entity entity = daoObject.one(id);
            promise.resolve(entity);
        }).start();

        return promise;
    }

    public Promise insert(Entity object)
    {
        Promise promise = new Promise();

        new Thread(() -> {
            long id = daoObject.insert(object);

            one(id).then(promise::resolve);
        }).start();

        return promise;
    }

    public Promise delete(Entity object)
    {
        Promise promise = new Promise();

        new Thread(() -> {
            daoObject.delete(object);

            promise.resolve(object);
        }).start();


        return promise;
    }

    public Promise update(Entity object)
    {
        Promise promise = new Promise();

        new Thread(() -> {
            daoObject.update(object);

            promise.resolve(object);
        }).start();

        return promise;
    }

    protected void setDaoObject(DaoObject daoObject)
    {
        this.daoObject = daoObject;
    }
}
