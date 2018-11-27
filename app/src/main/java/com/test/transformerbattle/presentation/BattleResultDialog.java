package com.test.transformerbattle.presentation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.transformerbattle.R;
import com.test.transformerbattle.domain.model.Transformer;

import java.util.List;

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
            setDescription(R.string.text_battle_tie);
            hideTeamImage();
        } else if (result.getWinningTeam().isEmpty()
                && result.getSurvivors().isEmpty()) {
            setDescription(R.string.text_battle_all_destroyed);
            hideTeamImage();
        } else {
            final int numberOfBattles = result.getNumberOfBattles();
            final String battleNumberDescription = getContext()
                    .getResources()
                    .getQuantityString(R.plurals.text_battle_number,
                            numberOfBattles, numberOfBattles);

            final String winnersDescription = getWinnersDescription(result.getWinningTeam());
            final String survivorsDescription = getSurvivorsDescription(result.getSurvivors());

            final StringBuilder description = new StringBuilder();
            if (!TextUtils.isEmpty(battleNumberDescription)) {
                description.append(battleNumberDescription);
            }

            if (!TextUtils.isEmpty(winnersDescription)) {
                if (description.length() > 0) {
                    description.append("\n\n");
                }

                description.append(winnersDescription);
            }

            if (!TextUtils.isEmpty(survivorsDescription)) {
                if (description.length() > 0) {
                    description.append("\n\n");
                }

                description.append(survivorsDescription);
            }

            setDescription(description.toString());
            setTeamImage(result.getWinningTeam());
        }
    }

    private String getWinnersDescription(List<Transformer> winningTeam) {
        if (winningTeam == null || winningTeam.isEmpty())
            return null;

        final StringBuilder winnerNames = new StringBuilder();
        for (Transformer winner : winningTeam) {
            if (winnerNames.length() > 0) {
                winnerNames.append(", ");
            }

            winnerNames.append(winner.getName());
        }

        final String winningTeamName;
        if (winningTeam.get(0).getTeam().equals("A")) {
            winningTeamName = getString(R.string.text_battle_team_autobots);
        } else {
            winningTeamName = getString(R.string.text_battle_team_decepticons);
        }

        return String.format(getString(R.string.text_battle_winning_team),
                winningTeamName, winnerNames.toString());
    }

    private String getSurvivorsDescription(List<Transformer> survivors) {
        if (survivors == null || survivors.isEmpty())
            return null;

        final StringBuilder survivorNames = new StringBuilder();
        for (Transformer survivor : survivors) {
            if (survivorNames.length() > 0) {
                survivorNames.append(", ");
            }

            survivorNames.append(survivor.getName());
        }

        final String survivorTeamName;
        if (survivors.get(0).getTeam().equals("A")) {
            survivorTeamName = getString(R.string.text_battle_team_autobots);
        } else {
            survivorTeamName = getString(R.string.text_battle_team_decepticons);
        }

        return String.format(getString(R.string.text_battle_survivors),
                survivorTeamName, survivorNames.toString());
    }

    private void setDescription(@StringRes int stringIdRes) {
        mTvDescription.setText(stringIdRes);
    }

    private void setDescription(String description) {
        mTvDescription.setText(description);
    }

    private void setTeamImage(List<Transformer> winningTeam) {
        if (winningTeam != null && !winningTeam.isEmpty()) {
            Picasso.get()
                    .load(winningTeam.get(0).getTeamIconUrl())
                    .into(mImgTeam);
        } else {
            hideTeamImage();
        }
    }

    private void hideTeamImage() {
        mImgTeam.setVisibility(View.GONE);
    }

    private String getString(@StringRes int resId) {
        return getContext().getString(resId);
    }

    @OnClick(R.id.btnClose)
    void close() {
        dismiss();
    }
}
