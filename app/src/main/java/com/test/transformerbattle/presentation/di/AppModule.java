package com.test.transformerbattle.presentation.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.test.transformerbattle.BuildConfig;
import com.test.transformerbattle.data.AllSparkRepositoryImpl;
import com.test.transformerbattle.data.TransformerRepositoryImpl;
import com.test.transformerbattle.data.datasource.local.LocalDatabase;
import com.test.transformerbattle.data.datasource.local.allspark.LocalAllSparkDataSource;
import com.test.transformerbattle.data.datasource.local.transformer.LocalTransformerDataSource;
import com.test.transformerbattle.data.datasource.remote.RemoteClient;
import com.test.transformerbattle.data.datasource.remote.allspark.RemoteAllSparkDataSource;
import com.test.transformerbattle.data.datasource.remote.transformer.RemoteTransformerDataSource;
import com.test.transformerbattle.domain.AllSparkRepository;
import com.test.transformerbattle.domain.TransformerRepository;
import com.test.transformerbattle.domain.scheduler.DefaultScheduler;
import com.test.transformerbattle.domain.usecase.TransformerUseCase;
import com.test.transformerbattle.domain.usecase.TransformerUseCaseImpl;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @Inject
    TransformerUseCase useCase(@Named("WorkScheduler") DefaultScheduler workScheduler,
                               @Named("PostScheduler") DefaultScheduler postScheduler,
                               AllSparkRepository allSparkRepository,
                               TransformerRepository transformerRepository) {
        return new TransformerUseCaseImpl(workScheduler, postScheduler,
                allSparkRepository, transformerRepository);
    }

    @Provides
    @Named("WorkScheduler")
    DefaultScheduler workScheduler() {
        return Schedulers::io;
    }

    @Provides
    @Named("PostScheduler")
    DefaultScheduler postScheduler() {
        return AndroidSchedulers::mainThread;
    }

    @Provides
    @Inject
    AllSparkRepository allSparkRepository(LocalAllSparkDataSource localDataSource,
                                          RemoteAllSparkDataSource remoteDataSource) {
        return new AllSparkRepositoryImpl(localDataSource, remoteDataSource);
    }

    @Provides
    @Inject
    LocalAllSparkDataSource localAllSparkDataSource(SharedPreferences preferences) {
        return new LocalAllSparkDataSource(preferences);
    }

    @Provides
    SharedPreferences sharedPreferences() {
        return mContext.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    @Provides
    @Inject
    RemoteAllSparkDataSource remoteAllSparkDataSource(RemoteClient client) {
        return new RemoteAllSparkDataSource(client);
    }

    @Provides
    @Inject
    TransformerRepository transformerRepository(LocalTransformerDataSource localDataSource,
                                                RemoteTransformerDataSource remoteDataSource) {
        return new TransformerRepositoryImpl(localDataSource, remoteDataSource);
    }

    @Provides
    @Inject
    LocalTransformerDataSource localTransformerDataSource(LocalDatabase database) {
        return new LocalTransformerDataSource(database);
    }

    @Provides
    LocalDatabase localDatabase() {
        return LocalDatabase.getInstance(mContext);
    }

    @Provides
    @Inject
    RemoteTransformerDataSource remoteTransformerDataSource(RemoteClient client) {
        return new RemoteTransformerDataSource(client);
    }

    @Provides
    RemoteClient remoteClient() {
        return RemoteClient.getInstance();
    }

    @Provides
    CompositeDisposable disposable() {
        return new CompositeDisposable();
    }
}
