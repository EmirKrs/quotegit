package com.emirhankaraarslan.quote.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.emirhankaraarslan.quote.model.Quote;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface QuoteDao {

    @Query("SELECT * FROM Quote")
    Flowable<List<Quote>> getArtWithNameAndId();

    @Query("SELECT * FROM Quote WHERE id = :id")
    Flowable<Quote> getArtById(int id);

    @Insert
    Completable insert(Quote quote);

    @Delete
    Completable delete(Quote quote);
}
