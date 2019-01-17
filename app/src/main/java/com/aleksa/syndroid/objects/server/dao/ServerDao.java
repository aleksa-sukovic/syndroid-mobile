package com.aleksa.syndroid.objects.server.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.aleksa.syndroid.library.database.dao.BaseDao;
import com.aleksa.syndroid.objects.server.models.Server;

import java.util.List;

@Dao
public interface ServerDao extends BaseDao<Server>
{
    @Insert
    long insert(Server server);

    @Query("DELETE FROM servers")
    void deleteAll();

    @Query("SELECT * FROM servers ORDER BY position ASC")
    List<Server> all();

    @Query("SELECT * FROM servers WHERE id = :id")
    Server one(long id);

    @Update
    void update(Server server);

    @Delete
    void delete(Server server);
}
