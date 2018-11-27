package com.test.transformerbattle.presentation.arena.adapter;

import android.graphics.Canvas;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class SwipeItemHelper extends ItemTouchHelper.SimpleCallback {

    private OnSwipeListener mListener;
    private View.OnClickListener mOnClickListener;
    private ViewHolder mSelectedHolder;
    private RecyclerView mRecyclerView;

    private float mAnchorPoint;
    private boolean mRemovePending;

    public SwipeItemHelper(int dragDirs, int swipeDirs, float anchorPoint,
                           @NonNull RecyclerView recyclerView,
                           @Nullable OnSwipeListener listener) {
        super(dragDirs, swipeDirs);
        mAnchorPoint = anchorPoint;
        mRecyclerView = recyclerView;
        mListener = listener;
        createListeners();

        new ItemTouchHelper(this)
                .attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder) {
        final View view = ((ViewHolder) viewHolder).mForegroundView;
        getDefaultUIUtil().clearView(view);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            if (mSelectedHolder != null && !mSelectedHolder.equals(viewHolder)) {
                swipeViewBack(mSelectedHolder.mForegroundView);
            }

            mSelectedHolder = (ViewHolder) viewHolder;

            final View foreground = mSelectedHolder.mForegroundView;
            final View background = mSelectedHolder.mBackgroundView;
            foreground.setOnClickListener(mOnClickListener);
            background.setOnClickListener(mOnClickListener);

            getDefaultUIUtil().onSelected(foreground);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas canvas,
                                @NonNull RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View view = ((ViewHolder) viewHolder).mForegroundView;
        getDefaultUIUtil().onDrawOver(canvas, recyclerView, view, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        final View foreground = ((ViewHolder) viewHolder).mForegroundView;
        final View itemView = ((ViewHolder) viewHolder).itemView;

        final float width = recyclerView.getWidth();
        final float threshold = width * mAnchorPoint;

        float oldDx = 0.0f;

        final Object tag = foreground.getTag();
        if (tag != null) {
            oldDx = (float) tag;
        }

        if ((Math.abs(oldDx) > threshold && !isCurrentlyActive)
                || (Math.abs(oldDx) == threshold)) {
            dX = Math.max(threshold, Math.abs(dX)) * (dX <= 0 ? -1 : 1);
        }

        oldDx = dX;
        foreground.setTag(oldDx);
        mRemovePending = dX != 0;

        getDefaultUIUtil().onDraw(canvas, recyclerView, foreground, dX, dY,
                actionState, isCurrentlyActive);

        final float alpha = (1 - (Math.abs(dX * 1.2f) / width));
        itemView.setAlpha(alpha);
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return .75f;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (mListener != null) {
            mListener.onSwipe(viewHolder.getAdapterPosition());
        }
    }

    private void swipeView(final ViewHolder holder) {
        final View foreground = holder.mForegroundView;
        final TranslateAnimation animation = new TranslateAnimation(
                0.0f,
                foreground.getTranslationX() / mAnchorPoint,
                foreground.getTranslationY(),
                foreground.getTranslationY());

        animation.setDuration(150);
        animation.setFillEnabled(true);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                getDefaultUIUtil().clearView(foreground);

                foreground.setTag(null);
                foreground.setOnClickListener(null);
                foreground.clearAnimation();

                holder.mBackgroundView.setOnClickListener(null);
                onSwiped(mSelectedHolder, getSwipeDirs(mRecyclerView, mSelectedHolder));

                mSelectedHolder = null;
                mRemovePending = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        foreground.startAnimation(animation);
    }

    private void swipeViewBack(final View view) {
        final TranslateAnimation animation = new TranslateAnimation(
                0.0f,
                view.getTranslationX() * -1,
                view.getTranslationY(),
                view.getTranslationY());

        animation.setDuration(150);
        animation.setFillEnabled(true);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                getDefaultUIUtil().clearView(view);

                view.setTag(null);
                view.setOnClickListener(null);
                view.clearAnimation();

                mRemovePending = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        view.startAnimation(animation);
    }

    private void createListeners() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (mRemovePending && mSelectedHolder != null) {
                    swipeViewBack(mSelectedHolder.mForegroundView);
                    mSelectedHolder = null;
                }
            }
        });

        mOnClickListener = v -> {
            if (!mRemovePending)
                return;

            if (v.getId() == mSelectedHolder.mForegroundView.getId()) {
                swipeViewBack(v);
            } else {
                swipeView(mSelectedHolder);
            }
        };
    }

    public interface OnSwipeListener {
        void onSwipe(int itemPosition);
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {

        private View mBackgroundView;
        private View mForegroundView;

        protected ViewHolder(View itemView) {
            super(itemView);
            mBackgroundView = itemView.findViewById(getBackgroundViewId());
            mForegroundView = itemView.findViewById(getForegroundViewId());
        }

        @IdRes
        protected abstract int getBackgroundViewId();

        @IdRes
        protected abstract int getForegroundViewId();
    }

}
