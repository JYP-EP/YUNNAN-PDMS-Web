package com.gridnt.pdms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gridnt.pdms.domain.*;

import java.util.List;

/**
 * 保护设备Service接口
 * 
 * @author gridnt
 * @date 2021-10-23
 */
public interface ITb1046IedService extends IService<Tb1046Ied> {

    List<TreeNode> getBaysVoltageByStation(String f1041Code);

    List<TreeNode> getIedsByBay(String bayCode);

    List<Tb1046Ied> getIEDsBySubstation(String Code,String Codetype);
    List<Tb1046IedListView> getIEDListView(String Code,String Codetype);

    List<TreeNode> getSubstationsListByDq(String dq);

    List<TreeNode> getSubstations();

    List<TreeNode> getDQDescList();

    Page<TbCrc> getCrcData(Page<TbCrc> page);

    List<DivisionNode> getIntelDeviceBySubstation(String stationCode);

    List<DivisionNode> getDeviceTree();

    Tb1046Ied getByCode(String code);

    Tb1041Substation getSubstationByDeviceCode(String deviceCode);

    List<TreeNode> getBaysByStationAndLevel(String f1041Code, String bayVol);

    List<TreeNode> getSubstationsTree();

    List<TreeNode>  getFullTree();
}