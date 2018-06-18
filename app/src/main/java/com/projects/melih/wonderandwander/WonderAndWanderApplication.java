package com.projects.melih.wonderandwander;

import android.app.Application;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
public class WonderAndWanderApplication extends Application {
    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build());
        }
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        Timber.plant(new Timber.DebugTree());
    }
}