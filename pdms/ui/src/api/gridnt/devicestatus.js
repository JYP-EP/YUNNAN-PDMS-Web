import request from '@/utils/request';

export function loadBackboard(f1046Code){
  return request({
    url:'/pdms/pdms/v2/devicestatus/backboard/'+f1046Code,
    method: 'get',
  })
}

export function loadPlate(f1046Code,data){
  return request({
    url:'/pdms/pdms/v2/devicestatus/platepoint/'+f1046Code,
    method: 'get',
    params:data
  })
}

export function loadAnalog(f1046Code,data){
  return request({
    url:'/pdms/pdms/v2/devicestatus/analog/'+f1046Code,
    method: 'get',
    params:data
  })
}


export function loadEventHistory(data){
  return request({
    url:'/pdms/pdms/v2/devicestatus/historyevent',
    method: 'post',
    data: data
  })
}

export function loadDetail(code){
  return request({
    url:'/pdms/pdms/v2/devicestatus/detail/'+code,
    method:'get'
  })
}


