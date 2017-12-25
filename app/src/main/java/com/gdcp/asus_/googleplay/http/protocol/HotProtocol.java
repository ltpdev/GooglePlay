package com.gdcp.asus_.googleplay.http.protocol;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/20.
 */

public class HotProtocol extends BaseProtocol<ArrayList<String>>{
    @Override
    public String getKey() {
        return "hot";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<String> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<String> mHotList = new ArrayList<String>();
            for (int i = 0; i < ja.length(); i++) {
                String str = ja.getString(i);
                mHotList.add(str);
            }

            return mHotList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
