package com.gridnt.pdms.constants;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
/**
 * @author liukai
 * 录波文件信息载体
 */
public class RecordModel implements Serializable {
    public RecordModel(){

    }
    public RecordModel(String fileName,String filePath){
        FileName = fileName;
        FilePath = filePath;
    }
    public RecordModel(String fileName,String filePath,String file){
        FileName = fileName;
        FilePath = filePath;
        File = file;
    }

    public String FileName;
    public String FilePath;
    public String File;
}
