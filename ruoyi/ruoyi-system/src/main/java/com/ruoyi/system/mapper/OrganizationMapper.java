package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 单位管理 数据层
 *
 * @author ruoyi
 */
public interface OrganizationMapper {
    /**
     * 查询单位管理数据
     *
     * @param organization 单位信息
     * @return 单位信息集合
     */
    List<Organization> selectOrganizationList(Organization organization);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @param orgCheckStrictly 单位树选择项是否关联显示
     * @return 选中部门列表
     */
    public List<Integer> selectOrgListByRoleId(@Param("roleId") Long roleId, @Param("orgCheckStrictly") boolean orgCheckStrictly);

    /**
     * 根据单位ID查询信息
     *
     * @param id 单位ID
     * @return 单位信息
     */
    Organization selectOrganizationById(Long id);

    /**
     * 根据ID查询所有子单位
     *
     * @param id 单位ID
     * @return 单位列表
     */
    List<Organization> selectChildrenOrganizationById(Long id);

    /**
     * 根据ID查询所有子单位（正常状态）
     *
     * @param id 单位ID
     * @return 子单位数
     */
    int selectNormalChildrenOrganizationById(Long id);

    /**
     * 是否存在子节点
     *
     * @param id 单位ID
     * @return 结果
     */
    int hasChildByOrganizationId(Long id);

    /**
     * 校验单位名称是否唯一
     *
     * @param name 单位名称
     * @param parentId   父单位ID
     * @return 结果
     */
    Organization checkOrganizationNameUnique(@Param("name") String name, @Param("parentId") Long parentId);

    /**
     * 新增单位信息
     *
     * @param organization 单位信息
     * @return 结果
     */
    int insertOrganization(Organization organization);

    /**
     * 修改单位信息
     *
     * @param organization 单位信息
     * @return 结果
     */
    int updateOrganization(Organization organization);

    /**
     * 修改所在单位的父级单位状态
     *
     * @param organization 单位
     */
    void updateOrganizationStatus(Organization organization);

    /**
     * 修改子元素关系
     *
     * @param organizationList 子元素
     * @return 结果
     */
    int updateOrganizationChildren(@Param("organizationList") List<Organization> organizationList);

    /**
     * 删除单位管理信息
     *
     * @param id 单位ID
     * @return 结果
     */
    int deleteOrganizationById(Long id);

    List<Organization> selectOrganizationListWithNoAuthor(Organization agency);
}
