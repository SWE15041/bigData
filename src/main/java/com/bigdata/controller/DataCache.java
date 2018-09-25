package com.bigdata.controller;

import com.bigdata.core.MainDataAnalysis;
import net.sf.json.JSONObject;

/**
 * 单例-双重检测机制
 */
public class DataCache {

    private static DataCache dataCache;

    //私有构造函数
    private DataCache() {
    }


    public static DataCache getInstance() {
        if (dataCache == null) {
            synchronized (DataCache.class) {
                if (dataCache == null) {
                    dataCache = new DataCache();
                }
            }
        }
        return dataCache;
    }

    public JSONObject getAnalysisResult(int type) {
        JSONObject jsonObject = null;
        switch (type) {
            case 0:
                jsonObject = MainDataAnalysis.runBySequentialComputing();
                break;
            case 1:
                jsonObject = MainDataAnalysis.runByParallelComputing();
                break;
            case 2:
                jsonObject = MainDataAnalysis.runByParallelComputingNotSafe();
                break;
        }
        return jsonObject;
    }

}
