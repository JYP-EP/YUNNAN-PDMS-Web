package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.Organization;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.OrganizationMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 单位管理 服务实现
 *
 * @author ruoyi
 */
@Service
public class OrganizationServiceImpl implements IOrganizationService {

    @Autowired
    private OrganizationMapper mapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 查询单位管理数据
     *
     * @param agency 单位信息
     * @return 单位信息集合
     */
    @Override
    @DataScope(organizationAlias = "a")
    public List<Organization> selectOrganizationList(Organization agency) {
        return mapper.selectOrganizationList(agency);
    }

    /**
     * 查询所有的单位数据
     * @param agency 单位信息
     * @return
     */
    @Override
    public List<Organization> selectOrganizationListWithNoAuthor(Organization agency) {
        return mapper.selectOrganizationListWithNoAuthor(agency);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param agencies 单位列表
     * @return 树结构列表
     */
    @Override
    public List<Organization> buildOrganizationTree(List<Organization> agencies) {
        List<Organization> returnList = new ArrayList<Organization>();
        List<Long> tempList = new ArrayList<Long>();
        for (Organization agency : agencies) {
            tempList.add(agency.getId());
        }
        for (Iterator<Organization> iterator = agencies.iterator(); iterator.hasNext(); ) {
            Organization agency = (Organization) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(agency.getParentId())) {
                recursionFn(agencies, agency);
                returnList.add(agency);
            }
        }
        if (returnList.isEmpty()) {
            returnList = agencies;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param agencies 单位列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildOrganizationTreeSelect(List<Organization> agencies) {
        List<Organization> agencyTrees = buildOrganizationTree(agencies);
        return agencyTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Integer> selectOrgListByRoleId(Long roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return mapper.selectOrgListByRoleId(roleId, role.isOrgCheckStrictly());
    }

    /**
     * 根据单位ID查询信息
     *
     * @param agencyId 单位ID
     * @return 单位信息
     */
    @Override
    public Organization selectOrganizationById(Long agencyId) {
        return mapper.selectOrganizationById(agencyId);
    }

    /**
     * 根据ID查询所有子单位（正常状态）
     *
     * @param agencyId 单位ID
     * @return 子单位数
     */
    @Override
    public int selectNormalChildrenOrganizationById(Long agencyId) {
        return mapper.selectNormalChildrenOrganizationById(agencyId);
    }

    /**
     * 是否存在子节点
     *
     * @param agencyId 单位ID
     * @return 结果
     */
    @Override
    public boolean hasChildByOrganizationId(Long agencyId) {
        int result = mapper.hasChildByOrganizationId(agencyId);
        return result > 0 ? true : false;
    }

    /**
     * 校验单位名称是否唯一
     *
     * @param agency 单位信息
     * @return 结果
     */
    @Override
    public String checkOrganizationNameUnique(Organization agency) {
        Long agencyId = StringUtils.isNull(agency.getId()) ? -1L : agency.getId();
        Organization info = mapper.checkOrganizationNameUnique(agency.getName(), agency.getParentId());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != agencyId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存单位信息
     *
     * @param agency 单位信息
     * @return 结果
     */
    @Override
    public int insertOrganization(Organization agency) {
        Organization parent = mapper.selectOrganizationById(agency.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.AGENCY_NORMAL.equals(parent.getStatus())) {
            throw new CustomException("单位停用，不允许新增");
        }
        agency.setAncestors(parent.getAncestors() + "," + parent.getId());
        return mapper.insertOrganization(agency);
    }

    /**
     * 修改保存单位信息
     *
     * @param agency 单位信息
     * @return 结果
     */
    @Override
    public int updateOrganization(Organization agency) {
        Organization newParentOrganization = mapper.selectOrganizationById(agency.getParentId());
        Organization oldOrganization = mapper.selectOrganizationById(agency.getId());
        if (StringUtils.isNotNull(newParentOrganization) && StringUtils.isNotNull(oldOrganization)) {
            String newAncestors = newParentOrganization.getAncestors() + "," + newParentOrganization.getId();
            String oldAncestors = oldOrganization.getAncestors();
            agency.setAncestors(newAncestors);
            updateOrganizationChildren(agency.getId(), newAncestors, oldAncestors);
        }
        int result = mapper.updateOrganization(agency);
        if (UserConstants.AGENCY_NORMAL.equals(agency.getStatus())) {
            // 如果该单位是启用状态，则启用该单位的所有上级单位
            updateParentOrganizationStatus(agency);
        }
        return result;
    }

    /**
     * 修改该单位的父级单位状态
     *
     * @param agency 当前单位
     */
    private void updateParentOrganizationStatus(Organization agency) {
        String updateBy = agency.getUpdateBy();
        agency = mapper.selectOrganizationById(agency.getId());
        agency.setUpdateBy(updateBy);
        mapper.updateOrganizationStatus(agency);
    }

    /**
     * 修改子元素关系
     *
     * @param agencyId     被修改的单位ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateOrganizationChildren(Long agencyId, String newAncestors, String oldAncestors) {
        List<Organization> children = mapper.selectChildrenOrganizationById(agencyId);
        for (Organization child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            mapper.updateOrganizationChildren(children);
        }
    }

    /**
     * 删除单位管理信息
     *
     * @param agencyId 单位ID
     * @return 结果
     */
    @Override
    public int deleteOrganizationById(Long agencyId) {
        return mapper.deleteOrganizationById(agencyId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<Organization> list, Organization t) {
        // 得到子节点列表
        List<Organization> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Organization tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Organization> getChildList(List<Organization> list, Organization t) {
        List<Organization> tlist = new ArrayList<Organization>();
        Iterator<Organization> it = list.iterator();
        while (it.hasNext()) {
            Organization n = (Organization) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Organization> list, Organization t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
