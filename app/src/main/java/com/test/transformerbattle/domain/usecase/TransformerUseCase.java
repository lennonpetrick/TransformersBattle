package com.test.transformerbattle.domain.usecase;

import android.support.annotation.NonNull;

import com.test.transformerbattle.domain.model.Transformer;
import com.test.transformerbattle.domain.scheduler.DefaultScheduler;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;

public abstract class TransformerUseCase {

    private final CompositeDisposable mDisposables;
    private final DefaultScheduler mWorkScheduler,
            mPostScheduler;

    public TransformerUseCase(@NonNull DefaultScheduler mWorkScheduler,
                              @NonNull DefaultScheduler mPostScheduler) {
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
     * @param observer A {@link DisposableCompletableObserver} to return success or error.
     * */
    abstract void save(@NonNull Transformer model,
                       @NonNull DisposableCompletableObserver observer);

    /**
     * Updates a transformer into repository.
     *
     * @param model The transformer to be updated.
     * @param observer A {@link DisposableCompletableObserver} to return success or error.
     * */
    abstract void update(@NonNull Transformer model,
                         @NonNull DisposableCompletableObserver observer);

    /**
     * Deletes a transformer from repository.
     *
     * @param model The transformer to be deleted.
     * @param observer A {@link DisposableCompletableObserver} to return success or error.
     * */
    abstract void delete(@NonNull Transformer model,
                         @NonNull DisposableCompletableObserver observer);

    /**
     * Gets a list of transformers.
     *
     * @param observer A {@link DisposableObserver} containing a list of Transformers.
     * */
    abstract void getAll(@NonNull DisposableObserver<List<Transformer>> observer);
}
