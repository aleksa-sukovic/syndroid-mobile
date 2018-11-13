package com.aleksa.syndroid.library.database.dao;

import java.util.List;

public interface BaseDao<T>
{
    List<T> all();
    T one(int id);
    void update(T object);
    void delete(T object);
    void insert(T object);
    void deleteAll();
}
