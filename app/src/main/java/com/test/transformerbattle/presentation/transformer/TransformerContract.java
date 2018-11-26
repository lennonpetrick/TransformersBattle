package com.test.transformerbattle.presentation.transformer;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.Menu;

import com.test.transformerbattle.domain.model.Transformer;

public interface TransformerContract {
    interface View {
        void showError(String message);
        void displayNameValueMissing();
        String getNameValue();
        void setNameValue(String value);
        void displayTeamValueMissing();
        String getTeamValue();
        void setTeamValue(String value);
        int getStrengthValue();
        void setStrengthValue(int value);
        int getIntelligenceValue();
        void setIntelligenceValue(int value);
        int getSpeedValue();
        void setSpeedValue(int value);
        int getEnduranceValue();
        void setEnduranceValue(int value);
        int getRankValue();
        void setRankValue(int value);
        int getCourageValue();
        void setCourageValue(int value);
        int getFirepowerValue();
        void setFirepowerValue(int value);
        int getSkillValue();
        void setSkillValue(int value);
        void finishSuccess();
        void showProgressDialog(@StringRes int stringId);
        void dismissProgressDialog();
        void changeToolbarButtonText(@StringRes int stringRes);
        void disposeErrorsDisposable();
    }

    interface Presenter {
        void destroy();
        void editTransformer(@Nullable Transformer transformer);
        void refreshToolbarButtonText();
        void saveOrUpdate();
    }
}
