import request from '@/utils/request'


/**
 * 查询子站信息
 * @param query
 * @returns {AxiosPromise}
 */
export function getSubstations() {
  return request({
    url: 'pdms/pdms/version1/ied/getSubstations' ,
    method: 'get'
  })
}
export function getSubstationsTree(){
  return request({
    url: 'pdms/pdms/version1/ied/getSubstationsTree' ,
    method: 'get'
  })
}
export function getDQDescList() {
  return request({
    url: 'pdms/pdms/version1/ied/getDQDescList' ,
    method: 'get'
  })
}
export function  getSubstationsListByDq(query){
  return request({
    url: 'pdms/pdms/version1/ied/getSubstationsListByDq',
    method: 'get',
    params:query
  })
}

export function getIEDsBySubstation(params){
  return request({
    url: 'pdms/pdms/version1/ied/getIEDsBySubstation',
    method: 'get',
    params: params
  })
}
export function getIEDListView(params){
  return request({
    url: 'pdms/pdms/version1/ied/getIEDListView',
    method: 'get',
    params: params
  })
}
export function getBaysVolByStation(query){
  return request({
    url: 'pdms/pdms/version1/ied/getBaysVolByStation' ,
    method: 'get',
    params:query
  })
}
export function getBaysByStationAndLevel(query){
  return request({
    url: 'pdms/pdms/version1/ied/getBaysByStationAndLevel' ,
    method: 'get',
    params:query
  })
}
export function getIedsByBay(query){
  return request({
    url: 'pdms/pdms/version1/ied/getIedsByBay' ,
    method: 'get',
    params:query
  })
}
export function getCrcData(params){
  return request({
    url: 'pdms/pdms/version1/ied/getCrcData' ,
    method: 'post',
    data: params

  })
}
export function loadSvgTabs(query){
  return request({
    url:'pdms/pdms/v1/svg/tabs',
    method: 'get',
    params:query
  })
}


export function getSubsStatusByDq(query){
  return request({
    url:'pdms/grpc/v1/dashboard/dq/status',
    method: 'post',
    data:query
  })
}

export function  getFullIedTree(){
  return request({
    url:'pdms/pdms/v1/ied/fulltree',
    method: 'get',
  })
}



