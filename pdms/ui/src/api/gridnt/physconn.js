import request from '@/utils/request';

export function loadPhysGroup(param){
  return request({
    url:'pdms/pdms/v1/physconn/group',
    method: 'post',
    data:param
  })
}

export function loadLogical(f1053Code){
  return request({
    url:'pdms/pdms/v1/physconn/'+f1053Code+'/logical',
    method: 'get',
  })
}
