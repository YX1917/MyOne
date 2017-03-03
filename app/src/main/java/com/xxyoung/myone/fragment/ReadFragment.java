package com.xxyoung.myone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxyoung.myone.R;
import com.xxyoung.myone.activity.ReadActivity;
import com.xxyoung.myone.bean.ReadingListBean;
import com.xxyoung.myone.netWork.ApiCallBiz;
import com.xxyoung.xlibrary.base.XFragment;
import com.xxyoung.xlibrary.netWork.RetrofitManger;
import com.xxyoung.xlibrary.recyclerview.CommonAdapter;
import com.xxyoung.xlibrary.recyclerview.SpaceItemDecoration;
import com.xxyoung.xlibrary.recyclerview.base.ViewHolder;
import com.xxyoung.xlibrary.recyclerview.wrapper.LoadMoreWrapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Xxyou on 2017/2/27.
 */

public class ReadFragment extends XFragment {
    private static final String TAG = ReadFragment.class.getSimpleName();

    private List<ReadingListBean.DataBean> mDataBeanList;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommonAdapter<ReadingListBean.DataBean> mCommonAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;

    @BindView(R.id.recycle_read)
    RecyclerView mRecycleRead;
    @BindView(R.id.swipe_read_refresh)
    SwipeRefreshLayout mSwipeReadRefresh;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_read;
    }

    @Override
    protected void loadData() {
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd HH:mm:ss");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        Log.e(TAG, "loadData: 加载数据"+str);
        requestNetwork("0");
    }

    private void setRecycleAdapter() {
        mCommonAdapter = new CommonAdapter<ReadingListBean.DataBean>(getActivity(),R.layout.item_read,mDataBeanList) {
            @Override
            protected void convert(ViewHolder holder, final ReadingListBean.DataBean dataBean, int position) {
                String type ="";
                if (dataBean.getTag_list().size()!=0) {
                    type =dataBean.getTag_list().get(0).getTitle();
                }else{
                    switch (dataBean.getCategory()) {
                        case "1":
                            type = "阅读";
                            break;
                        case "2":
                            type = "连载";
                            break;
                        default:
                            type = "其他";
                            break;
                    }
                }

                holder.setText(R.id.tv_read_type,"-"+type+"-");
                holder.setText(R.id.tv_read_title,dataBean.getTitle());
                holder.setText(R.id.tv_read_author,"文/"+dataBean.getAuthor().getUser_name());
                holder.setimg(R.id.img_read_picture,dataBean.getImg_url());
                holder.setText(R.id.tv_read_forward,dataBean.getForward());
                holder.setText(R.id.tv_read_time,"11小时");
                holder.setText(R.id.tv_read_likeCount,String.valueOf(dataBean.getLike_count()));
                holder.setOnClickListener(R.id.rl_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("item_id",dataBean.getItem_id());
                        intent.setClass(getActivity(), ReadActivity.class);
                        startActivity(intent);
                    }
                });


            }
        };


        
        mLoadMoreWrapper = new LoadMoreWrapper(mCommonAdapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e(TAG, "onLoadMoreRequested: " + "加载更多");
               requestNetwork(mDataBeanList.get(mDataBeanList.size()-1).getId());
            }
        });

        mRecycleRead.setAdapter(mLoadMoreWrapper);
    }

    @Override
    public void initView() {
        Log.e(TAG, "loadData: 初始化控件");
        mRecycleRead.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.dimen_12)));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleRead.setLayoutManager(mLayoutManager);
        mSwipeReadRefresh.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
        mSwipeReadRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "onRefresh: " + "重新加载");
                requestNetwork("0");
            }
        });


    }

    private void requestNetwork(final String page) {
        RetrofitManger
                .getInstance()
                .createReq(ApiCallBiz.class)
                .getReadingList(page,"mi", "4.0.2", "ffffffff-a90e-706a-63f7-ccf973aae5ee", "android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReadingListBean>() {
                    @Override
                    public void onStart() {
                        mSwipeReadRefresh.setRefreshing(page.equals("0"));
                    }

                    @Override
                    public void onCompleted() {
                        if (mSwipeReadRefresh.isRefreshing()){
                            mSwipeReadRefresh.setRefreshing(false);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mSwipeReadRefresh.isRefreshing()){
                            mSwipeReadRefresh.setRefreshing(false);
                        }
                        Log.e(TAG, "onError: " + e.getMessage());

                    }

                    @Override
                    public void onNext(ReadingListBean readingListBean) {
                        Log.e(TAG, "onNext: " + readingListBean.getData().get(0).getAd_linkurl());
                        if (mDataBeanList==null&&page.equals("0")) {
                            mDataBeanList = readingListBean.getData();
                            setRecycleAdapter();
                        }else{
                            mDataBeanList.addAll(readingListBean.getData());
                            mCommonAdapter.notifyDataSetChanged();
                        }

                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
