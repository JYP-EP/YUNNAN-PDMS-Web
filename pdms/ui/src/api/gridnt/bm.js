import request from '@/utils/request'

// 查询部门信息列表
export function listBm(query) {
  return request({
    url: '/gridnt/bm/list',
    method: 'get',
    params: query
  })
}

// 查询部门信息详细
export function getBm(bmid) {
  return request({
    url: '/gridnt/bm/' + bmid,
    method: 'get'
  })
}

// 新增部门信息
export function addBm(data) {
  return request({
    url: '/gridnt/bm',
    method: 'post',
    data: data
  })
}

// 修改部门信息
export function updateBm(data) {
  return request({
    url: '/gridnt/bm',
    method: 'put',
    data: data
  })
}

// 删除部门信息
export function delBm(bmid) {
  return request({
    url: '/gridnt/bm/' + bmid,
    method: 'delete'
  })
}

// 导出部门信息
export function exportBm(query) {
  return request({
    url: '/gridnt/bm/export',
    method: 'get',
    params: query
  })
}