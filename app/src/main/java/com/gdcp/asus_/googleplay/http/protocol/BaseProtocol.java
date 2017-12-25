package com.gdcp.asus_.googleplay.http.protocol;

import com.gdcp.asus_.googleplay.http.HttpHelper;
import com.gdcp.asus_.googleplay.utils.IOUtils;
import com.gdcp.asus_.googleplay.utils.LogUtils;
import com.gdcp.asus_.googleplay.utils.StringUtils;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *  访问网络的基类
 *
 */

public abstract class BaseProtocol<T> {
     public T getData(int index){
         LogUtils.i("enter getdata");
         String result=getCache(index);
         LogUtils.i("enter getdata"+result);
         if (StringUtils.isEmpty(result)){
             result=getDataFromServer(index);
         }
         if (result!=null){
             T data=parseData(result);
             return data;
         }
         return null;
     }

    private String getDataFromServer(int index) {
        LogUtils.i("enter getDataFromServer");
        HttpHelper.HttpResult httpResult=HttpHelper.get(HttpHelper.URL+getKey()+"?index="+index+getParams());
        if (httpResult!=null){
            String result=httpResult.getString();
            LogUtils.i("enter getDataFromServer"+result);
            //写缓存
            if (!StringUtils.isEmpty(result)){
                setCache(index,result);
            }
            return result;
        }
        return null;
    }
    //获取网络关键词
    public abstract String getKey();
    //获取网络链接参数
    public abstract String getParams();

    //写入缓存
    public void setCache(int index,String json){
        //本应用的缓存文件夹
        File cacheDir= UIUtils.getContext().getCacheDir();
        File cacheFile=new File(cacheDir,getKey()+"?index="+index+getParams());
        FileWriter fileWriter=null;
        try {
             fileWriter=new FileWriter(cacheFile);
            long deadline=System.currentTimeMillis()+30*60*1000;
            fileWriter.write(deadline+"\n");
            fileWriter.write(json);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(fileWriter);
        }
    }

    //读缓存
    public String getCache(int index){
        File cacheDir= UIUtils.getContext().getCacheDir();
        File cacheFile=new File(cacheDir,getKey()+"?index="+index+getParams());
        if (cacheFile.exists()){
            BufferedReader reader=null;
            try {
                reader=new BufferedReader(new FileReader(cacheFile));
                String deadline=reader.readLine();
                long deadtime=Long.parseLong(deadline);
                if(System.currentTimeMillis()<deadtime){
                    StringBuffer sb=new StringBuffer();
                      String line;
                      while ((line=reader.readLine())!=null){
                          sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (reader!=null){
                    IOUtils.close(reader);
                }
            }
        }
        return null;
    }

    public abstract T parseData(String result);
}
