package com.gridnt.pdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gridnt.pdms.domain.Physconnview;

import java.util.List;
import java.util.Map;

public interface IPhysconnviewService extends IService<Physconnview> {

    List[] loadGroups( String f1046Code);

    List<Map<String,Object>> loadLogical(String f1053Code);
}
