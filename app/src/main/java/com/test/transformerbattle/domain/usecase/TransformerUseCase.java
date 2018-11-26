package com.test.transformerbattle.domain.usecase;

import android.support.annotation.NonNull;

import com.test.transformerbattle.domain.model.Transformer;
import com.test.transformerbattle.domain.scheduler.DefaultScheduler;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class TransformerUseCase {

    private final CompositeDisposable mDisposables;
    private final DefaultScheduler mWorkScheduler,
            mPostScheduler;

    public TransformerUseCase(DefaultScheduler mWorkScheduler,
                              DefaultScheduler mPostScheduler) {
        this.mWorkScheduler = mWorkScheduler;
        this.mPostScheduler = mPostScheduler;
        this.mDisposables = new CompositeDisposable();
    }

    /**
     * Adds a disposable into {@link CompositeDisposable}.
     *
     * @param disposable A {@link Disposable} from an observer.
     * */
    protected void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }

    /**
     * Returns the work thread.
     *
     * @return {@link Scheduler}
     * */
    protected Scheduler getWorkScheduler() {
        return mWorkScheduler.getScheduler();
    }

    /**
     * Returns the UI thread.
     *
     * @return {@link Scheduler}
     * */
    protected Scheduler getPostScheduler() {
        return mPostScheduler.getScheduler();
    }

    /**
     * Dispose all observers.
     */
    public void dispose() {
        if (!mDisposables.isDisposed()) {
            mDisposables.dispose();
        }
    }

    /**
     * Creates a new transformer into repository.
     *
     * @param model The new transformer to be created.
     * @return A completable indicating whether the request was success.
     * */
    abstract Completable save(@NonNull Transformer model);

    /**
     * Updates a transformer into repository.
     *
     * @param model The transformer to be updated.
     * @return A completable indicating whether the request was success.
     * */
    abstract Completable update(@NonNull Transformer model);

    /**
     * Deletes a transformer from repository.
     *
     * @param model The transformer to be deleted.
     * @return A completable indicating whether the request was success.
     * */
    abstract Completable delete(@NonNull Transformer model);

    /**
     * Gets a list of transformers.
     *
     * @return An observable emitting a transformers list.
     * */
    abstract Observable<List<Transformer>> getAll();
}
