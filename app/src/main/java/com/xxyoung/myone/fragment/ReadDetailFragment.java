package com.xxyoung.myone.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxyoung.myone.R;
import com.xxyoung.myone.bean.ReadDetailBean;
import com.xxyoung.myone.netWork.ApiCallBiz;
import com.xxyoung.xlibrary.base.XFragment;
import com.xxyoung.xlibrary.netWork.RetrofitManger;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Xxyou on 2017/3/2.
 */

public class ReadDetailFragment extends XFragment {


    @BindView(R.id.tv_readDetail_title)
    TextView mTvReadDetailTitle;
    @BindView(R.id.tv_readDetail_author)
    TextView mTvReadDetailAuthor;
    @BindView(R.id.tv_readDetail_content)
    TextView mTvReadDetailContent;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragmemt_readdetail;
    }

    @Override
    protected void loadData() {
        RetrofitManger
                .getInstance()
                .createReq(ApiCallBiz.class)
                .getReadDetail((String) getArguments().get("item_id"), "mi", "4.0.2", "ffffffff-a90e-706a-63f7-ccf973aae5ee", "android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReadDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ReadActivity.this", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ReadDetailBean readDetailBean) {
                        Log.e("ReadActivity.this", "onNext: " + readDetailBean.getData().getHp_content());
                        RichText.from(readDetailBean.getData().getHp_content()).into(mTvReadDetailContent);
                    }

                });

    }

    @Override
    public void initView() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
