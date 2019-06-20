package com.example.bashreader.MainModule;

import com.example.bashreader.service.data.AppContext;
import com.example.bashreader.service.data.DataReceiver;
import com.example.bashreader.service.database.BashRepository;
import com.example.bashreader.viewmodel.BashViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component (modules = {BashRepository.class, DataReceiver.class, AppContext.class})
@Singleton
public interface MainComponent {
    void inject (BashViewModel bashViewModel);
}
