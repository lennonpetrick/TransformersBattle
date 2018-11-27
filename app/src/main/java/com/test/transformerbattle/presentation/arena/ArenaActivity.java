package com.test.transformerbattle.presentation.arena;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.rxbinding2.view.RxView;
import com.test.transformerbattle.R;
import com.test.transformerbattle.domain.model.Transformer;
import com.test.transformerbattle.presentation.arena.adapter.ItemTransformersAdapter;
import com.test.transformerbattle.presentation.arena.adapter.SwipeItemHelper;
import com.test.transformerbattle.presentation.arena.di.ArenaModule;
import com.test.transformerbattle.presentation.arena.di.DaggerArenaComponent;
import com.test.transformerbattle.presentation.di.AppModule;
import com.test.transformerbattle.presentation.transformer.TransformerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class ArenaActivity extends AppCompatActivity implements ArenaContract.View {

    private static final int TRANSFORMER_REQUEST_CODE = 1;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.fabNewTransformer) FloatingActionButton mFabNewTransformer;

    @Inject
    ArenaContract.Presenter mPresenter;
    @Inject
    CompositeDisposable mListenersDisposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        ButterKnife.bind(this);
        createToolbar();
        injectDependencies();
        setUpRecyclerView();
        setUpRecyclerOnSwipe();
        setListeners();
        mPresenter.load();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TRANSFORMER_REQUEST_CODE
                && resultCode == RESULT_OK) {
            mPresenter.load();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_arena, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_battle: {
                mPresenter.battle();
                return true;
            }
            default : {
                return false;
            }
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void setRecyclerView(List<Transformer> transformers) {
        if (transformers == null)
            return;

        final ItemTransformersAdapter adapter = (ItemTransformersAdapter)
                mRecyclerView.getAdapter();

        if (adapter != null) {
            adapter.setTransformers(transformers);
        }
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mRecyclerView.getRootView(), message, Snackbar.LENGTH_LONG).show();
    }

    private void createToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void injectDependencies() {
        DaggerArenaComponent
                .builder()
                .arenaModule(new ArenaModule(this))
                .appModule(new AppModule(this))
                .build()
                .inject(this);
    }

    private void setUpRecyclerView() {
        final DividerItemDecoration divider = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Drawable drawable = getDrawable(R.drawable.layout_recycler_transparent_divider);
            if (drawable != null) {
                divider.setDrawable(drawable);
            }
        }

        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(false);

        final ItemTransformersAdapter adapter = new ItemTransformersAdapter(new ArrayList<>());
        adapter.setOnItemClickListener(this::startTransformActivity);
        mRecyclerView.setAdapter(adapter);
    }

    private void setUpRecyclerOnSwipe() {
        final SwipeItemHelper.OnSwipeListener onSwipeListener = itemPosition -> {
            final ItemTransformersAdapter adapter = (ItemTransformersAdapter)
                    mRecyclerView.getAdapter();

            if (adapter != null) {
                mPresenter.delete(adapter.removeTransformer(itemPosition));
            }
        };

        new SwipeItemHelper(0, ItemTouchHelper.LEFT, 0.17f,
                mRecyclerView, onSwipeListener);
    }

    private void setListeners() {
        mListenersDisposables.add(RxView.clicks(mFabNewTransformer)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> startTransformActivity(null)));
    }

    private void startTransformActivity(Transformer transformer) {
        final Intent intent = new Intent(this, TransformerActivity.class);

        if (transformer != null) {
            intent.putExtra(Transformer.class.getName(), transformer);
        }

        startActivityForResult(intent, TRANSFORMER_REQUEST_CODE);
    }
}
