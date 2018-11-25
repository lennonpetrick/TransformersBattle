package com.test.transformerbattle.data.datasource.remote.transformer;

import com.test.transformerbattle.data.datasource.remote.Envelop;
import com.test.transformerbattle.data.entity.TransformerEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TransformerService {

    @POST("transformers/")
    Single<TransformerEntity> save(@HeaderMap Map<String, String> headers,
                                   @Body TransformerEntity entity);

    @PUT("transformers/")
    Single<TransformerEntity> update(@HeaderMap Map<String, String> headers,
                                     @Body TransformerEntity entity);

    @DELETE("transformers/{transformerId}")
    Completable delete(@HeaderMap Map<String, String> headers,
                       @Path("transformerId") String id);

    @GET("transformers/")
    Single<Envelop<List<TransformerEntity>>> fetch(@HeaderMap Map<String, String> headers);

}
