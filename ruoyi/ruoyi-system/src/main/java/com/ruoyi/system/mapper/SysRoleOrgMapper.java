package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysRoleOrg;

import java.util.List;

/**
 * 角色与单位关联表 数据层
 * @author hanzhaojun
 */
public interface SysRoleOrgMapper
{
    /**
     * 通过角色ID删除角色和单位关联
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleOrgByRoleId(Long roleId);

    /**
     * 批量删除角色单位关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleOrg(Long[] ids);

    /**
     * 查询单位使用数量
     * 
     * @param deptId 单位ID
     * @return 结果
     */
    public int selectCountRoleOrgByOrgId(Long deptId);

    /**
     * 批量新增角色单位信息
     * 
     * @param roleOrgList 角色单位列表
     * @return 结果
     */
    public int batchRoleOrg(List<SysRoleOrg> roleOrgList);
}
