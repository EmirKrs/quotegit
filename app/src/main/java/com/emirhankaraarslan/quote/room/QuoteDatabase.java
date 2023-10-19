package com.emirhankaraarslan.quote.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.emirhankaraarslan.quote.model.Quote;

@Database(entities = {Quote.class}, version = 1)
public abstract class QuoteDatabase extends RoomDatabase {

    public abstract QuoteDao quoteDao();
}
