package com.bigdata.core;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MainDataAnalysis {

    /**
     * 程序主入口
     *
     * return JSONObject
     */
    public static JSONObject run() {
        try {
                //计时器工具初始化
            ProgramTimer pt = new ProgramTimer();
            pt.start();

            //IP分布范围工具初始化
            IpAreaRange ipar = new IpAreaRange();

            //MAC地址统计工具初始化
            MacAddressStatistics mast = new MacAddressStatistics();

            String pattern = new String();
            //日志格式正则(NginX日志,txt)
            //pattern = "([^ ]*) ([^ ]*) ([^ ]*) (\\[.*\\]) (\\\".*?\\\") (-|[0-9]*) (-|[0-9]*) (\\\".*?\\\") (\\\".*?\\\") \\\"([^\\\"]*)\\\"";
            //日志格式正则(比赛日志,csv)
            pattern = "(,)?((\\\"[^\\\"]*(\\\"{2})*[^\\\"]*\\\")*[^,]*)";
            Pattern r = Pattern.compile(pattern);

            //读入日志文件
            File file = new File(FilePath.logFile);

            //缓冲流
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));

            //缓冲区大小为5M
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"), 5 * 1024 * 1024);

            String line = "";

            //处理的记录数
            int dataCount = 0;
            int sumDataCount = 0;
            //提取的条件
            String needDate = "2018-07-17";

            while ((line = reader.readLine()) != null) {
                //TODO: single Thread Task
                //抽取日志文件的一行进行处理
                sumDataCount++;
                Matcher matcher = r.matcher(line);
                ArrayList<String> listOfLineData = new ArrayList<String>();
                while(matcher.find()) {
                    String cell = matcher.group(2);//group(2) is ((\"[^\"]*(\"{2})*[^\"]*\")*[^,]*)
                    Pattern pattern2 = Pattern.compile("\"((.)*)\"");
                    Matcher matcher2 = pattern2.matcher(cell);
                    if(matcher2.find()) {
                        cell = matcher2.group(1);
                    }
                    listOfLineData.add(cell);
                }
                if (listOfLineData.get(1).equals(needDate)){
                    String region;
                    if (listOfLineData.get(5).contains(",")){
                        String[] tmp = listOfLineData.get(5).split(", ");
                        //System.out.println("私有:" + tmp[0] + " " + "公有:" + tmp[1]);
                        region = ipar.search(tmp[1]);
                    }
                    else {
                        region = ipar.search(listOfLineData.get(5));
                    }
                    if (listOfLineData.get(4).isEmpty()){
                        mast.insert("00-00-00-00-00-00");
                    }
                    else {
                        mast.insert(listOfLineData.get(4));
                    }
                    dataCount++;
                    //System.out.println(listOfLineData.get(4) + " " + listOfLineData.get(5) + " " + region);
                }
            }
            //输出匹配记录数
            System.out.println("Matched Data Counter: " + dataCount);
            System.out.println("Summary Data Counter: " + sumDataCount);
            //程序运行时间
            System.out.println(pt.runningTime());
            //将结果转换为JSON传出
            JSONArray arrayOfMac =  JSONArray.fromObject(mast.getAnswer());
            JSONArray arrayOfIp =  JSONArray.fromObject(ipar.getRegionCount());
            JSONObject jsonOfAnswer = new JSONObject();
            jsonOfAnswer.put("mac",arrayOfMac);
            jsonOfAnswer.put("prov",arrayOfIp);
            //System.out.println(jsonOfAnswer);
            return jsonOfAnswer;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
}