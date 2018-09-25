package com.bigdata.controller;

import com.bigdata.config.AsyncTaskService;
import com.bigdata.core.MainDataAnalysis;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/api")
public class DataController {

    private JSONObject jsonObject0;
    private JSONObject jsonObject1;
    private JSONObject jsonObject2;
    private JSONObject jsonObject;
    private int type;

    private AnnotationConfigApplicationContext context;
    @Autowired
    private AsyncTaskService asyncTaskService;

    @RequestMapping(value = "/data/analysis/{type}", method = RequestMethod.GET)
    public JSONObject analysis(@PathVariable int type) {
        this.type=type;
        switch (type) {
            case 0:
                jsonObject = jsonObject0;
                break;
            case 1:
                jsonObject = jsonObject1;
                break;
            case 2:
                jsonObject = jsonObject2;
                break;
        }
        if (jsonObject != null) {
            return jsonObject;
        } else {
            jsonObject = DataCache.getInstance().getAnalysisResult(type);
            return this.jsonObject;
        }
    }

    @PostConstruct
    public void init() {
//        System.out.println("开始分析日志文件1...");
//        jsonObject0 = MainDataAnalysis.runBySequentialComputing();
//        System.out.println("日志文件分析完成1");
//        System.out.println("开始分析日志文件2...");
//        jsonObject1 = MainDataAnalysis.runByParallelComputing();
//        System.out.println("日志文件分析完成2");
//        System.out.println("开始分析日志文件3...");
//        jsonObject2 = MainDataAnalysis.runByParallelComputingNotSafe();
//        System.out.println("日志文件分析完成3");
//        context=new  AnnotationConfigApplicationContext(AsyncTaskConfig.class);
//        asyncTaskService=new context.getBean(AsyncTaskService.class);

        while (true){
            asyncTaskService.executeAsyncTask(type);
            asyncTaskService.executeAsyncTask();
        }

    }

    @RequestMapping(value = "/server/cpuInfo", method = RequestMethod.GET)
    public JSONObject getCPUinfo() {
        JSONObject systemInfo = null;
        try {
            systemInfo = MainDataAnalysis.getSystemInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return systemInfo;
    }

}
