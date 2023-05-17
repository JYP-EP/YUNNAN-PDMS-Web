package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.Organization;

import java.util.List;

/**
 * 单位管理 服务层
 *
 * @author ruoyi
 */
public interface IOrganizationService {
    /**
     * 查询单位管理数据
     *
     * @param agency 单位信息
     * @return 单位信息集合
     */
    List<Organization> selectOrganizationList(Organization agency);


    /**
     * 查询单位管理数据
     *
     * @param agency 单位信息
     * @return 单位信息集合
     */
    List<Organization> selectOrganizationListWithNoAuthor(Organization agency);

    /**
     * 构建前端所需要树结构
     *
     * @param agencies 单位列表
     * @return 树结构列表
     */
    List<Organization> buildOrganizationTree(List<Organization> agencies);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param agencies 单位列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildOrganizationTreeSelect(List<Organization> agencies);

    /**
     * 根据角色ID查询单位树信息
     *
     * @param roleId 角色ID
     * @return 选中单位列表
     */
    public List<Integer> selectOrgListByRoleId(Long roleId);

    /**
     * 根据单位ID查询信息
     *
     * @param agentId 单位ID
     * @return 单位信息
     */
    Organization selectOrganizationById(Long agentId);

    /**
     * 根据ID查询所有子单位（正常状态）
     *
     * @param agentId 单位ID
     * @return 子单位数
     */
    int selectNormalChildrenOrganizationById(Long agentId);

    /**
     * 是否存在单位子节点
     *
     * @param agentId 单位ID
     * @return 结果
     */
    boolean hasChildByOrganizationId(Long agentId);

    /**
     * 校验单位名称是否唯一
     *
     * @param agency 单位信息
     * @return 结果
     */
    String checkOrganizationNameUnique(Organization agency);

    /**
     * 新增保存单位信息
     *
     * @param agency 单位信息
     * @return 结果
     */
    int insertOrganization(Organization agency);

    /**
     * 修改保存单位信息
     *
     * @param agency 单位信息
     * @return 结果
     */
    int updateOrganization(Organization agency);

    /**
     * 删除单位管理信息
     *
     * @param agencyId 单位ID
     * @return 结果
     */
    int deleteOrganizationById(Long agencyId);
}
