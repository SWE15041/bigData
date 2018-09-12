package com.bigdata.controller;

import com.bigdata.core.MainDataAnalysis;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/data")
public class DataController {

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    public JSONObject analysis(HttpSession session) {
        Object analysisDataResult = session.getAttribute("analysisDataResult");
        if (analysisDataResult != null) {
            System.out.println("session");
            JSONObject fromObject = JSONObject.fromObject(analysisDataResult);
            return fromObject;
        }
        JSONObject jsonObject = MainDataAnalysis.run();
        session.setAttribute("analysisDataResult", jsonObject);
        return jsonObject;
    }
}
