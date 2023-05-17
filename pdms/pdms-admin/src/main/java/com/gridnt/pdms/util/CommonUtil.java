package com.gridnt.pdms.util;

import java.util.Map;
import java.util.Set;

public class CommonUtil {
    /**
     *
     * Map转String
     * 1、获取map的key的set集合 转换成String数组
     * 2、循环String数组 拼接字符串 每个key value 一组间用 顿号隔开
     * @param map
     * @return
     */
    public static String mapConvertString(Map<String,Object> map){
        Set<String> keySet = map.keySet();
        //将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
            }
            if(i != keyArray.length-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
