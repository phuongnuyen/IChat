package com.direct.ichat.Activity;

import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;

import com.direct.ichat.Adapter.MainViewPagerAdapter;
import com.direct.ichat.Fagment.FriendsFragment;
import com.direct.ichat.Fagment.MainBarFragment;
import com.direct.ichat.Fagment.RecentChatsFragment;
import com.direct.ichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    MainViewPagerAdapter viewPagerAdapter;
    @BindView(R.id.srv_message)
    SearchView srvMessage;
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
        viewPagerAdapter.AddFragment(new RecentChatsFragment());
        viewPagerAdapter.AddFragment(new FriendsFragment());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {

                    }
                });

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
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
