package com.example.wirtualnaszafa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//DAO = data access objects
@Dao
public interface WardrobeDAO {
    @Query("SELECT * FROM WardrobeDB")
    List<WardrobeDB> getAll();

    @Query("SELECT * FROM WardrobeDB")
    LiveData<List<WardrobeDB>> getAllLive();

    @Query("SELECT path FROM WardrobeDB")
    List<String> findPath();

    @Insert
    void insert(WardrobeDB wardrobeDB);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WardrobeDB> wardrobeDBs);

    @Delete
    void delete(WardrobeDB wardrobeDB);

    @Update
    void update(WardrobeDB wardrobeDB);
}
