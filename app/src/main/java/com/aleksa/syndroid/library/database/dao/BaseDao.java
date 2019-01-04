package com.aleksa.syndroid.library.database.dao;

import java.util.List;

public interface BaseDao<Entity>
{
    List<Entity> all();
    Entity one(long id);
    void update(Entity object);
    void delete(Entity object);
    long insert(Entity object);
    void deleteAll();
}
