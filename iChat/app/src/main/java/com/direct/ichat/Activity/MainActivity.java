package com.direct.ichat.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.direct.ichat.Adapter.MainViewPagerAdapter;
import com.direct.ichat.Fagment.FriendsFragment;
import com.direct.ichat.Fagment.SettingFragment;
import com.direct.ichat.Fagment.WaitingForAcceptFragment;
import com.direct.ichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    MainViewPagerAdapter viewPagerAdapter;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        InitViewPager();
    }

    private void InitViewPager(){
        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new FriendsFragment());
        viewPagerAdapter.AddFragment(new WaitingForAcceptFragment());
        viewPagerAdapter.AddFragment(new Fragment());
        viewPagerAdapter.AddFragment(new SettingFragment());


        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(
            new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    tabLayout.getTabAt(position).select();
                }
            });

        // tab 1
        View icon1 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        icon1.findViewById(R.id.iv_tab_icon).setBackgroundResource(R.drawable.ic_message);
        tabLayout.addTab(tabLayout.newTab().setCustomView(icon1));
        // tab 2
        View icon2 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        icon2.findViewById(R.id.iv_tab_icon).setBackgroundResource(R.drawable.ic_friends);
        tabLayout.addTab(tabLayout.newTab().setCustomView(icon2));
        // tab 3
        View icon3 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        icon3.findViewById(R.id.iv_tab_icon).setBackgroundResource(R.drawable.ic_feeling);
        tabLayout.addTab(tabLayout.newTab().setCustomView(icon3));
        // tab 4
        View icon4 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        icon4.findViewById(R.id.iv_tab_icon).setBackgroundResource(R.drawable.ic_more);
        tabLayout.addTab(tabLayout.newTab().setCustomView(icon4));
//
//        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}
