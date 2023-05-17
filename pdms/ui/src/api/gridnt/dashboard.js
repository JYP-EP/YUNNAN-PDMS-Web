import request from '@/utils/request';

export function getAllStationStatusData(){
  return request({
    url:'pdms/grpc/v1/dashboard/getAllStationStatusData/',
    method: 'get',
  })
}

export function getSubStationStatus(f1041Code){
  return request({
    url:'pdms/grpc/v1/dashboard/getSubStationStatus/'+f1041Code,
    method: 'get',
  })
}

export function getWarningStatistics(f1041Code){
  return request({
    url:'pdms/grpc/v1/dashboard/getWarningStatistics/'+f1041Code,
    method: 'get',
  })
}

export function getWarningStatisticsList(){
  return request({
    url:'pdms/grpc/v1/dashboard/getWarningStatisticsList/',
    method: 'get',
  })
}

export function getWarningAllCount(){
  return request({
    url:'pdms/grpc/v1/dashboard/getWarningAllCount/',
    method: 'get',
  })
}

export function getSubstationListForMap(){
  return request({
    url: 'pdms/grpc/v1/dashboard/getSubstationListForMap',
    methods: 'get'
  });
}

export function getSubstationStatusForMap(){
  return request({
    url: 'pdms/grpc/v1/dashboard/getsubstationstatusformap',
    methods: 'get'
  });
}
