package com.njnu.kai.practice.material;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.ToastUtils;
import com.njnu.kai.support.ViewUtils;


/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-12-11
 */
public class CoordinatorLayoutActivity extends AppCompatActivity {

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private FloatingActionButton mFabActionTop;
    private FloatingActionButton mFabActionBottom;
    private AppBarLayout mAppbarLayout;
    private CollapsingToolbarLayout mCollapsingToobarLayout;

    private RecyclerView mRecyclerView;
    private StringAdapter mAdapter;

    private View.OnClickListener mOnClickListener = v -> {
        final int viewId = v.getId();
        switch (viewId) {
            case R.id.fab_action_top:
                dealFabAction();
                break;

            default:
                ToastUtils.showToast("default id=%d name=%s", viewId, v.getClass().getSimpleName());
                break;
        }
    };

    private void dealFabAction() {
        Snackbar.make(mCoordinatorLayout, "Test message", Snackbar.LENGTH_SHORT)
                .setAction("hello world", mOnClickListener)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mAppbarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        mCollapsingToobarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        mToolbar.setSubtitle("subtitle");
        mToolbar.setNavigationIcon(R.drawable.ic_launcher);
        mCollapsingToobarLayout.setTitle("Material Test2");
        setTitle("Material Test");
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mFabActionTop = (FloatingActionButton) findViewById(R.id.fab_action_top);
        mFabActionBottom = (FloatingActionButton) findViewById(R.id.fab_action_bottom);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new StringAdapter();
        mAdapter.updateData(StringAdapter.mockData(1, 26));
        mRecyclerView.setAdapter(mAdapter);
        ViewUtils.bindClickListener(mOnClickListener, mFabActionTop, mFabActionBottom);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "test1");
        menu.add(0, 2, 0, "test2");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                ToastUtils.showToast("id = 1");
                break;

            default:
                ToastUtils.showToast("id = other");
                break;
        }
        return true;
    }

}
