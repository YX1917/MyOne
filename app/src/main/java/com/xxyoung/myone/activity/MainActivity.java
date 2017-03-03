package com.xxyoung.myone.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.xxyoung.myone.R;
import com.xxyoung.myone.fragment.HomeFragment;
import com.xxyoung.myone.fragment.MovieFragment;
import com.xxyoung.myone.fragment.MusicFragment;
import com.xxyoung.myone.fragment.ReadFragment;
import com.xxyoung.xlibrary.base.XActivity;
import com.xxyoung.xlibrary.view.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends XActivity {

    @BindView(R.id.viewPager_main)
    ViewPager mViewPagerMain;
    @BindView(R.id.bottom_nav)
    BottomNavigationViewEx mBottomNav;
    private FragmentPagerAdapter pagerAdapter;


    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBottomNav();
        initViewPager();
    }

    private void initViewPager() {
        if (fragmentList.isEmpty()) {
            fragmentList.add(new HomeFragment());
            fragmentList.add(new ReadFragment());
            fragmentList.add(new MusicFragment());
            fragmentList.add(new MovieFragment());
        }
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

        mViewPagerMain.setAdapter(pagerAdapter);
        mViewPagerMain.setCurrentItem(0);
        mViewPagerMain.setOffscreenPageLimit(4);

    }

    /**
     * 设置底部导航栏
     */
    private void initBottomNav() {
        mBottomNav.enableAnimation(true);
        mBottomNav.enableShiftingMode(false);
        mBottomNav.enableItemShiftingMode(false);
        mBottomNav.setTextVisibility(true);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_main:
                        mViewPagerMain.setCurrentItem(0);
                        break;
                    case R.id.bottom_news:
                        mViewPagerMain.setCurrentItem(1);
                        break;
                    case R.id.bottom_communication:
                        mViewPagerMain.setCurrentItem(2);
                        break;
                    case R.id.bottom_mine:
                        mViewPagerMain.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void initToolBar() {
        setTitle(getString(R.string.one));
        setIsShowBack(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
