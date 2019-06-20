package com.example.bashreader.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.bashreader.MainModule.DaggerMainComponent;
import com.example.bashreader.MainModule.MainComponent;
import com.example.bashreader.service.data.AppContext;
import com.example.bashreader.service.data.DataReceiver;
import com.example.bashreader.service.database.BashRepository;
import com.example.bashreader.service.model.BashQuote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

public class BashViewModel extends AndroidViewModel {

    @Inject
    DataReceiver dataReceiver;

    @Inject
    BashRepository bashRepository;

    //WTF?!
    /*@Inject
    AppContext appContext;*/

    private SharedPreferences sharedPreferences;
    private static Application application;

    public LiveData<List<BashQuote>> getQuoteList() {
        return bashRepository.getAllQuotes();
    }

    private static Context provideContext() {
        return application.getApplicationContext();
    }

    public BashViewModel(Application application) {
        super(application);
        BashViewModel.application = application;
        buildComponent().inject(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(provideContext());
    }

    public void downloadQuotesSync() {

        int num = sharedPreferences.getInt("num", 5);

        List<String> list = new ArrayList<>();
        list.addAll(sharedPreferences.getStringSet("sources", new HashSet<>()));
        BashRepository.deleteAllQuotes();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<BashQuote> quotes = dataReceiver.getQuoteListSync(list, num);
                Collections.shuffle(quotes);
                BashRepository.insertAllQuotes(formatText(quotes));
            }
        });
        thread.start();
    }

    private List<BashQuote> formatText(List<BashQuote> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, (new BashQuote(list.get(i).getQuote()
                    .replaceAll("<br />", "")
                    .replaceAll("&quot;", "\"")
                    .replaceAll("&laquo;", "«")
                    .replaceAll("&raquo;", "»")
                    .replaceAll("&lt;", "<")
                    .replaceAll("&gt;", ">")
                    .replaceAll("<p>", "")
                    .replaceAll("</p>", "")
                    .replaceAll("&nbsp;", " ")
                    .replaceAll("</a>", "")
                    .replaceAll("<a rel=\"nofollow\">", ""))));
        }
        return list;
    }

    private MainComponent buildComponent() {
        return DaggerMainComponent.builder()
                .dataReceiver(new DataReceiver())
                .bashRepository(new BashRepository(provideContext()))
                .appContext(new AppContext(application))
                .build();
    }
}
