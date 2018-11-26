package com.test.transformerbattle.presentation.arena.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.transformerbattle.R;
import com.test.transformerbattle.domain.model.Transformer;
import com.test.transformerbattle.utils.StatsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemTransformersAdapter extends RecyclerView.Adapter<ItemTransformersAdapter.ViewHolder> {

    private List<Transformer> mTransformers;
    private OnItemClickListener mOnItemClickListener;

    public ItemTransformersAdapter(@NonNull List<Transformer> transformers) {
        this.mTransformers = transformers;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setTransformers(@NonNull List<Transformer> transformers) {
        mTransformers = transformers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_transformers, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mTransformers.get(i));
    }

    @Override
    public int getItemCount() {
        return mTransformers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mTransformers.get(getAdapterPosition()));
                }
            });
        }

        private void bind(Transformer transformer) {
            Picasso.get()
                    .load(transformer.getTeamIconUrl())
                    .into(mImgTeam);

            mTvName.setText(transformer.getName());
            mProgressStrength.setProgress(getProgressPercent(transformer.getStrength()));
            mProgressIntelligence.setProgress(getProgressPercent(transformer.getIntelligence()));
            mProgressSpeed.setProgress(getProgressPercent(transformer.getSpeed()));
            mProgressEndurance.setProgress(getProgressPercent(transformer.getEndurance()));
            mProgressRank.setProgress(getProgressPercent(transformer.getRank()));
            mProgressCourage.setProgress(getProgressPercent(transformer.getCourage()));
            mProgressFirePower.setProgress(getProgressPercent(transformer.getFirepower()));
            mProgressSkill.setProgress(getProgressPercent(transformer.getSkill()));
        }

        private int getProgressPercent(int value) {
            return StatsUtils.valueToProgress(value);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Transformer transformer);
    }
}
