package com.xxyoung.myone.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxyoung.myone.R;
import com.xxyoung.myone.bean.IdListBean;
import com.xxyoung.myone.netWork.ApiCallBiz;
import com.xxyoung.xlibrary.base.XFragment;
import com.xxyoung.xlibrary.netWork.RetrofitManger;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Xxyou on 2017/2/27.
 */

public class HomeFragment extends XFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.textView)
    TextView mTextView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {
        Log.e(TAG, "loadData: 加载数据");
        RetrofitManger.getInstance()
                .createReq(ApiCallBiz.class)
                .getIdList("mi", "4.0.2", "ffffffff-a90e-706a-63f7-ccf973aae5ee", "android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IdListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage() );

                    }

                    @Override
                    public void onNext(IdListBean idListBean) {
                        Log.e(TAG, "onNext: "+idListBean.getData().get(0) );
                    }

                });
    }

    @Override
    public void initView() {
        Log.e(TAG, "loadData: 初始化控件");
        mTextView.setText("home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
