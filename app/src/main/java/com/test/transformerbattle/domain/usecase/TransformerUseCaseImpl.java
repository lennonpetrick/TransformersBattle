package com.test.transformerbattle.domain.usecase;

import android.support.annotation.NonNull;

import com.test.transformerbattle.data.entity.TransformerEntity;
import com.test.transformerbattle.domain.AllSparkRepository;
import com.test.transformerbattle.domain.TransformerRepository;
import com.test.transformerbattle.domain.model.Transformer;
import com.test.transformerbattle.domain.model.TransformerMapper;
import com.test.transformerbattle.domain.scheduler.DefaultScheduler;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;

public class TransformerUseCaseImpl extends TransformerUseCase {

    private AllSparkRepository mAllSparkRepository;
    private TransformerRepository mTransformerRepository;

    public TransformerUseCaseImpl(@NonNull DefaultScheduler workScheduler,
                                  @NonNull DefaultScheduler postScheduler,
                                  @NonNull AllSparkRepository allSparkRepository,
                                  @NonNull TransformerRepository transformerRepository) {
        super(workScheduler, postScheduler);
        this.mAllSparkRepository = allSparkRepository;
        this.mTransformerRepository = transformerRepository;
    }

    @Override
    public void save(@NonNull Transformer model,
              @NonNull DisposableCompletableObserver observer) {
        final Completable completable = mAllSparkRepository.getAuthorization()
                .subscribeOn(getWorkScheduler())
                .observeOn(getWorkScheduler())
                .flatMapCompletable(token ->
                        mTransformerRepository.save(token, toEntity(model)))
                .observeOn(getPostScheduler());

        addDisposable(completable.subscribeWith(observer));
    }

    @Override
    public void update(@NonNull Transformer model,
                @NonNull DisposableCompletableObserver observer) {
        final Completable completable = mAllSparkRepository.getAuthorization()
                .subscribeOn(getWorkScheduler())
                .observeOn(getWorkScheduler())
                .flatMapCompletable(token ->
                        mTransformerRepository.update(token, toEntity(model)))
                .observeOn(getPostScheduler());

        addDisposable(completable.subscribeWith(observer));
    }

    @Override
    public void delete(@NonNull Transformer model,
                @NonNull DisposableCompletableObserver observer) {
        final Completable completable = mAllSparkRepository.getAuthorization()
                .subscribeOn(getWorkScheduler())
                .observeOn(getWorkScheduler())
                .flatMapCompletable(token ->
                        mTransformerRepository.delete(token, toEntity(model)))
                .observeOn(getPostScheduler());

        addDisposable(completable.subscribeWith(observer));
    }

    @Override
    public void getAll(@NonNull DisposableObserver<List<Transformer>> observer) {
        final Observable<List<Transformer>> observable = mAllSparkRepository.getAuthorization()
                .subscribeOn(getWorkScheduler())
                .observeOn(getWorkScheduler())
                .flatMapObservable(token -> mTransformerRepository.getAll(token)
                        .map(this::toModels))
                .observeOn(getPostScheduler());

        addDisposable(observable.subscribeWith(observer));
    }

    private TransformerEntity toEntity(Transformer model) {
        return TransformerMapper.transformToEntity(model);
    }

    private List<Transformer> toModels(List<TransformerEntity> entities) {
        return TransformerMapper.transformToModels(entities);
    }
}
