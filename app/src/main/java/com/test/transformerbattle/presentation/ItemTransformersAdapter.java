package com.test.transformerbattle.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.transformerbattle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemTransformersAdapter extends RecyclerView.Adapter<ItemTransformersAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgTeam) ImageView mImgTeam;
        @BindView(R.id.tvName) TextView mTvName;
        @BindView(R.id.progressStrength) ProgressBar mProgressStrength;
        @BindView(R.id.progressIntelligence) ProgressBar mProgressIntelligence;
        @BindView(R.id.progressSpeed) ProgressBar mProgressSpeed;
        @BindView(R.id.progressEndurance) ProgressBar mProgressEndurance;
        @BindView(R.id.progressRank) ProgressBar mProgressRank;
        @BindView(R.id.progressCourage) ProgressBar mProgressCourage;
        @BindView(R.id.progressFirePower) ProgressBar mProgressFirePower;
        @BindView(R.id.progressSkill) ProgressBar mProgressSkill;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
