package com.gdcp.asus_.googleplay.ui.fragment;

import android.view.View;

import com.gdcp.asus_.googleplay.adapter.MyBaseAdapter;
import com.gdcp.asus_.googleplay.domain.SubjectInfo;
import com.gdcp.asus_.googleplay.holder.BaseHolder;
import com.gdcp.asus_.googleplay.holder.SubjectHolder;
import com.gdcp.asus_.googleplay.http.protocol.SubjectProtocol;
import com.gdcp.asus_.googleplay.ui.view.LoadingPage;
import com.gdcp.asus_.googleplay.ui.view.MyListView;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/13.
 */

public class SubjectFragment extends BaseFragment{
    private ArrayList<SubjectInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView myListView=new MyListView(UIUtils.getContext());
        myListView.setAdapter(new SubjectAdapter(data));
        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        SubjectProtocol subjectProtocol=new SubjectProtocol();
        data=subjectProtocol.getData(0);
        return check(data);
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{

        public SubjectAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<SubjectInfo> getHolder(int position) {
            return new SubjectHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onLoadMore() {
            SubjectProtocol subjectProtocol=new SubjectProtocol();
            ArrayList moreData=subjectProtocol.getData(getDataSize());
            return moreData;
        }
    }
}
