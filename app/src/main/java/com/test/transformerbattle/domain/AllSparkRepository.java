package com.test.transformerbattle.domain;

import io.reactivex.Single;

public interface AllSparkRepository {

    /**
     * Gets an authorization from the AllSpark resources.
     *
     * @return A single of a string containing the authorization.
     * */
    Single<String> getAuthorization();

}
