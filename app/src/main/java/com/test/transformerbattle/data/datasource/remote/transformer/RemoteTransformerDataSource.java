package com.test.transformerbattle.data.datasource.remote.transformer;

import android.support.annotation.NonNull;

import com.test.transformerbattle.data.datasource.remote.Envelop;
import com.test.transformerbattle.data.datasource.remote.RemoteClient;
import com.test.transformerbattle.data.entity.TransformerEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public class RemoteTransformerDataSource {

    private TransformerService mService;

    public RemoteTransformerDataSource(RemoteClient client) {
        this.mService = client.createService(TransformerService.class);
    }

    /**
     * Creates a new transformer into remote service.
     *
     * @param authorization It's an AllSpark authorization.
     * @param entity The new transformer to be created.
     * @return A single of the recent created transformer.
     * */
    public Single<TransformerEntity> save(@NonNull String authorization,
                                          @NonNull TransformerEntity entity) {
        return mService.save(getHeaders(authorization), entity);
    }

    /**
     * Updates a transformer into remote service.
     *
     * @param authorization It's an AllSpark authorization.
     * @param entity The transformer to be updated.
     * @return A single of the recent updated transformer.
     * */
    public Single<TransformerEntity> update(@NonNull String authorization,
                                            @NonNull TransformerEntity entity) {
        return mService.update(getHeaders(authorization), entity);
    }

    /**
     * Deletes a transformer from remote service.
     *
     * @param authorization It's an AllSpark authorization.
     * @param id The desired transformer's id to be deleted.
     * @return A completable indicating whether the request was success.
     * */
    public Completable delete(@NonNull String authorization, @NonNull String id) {
        return mService.delete(getHeaders(authorization), id);
    }

    /**
     * Fetches a list of transformers from the remote service.
     *
     * @param authorization It's an AllSpark authorization.
     * @return A single of a transformer list.
     * */
    public Single<List<TransformerEntity>> fetch(@NonNull String authorization) {
        return mService.fetch(getHeaders(authorization))
                .map(Envelop::getTransformers);
    }

    private Map<String, String> getHeaders(String authorization) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authorization);
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
