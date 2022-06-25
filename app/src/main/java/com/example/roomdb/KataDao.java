package com.example.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface KataDao {

    @Insert
    void insert(Kata kata);

    @Query("DELETE FROM kata_table where mKey = :keyword")
    void deleteAll(String keyword);

    @Query("SELECT * from kata_table WHERE mKey = :keyword ORDER BY word ASC")
    LiveData<List<Kata>> getAllWords(String keyword);

    @Query("DELETE from kata_table WHERE mKey = :keyword")
    void deleteWord(String keyword);
}