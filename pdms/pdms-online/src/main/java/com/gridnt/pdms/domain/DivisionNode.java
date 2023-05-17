package com.gridnt.pdms.domain;

import com.gridnt.pdms.constants.SubsationConstants;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@FieldNameConstants
public class DivisionNode implements Serializable {
	private String id;
	private String label;
	private String type;
	private boolean isLeaf;

	private List<DivisionNode> children;

	public DivisionNode(){}

	public DivisionNode(String division){
		this.id = division;
		this.label = division;
		this.type = SubsationConstants.OPERATOR_TYPE;
		this.isLeaf = false;
		this.children = null;
	}

	public DivisionNode(Tb1041Substation substation){
		this.id = substation.getF1041Code();
		this.label = substation.getF1041Desc();
		this.children = null;
		this.type = SubsationConstants.SUBSTATION_TYPE;
		this.isLeaf = true;
	}

	public DivisionNode(Tb1046Ied ied){
		this.id = ied.getF1046Code();
		this.label = ied.getF1046Desc();
		this.children = null;
		this.type = SubsationConstants.IED_TYPE;
		this.isLeaf =true;
	}
}
