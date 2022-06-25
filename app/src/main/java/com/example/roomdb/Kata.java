package com.example.roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "kata_table")
public class Kata {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    @ColumnInfo(name = "mKey")
    private String mKey;

    public Kata(@NonNull String word, String key) {this.mWord = word;this.mKey = key;}
    public String getWord(){return this.mWord;}
    public String getKey(){return this.mKey;}

}
