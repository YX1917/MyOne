package com.xxyoung.xlibrary.base;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xxyoung.xlibrary.R;
import com.xxyoung.xlibrary.utils.AppManager;

import butterknife.ButterKnife;

public abstract class XActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private TextView mTextView_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        initActionBar();
        AppManager.getAppManager().addActivity(this);
    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextView_title = (TextView) findViewById(R.id.toolbar_title);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            mActionBar.setDisplayShowTitleEnabled(false);
            if (mActionBar != null) {
                //设置返回按钮
                mActionBar.setHomeAsUpIndicator(R.drawable.navigation_back);
            }
        }

        initToolBar();
    }

    protected abstract void initToolBar();


    protected void setIsShowBack(boolean isShow) {
        mActionBar.setDisplayHomeAsUpEnabled(isShow);
    }

    protected Toolbar getToolBar() {
        return mToolbar;
    }

    /**
     * toolbar设置标题
     *
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        if (mToolbar != null) {
            mTextView_title.setText(title);
        } else {
            mTextView_title.setText(title);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity();
    }

    /**
     * 隐藏toolbar
     */
    public void hideToolBar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏toolbar
     */
    public void showToolBar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
        }
    }

    protected abstract int getContentView();
}
