
package com.example.bashreader.service.data;

import android.util.Log;

import com.example.bashreader.service.model.BashQuote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataReceiver {

    private DataReceiver dataReceiver;
    private BashAPI bashAPI;
    private int num;

    private static List<BashQuote> bashQuotes;

    public DataReceiver() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bashAPI = retrofit.create(BashAPI.class);
    }

    @Provides
    @Singleton
    synchronized DataReceiver getInstance() {
        if (dataReceiver == null) {
            dataReceiver = new DataReceiver();
        }
        return dataReceiver;
    }

    public List<BashQuote> getQuoteListSync(List<String> list, int num) {
        this.num = num;
        if (bashQuotes == null) {
            bashQuotes = new ArrayList<>();
        }
        loadQuotesSync(list);
        return bashQuotes;
    }

    private List<BashQuote> loadQuotesSync (List<String> lists) {

        Call<List<BashQuote>> call;

        bashQuotes.clear();
        for (String str : lists) {
            switch (str) {
                case "bash.im":
                    call = bashAPI.getData(str, "bash", num);
                    try {
                        bashQuotes.addAll(call.execute().body());
                        bashQuotes.remove(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case "zadolba.li":
                    call = bashAPI.getData(str, "zadolbali", num);
                    try {
                        bashQuotes.addAll(call.execute().body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;

                case "xkcdb.com":
                    call = bashAPI.getData(str, "XKCDB", num);
                    try {
                        bashQuotes.addAll(call.execute().body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        return bashQuotes;
    }

    public List<BashQuote> getQuoteList(String site, String name, int num) {
        if (bashQuotes == null) {
            bashQuotes = new ArrayList<>();
        }
        loadQuotes(site, name, num);
        return bashQuotes;
    }

    private void loadQuotes(String site, String name, int num) {

        bashAPI.getData(site, name, num).enqueue(new Callback<List<BashQuote>>() {
            @Override
            public void onResponse(Call<List<BashQuote>> call, Response<List<BashQuote>> response) {
                bashQuotes.clear();
                bashQuotes.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<BashQuote>> call, Throwable t) {
                Log.e("Download failed", "onFailure: ", t);
            }
        });
    }
}