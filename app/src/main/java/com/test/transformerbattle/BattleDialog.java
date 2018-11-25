package com.test.transformerbattle;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BattleDialog extends Dialog {

    @BindView(R.id.imgTeam) ImageView mImgTeam;
    @BindView(R.id.tvDescription) TextView mTvDescription;

    @SuppressLint("InflateParams")
    public BattleDialog(@NonNull Context context) {
        super(context);
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_battle_dialog, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        ButterKnife.bind(this);

        final Window window = getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    @OnClick(R.id.btnClose)
    void close() {
        dismiss();
    }
}
