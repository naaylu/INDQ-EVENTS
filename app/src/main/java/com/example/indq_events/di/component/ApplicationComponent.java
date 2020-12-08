package com.example.indq_events.di.component;

import android.app.Application;

import com.example.indq_events.base.BaseApplication;
import com.example.indq_events.di.module.ActivityBindingModule;
import com.example.indq_events.di.module.AplicationModule;
import com.example.indq_events.di.module.ContextModule;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ContextModule.class, AplicationModule.class, AndroidSupportInjectionModule.class, ActivityBindingModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }


