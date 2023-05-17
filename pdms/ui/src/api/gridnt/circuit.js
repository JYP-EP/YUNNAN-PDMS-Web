import request from '@/utils/request';

export function loadCbList(iedCode){
  return request({
    url:'pdms/pdms/v1/circuit/cb/'+iedCode,
    method: 'get',
  })
}

export function findGroupBy1065(tb1065Code){
  return request({
    url:'pdms/pdms/v1/circuit/tb1065/group/'+tb1065Code,
    method: 'get',
  })
}

export function findBy1065(tb1065Code){
  return request({
    url:'pdms/pdms/v1/circuit/tb1065/'+tb1065Code,
    method: 'get',
  })
}
