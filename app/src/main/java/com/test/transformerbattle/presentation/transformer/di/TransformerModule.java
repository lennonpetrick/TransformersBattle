package com.test.transformerbattle.presentation.transformer.di;

import android.content.Context;

import com.test.transformerbattle.domain.usecase.TransformerUseCase;
import com.test.transformerbattle.presentation.transformer.TransformerContract;
import com.test.transformerbattle.presentation.transformer.TransformerPresenter;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class TransformerModule {

    private Context mContext;

    public TransformerModule(Context context) {
        this.mContext = context;
    }

    @Provides
    TransformerContract.View view() {
        return (TransformerContract.View) mContext;
    }

    @Provides
    @Inject
    TransformerContract.Presenter presenter(TransformerContract.View view,
                                            TransformerUseCase useCase) {
        return new TransformerPresenter(view, useCase);
    }

}
