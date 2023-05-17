import request from '@/utils/request'


// 查询系统全局配置详细
export function getConf() {
  return request({
    url: '/system/conf/',
    method: 'get'
  })
}

// 新增系统全局配置
export function addConf(data) {
  return request({
    url: '/system/conf',
    method: 'post',
    data: data
  })
}
// 修改系统全局配置
export function updateConf(data) {
  return request({
    url: '/system/conf',
    method: 'put',
    data: data
  })
}


