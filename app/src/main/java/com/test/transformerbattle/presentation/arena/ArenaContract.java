package com.test.transformerbattle.presentation.arena;

import com.test.transformerbattle.domain.model.Transformer;
import com.test.transformerbattle.presentation.Battle;

import java.util.List;

public interface ArenaContract {

    interface View {
        void setRecyclerView(List<Transformer> transformers);
        void showError(String message);
        void showBattleResultDialog(Battle.Result result);
    }

    interface Presenter {
        void destroy();
        void load();
        void delete(Transformer transformer);
        void battle(List<Transformer> transformers);
    }
}
