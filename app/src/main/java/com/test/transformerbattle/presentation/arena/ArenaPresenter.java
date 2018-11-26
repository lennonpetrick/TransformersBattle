package com.test.transformerbattle.presentation.arena;

import android.support.annotation.NonNull;

import com.test.transformerbattle.domain.usecase.TransformerUseCase;

public class ArenaPresenter implements ArenaContract.Presenter {

    private ArenaContract.View mView;
    private TransformerUseCase mUseCase;

    public ArenaPresenter(@NonNull ArenaContract.View view,
                          @NonNull TransformerUseCase useCase) {
        this.mView = view;
        this.mUseCase = useCase;
    }

    @Override
    public void destroy() {
        mUseCase.dispose();
        mUseCase = null;
        mView = null;
    }
}
