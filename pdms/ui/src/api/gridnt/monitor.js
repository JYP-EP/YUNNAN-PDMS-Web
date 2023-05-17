import request from '@/utils/request'

export function localDateStringFromEpoch(epoch) {
  if(epoch == null){
    return "";
  }

  let result = new Date(0);
  result.setUTCSeconds(epoch);
  return result.getFullYear() + "-" + (result.getMonth() + 1) + "-" + result.getDate()
    + " " + result.getHours() + ":" + result.getMinutes() + ":" + result.getSeconds()
    + "." + result.getMilliseconds();
}

export function getAnalogInfo(deviceID){
  return request({
    url: 'pdms/pdms/version1/basic/getAnalogInfo/' + deviceID,
    method: 'get'
  })
}

export function getConditionInfo(deviceID){
  return request({
    url: 'pdms/pdms/version1/basic/getConditionInfo/' + deviceID,
    method: 'get'
  })
}

export function getStatusInfo(deviceID){
  return request({
    url: 'pdms/pdms/version1/basic/getStatusInfo/' + deviceID,
    method: 'get'
  })
}

export function getWarningInfo(deviceID){
  return request({
    url: 'pdms/pdms/version1/basic/getWarningInfo/' + deviceID,
    method: 'get'
  })
}

export function getOtherStatusInfo(deviceID){
  return request({
    url: 'pdms/pdms/version1/basic/getOtherStatusInfo/' + deviceID,
    method: 'get'
  })
}

export function getStripeInfo(deviceID){
  return request({
    url: 'pdms/pdms/version1/basic/getStripeInfo/' + deviceID,
    method: 'get'
  })
}

export function getSettingInfo(deviceID){
  return request({
    url: 'pdms/pdms/version1/basic/getSettingInfo/' + deviceID,
    method: 'get'
  })
}

export function getParameterInfo(deviceID){
  return request({
    url: 'pdms/pdms/version1/basic/getParameterInfo/' + deviceID,
    method: 'get'
  })
}

export function getRecordList(deviceID){
  return request({
    url: 'pdms/pdms/version1/basic/getRecordList/' + deviceID,
    method: 'get'
  })
}
