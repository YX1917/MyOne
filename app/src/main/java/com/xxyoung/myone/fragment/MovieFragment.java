package com.xxyoung.myone.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxyoung.myone.R;
import com.xxyoung.xlibrary.base.XFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xxyou on 2017/2/27.
 */

public class MovieFragment extends XFragment {
    private static final String TAG = MovieFragment.class.getSimpleName();
    @BindView(R.id.textView)
    TextView mTextView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {
        Log.e(TAG, "loadData: 加载数据" );
    }

    @Override
    public void initView() {
        Log.e(TAG, "loadData: 初始化控件" );
        mTextView.setText("movie");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
