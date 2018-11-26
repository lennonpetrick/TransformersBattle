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

public class TransformerUseCaseImpl extends TransformerUseCase {

    private AllSparkRepository mAllSparkRepository;
    private TransformerRepository mTransformerRepository;

    public TransformerUseCaseImpl(DefaultScheduler workScheduler,
                                  DefaultScheduler postScheduler,
                                  AllSparkRepository allSparkRepository,
                                  TransformerRepository transformerRepository) {
        super(workScheduler, postScheduler);
        this.mAllSparkRepository = allSparkRepository;
        this.mTransformerRepository = transformerRepository;
    }

    @Override
    public Completable save(@NonNull Transformer model) {
        return mAllSparkRepository.getAuthorization()
                .flatMapCompletable(token ->
                        mTransformerRepository.save(token, toEntity(model)));
    }

    @Override
    public Completable update(@NonNull Transformer model) {
        return mAllSparkRepository.getAuthorization()
                .flatMapCompletable(token ->
                        mTransformerRepository.update(token, toEntity(model)));
    }

    @Override
    public Completable delete(@NonNull Transformer model) {
        return mAllSparkRepository.getAuthorization()
                .flatMapCompletable(token ->
                        mTransformerRepository.delete(token, toEntity(model)));
    }

    @Override
    public Observable<List<Transformer>> getAll() {
        return mAllSparkRepository.getAuthorization()
                .flatMapObservable(token ->
                        mTransformerRepository.getAll(token)
                                .map(this::toModels));
    }

    private TransformerEntity toEntity(Transformer model) {
        return TransformerMapper.transformToEntity(model);
    }

    private List<Transformer> toModels(List<TransformerEntity> entities) {
        return TransformerMapper.transformToModels(entities);
    }
}
