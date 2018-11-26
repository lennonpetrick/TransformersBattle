package com.test.transformerbattle.presentation.transformer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.test.transformerbattle.R;
import com.test.transformerbattle.domain.model.Transformer;
import com.test.transformerbattle.domain.usecase.TransformerUseCase;
import com.test.transformerbattle.utils.StatsUtils;

import io.reactivex.observers.DisposableCompletableObserver;

public class TransformerPresenter implements TransformerContract.Presenter {

    private TransformerContract.View mView;
    private TransformerUseCase mUseCase;
    private Transformer mTransformer;

    public TransformerPresenter(@NonNull TransformerContract.View view,
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
    public void editTransformer(@Nullable Transformer transformer) {
        mTransformer = transformer;
        displayTransformer(transformer);
    }

    @Override
    public void refreshToolbarButtonText() {
        final int stringRes = mTransformer == null || isNewTransformer(mTransformer) ?
                R.string.menu_action_transformer_save : R.string.menu_action_transformer_update;
        mView.changeToolbarButtonText(stringRes);
    }

    @Override
    public void saveOrUpdate() {
        saveOrUpdate(createTransformerFromFields());
    }

    private Transformer createTransformerFromFields() {
        final Transformer transformer = new Transformer();

        transformer.setName(mView.getNameValue());
        transformer.setTeam(mView.getTeamValue());
        transformer.setStrength(
                progressToValue(mView.getStrengthValue()));
        transformer.setIntelligence(
                progressToValue(mView.getIntelligenceValue()));
        transformer.setSpeed(
                progressToValue(mView.getSpeedValue()));
        transformer.setEndurance(
                progressToValue(mView.getEnduranceValue()));
        transformer.setRank(
                progressToValue(mView.getRankValue()));
        transformer.setCourage(
                progressToValue(mView.getCourageValue()));
        transformer.setFirepower(
                progressToValue(mView.getFirepowerValue()));
        transformer.setSkill(
                progressToValue(mView.getSkillValue()));

        if (mTransformer != null) {
            transformer.setId(mTransformer.getId());
        }

        return transformer;
    }

    private void displayTransformer(Transformer transformer) {
        if (transformer == null)
            return;

        mView.setNameValue(transformer.getName());
        mView.setTeamValue(transformer.getTeam());
        mView.setStrengthValue(
                valueToProgress(transformer.getStrength()));
        mView.setIntelligenceValue(
                valueToProgress(transformer.getIntelligence()));
        mView.setSpeedValue(
                valueToProgress(transformer.getSpeed()));
        mView.setEnduranceValue(
                valueToProgress(transformer.getEndurance()));
        mView.setRankValue(
                valueToProgress(transformer.getRank()));
        mView.setCourageValue(
                valueToProgress(transformer.getCourage()));
        mView.setFirepowerValue(
                valueToProgress(transformer.getFirepower()));
        mView.setSkillValue(
                valueToProgress(transformer.getSkill()));
    }

    private boolean isNewTransformer(Transformer transformer) {
        return TextUtils.isEmpty(transformer.getId());
    }

    private void saveOrUpdate(Transformer transformer) {
        if (!isTransformerValid(transformer))
            return;

        if (isNewTransformer(transformer)) {
            save(transformer);
        } else {
            update(transformer);
        }
    }

    private boolean isTransformerValid(Transformer transformer) {
        mView.disposeErrorsDisposable();
        boolean isValid = true;

        if (TextUtils.isEmpty(transformer.getName())) {
            mView.displayNameValueMissing();
            isValid = false;
        }

        if (TextUtils.isEmpty(transformer.getTeam())) {
            mView.displayTeamValueMissing();
            isValid = false;
        }

        return isValid;
    }

    private void save(Transformer transformer) {
        mView.showProgressDialog(R.string.transformer_save_progress);
        mUseCase.save(transformer, getCompletableObserver());
    }

    private void update(Transformer transformer) {
        mView.showProgressDialog(R.string.transformer_update_progress);
        mUseCase.update(transformer, getCompletableObserver());
    }

    private DisposableCompletableObserver getCompletableObserver() {
        return new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mView.dismissProgressDialog();
                mView.finishSuccess();
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissProgressDialog();
                mView.showError(e.getMessage());
            }
        };
    }

    private int valueToProgress(int value) {
        return StatsUtils.valueToProgress(value);
    }

    private int progressToValue(int progress) {
        return StatsUtils.progressToValue(progress);
    }
}
