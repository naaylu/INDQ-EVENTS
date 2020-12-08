package com.example.indq_events.di.module;

import com.example.indq_events.LoginActivity;
import com.example.indq_events.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();


    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

}

