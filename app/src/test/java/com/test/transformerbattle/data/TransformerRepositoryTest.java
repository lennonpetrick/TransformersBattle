package com.test.transformerbattle.data;

import com.test.transformerbattle.data.datasource.local.transformer.LocalTransformerDataSource;
import com.test.transformerbattle.data.datasource.remote.transformer.RemoteTransformerDataSource;
import com.test.transformerbattle.data.entity.TransformerEntity;
import com.test.transformerbattle.domain.TransformerRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransformerRepositoryTest {

    @Mock
    private LocalTransformerDataSource mLocalDataSource;
    @Mock
    private RemoteTransformerDataSource mRemoteDataSource;
    @Mock
    private TransformerEntity mEntity;

    private TransformerRepository mRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mRepository = new TransformerRepositoryImpl(mLocalDataSource, mRemoteDataSource);
    }

    @Test
    public void save() {
        when(mRemoteDataSource.save(anyString(), any())).thenReturn(Single.just(mEntity));
        when(mLocalDataSource.insert(any())).thenReturn(Completable.complete());

        mRepository.save("TOKEN", mEntity)
                .test()
                .assertComplete();

        verify(mRemoteDataSource).save("TOKEN", mEntity);
        verify(mLocalDataSource).insert(mEntity);
    }

    @Test
    public void update() {
        when(mRemoteDataSource.update(anyString(), any())).thenReturn(Single.just(mEntity));
        when(mLocalDataSource.update(any())).thenReturn(Completable.complete());

        mRepository.update("TOKEN", mEntity)
                .test()
                .assertComplete();

        verify(mRemoteDataSource).update("TOKEN", mEntity);
        verify(mLocalDataSource).update(mEntity);
    }

    @Test
    public void delete() {
        when(mRemoteDataSource.delete(anyString(), anyString())).thenReturn(Completable.complete());
        when(mLocalDataSource.delete(any())).thenReturn(Completable.complete());
        when(mEntity.getId()).thenReturn("id");

        mRepository.delete("TOKEN", mEntity)
                .test()
                .assertComplete();

        verify(mRemoteDataSource).delete("TOKEN", "id");
        verify(mLocalDataSource).delete(mEntity);
    }

    @Test
    public void getAll() {
        List<TransformerEntity> entities = new ArrayList<>();
        entities.add(mEntity);

        when(mRemoteDataSource.fetch(anyString())).thenReturn(Single.just(entities));
        when(mLocalDataSource.getAll()).thenReturn(Single.just(entities));

        mRepository.getAll("TOKEN")
                .test()
                .assertValueCount(2);

        verify(mLocalDataSource).getAll();
        verify(mRemoteDataSource).fetch("TOKEN");
    }
}