package com.test.transformerbattle.domain;

import android.support.annotation.NonNull;

import com.test.transformerbattle.data.entity.TransformerEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface TransformerRepository {

    /**
     * Creates a new transformer into data sources.
     *
     * @param authorization It's an AllSpark authorization.
     * @param entity The new transformer to be created.
     * @return A completable indicating whether the request was success.
     * */
    Completable save(@NonNull String authorization, @NonNull TransformerEntity entity);

    /**
     * Updates a transformer into data sources.
     *
     * @param authorization It's an AllSpark authorization.
     * @param entity The transformer to be updated.
     * @return A completable indicating whether the request was success.
     * */
    Completable update(@NonNull String authorization, @NonNull TransformerEntity entity);

    /**
     * Deletes a transformer from data sources.
     *
     * @param authorization It's an AllSpark authorization.
     * @param entity The transformer to be deleted.
     * @return A completable indicating whether the request was success.
     * */
    Completable delete(@NonNull String authorization, @NonNull TransformerEntity entity);

    /**
     * Returns a local list of transformers and then fetches an updated list from remote.
     *
     * @param authorization It's an AllSpark authorization.
     * @return An observable emitting a local list and remote list.
     * */
    Observable<List<TransformerEntity>> getAll(@NonNull String authorization);

}
