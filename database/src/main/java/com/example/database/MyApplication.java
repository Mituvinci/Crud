package com.example.database;

import android.app.Application;



import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mitu on 5/3/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       // LeakCanary.install(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
