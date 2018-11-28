package com.test.transformerbattle.presentation.arena;

import com.test.transformerbattle.domain.usecase.TransformerUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class ArenaPresenterTest {

    @Mock
    private ArenaContract.View mView;
    @Mock
    private TransformerUseCase mUseCase;

    private ArenaContract.Presenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new ArenaPresenter(mView, mUseCase);
    }

    @Test
    public void load() {
        mPresenter.load();
        verify(mUseCase).getAll(any());
    }

    @Test
    public void delete() {
        mPresenter.delete(null);
        verify(mUseCase).delete(any(), any());
    }

    @Test
    public void battle() {
        mPresenter.battle(new ArrayList<>());
        verify(mUseCase).battle(any(), any());
    }
}