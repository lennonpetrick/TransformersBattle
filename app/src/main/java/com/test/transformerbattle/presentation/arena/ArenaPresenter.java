package com.test.transformerbattle.presentation.arena;

import android.support.annotation.NonNull;

import com.test.transformerbattle.domain.model.Transformer;
import com.test.transformerbattle.domain.usecase.TransformerUseCase;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

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

    @Override
    public void load() {
        mUseCase.getAll(new DisposableObserver<List<Transformer>>() {
            @Override
            public void onNext(List<Transformer> transformers) {
                mView.setRecyclerView(transformers);
            }

            @Override
            public void onError(Throwable e) {
                mView.showError(e.getMessage());
            }

            @Override
            public void onComplete() {}
        });
    }

    @Override
    public void battle() {

    }
}
