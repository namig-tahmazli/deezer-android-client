package com.namig.tahmazli.deezerandroidclient.utils.usecase;

import androidx.annotation.MainThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class UseCaseObservable<Listener> {
    private final Lock lock = new ReentrantLock();
    private final List<Listener> mListeners = new ArrayList<>();

    private final Executor mMainExecutor;
    public UseCaseObservable(final Executor mainExecutor) {
        this.mMainExecutor = mainExecutor;
    }

    public void addListener(final Listener listener) {
        updateListeners(() -> mListeners.add(listener));
    }

    public void removeListener(final Listener listener) {
        updateListeners(() -> mListeners.remove(listener));
    }

    @MainThread
    protected void notifyListeners(final NotifyListener<Listener> notifier) {
        try {
            lock.lock();
            for (Listener listener : mListeners) {
                mMainExecutor.execute(() -> notifier.notify(listener));
            }
        } finally {
            lock.unlock();
        }
    }

    private void updateListeners(final UpdateListenerJob doJob) {
        if (lock.tryLock()) {
            try {
                doJob.invoke();
            } finally {
                lock.unlock();
            }
        } else {
            new Thread(() -> {
                try {
                    lock.lock();
                    doJob.invoke();
                } finally {
                    lock.unlock();
                }
            }).start();
        }
    }

    private interface UpdateListenerJob {
        void invoke();
    }

    public interface NotifyListener<Listener> {
        void notify(final Listener listener);
    }
}
