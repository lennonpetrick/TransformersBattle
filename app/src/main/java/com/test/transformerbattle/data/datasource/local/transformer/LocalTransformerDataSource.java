package com.test.transformerbattle.data.datasource.local.transformer;

import android.support.annotation.NonNull;

import com.test.transformerbattle.data.datasource.local.LocalDatabase;
import com.test.transformerbattle.data.entity.TransformerEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalTransformerDataSource {

    private TransformerDao mTransformerDao;

    public LocalTransformerDataSource(LocalDatabase database) {
        this.mTransformerDao = database.transformerDao();
    }

    /**
     * Inserts a new transformer into local database.
     *
     * @param entity The new transformer to be inserted.
     * @return A completable.
     * */
    public Completable insert(@NonNull TransformerEntity entity) {
        return Completable.create(emitter -> {
            mTransformerDao.insert(entity);
            emitter.onComplete();
        });
    }

    /**
     * Updates a transformer in the local database.
     *
     * @param entity The transformer to be updated.
     * @return A completable.
     * */
    public Completable update(@NonNull TransformerEntity entity) {
        return Completable.create(emitter -> {
            mTransformerDao.update(entity);
            emitter.onComplete();
        });
    }

    /**
     * Deletes a transformer from local database.
     *
     * @param entity The transformer to be deleted.
     * @return A completable.
     * */
    public Completable delete(@NonNull TransformerEntity entity) {
        return Completable.create(emitter -> {
            mTransformerDao.delete(entity);
            emitter.onComplete();
        });
    }

    /**
     * Gets a list of all transformers from local database.
     *
     * @return A single of a transformer list.
     * */
    public Single<List<TransformerEntity>> getAll() {
        return Single.create(emitter -> emitter.onSuccess(mTransformerDao.getAll()));
    }
}
