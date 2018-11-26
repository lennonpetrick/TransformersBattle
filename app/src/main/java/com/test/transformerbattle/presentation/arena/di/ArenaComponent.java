package com.test.transformerbattle.presentation.arena.di;

import com.test.transformerbattle.presentation.arena.ArenaActivity;
import com.test.transformerbattle.presentation.di.AppModule;

import dagger.Component;

@Component(modules = {ArenaModule.class, AppModule.class})
public interface ArenaComponent {
    void inject(ArenaActivity activity);
}
