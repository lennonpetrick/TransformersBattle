package com.test.transformerbattle.data.datasource.remote.allspark;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface AllSparkService {

    @GET("allspark/")
    Single<String> getAuthorization();

}
