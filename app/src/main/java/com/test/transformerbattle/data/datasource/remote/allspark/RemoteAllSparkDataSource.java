package com.test.transformerbattle.data.datasource.remote.allspark;

import com.test.transformerbattle.data.datasource.remote.RemoteClient;

import io.reactivex.Single;

public class RemoteAllSparkDataSource {

    private AllSparkService mService;

    public RemoteAllSparkDataSource(RemoteClient client) {
        this.mService = client.createService(AllSparkService.class);
    }

    /**
     * Gets an authorization from remote AllSpark to access other services.
     *
     * @return A single of a string containing the authorization.
     * */
    public Single<String> getAuthorization() {
        return mService.getAuthorization();
    }
}
