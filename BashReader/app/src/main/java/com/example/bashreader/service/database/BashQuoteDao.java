package com.example.bashreader.service.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.bashreader.service.model.BashQuote;

import java.util.List;

@Dao
public interface BashQuoteDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void InsertAll(List<BashQuote> quotes);

    @Query ("DELETE FROM BashQuote")
    void deleteAll();

    @Query ("DELETE FROM BashQuote WHERE id in (SELECT id FROM BashQuote LIMIT 1)")
    void deleteHeader();

    @Query("Select * from BashQuote")
    LiveData<List<BashQuote>> getAllBashQuotes();

    @Query("SELECT * FROM BashQuote WHERE id = :quoteId")
    LiveData<BashQuote> getBashQuote(int quoteId);
}
