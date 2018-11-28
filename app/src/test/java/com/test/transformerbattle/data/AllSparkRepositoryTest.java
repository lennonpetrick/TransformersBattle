package com.test.transformerbattle.data;

import com.test.transformerbattle.data.datasource.local.allspark.LocalAllSparkDataSource;
import com.test.transformerbattle.data.datasource.remote.allspark.RemoteAllSparkDataSource;
import com.test.transformerbattle.domain.AllSparkRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AllSparkRepositoryTest {

    @Mock
    private LocalAllSparkDataSource mLocalDataSource;
    @Mock
    private RemoteAllSparkDataSource mRemoteDataSource;

    private AllSparkRepository mRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mRepository = new AllSparkRepositoryImpl(mLocalDataSource, mRemoteDataSource);
    }

    @Test
    public void getAuthorizationFromRemote() {
        when(mRemoteDataSource.getAuthorization()).thenReturn(Single.just("TOKEN"));
        when(mLocalDataSource.hasAuthorization()).thenReturn(false);
        when(mLocalDataSource.storeAuthorization(anyString())).thenReturn(Completable.complete());

        mRepository.getAuthorization()
                .test()
                .assertValue("TOKEN");

        verify(mLocalDataSource, never()).getAuthorization();
        verify(mLocalDataSource).storeAuthorization(anyString());
        verify(mRemoteDataSource).getAuthorization();
    }

    @Test
    public void getAuthorizationFromLocal() {
        when(mLocalDataSource.getAuthorization()).thenReturn(Single.just("TOKEN"));
        when(mLocalDataSource.hasAuthorization()).thenReturn(true);

        mRepository.getAuthorization()
                .test()
                .assertValue("TOKEN");

        verify(mLocalDataSource).getAuthorization();
        verify(mLocalDataSource, never()).storeAuthorization(anyString());
        verify(mRemoteDataSource, never()).getAuthorization();
    }

    @Test
    public void getAuthorizationFromCache() {
        when(mLocalDataSource.getAuthorization()).thenReturn(Single.just("TOKEN"));
        when(mLocalDataSource.hasAuthorization()).thenReturn(true);

        mRepository.getAuthorization()
                .test()
                .assertValue("TOKEN");

        mRepository.getAuthorization()
                .test()
                .assertValue("TOKEN");

        verify(mLocalDataSource).getAuthorization();
        verify(mLocalDataSource, never()).storeAuthorization(anyString());
        verify(mRemoteDataSource, never()).getAuthorization();
    }
}