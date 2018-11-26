package com.test.transformerbattle.presentation.transformer.di;

import com.test.transformerbattle.presentation.di.AppModule;
import com.test.transformerbattle.presentation.transformer.TransformerActivity;

import dagger.Component;

@Component(modules = {TransformerModule.class, AppModule.class})
public interface TransformerComponent {
    void inject(TransformerActivity activity);
}
