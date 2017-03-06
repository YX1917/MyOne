package com.xxyoung.myone.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xxyoung.myone.R;
import com.xxyoung.myone.bean.ReadCommentBean;
import com.xxyoung.myone.bean.ReadDetailBean;
import com.xxyoung.myone.netWork.ApiCallBiz;
import com.xxyoung.xlibrary.base.XFragment;
import com.xxyoung.xlibrary.netWork.RetrofitManger;
import com.xxyoung.xlibrary.recyclerview.CommonAdapter;
import com.xxyoung.xlibrary.recyclerview.wrapper.LoadMoreWrapper;
import com.xxyoung.xlibrary.view.GlideCircleTransform;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Xxyou on 2017/3/2.
 */

public class ReadDetailFragment extends XFragment {
    private CommonAdapter<ReadCommentBean> mReadCommentBeanCommonAdapter;
    private LoadMoreWrapper<ReadCommentBean> mReadCommentBeanLoadMoreWrapper;


    @BindView(R.id.tv_readDetail_hpTitle)
    TextView mTvReadDetailHpTitle;
    @BindView(R.id.tv_readDetail_hpAuthor)
    TextView mTvReadDetailHpAuthor;
    @BindView(R.id.tv_readDetail_content)
    TextView mTvReadDetailContent;
    @BindView(R.id.tv_readDetail_hpAuthorIntroduce)
    TextView mTvReadDetailHpAuthorIntroduce;
    @BindView(R.id.tv_readDetail_copyRight)
    TextView mTvReadDetailCopyRight;
    @BindView(R.id.img_readDetail_headPicture)
    ImageView mImgReadDetailHeadPicture;
    @BindView(R.id.tv_readDetail_userName)
    TextView mTvReadDetailUserName;
    @BindView(R.id.tv_readDetail_webName)
    TextView mTvReadDetailWebName;
    @BindView(R.id.tv_readDetail_summary)
    TextView mTvReadDetailSummary;
    @BindView(R.id.btn_readDetail_follow)
    Button mBtnReadDetailFollow;
    @BindView(R.id.recycle_readDetail_comment)
    RecyclerView mRecycleReadDetailComment;
    @BindView(R.id.error_view)
    LinearLayout mErrorView;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.default_loading)
    LinearLayout mDefaultLoading;
    

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragmemt_readdetail;
    }

    @Override
    protected void loadData() {
        loadContent();
        loadComment();
       

    }

    private void loadComment() {
        RetrofitManger
                .getInstance()
                .createReq(ApiCallBiz.class)
                .getReadComment((String) getArguments().get("item_id"), "0","mi", "4.0.2", "ffffffff-a90e-706a-63f7-ccf973aae5ee", "android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReadDetailBean>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ReadDetailFragment", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ReadDetailBean readDetailBean) {
                       
                    }
                });
    }

    private void loadContent() {
        RetrofitManger
                .getInstance()
                .createReq(ApiCallBiz.class)
                .getReadDetail((String) getArguments().get("item_id"), "mi", "4.0.2", "ffffffff-a90e-706a-63f7-ccf973aae5ee", "android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReadDetailBean>() {
                    @Override
                    public void onStart() {
                        if (mErrorView.getVisibility() == View.VISIBLE) {
                            mErrorView.setVisibility(View.GONE);
                        }
                        if (mScrollView.getVisibility() == View.VISIBLE) {
                            mScrollView.setVisibility(View.GONE);
                        }
                        if (mDefaultLoading.getVisibility() == View.GONE) {
                            mDefaultLoading.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCompleted() {
                        mDefaultLoading.setVisibility(View.GONE);
                        mScrollView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ReadActivity.this", "onError: " + e.getMessage());
                        mErrorView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(ReadDetailBean readDetailBean) {
                        Log.e("ReadActivity.this", "onNext: " + readDetailBean.getData().getHp_content());
                        mTvReadDetailHpTitle.setText(readDetailBean.getData().getHp_title());
                        mTvReadDetailHpAuthor.setText("文/"+readDetailBean.getData().getHp_author());
                        RichText.from(readDetailBean.getData().getHp_content()).into(mTvReadDetailContent);
                        mTvReadDetailHpAuthorIntroduce.setText(readDetailBean.getData().getHp_author_introduce());
                        if (!readDetailBean.getData().getCopyright().equals("")) {
                            mTvReadDetailCopyRight.setText(readDetailBean.getData().getCopyright());
                            mTvReadDetailCopyRight.setVisibility(View.VISIBLE);
                        }
                        Glide.with(getActivity()).load(readDetailBean.getData().getAuthor().get(0).getWeb_url()).transform(new GlideCircleTransform(getActivity())).into(mImgReadDetailHeadPicture);
                        mTvReadDetailUserName.setText(readDetailBean.getData().getAuthor().get(0).getUser_name());
                        mTvReadDetailWebName.setText(readDetailBean.getData().getAuthor().get(0).getWb_name());
                        mTvReadDetailSummary.setText(readDetailBean.getData().getAuthor().get(0).getSummary());
                    }

                });
    }

    @Override
    public void initView() {
        mRecycleReadDetailComment.setLayoutManager(new LinearLayoutManager(getActivity()));
        
    }

}
