package com.projects.melih.wonderandwander.common;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

/**
 * @see <a href="https://github.com/googlesamples/android-architecture/">SingleLiveEvent.java/a>
 * <p>
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {
    private final AtomicBoolean pending = new AtomicBoolean(false);

    @Override
    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<T> observer) {
        if (hasActiveObservers()) {
            Timber.w("Multiple observers registered but only one will be notified of changes.");
        }

        // Observe the internal MutableLiveData
        super.observe(owner, t -> {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }

    @Override
    @MainThread
    public void setValue(@Nullable T t) {
        pending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @SuppressWarnings("unused")
    @MainThread
    public void call() {
        setValue(null);
    }
}