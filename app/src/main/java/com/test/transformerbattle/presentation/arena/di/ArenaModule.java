package com.test.transformerbattle.presentation.arena.di;

import android.content.Context;

import com.test.transformerbattle.domain.usecase.TransformerUseCase;
import com.test.transformerbattle.presentation.arena.ArenaContract;
import com.test.transformerbattle.presentation.arena.ArenaPresenter;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ArenaModule {

    private Context mContext;

    public ArenaModule(Context context) {
        this.mContext = context;
    }

    @Provides
    ArenaContract.View view() {
        return (ArenaContract.View) mContext;
    }

    @Provides
    @Inject
    ArenaContract.Presenter presenter(ArenaContract.View view, TransformerUseCase useCase) {
        return new ArenaPresenter(view, useCase);
    }

}
