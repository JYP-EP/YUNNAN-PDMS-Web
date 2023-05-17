import request from '@/utils/request';

export function loadOpticalfiberList(bayId){
  return request({
    url:'/pdms/pdms/v2/bay/'+bayId+'/opticalfiber',
    method: "get",
  })
}

export function loadLogicList(bayId){
  return request({
    url:'/pdms/pdms/v2/bay/'+bayId+'/logic',
    method: "get",
  })
}
