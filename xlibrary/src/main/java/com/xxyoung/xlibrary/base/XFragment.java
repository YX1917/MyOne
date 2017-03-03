package com.xxyoung.xlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by YX on 2016/12/6.
 */

public abstract class XFragment extends Fragment {
    private static final String TAG = XFragment.class.getSimpleName();
    /** Fragment当前状态是否可见 */
    private boolean isViewCreated;
    private boolean isLoadDataCompleted;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    protected abstract int getContentViewLayoutID();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initView();
        isViewCreated= true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint: " +"isVisibleToUser:" +isVisibleToUser+"  isViewCreated:"+isViewCreated+"  isVisibleToUser"+isVisibleToUser);
        if (isVisibleToUser&&isViewCreated&&!isLoadDataCompleted) {
            loadData();
            isLoadDataCompleted = true;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            loadData();
        }
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void loadData();


    public abstract void initView();



}
