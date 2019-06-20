package com.example.bashreader.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.bashreader.service.model.BashQuote;
import com.example.bashreader.R;
import com.example.bashreader.viewmodel.BashViewModel;

import java.util.ArrayList;
import java.util.List;

public class BashActivity extends AppCompatActivity {

    FloatingActionButton fab;
    BashViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null && activeNetwork.isConnectedOrConnecting());

        if (isConnected) {
            model = ViewModelProviders.of(this).get(BashViewModel.class);
            model.getQuoteList().observe(BashActivity.this, new Observer<List<BashQuote>>() {
                @Override
                public void onChanged(@Nullable List<BashQuote> bashQuotes) {
                    initRecyclerView(bashQuotes);
                }
            });
        } else
            Log.d("Waka", "Empty");
        initRecyclerView(new ArrayList<>());

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.downloadQuotesSync();
            }
        });
    }

    private void initRecyclerView(List<BashQuote> quotes) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BashRecyclerAdapter(quotes));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.settings == item.getItemId()) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
