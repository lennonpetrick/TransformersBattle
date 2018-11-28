package com.test.transformerbattle.presentation.transformer;

import com.test.transformerbattle.R;
import com.test.transformerbattle.domain.model.Transformer;
import com.test.transformerbattle.domain.usecase.TransformerUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransformerPresenterTest {

    @Mock
    private TransformerContract.View mView;
    @Mock
    private TransformerUseCase mUseCase;

    private TransformerContract.Presenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new TransformerPresenter(mView, mUseCase);
    }

    @Test
    public void editTransformer() {
        Transformer model = new Transformer();
        model.setName("Optimus Prime");
        model.setTeam("A");
        model.setStrength(9);
        model.setIntelligence(8);
        model.setSpeed(7);
        model.setEndurance(6);
        model.setRank(5);
        model.setCourage(4);
        model.setFirepower(3);
        model.setSkill(2);

        mPresenter.editTransformer(model);

        verify(mView).setNameValue("Optimus Prime");
        verify(mView).setTeamValue("A");
        verify(mView).setStrengthValue(90);
        verify(mView).setIntelligenceValue(80);
        verify(mView).setSpeedValue(70);
        verify(mView).setEnduranceValue(60);
        verify(mView).setRankValue(50);
        verify(mView).setCourageValue(40);
        verify(mView).setFirepowerValue(30);
        verify(mView).setSkillValue(20);
    }

    @Test
    public void refreshToolbarButtonText() {
        mPresenter.refreshToolbarButtonText();
        verify(mView).changeToolbarButtonText(anyInt());
    }

    @Test
    public void saveOrUpdate() {
        when(mView.getNameValue()).thenReturn("Optimus Prime");
        when(mView.getTeamValue()).thenReturn("A");
        when(mView.getStrengthValue()).thenReturn(90);
        when(mView.getIntelligenceValue()).thenReturn(80);
        when(mView.getSpeedValue()).thenReturn(70);
        when(mView.getEnduranceValue()).thenReturn(60);
        when(mView.getRankValue()).thenReturn(50);
        when(mView.getCourageValue()).thenReturn(40);
        when(mView.getFirepowerValue()).thenReturn(30);
        when(mView.getSkillValue()).thenReturn(20);

        mPresenter.saveOrUpdate();

        verify(mView).getNameValue();
        verify(mView).getTeamValue();
        verify(mView).getStrengthValue();
        verify(mView).getIntelligenceValue();
        verify(mView).getSpeedValue();
        verify(mView).getEnduranceValue();
        verify(mView).getRankValue();
        verify(mView).getCourageValue();
        verify(mView).getFirepowerValue();
        verify(mView).getSkillValue();

        verify(mView).showProgressDialog(R.string.transformer_save_progress);
        verify(mUseCase).save(any(), any());
    }
}