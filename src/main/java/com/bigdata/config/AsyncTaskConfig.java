package com.bigdata.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync //利用@EnableAsync注解开启异步任务支持
public class AsyncTaskConfig implements AsyncConfigurer {

    /**
     * /配置类实现AsyncConfigurer接口并重写 getAsyncExecutor 方法,并返回一个 ThreadPoolTaskExecutor,
     * 这样我们就获得了一个线程池 taskExecutor
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor =new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);//最小核心线程数
        taskExecutor.setMaxPoolSize(10);//最多线程数
        taskExecutor.setKeepAliveSeconds(1);//无任务时线程存货时间
        taskExecutor.initialize();//

        return null;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
