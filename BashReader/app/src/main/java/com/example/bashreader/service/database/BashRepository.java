package com.example.bashreader.service.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.example.bashreader.service.model.BashQuote;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BashRepository {

    //TODO: this instance must be injected using Dagger 2
    private static BashRepository bashRepository;

    private static BashDatabase bashDatabase;

    public BashRepository(Context context) {
        bashDatabase = BashDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    public BashRepository getInstance(Context context) {
        if (bashRepository == null) {
            bashRepository = new BashRepository(context);
        }
        return bashRepository;
    }

    public static void insertAllQuotes(List<BashQuote> quotes) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    bashDatabase.bashQuoteDao().InsertAll(quotes);
                    return null;
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllQuotes() {
        try {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    bashDatabase.bashQuoteDao().deleteAll();
                    return null;
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void deleteHeader() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                bashDatabase.bashQuoteDao().deleteHeader();
                return null;
            }
        }.execute();
    }

    public LiveData<List<BashQuote>> getAllQuotes() {
        return bashDatabase.bashQuoteDao().getAllBashQuotes();
    }
}
