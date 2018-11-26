package com.test.transformerbattle.presentation.arena;

import com.test.transformerbattle.domain.model.Transformer;

import java.util.List;

public interface ArenaContract {

    interface View {
        void setRecyclerView(List<Transformer> transformers);
        void showError(String message);
    }

    interface Presenter {
        void destroy();
        void load();
        void battle();
    }
}
