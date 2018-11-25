package com.test.transformerbattle.data;

import com.test.transformerbattle.data.datasource.local.allspark.LocalAllSparkDataSource;
import com.test.transformerbattle.data.datasource.remote.allspark.RemoteAllSparkDataSource;
import com.test.transformerbattle.domain.AllSparkRepository;

import io.reactivex.Single;

public class AllSparkRepositoryImpl implements AllSparkRepository {

    private LocalAllSparkDataSource mLocalDataSource;
    private RemoteAllSparkDataSource mRemoteDataSource;
    private String mAuthorizationCache;

    public AllSparkRepositoryImpl(LocalAllSparkDataSource localDataSource,
                                  RemoteAllSparkDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Single<String> getAuthorization() {
        if (mAuthorizationCache != null) {
            return Single.just(mAuthorizationCache);
        }

        if (mLocalDataSource.hasAuthorization()) {
            return getLocalAuthorization();
        }

        return getRemoteAuthorization();
    }

    private Single<String> getLocalAuthorization() {
        return mLocalDataSource.getAuthorization()
                .doAfterSuccess(this::cacheAuthorization);
    }

    private Single<String> getRemoteAuthorization() {
        return mRemoteDataSource.getAuthorization()
                .flatMap(token -> mLocalDataSource.storeAuthorization(token)
                        .toSingleDefault(token))
                .doAfterSuccess(this::cacheAuthorization);
    }

    private void cacheAuthorization(String token) {
        mAuthorizationCache = token;
    }
}
