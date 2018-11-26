package com.test.transformerbattle.presentation.di;

import com.test.transformerbattle.presentation.arena.ArenaActivity;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(ArenaActivity activity);
}
