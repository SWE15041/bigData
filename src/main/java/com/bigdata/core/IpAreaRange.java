package com.bigdata.core;

import java.io.*;
import com.bigdata.util.ip.IpHelper;
import java.util.HashMap;
import java.util.TreeMap;

public class IpAreaRange {

    private String ipRangeFileName;
    private SizeBalancedTree ipRangeTree_S;
    private SizeBalancedTree ipRangeTree_T;
    private HashMap<String,Integer> map;
    private TreeMap<String,Integer> countOfRegion;

    public IpAreaRange() {
        //this.ipRangeFileName = "" ;
        //this.ipRangeTree_S = new SizeBalancedTree();
        //this.ipRangeTree_T = new SizeBalancedTree();
        map = new HashMap<String, Integer>();
        ValueComparator bvc =  new ValueComparator(map);
        countOfRegion = new TreeMap<String,Integer>(bvc);
    }

    public IpAreaRange(String fileName) {
        this.ipRangeFileName = fileName;
        this.ipRangeTree_S = new SizeBalancedTree<NodeOfIp>();
        this.ipRangeTree_T = new SizeBalancedTree<NodeOfIp>();
        this.build();
    }

    /**
     * 建立IP段SBT
     *
     * @param ipRangeTree_S
     * @param ipRangeTree_T
     */
    public void build() {
        try {
            File file = new File(ipRangeFileName);
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"), 5 * 1024 * 1024);
            String line = "";
            int value = 0;
            String area = "";
            while ((line = reader.readLine()) != null) {
                if (line.charAt(1) >= '0' && line.charAt(1) <= '9') {
                    String[] tempIpRange = line.split("   ");
                    String tempIp_S = tempIpRange[0];
                    String tempIp_T = tempIpRange[1];
                    NodeOfIp nodeIP_S = new NodeOfIp(IpV4Util.ipToInt(tempIp_S),area);
                    NodeOfIp nodeIP_T = new NodeOfIp(IpV4Util.ipToInt(tempIp_T),area);
                    ipRangeTree_S.insert(nodeIP_S);
                    ipRangeTree_T.insert(nodeIP_T);
                }
                else {
                    area = line;
                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 传入IP地址，搜索IpHelper的结果，返回省份信息
     *
     * @param checkIp
     * @return ans
     */
    public String search(String checkIp) {
        String region = "null";
        try {
            region = IpHelper.findRegionByIp(checkIp);
            insertRegion(region);
        }
        catch (Exception e) {
            System.out.println("Err " + e);
        }
        return region;
    }

    public void insertRegion(String region) {
        if (map.containsKey(region)) {
           Integer times = map.get(region);
           map.put(region, times+1);
        }
        else {
            map.put(region, 1);
        }
    }

    public TreeMap<String, Integer> getRegionCount() {
        countOfRegion.putAll(map);
        return this.countOfRegion;
    }
}

