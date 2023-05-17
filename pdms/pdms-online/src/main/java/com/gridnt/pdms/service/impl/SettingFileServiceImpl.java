package com.gridnt.pdms.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Charsets;
import com.gridnt.pdms.domain.*;
import com.gridnt.pdms.mapper.SettingFileMapper;
import com.gridnt.pdms.service.SettingFileService;
import com.gridnt.pdms.util.XmlBeanUtils;
import com.gridnt.pdms.util.XsteamUtil;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
@DataSource(value = DataSourceType.SLAVE)
public class SettingFileServiceImpl extends ServiceImpl<SettingFileMapper, SettingFile> implements SettingFileService {

    @Override
    public Map<String,ConstantValueVo> getSettingFileData(String code) {
        Map<String,ConstantValueVo> result = new HashMap<>();
        List<SettingFile> settingFiles = baseMapper.getSettingFileDataByCode(code);
        if (ObjectUtils.isNotEmpty(settingFiles)) {
            try {
                SettingFile settingFile = settingFiles.get(0);
                InputStream binaryStream = new ByteArrayInputStream(settingFile.getContent());
                ZipInputStream in = new ZipInputStream(binaryStream);
                ZipEntry entry = null;
                StringBuilder sb = new StringBuilder();
                //获取压缩文件中的每个子文件
                while ((entry = in.getNextEntry()) != null) {
                    String name = entry.getName();
                    if (name.contains("DZXML")) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        SettingFileContentXml settingFileContentXml = (SettingFileContentXml)XmlBeanUtils.xmlToBean(sb.toString(), SettingFileContentXml.class);
                        List<SettingDataVo> settings = settingFileContentXml.getSettings();
                        settings.forEach(setting ->{
                            ConstantValueVo constantValueVo = new ConstantValueVo();
                            constantValueVo.setGrp(setting.getGrp());
                            constantValueVo.setClassName(setting.getDataSet());
                            constantValueVo.setLd(setting.getInstanceName());
                            List<InfoGroup> infoGroupList = setting.getInfoGroupList();
                            String setGrpNum = "";
                            List<SiVo> allSiList = new ArrayList<>();
                            for (int i = 0; i < infoGroupList.size(); i++) {
                                InfoGroup infoGroup = infoGroupList.get(i);
                                List<SiVo> siList = infoGroup.getSiList();
                                setGrpNum = infoGroup.getSetGrpNum();
                                allSiList.addAll(siList);
                            }
                            int anInt = Integer.parseInt(setGrpNum);
                            List<Map<String,String>> grpList = new ArrayList<>();
                            for (int i = 1; i <= anInt; i++) {
                                Map<String,String> grpMap = new HashMap<>();
                                grpMap.put("name","定值区"+i);
                                grpMap.put("value",i+"");
                                grpList.add(grpMap);
                            }
                            constantValueVo.setSiList(allSiList);
                            constantValueVo.setGrpList(grpList);
                            result.put(setting.getDataSet(),constantValueVo);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        return null;
    }

    /**
     * 遍历元素
     * @param node
     * @param json
     */
//    private static void iterateNodes(Element node, JSONObject json) {
//        //获取当前元素名称
//        String nodeName = node.getName();
//        //判断已遍历的JSON中是否已经有了该元素的名称
//        if(json.containsKey(nodeName)){
//            //该元素在同级下有多个
//            Object Object = json.get(nodeName);
//            JSONArray array = null;
//            if(Object instanceof JSONArray){
//                array = (JSONArray) Object;
//            }else {
//                array = new JSONArray();
//                array.add(Object);
//            }
//            //获取该元素下所有子元素
//            List<Element> listElement = node.elements();
//            if(listElement.isEmpty()){
//                //该元素无子元素，获取元素的值
//                String nodeValue = node.getTextTrim();
//                array.add(nodeValue);
//                json.put(nodeName, array);
//                return ;
//            }
//            //有子元素
//            JSONObject newJson = new JSONObject();
//            //遍历所有子元素
//            for(Element e:listElement){
//                //递归
//                iterateNodes(e,newJson);
//            }
//            array.add(newJson);
//            json.put(nodeName, array);
//            return ;
//        }
//        //该元素同级下第一次遍历
//        //获取该元素下所有子元素
//        List<Element> listElement = node.elements();
//        if(listElement.isEmpty()){
//            //该元素无子元素，获取元素的值
//            String nodeValue = node.getTextTrim();
//            json.put(nodeName, nodeValue);
//            return ;
//        }
//        //有子节点，新建一个JSONObject来存储该节点下子节点的值
//        JSONObject object = new JSONObject();
//        for(Element e:listElement){
//            //递归
//            iterateNodes(e,object);
//        }
//        json.put(nodeName, object);
//        return ;
//    }

}
