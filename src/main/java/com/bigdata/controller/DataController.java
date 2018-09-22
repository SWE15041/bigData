package com.bigdata.controller;

import com.bigdata.core.MainDataAnalysis;
import com.bigdata.core.SystemInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/api")
public class DataController {

    private JSONObject jsonObject;


    @RequestMapping(value = "/data/analysis/{type}", method = RequestMethod.GET)
    public JSONObject analysis(@PathVariable int type) {
        if (jsonObject != null) {
            return jsonObject;
        } else {
            JSONObject jsonObject = DataCache.getInstance().getAnalysisResult(type);
            return this.jsonObject;
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("开始分析日志文件1...");
        jsonObject = MainDataAnalysis.runBySequentialComputing();
        System.out.println("日志文件分析完成1");
        System.out.println("开始分析日志文件2...");
        jsonObject = MainDataAnalysis.runByParallelComputing();
        System.out.println("日志文件分析完成2");
        System.out.println("开始分析日志文件3...");
        jsonObject = MainDataAnalysis.runByParallelComputingNotSafe();
        System.out.println("日志文件分析完成3");
    }

    @RequestMapping(value = "/server/cpuinfo",method = RequestMethod.GET)
    public double  getCPUinfo(){
        double cpuUsage = SystemInfo.getCpuUsage();
        return cpuUsage;
    }

}
