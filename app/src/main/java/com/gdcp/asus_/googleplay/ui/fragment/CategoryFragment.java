package com.gdcp.asus_.googleplay.ui.fragment;

import android.view.View;

import com.gdcp.asus_.googleplay.adapter.MyBaseAdapter;
import com.gdcp.asus_.googleplay.domain.CategoryInfo;
import com.gdcp.asus_.googleplay.holder.BaseHolder;
import com.gdcp.asus_.googleplay.holder.CategoryHolder;
import com.gdcp.asus_.googleplay.holder.TitleHolder;
import com.gdcp.asus_.googleplay.http.protocol.CategoryProtocol;
import com.gdcp.asus_.googleplay.ui.view.LoadingPage;
import com.gdcp.asus_.googleplay.ui.view.MyListView;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/13.
 */

public class CategoryFragment extends BaseFragment{

    private ArrayList<CategoryInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView myListView=new MyListView(UIUtils.getContext());
        myListView.setAdapter(new CategoryAdapter(data));
        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        CategoryProtocol categoryProtocol=new CategoryProtocol();
        data=categoryProtocol.getData(0);
        return check(data);
    }
  class  CategoryAdapter extends MyBaseAdapter<CategoryInfo>{


      public CategoryAdapter(ArrayList<CategoryInfo> data) {
          super(data);
      }

      @Override
      public BaseHolder<CategoryInfo> getHolder(int position) {
          CategoryInfo categoryInfo=data.get(position);
          if (categoryInfo.isTitle()){
                return new TitleHolder();
          }else {
              return new CategoryHolder();
          }

      }

      @Override
      public ArrayList<CategoryInfo> onLoadMore() {
          return null;
      }

      @Override
      public boolean hasMore() {
          return false;
      }

      @Override
      public int getViewTypeCount() {
          return super.getViewTypeCount()+1;
      }

      @Override
      public int getInnerType(int position) {
          CategoryInfo categoryInfo=data.get(position);
          if (categoryInfo.isTitle()){
              //返回标题类型
              return super.getInnerType(position)+1;
          }else {
              //返回普通类型
              return super.getInnerType(position);
          }

      }
  }

}
