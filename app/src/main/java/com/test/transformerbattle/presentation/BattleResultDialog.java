package com.test.transformerbattle.presentation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.transformerbattle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BattleResultDialog extends Dialog {

    @BindView(R.id.imgTeam) ImageView mImgTeam;
    @BindView(R.id.tvDescription) TextView mTvDescription;

    @SuppressLint("InflateParams")
    public BattleResultDialog(@NonNull Context context, Battle.Result result) {
        super(context);
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_battle_result_dialog, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        ButterKnife.bind(this);

        final Window window = getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
        }

        displayResult(result);
    }

    private void displayResult(Battle.Result result) {
        if (result.isTie()) {
            // tie
        } else if (result.getWinnerTeam().isEmpty()
                && result.getSurvivors().isEmpty()) {
            // destroyed all
        } else {

        }
    }

    private void setDescription(int description) {
        mTvDescription.setText(description);
    }

    private void setTeamImage(String url) {
        Picasso.get()
                .load(url)
                .into(mImgTeam);
    }

    private void hideTeamImage() {
        mImgTeam.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnClose)
    void close() {
        dismiss();
    }
}
