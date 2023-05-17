package com.gridnt.pdms.domain;

import com.gridnt.pdms.constants.SubsationConstants;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.List;

/**
 * @author liukai
 */
@Data
@Accessors(chain = true)
@FieldNameConstants
public class TreeNode implements Serializable {

    private String id;

    private String label;

    private List<TreeNode> children;

    private String type;

    /**
     * 额外字段
     */
    private String extra;
    /**
     * 电压等级
     */
    private String voltage;
    //当前数据对象
    private Object me;

    private boolean isLeaf;

    public TreeNode(){}

    public TreeNode(Tb1042Bay bay){
        this.id = bay.getF1042Code();
        this.label = bay.getF1042Name();
        this.children = null;
        this.type = SubsationConstants.BAY_TYPE;
        this.extra = "";
        this.voltage = "";
    }
    public TreeNode(Tb1046Ied ied){
        this.id = ied.getF1046Code();
        this.label = ied.getF1046Name()+":" +ied.getF1046Desc();
        this.children = null;
        this.type = SubsationConstants.IED_TYPE;
        this.me = ied;
        this.isLeaf =true;
        this.extra = "";
        this.voltage = "";
    }
    public TreeNode(Tb1041Substation substation){
        this.id = substation.getF1041Code();
        this.label = substation.getF1041Desc();
        this.children = null;
        this.type = SubsationConstants.SUBSTATION_TYPE;
        this.extra = "";
        this.voltage = "";
    }
}
