package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Organization;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.IOrganizationService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * 单位信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/organization")
public class OrganizationController extends BaseController {
    @Autowired
    private IOrganizationService organizationService;

    /**
     * 获取单位列表
     */
    @PreAuthorize("@ss.hasPermi('system:organization:list')")
    @GetMapping("/list")
    public AjaxResult list(Organization organization) {
        List<Organization> agencies = organizationService.selectOrganizationList(organization);
        return AjaxResult.success(agencies);
    }

    /**
     * 查询单位列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:organization:list')")
    @GetMapping("/list/exclude/{organizationId}")
    public AjaxResult excludeChild(@PathVariable(value = "organizationId", required = false) Long organizationId) {
        List<Organization> agencies = organizationService.selectOrganizationList(new Organization());
        Iterator<Organization> it = agencies.iterator();
        while (it.hasNext()) {
            Organization a = it.next();
            if (a.getId().intValue() == organizationId
                    || ArrayUtils.contains(StringUtils.split(a.getAncestors(), ","), organizationId + "")) {
                it.remove();
            }
        }
        return AjaxResult.success(agencies);
    }

    /**
     * 根据单位编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:organization:query')")
    @GetMapping(value = "/{organizationId}")
    public AjaxResult getInfo(@PathVariable Long organizationId) {
        return AjaxResult.success(organizationService.selectOrganizationById(organizationId));
    }

    /**
     * 获取单位下拉树列表
     */
    @GetMapping("/treeSelect")
    public AjaxResult treeselect(Organization organization) {
        List<Organization> agencies = organizationService.selectOrganizationList(organization);
        return AjaxResult.success(organizationService.buildOrganizationTreeSelect(agencies));
    }

    /**
     * 获取单位下拉树列表
     */
    @GetMapping("/treeSelectNoAuthor")
    public AjaxResult treeselectNoAuthor(Organization organization) {
        List<Organization> agencies = organizationService.selectOrganizationListWithNoAuthor(organization);
        return AjaxResult.success(organizationService.buildOrganizationTreeSelect(agencies));
    }

    /**
     * 加载对应角色单位列表树
     */
    @GetMapping(value = "/roleOrgTreeselect/{roleId}")
    public AjaxResult roleOrgTreeselect(@PathVariable("roleId") Long roleId)
    {
        List<Organization> orgs = organizationService.selectOrganizationList(new Organization());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", organizationService.selectOrgListByRoleId(roleId));
        ajax.put("orgs", organizationService.buildOrganizationTreeSelect(orgs));
        return ajax;
    }

    /**
     * 新增单位
     */
    @PreAuthorize("@ss.hasPermi('system:organization:add')")
    @Log(title = "单位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Organization organization) {
        if (UserConstants.NOT_UNIQUE.equals(organizationService.checkOrganizationNameUnique(organization))) {
            return AjaxResult.error("新增单位'" + organization.getName() + "'失败，单位名称已存在");
        }
        organization.setCreateBy(SecurityUtils.getUsername());
        return toAjax(organizationService.insertOrganization(organization));
    }

    /**
     * 修改单位
     */
    @PreAuthorize("@ss.hasPermi('system:organization:edit')")
    @Log(title = "单位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Organization organization) {
        if (UserConstants.NOT_UNIQUE.equals(organizationService.checkOrganizationNameUnique(organization))) {
            return AjaxResult.error("修改单位'" + organization.getName() + "'失败，单位名称已存在");
        } else if (organization.getParentId().equals(organization.getId())) {
            return AjaxResult.error("修改单位'" + organization.getName() + "'失败，上级单位不能是自己");
        } else if (StringUtils.equals(UserConstants.AGENCY_DISABLE, organization.getStatus())
                && organizationService.selectNormalChildrenOrganizationById(organization.getId()) > 0) {
            return AjaxResult.error("该单位包含未停用的子单位！");
        }
        organization.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(organizationService.updateOrganization(organization));
    }

    /**
     * 删除单位
     */
    @PreAuthorize("@ss.hasPermi('system:organization:remove')")
    @Log(title = "单位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{organizationId}")
    public AjaxResult remove(@PathVariable Long organizationId) {
        if (organizationService.hasChildByOrganizationId(organizationId)) {
            return AjaxResult.error("存在下级单位,不允许删除");
        }
        return toAjax(organizationService.deleteOrganizationById(organizationId));
    }
}
