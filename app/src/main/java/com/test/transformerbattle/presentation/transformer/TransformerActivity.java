package com.test.transformerbattle.presentation.transformer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxRadioGroup;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.test.transformerbattle.R;
import com.test.transformerbattle.domain.model.Transformer;
import com.test.transformerbattle.presentation.di.AppModule;
import com.test.transformerbattle.presentation.transformer.di.DaggerTransformerComponent;
import com.test.transformerbattle.presentation.transformer.di.TransformerModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class TransformerActivity extends AppCompatActivity implements TransformerContract.View {

    @BindView(R.id.inputName) TextInputLayout mInputName;
    @BindView(R.id.edName) EditText mEdName;
    @BindView(R.id.tvTeam) TextView mLabelTeam;
    @BindView(R.id.rgTeam) RadioGroup mRgTeam;
    @BindView(R.id.sbStrength) SeekBar mSbStrength;
    @BindView(R.id.sbIntelligence) SeekBar mSbIntelligence;
    @BindView(R.id.sbSpeed) SeekBar mSbSpeed;
    @BindView(R.id.sbEndurance) SeekBar mSbEndurance;
    @BindView(R.id.sbRank) SeekBar mSbRank;
    @BindView(R.id.sbCourage) SeekBar mSbCourage;
    @BindView(R.id.sbFirePower) SeekBar mSbFirePower;
    @BindView(R.id.sbSkill) SeekBar mSbSkill;

    @Inject
    TransformerContract.Presenter mPresenter;
    @Inject
    CompositeDisposable mErrorsDisposable;

    private Menu mMenu;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformer);
        ButterKnife.bind(this);
        createToolbar();
        injectDependencies();
        checkExtras(getIntent().getExtras());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_transformer, menu);
        mPresenter.refreshToolbarButtonText();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_save_update: {
                mPresenter.saveOrUpdate();
                return true;
            }

            case android.R.id.home : {
                finish();
                return true;
            }

            default : {
                return false;
            }
        }
    }

    @Override
    protected void onDestroy() {
        disposeErrorsDisposable();
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mInputName.getRootView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void displayNameValueMissing() {
        mInputName.setError(getText(R.string.transformer_name_missing));
        mInputName.setErrorEnabled(true);
        subscribeEditTextToCancelError(mEdName, mInputName);
    }

    @Override
    public String getNameValue() {
        return mEdName.getText().toString();
    }

    @Override
    public void setNameValue(String value) {
        mEdName.setText(value);
    }

    @Override
    public void displayTeamValueMissing() {
        mLabelTeam.setError(getText(R.string.transformer_team_missing));
        subscribeRadioGroupToCancelError(mRgTeam, mLabelTeam);
    }

    @Override
    public String getTeamValue() {
        switch (mRgTeam.getCheckedRadioButtonId()) {
            case R.id.rbTeamAutobots: {
                return "A";
            }

            case R.id.rbTeamDecepticons: {
                return "D";
            }

            default: {
                return null;
            }
        }
    }

    @Override
    public void setTeamValue(String value) {
        switch (value) {
            case "A": {
                mRgTeam.check(R.id.rbTeamAutobots);
                break;
            }

            case "D": {
                mRgTeam.check(R.id.rbTeamDecepticons);
                break;
            }

            default: {
                mRgTeam.check(-1);
            }
        }
    }

    @Override
    public int getStrengthValue() {
        return mSbStrength.getProgress();
    }

    @Override
    public void setStrengthValue(int value) {
        mSbStrength.setProgress(value);
    }

    @Override
    public int getIntelligenceValue() {
        return mSbIntelligence.getProgress();
    }

    @Override
    public void setIntelligenceValue(int value) {
        mSbIntelligence.setProgress(value);
    }

    @Override
    public int getSpeedValue() {
        return mSbSpeed.getProgress();
    }

    @Override
    public void setSpeedValue(int value) {
        mSbSpeed.setProgress(value);
    }

    @Override
    public int getEnduranceValue() {
        return mSbEndurance.getProgress();
    }

    @Override
    public void setEnduranceValue(int value) {
        mSbEndurance.setProgress(value);
    }

    @Override
    public int getRankValue() {
        return mSbRank.getProgress();
    }

    @Override
    public void setRankValue(int value) {
        mSbRank.setProgress(value);
    }

    @Override
    public int getCourageValue() {
        return mSbCourage.getProgress();
    }

    @Override
    public void setCourageValue(int value) {
        mSbCourage.setProgress(value);
    }

    @Override
    public int getFirepowerValue() {
        return mSbFirePower.getProgress();
    }

    @Override
    public void setFirepowerValue(int value) {
        mSbFirePower.setProgress(value);
    }

    @Override
    public int getSkillValue() {
        return mSbSkill.getProgress();
    }

    @Override
    public void setSkillValue(int value) {
        mSbSkill.setProgress(value);
    }

    @Override
    public void finishSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showProgressDialog(@StringRes int stringId) {
        // To avoid memory leak
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(stringId));
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void changeToolbarButtonText(int stringRes) {
        final MenuItem item = mMenu.findItem(R.id.menu_action_save_update);
        if (item != null) {
            item.setTitle(stringRes);
        }
    }

    @Override
    public void disposeErrorsDisposable() {
        mErrorsDisposable.clear();
    }

    private void createToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void injectDependencies() {
        DaggerTransformerComponent
                .builder()
                .transformerModule(new TransformerModule(this))
                .appModule(new AppModule(this))
                .build()
                .inject(this);
    }

    private void checkExtras(Bundle extras) {
        if (extras == null)
            return;

        final Transformer transformer = extras.getParcelable(Transformer.class.getName());
        if (transformer != null) {
            mPresenter.editTransformer(transformer);
        }
    }

    private void subscribeEditTextToCancelError(final EditText subscriber,
                                                final View errorView) {
        mErrorsDisposable.add(RxTextView
                .textChanges(subscriber)
                .take(2)
                .doOnComplete(() -> cancelErrorLayout(errorView))
                .subscribe());
    }

    private void subscribeRadioGroupToCancelError(final RadioGroup subscriber,
                                                  final View errorView) {
        mErrorsDisposable.add(RxRadioGroup
                .checkedChanges(subscriber)
                .take(2)
                .doOnComplete(() -> cancelErrorLayout(errorView))
                .subscribe());
    }

    private void cancelErrorLayout(View view) {
        if (view == null)
            return;

        if (view instanceof TextInputLayout) {
            TextInputLayout inputLayout = (TextInputLayout) view;
            inputLayout.setErrorEnabled(false);
            inputLayout.setError(null);
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setError(null);
        }
    }
}
