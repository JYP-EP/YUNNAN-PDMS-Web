import request from '@/utils/request'

// 查询部门列表
export function list(query) {
  return request({
    url: '/system/organization/list',
    method: 'get',
    params: query
  })
}

// 查询部门列表（排除节点）
export function listExcludeChild(agencyId) {
  return request({
    url: '/system/organization/list/exclude/' + agencyId,
    method: 'get'
  })
}

// 查询部门详细
export function get(agencyId) {
  return request({
    url: '/system/organization/' + agencyId,
    method: 'get'
  })
}

// 查询单位下拉树结构
export function treeSelect() {
  return request({
    url: '/system/organization/treeselect',
    method: 'get'
  })
}

// 根据角色ID查询单位树结构
export function roleOrgTreeselect(roleId) {
  return request({
    url: '/system/organization/roleOrgTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增部门
export function add(data) {
  return request({
    url: '/system/organization',
    method: 'post',
    data: data
  })
}

// 修改部门
export function update(data) {
  return request({
    url: '/system/organization',
    method: 'put',
    data: data
  })
}

// 删除部门
export function del(agencyId) {
  return request({
    url: '/system/organization/' + agencyId,
    method: 'delete'
  })
}
