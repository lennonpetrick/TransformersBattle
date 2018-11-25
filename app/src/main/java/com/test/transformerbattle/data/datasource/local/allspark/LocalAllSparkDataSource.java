package com.test.transformerbattle.data.datasource.local.allspark;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalAllSparkDataSource {

    private static final String AUTHORIZATION_KEY = "authorization_key";

    private SharedPreferences mSharedPreferences;

    public LocalAllSparkDataSource(SharedPreferences preferences) {
        this.mSharedPreferences = preferences;
    }

    /**
     * Gets the previous saved authorization from local database.
     *
     * @return A single of a string containing the authorization.
     * */
    public Single<String> getAuthorization() {
        return Single.create(emitter -> emitter.onSuccess(
                mSharedPreferences.getString(AUTHORIZATION_KEY, "")));
    }

    /**
     * Stores the authorization into local database.
     *
     * @return A completable.
     * */
    public Completable storeAuthorization(@NonNull String token) {
        return Completable.create(emitter -> {
            final SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(AUTHORIZATION_KEY, token);
            editor.apply();
            emitter.onComplete();
        });
    }
}
