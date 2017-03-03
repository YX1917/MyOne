package com.xxyoung.myone.activity;

import android.os.Bundle;
import android.util.Log;

import com.xxyoung.myone.R;
import com.xxyoung.myone.bean.ReadDetailBean;
import com.xxyoung.myone.fragment.ReadDetailFragment;
import com.xxyoung.myone.netWork.ApiCallBiz;
import com.xxyoung.xlibrary.base.XActivity;
import com.xxyoung.xlibrary.netWork.RetrofitManger;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReadActivity extends XActivity{


    private String item_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item_id = getIntent().getStringExtra("item_id");
        loadData();
        Bundle bundle = new Bundle();
        bundle.putString("item_id", item_id);
        ReadDetailFragment readDetailFragment = new ReadDetailFragment();
        readDetailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_readDetail, readDetailFragment)
                .commit();

    }


    private void loadData() {
        RetrofitManger
                .getInstance()
                .createReq(ApiCallBiz.class)
                .getReadDetail(item_id, "mi", "4.0.2", "ffffffff-a90e-706a-63f7-ccf973aae5ee", "android")
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
//                        RichText.from(readDetailBean.getData().getHp_content()).into(mText);
                    }

                });
    }

    @Override
    protected void initToolBar() {
        setTitle("一个阅读");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_read;
    }
}
