package com.projects.melih.wonderandwander.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

/**
 * Created by Melih Gültekin on 31.07.2018
 */
public class FirebaseDatabaseManager implements LifecycleObserver {
    private final Callback callback;
    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();

    public FirebaseDatabaseManager(@NonNull Fragment lifecycleOwner, @NonNull DatabaseReference ref, @NonNull Callback callback) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        this.callback = callback;
        this.query = ref;
        lifecycle.addObserver(FirebaseDatabaseManager.this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        Timber.d("addValueEventListener");
        query.addValueEventListener(listener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        Timber.d("removeEventListener");
        query.removeEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            callback.onDataChange(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Timber.e("Can't listen to query " + query, databaseError.toException());
        }
    }

    public interface Callback {
        void onDataChange(@NonNull DataSnapshot dataSnapshot);
    }
}