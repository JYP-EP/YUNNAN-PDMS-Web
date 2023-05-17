package com.gridnt.pdms.controller;

import com.gridnt.common.web.ResponseDTO;
import com.gridnt.common.web.WebUtils;
import com.gridnt.minio.entity.FileVo;
import com.gridnt.minio.service.IMinioService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("pdms/{version}/svg")
@RestController
public class SvgController {


    @Autowired
    IMinioService minioService;

    @GetMapping("tabs")
    public ResponseDTO<List<Map<String,String>>> loadSvgTabs(@RequestParam Map<String,String> param) throws Exception {
        String subsName = param.get("subsName");
        List<Map<String,String>>  list = new ArrayList<>();
        List<FileVo> files = minioService.fileList(subsName);
        if(CollectionUtils.isNotEmpty(files)){
            List<FileVo> useFile = files.parallelStream().filter(fileVo -> fileVo.getFileName().indexOf("主接线图") > -1 |fileVo.getFileName().indexOf("网络拓扑图") > -1 )
                    .collect(Collectors.toList());
            for(FileVo fileVo : useFile){
                Map<String,String> fileMap = new HashMap<>();
                String tabName = fileVo.getFileName();
                tabName = tabName.substring(tabName.lastIndexOf(File.separator)+1,tabName.lastIndexOf("."));
                fileMap.put("tabName",tabName);
                fileMap.put("filePath",minioService.getUrl(fileVo.getFileName()));
                list.add(fileMap);
            }
        }
        if(CollectionUtils.isNotEmpty(list)){
            list.sort(new Comparator<Map<String, String>>() {
                @Override
                public int compare(Map<String, String> o1, Map<String, String> o2) {
                    if(o1.get("tabName").indexOf("主接线图") > -1){
                        return -1;
                    }else if(o2.get("tabName").indexOf("主接线图") > -1){
                        return 1;
                    }else{
                        return  o1.get("tabName").compareTo(o2.get("tabName"));
                    }
                }
            });
        }
        return WebUtils.createSuccessResponse(list);
    }

    private  List<Map<String,String>> loadNetworkFiles(File file){
        List<Map<String,String>> filePathList = new ArrayList<>();
        if(file.exists()){
            File[] fileList = file.listFiles();
            for(int i = 0;i< fileList.length;i++){
                File subFile = fileList[i];
                filePathList.addAll(loadNetworkFiles(subFile));
            }
        }else if(file.isFile()){
            if(file.getName().endsWith("网络拓扑图.svg") || file.getName().endsWith("主接线图.svg")){
                String showName = file.getName().substring(0,file.getName().lastIndexOf("."));
                Map<String,String> fileMap = new HashMap<>();
                fileMap.put("tabName",showName);
                fileMap.put("filePath",file.getAbsolutePath().replace("",""));
                return Arrays.asList(fileMap) ;
            }
        }
        return filePathList;

    }

    public static void main(String[] args) {
//        File file = new File("/media/wcy/D/workspace/pdms-web/pdms/ui/public/images/110kV金沙变电站");
//        System.out.println(loadNetworkFiles(file));
    }
}
