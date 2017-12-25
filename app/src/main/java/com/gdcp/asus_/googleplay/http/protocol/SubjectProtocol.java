package com.gdcp.asus_.googleplay.http.protocol;

import com.gdcp.asus_.googleplay.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/19.
 */

public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectInfo>>{
    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<SubjectInfo> parseData(String result) {
        try{
            JSONArray jsonArray=new JSONArray(result);
            ArrayList<SubjectInfo>subjectInfos=new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                 SubjectInfo subjectInfo=new SubjectInfo();
                 subjectInfo.setDes(jsonObject.getString("des"));
                subjectInfo.setUrl(jsonObject.getString("url"));
                subjectInfos.add(subjectInfo);
            }
            return subjectInfos;
        }catch (Exception e){
             e.printStackTrace();
        }
        return null;
    }
}
