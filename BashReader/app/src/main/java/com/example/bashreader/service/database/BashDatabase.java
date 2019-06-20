package com.example.bashreader.service.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.bashreader.service.model.BashQuote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Database(entities = {BashQuote.class}, version = 1, exportSchema = false)
public abstract class BashDatabase extends RoomDatabase {

    public abstract BashQuoteDao bashQuoteDao();

    private static BashDatabase db;

    @Provides
    @Singleton
    static BashDatabase getInstance(Context context) {

        if (db == null) {
            db = Room.databaseBuilder(context, BashDatabase.class, "BashDatabase").build();
        }
        return db;
    }
}
