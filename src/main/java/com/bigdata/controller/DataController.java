package com.bigdata.controller;

import com.bigdata.core.MainDataAnalysis;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/data")
public class DataController {

    private JSONObject jsonObject;

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    public JSONObject analysis() {
        if (jsonObject != null) {
            return jsonObject;
        } else {
            jsonObject = MainDataAnalysis.run();
            return jsonObject;
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("开始分析日志文件...");
        jsonObject = MainDataAnalysis.run();
        System.out.println("日志文件分析完成");
    }
}
