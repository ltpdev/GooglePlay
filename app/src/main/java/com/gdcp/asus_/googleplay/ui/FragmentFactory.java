package com.gdcp.asus_.googleplay.ui;

import com.gdcp.asus_.googleplay.ui.fragment.AppFragment;
import com.gdcp.asus_.googleplay.ui.fragment.BaseFragment;
import com.gdcp.asus_.googleplay.ui.fragment.CategoryFragment;
import com.gdcp.asus_.googleplay.ui.fragment.GameFragment;
import com.gdcp.asus_.googleplay.ui.fragment.HomeFragment;
import com.gdcp.asus_.googleplay.ui.fragment.HotFragment;
import com.gdcp.asus_.googleplay.ui.fragment.RecommendFragment;
import com.gdcp.asus_.googleplay.ui.fragment.SubjectFragment;

import java.util.HashMap;

/**
 * Created by asus- on 2017/12/13.
 */

public class FragmentFactory {
   private static HashMap<Integer,BaseFragment>fragmentHashMap=new HashMap<>();
    public static BaseFragment createFragment(int position){
        BaseFragment fragment=fragmentHashMap.get(position);
        if (fragment==null) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AppFragment();
                    break;
                case 2:
                    fragment = new GameFragment();
                    break;
                case 3:
                    fragment = new SubjectFragment();
                    break;
                case 4:
                    fragment = new RecommendFragment();
                    break;
                case 5:
                    fragment = new CategoryFragment();
                    break;
                case 6:
                    fragment = new HotFragment();
                    break;
            }
            fragmentHashMap.put(position, fragment);
        }
        return fragment;
    }

}
