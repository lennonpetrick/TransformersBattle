package com.test.transformerbattle.presentation;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;

import com.test.transformerbattle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransformerActivity extends AppCompatActivity {

    @BindView(R.id.inputName) TextInputLayout mInputName;
    @BindView(R.id.edName) EditText mEdName;
    @BindView(R.id.sbStrength) SeekBar mSbStrength;
    @BindView(R.id.sbIntelligence) SeekBar mSbIntelligence;
    @BindView(R.id.sbSpeed) SeekBar mSbSpeed;
    @BindView(R.id.sbEndurance) SeekBar mSbEndurance;
    @BindView(R.id.sbRank) SeekBar mSbRank;
    @BindView(R.id.sbCourage) SeekBar mSbCourage;
    @BindView(R.id.sbFirePower) SeekBar mSbFirePower;
    @BindView(R.id.sbSkill) SeekBar mSbSkill;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformer);
        ButterKnife.bind(this);
        createToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_transformer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_save_update: {
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

    public void changeToolbarButtonText(@StringRes int stringRes) {
        final MenuItem item = mMenu.findItem(R.id.menu_action_save_update);
        if (item != null) {
            item.setTitle(stringRes);
        }
    }

    private void createToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
