package com.gridnt.pdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gridnt.pdms.domain.*;
import com.gridnt.pdms.mapper.Tb1041SubstationMapper;
import com.gridnt.pdms.mapper.Tb1042BayMapper;
import com.gridnt.pdms.mapper.Tb1046IedListViewMapper;
import com.gridnt.pdms.service.ITb1041SubstationService;
import com.gridnt.pdms.service.ITb1042BayService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gridnt.pdms.mapper.Tb1046IedMapper;
import com.gridnt.pdms.service.ITb1046IedService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 保护设备Service业务层处理
 *
 * @author gridnt
 * @date 2021-10-23
 */
@DataSource(value = DataSourceType.SLAVE)
@Service
public class Tb1046IedServiceImpl extends ServiceImpl<Tb1046IedMapper, Tb1046Ied> implements ITb1046IedService {

    @Autowired
    private Tb1042BayMapper tb1042BayMapper;

    @Autowired
    private Tb1046IedListViewMapper tb1046IEDLISTVIEWMapper;

    @Autowired
    private Tb1041SubstationMapper tb1041SubstationMapper;

    @Override
    public List<DivisionNode> getDeviceTree(){
        List<Tb1041Substation> substations = tb1041SubstationMapper.selectList(Wrappers.emptyWrapper());
        if(substations != null){
            List<DivisionNode> divisionNodes = new ArrayList<>();
            for(Tb1041Substation substation : substations){
                DivisionNode rootNode = new DivisionNode(substation);
                List<DivisionNode> deviceNode = getIntelDeviceBySubstation(substation.getF1041Code());
                rootNode.setChildren(deviceNode);
                divisionNodes.add(rootNode);
            }
            return divisionNodes;
        }
        return null;
    }

    @Override
    public List<TreeNode> getBaysVoltageByStation(String f1041Code) {
        List<String> list = tb1042BayMapper.getBaysVoltageByStation(f1041Code);
        List<TreeNode> treeList = new ArrayList<>();
        if(list!=null){
            list.forEach(a->{
               TreeNode tree = new TreeNode();
               if(a.equals("0")){
                   tree.setLabel("无电压等级");
               }else {
                   tree.setLabel(a + "kV");
               }
               tree.setVoltage(a+"");
               tree.setType("V");
               tree.setId(a+"");
               tree.setExtra(f1041Code);
               tree.setLeaf(false);
               treeList.add(tree);
           });
            return treeList;
        }
        return null;
    }

    @Override
    public List<TreeNode> getBaysByStationAndLevel(String f1041Code, String bayVol) {
        QueryWrapper<Tb1042Bay> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1041_CODE",f1041Code);
        queryWrapper.eq("F1042_VOLTAGE",bayVol);
        queryWrapper.orderByAsc("F1041_CODE","F1042_CODE");
        List<Tb1042Bay> tb1042BaysList = tb1042BayMapper.selectList(queryWrapper);
        List<TreeNode> treeList =   tb1042BaysList.stream().map(TreeNode::new).collect(Collectors.toList());
        if(treeList.size()>0){
            TreeNode firstNode = treeList.get(0);
            firstNode.setChildren(getIedsByBay(firstNode.getId()));
        }
        return treeList;
    }



