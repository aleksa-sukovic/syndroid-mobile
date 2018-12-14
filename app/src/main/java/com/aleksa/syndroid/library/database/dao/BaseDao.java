package com.aleksa.syndroid.library.database.dao;

import java.util.List;

public interface BaseDao<T>
{
    List<T> all();
    T one(long id);
    void update(T object);
    void delete(T object);
    long insert(T object);
    void deleteAll();
}
