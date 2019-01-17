package com.aleksa.syndroid.objects.unit_item.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.aleksa.syndroid.library.database.dao.BaseDao;
import com.aleksa.syndroid.objects.unit_item.models.Unit;

import java.util.List;

@Dao
public interface UnitDao extends BaseDao<Unit>
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Unit unit);

    @Query("DELETE FROM units")
    void deleteAll();

    @Query("SELECT * FROM units ORDER BY position ASC")
    List<Unit> all();

    @Query("SELECT * FROM units ORDER BY position ASC LIMIT 5")
    List<Unit> navigationUnits();

    @Query("SELECT * FROM units WHERE id = :id")
    Unit one(long id);

    @Update
    void update(Unit unit);

    @Delete
    void delete(Unit unit);
}