    @Override
    public List<TreeNode> getIedsByBay(String bayCode) {
        List<Tb1046Ied> tb1046Ieds = getIntelDeviceByBay(bayCode);
        if(tb1046Ieds!=null){
            return  tb1046Ieds.stream().map(TreeNode::new).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Tb1046Ied> getIEDsBySubstation(String Code,String Codetype){
        if(Codetype.equals("I")) {
            List<Tb1046Ied> devicesInBay = getIntelDeviceByCode(Code);
            return devicesInBay;
        }

        QueryWrapper<Tb1042Bay> queryWrapper = Wrappers.query();

        switch (Codetype) {
            case "S":
                queryWrapper.eq("F1041_CODE", Code);
                break;
            case "V":
                queryWrapper.eq("F1042_VOLTAGE", Code);
                break;
            case "B":
                queryWrapper.eq("F1042_CODE", Code);
                break;
        }
        queryWrapper.orderByAsc("F1041_CODE");
        List<Tb1042Bay> bays = tb1042BayMapper.selectList(queryWrapper);

        if (bays != null && bays.size() > 0) {
            List<Tb1046Ied> devices = new ArrayList<>();
            for (Tb1042Bay bay : bays) {
                List<Tb1046Ied> devicesInBay = getIntelDeviceByBay(bay.getF1042Code());
                devices.addAll(devicesInBay);
            }
            return devices;
        }
        return null;
    }

    @Override
    public List<Tb1046IedListView> getIEDListView(String Code,String Codetype){

        QueryWrapper<Tb1046IedListView> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1046_CODE",Code);
        return tb1046IEDLISTVIEWMapper.selectList(queryWrapper);
    }

    private List<Tb1046Ied> getIntelDeviceByBay(String bayCode){
        QueryWrapper<Tb1046Ied> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1042_CODE",bayCode);
        queryWrapper.orderByAsc("F1042_CODE","F1046_CODE");
        return baseMapper.selectList(queryWrapper);
    }

    private List<Tb1046Ied> getIntelDeviceByCode(String bayCode){
        QueryWrapper<Tb1046Ied> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1046_CODE",bayCode);
        queryWrapper.orderByAsc("F1042_CODE","F1046_CODE");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<TreeNode> getSubstations() {
        List<Tb1041Substation> substations = tb1041SubstationMapper.selectList(Wrappers.emptyWrapper());
        if(substations!=null){
            return  substations.stream().map(TreeNode::new).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<TreeNode> getSubstationsTree() {
        List<TreeNode> list = new ArrayList<>();
        List<Tb1041Substation> substations = tb1041SubstationMapper.selectList(Wrappers.emptyWrapper());
        if(substations!=null){
            Map<String,List<Tb1041Substation>> map = substations.stream().collect(Collectors.groupingBy(ord -> ord.getF1041Dqdesc()));
            for (Map.Entry<String, List<Tb1041Substation>> entry : map.entrySet()) {
                TreeNode treeNode = new TreeNode();
                List<Tb1041Substation> substationList = entry.getValue();
                List<TreeNode> treeNodes = substationList.stream().map(TreeNode::new).collect(Collectors.toList());
                treeNode.setChildren(treeNodes);
                treeNode.setLabel(entry.getKey());
                treeNode.setId(entry.getKey());
                System.out.println("key= " + entry.getKey() + " and value= " + substationList);
                list.add(treeNode);
            }
        }
        //与树的排序一致
        list.sort(new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode o1, TreeNode o2) {
                return o2.getId().compareTo(o1.getId());
            }
        });
        return list;
    }

    @Override
    public List<TreeNode> getFullTree() {
        //查询所有的地区树树信息
        List<TreeNode> dqTree = getDQDescList();
        for(int i = 0;i<dqTree.size();i++){
            TreeNode dqTreeNode = dqTree.get(i);
            List<TreeNode> subsTree = getSubstationsListByDq(dqTreeNode.getId());
            dqTreeNode.setChildren(subsTree);
            for(int j = 0;j<subsTree.size();j++){
                TreeNode subsTreeNode = subsTree.get(j);
                List<TreeNode> volTree = getBaysVoltageByStation(subsTreeNode.getId());
                subsTreeNode.setChildren(volTree);
                for(int k = 0;k<volTree.size();k++){
                    TreeNode volTreeNode = volTree.get(k);
                    List<TreeNode> bayTree =  getBaysByStationAndLevel(subsTreeNode.getId(),volTreeNode.getId());
                    volTreeNode.setChildren(bayTree);
                    for(int m = 0;m<bayTree.size();m++){
                        TreeNode bayTreeNode = bayTree.get(m);
                        List<TreeNode> iedTree = getIedsByBay(bayTreeNode.getId());
                        bayTreeNode.setChildren(iedTree);
                    }
                }

            }
        }
        return dqTree;
    }

    @Override
    public List<TreeNode> getDQDescList(){
        List<String> substations = tb1041SubstationMapper.getDQDescList();
        List<TreeNode> treeList= new ArrayList<>();
        substations.forEach(a->{
            TreeNode tree = new TreeNode();
            tree.setLabel(a);
            tree.setId(a);
            tree.setExtra(a);
            tree.setType("D");
            treeList.add(tree);
        });
        return treeList;
    }

    @Override
    public List<TreeNode> getSubstationsListByDq(String dq){
        QueryWrapper<Tb1041Substation> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1041_DQDESC", dq);
        List<Tb1041Substation> substations = tb1041SubstationMapper.selectList(queryWrapper);
        if(substations!=null){
            return  substations.stream().map(TreeNode::new).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Page<TbCrc> getCrcData(Page<TbCrc> page) {
        return tb1041SubstationMapper.getCrcData(page);
    }

    @Override
    public List<DivisionNode> getIntelDeviceBySubstation(String stationCode){
        QueryWrapper<Tb1042Bay> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1041_CODE", stationCode);
        queryWrapper.orderByAsc("F1041_CODE");
        List<Tb1042Bay> bays = tb1042BayMapper.selectList(queryWrapper);
        if(bays!=null && bays.size() > 0){
            List<Tb1046Ied> devices = new ArrayList<>();
            for(Tb1042Bay bay : bays){
                List<Tb1046Ied> devicesInBay = getIntelDeviceByBay(bay.getF1042Code());
                devices.addAll(devicesInBay);
            }
            return devices.stream().map(DivisionNode::new).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Tb1046Ied getByCode(String code){
        QueryWrapper<Tb1046Ied> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1046_CODE", code);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Tb1041Substation getSubstationByDeviceCode(String deviceCode){
        QueryWrapper<Tb1046Ied> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1046_CODE", deviceCode);
        Tb1046Ied ied = baseMapper.selectOne(queryWrapper);
        if(ied == null) {
            return null;
        }

        QueryWrapper<Tb1042Bay> queryWrapper1 = Wrappers.query();
        queryWrapper1.eq("F1042_CODE", ied.getF1042Code());
        Tb1042Bay bay = tb1042BayMapper.selectOne(queryWrapper1);
        if(bay == null) {
            return null;
        }

        QueryWrapper<Tb1041Substation> queryWrapper2 = Wrappers.query();
        queryWrapper2.eq("F1041_CODE", bay.getF1041Code());
        return tb1041SubstationMapper.selectOne(queryWrapper2);
    }


}