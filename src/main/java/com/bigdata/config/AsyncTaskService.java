package com.bigdata.config;

import com.bigdata.controller.DataCache;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskService {
    @Async
    public void executeAsyncTask(int type){
        System.out.println("执行异步任务："+type);
        DataCache.getInstance().getAnalysisResult(type);
    }
    @Async
    public void executeAsyncTask(){
        System.out.println("执行异步任务：getCPUInfo");
    }
}
