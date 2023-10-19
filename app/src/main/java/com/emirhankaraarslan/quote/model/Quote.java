package com.emirhankaraarslan.quote.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Quote {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "quotename")
    public String quoteName;
    @ColumnInfo(name = "authorname")
    public String authorName;
    @ColumnInfo(name = "bookname")
    public String bookName;

    public Quote(String quoteName, String authorName, String bookName){
        this.quoteName = quoteName;
        this.authorName = authorName;
        this.bookName = bookName;
    }
}
