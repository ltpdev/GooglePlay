package com.gdcp.asus_.googleplay.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.ui.FragmentFactory;
import com.gdcp.asus_.googleplay.ui.fragment.BaseFragment;
import com.gdcp.asus_.googleplay.utils.UIUtils;

public class MainActivity extends AppCompatActivity {
private TabLayout tabLayout;
private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                BaseFragment baseFragment=FragmentFactory.createFragment(tab.getPosition());
                //开始加载数据
                baseFragment.loadData();
                //Toast.makeText(UIUtils.getContext(),"onTabSelected()"+tab.getPosition(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        MyAdapter myAdapter=new MyAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(myAdapter);
    }
    class MyAdapter extends FragmentPagerAdapter{
        String []tabNames;
        public MyAdapter(FragmentManager fm) {
            super(fm);
            tabNames=UIUtils.getStringArray(R.array.tab_names);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

    }
}
