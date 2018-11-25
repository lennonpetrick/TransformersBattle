package com.test.transformerbattle.data;

import android.support.annotation.NonNull;

import com.test.transformerbattle.data.datasource.local.transformer.LocalTransformerDataSource;
import com.test.transformerbattle.data.datasource.remote.transformer.RemoteTransformerDataSource;
import com.test.transformerbattle.data.entity.TransformerEntity;
import com.test.transformerbattle.domain.TransformerRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class TransformerRepositoryImpl implements TransformerRepository {

    private LocalTransformerDataSource mLocalDataSource;
    private RemoteTransformerDataSource mRemoteDataSource;

    public TransformerRepositoryImpl(LocalTransformerDataSource localDataSource,
                                     RemoteTransformerDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Completable save(@NonNull String authorization, @NonNull TransformerEntity entity) {
        return mRemoteDataSource.save(authorization, entity)
                .flatMapCompletable(transformerEntity ->
                        mLocalDataSource.insert(transformerEntity));
    }

    @Override
    public Completable update(@NonNull String authorization, @NonNull TransformerEntity entity) {
        return mRemoteDataSource.update(authorization, entity)
                .flatMapCompletable(transformerEntity ->
                        mLocalDataSource.update(transformerEntity));
    }

    @Override
    public Completable delete(@NonNull String authorization, @NonNull TransformerEntity entity) {
        return mRemoteDataSource.delete(authorization, entity.getId())
                .andThen(mLocalDataSource.delete(entity));
    }

    @Override
    public Observable<List<TransformerEntity>> getAll(@NonNull String authorization) {
        return mLocalDataSource.getAll()
                .concatWith(mRemoteDataSource.fetch(authorization))
                .toObservable();
    }
}
