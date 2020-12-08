package com.example.indq_events.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.indq_events.di.util.ViewModelKey;

import com.example.indq_events.util.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    /*@Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindoginViewModel(LoginViewModel loginViewModel);*/


}

