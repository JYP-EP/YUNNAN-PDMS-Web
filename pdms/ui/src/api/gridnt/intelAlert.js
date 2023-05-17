import request from '@/utils/request'

export function getIntelAlertData(params) {
  return request({
    url: 'pdms/pdms/version1/intel/getByPage',
    method: 'post',
    data: params
  })
}

export function getIntelAlertStat(params) {
  return request({
    url: 'pdms/pdms/version1/intel/getStat',
    method: 'post',
    data: params
  })
}

export function getFrequentAlert(params) {
  return request({
    url: 'pdms/pdms/version1/intel/frequentAlert',
    method: 'post',
    data: params
  });
}

export function getFrequentStat(params) {
  return request({
    url: 'pdms/pdms/version1/intel/frequentStat',
    method: 'post',
    data: params
  });
}

export function getProtectTunnelAlert(params) {
  return request({
    url: 'pdms/pdms/version1/intel/tunnelAlert',
    method: 'post',
    data: params
  });
}

export function getBlockingAlert(params) {
  return request({
    url: 'pdms/pdms/version1/intel/blockingAlert',
    method: 'post',
    data: params
  });
}

export function getBlockingStat(params) {
  return request({
    url: 'pdms/pdms/version1/intel/blockingStat',
    method: 'post',
    data: params
  });
}

export function getInspectionAlert(params) {
  return request({
    url: 'pdms/pdms/version1/intel/inspectionAlert',
    method: 'post',
    data: params
  });
}

export function getCheckAlert(params) {
  return request({
    url: 'pdms/pdms/version1/intel/checkAlert',
    method: 'post',
    data: params
  });
}

export function getStatAlert(params) {
  return request({
    url: 'pdms/pdms/version1/intel/statAlert',
    method: 'post',
    data: params
  });
}

export function getFrequentAlertDetail(params) {
  return request({
    url: 'pdms/pdms/version1/intel/detail/frequent',
    method: 'get',
    params: params
  });
}

// 区外分析-xml解析
export function getOutRimAlertDetail(params) {
  return request({
    url: 'pdms/pdms/version1/intel/detail/outrim',
    method: 'get',
    params: params
  });
}
// 区外分析-列表查询
export function getOutRimAlert(params) {
  return request({
    url: 'pdms/pdms/version1/intel/outRimAlert',
    method: 'post',
    data: params
  });
}

// 主变变中-xml解析
export function getTransformerCurrentAlertDetail(params) {
  return request({
    url: 'pdms/pdms/version1/intel/detail/transformer',
    method: 'get',
    params: params
  });
}// 主变变中-列表查询
export function getTransformerCurrentAlert(params) {
  return request({
    url: 'pdms/pdms/version1/intel/transformerAlert',
    method: 'post',
    data: params
  });
}


export function getTunnelAlertDetail(params) {
  return request({
    url: 'pdms/pdms/version1/intel/detail/tunnel',
    method: 'get',
    params: params
  });
}

export function getSTATDetail(params) {
  return request({

    url: `pdms/pdms/version1/intel/detail/stat0${params.type - 7}`,
    method: 'get',
    params: params
  });
}

export function getCheckReportDetail(params) {
  return request({
    url: `pdms/pdms/version1/intel/detail/checkReport`,
    method: 'get',
    params: params
  });
}
export function getSecCircuitFaultReportDetail(params) {
  return request({
    url: `pdms/pdms/version1/intel/detail/SecCircuitFaultReport`,
    method: 'get',
    params: params
  });
}

export function getStatusMonitorReportDetail(params) {
  return request({
    url: `pdms/pdms/version1/intel/detail/StatusMonitorReport`,
    method: 'get',
    params: params
  });
}

export function getSamCompReportDetail(params) {
  return request({
    url: `pdms/pdms/version1/intel/detail/SamCompReport`,
    method: 'get',
    params: params
  });
}

export function getSettingReportDetail(params) {
  return request({
    url: `pdms/pdms/version1/intel/detail/SettingReport`,
    method: 'get',
    params: params
  });
}
export function getDevAlaLocationReportDetail(params) {
  return request({
    url: `pdms/pdms/version1/intel/detail/DevAlaLocationReport`,
    method: 'get',
    params: params
  });
}

export function getTripDiagReportDetail(params) {
  return request({
    url: `pdms/pdms/version1/intel/detail/TripDiagReport`,
    method: 'get',
    params: params
  });
}

export function getRelayBlkInfoReportDetail(params) {
  return request({
    url: `pdms/pdms/version1/intel/detail/RelayBlkInfoReport`,
    method: 'get',
    params: params
  });
}


export function getInspectionDetail(params) {
  return request({
    url: `pdms/pdms/version1/intel/detail/inspection`,
    method: 'get',
    params: params
  });
}

export function getFileRawContent(params) {
  return request({
    url: `pdms/pdms/version1/intel/fileRawContent`,
    method: 'get',
    params: params
  });
}

export function getWarning(params){
  return request({
    url: 'pdms/pdms/version1/statistics/warning' ,
    method: 'post',
    data:params
  })
}

export function getWarningStat(params){
  return request({
    url: 'pdms/pdms/version1/statistics/warning/stat' ,
    method: 'post',
    data:params
  })
}

export function getSubstation(){
  return request({
    url: 'pdms/pdms/version1/intel/division',
    methods: 'get'
  });
}

export function getDeviceTree(){
  return request({
    url: 'pdms/pdms/version1/basic/getDeviceTree',
    methods: 'get'
  });
}

export function getJudgeReportStatistics(){
  return request({
    url: 'pdms/pdms/version1/intel/getJudgeReportStatistics',
    methods: 'get'
  });
}

export function getSubstationList(){
  return request({
    url: 'pdms/pdms/version1/intel/getSubstationList',
    methods: 'get'
  });
}

export function getVisitRate(){
  return request({
    url: 'pdms/pdms/version1/intel/getVisitRate',
    methods: 'get'
  });
}
export function getNotVisitStationList(){
  return request({
    url: 'pdms/pdms/version1/intel/getNotVisitStationList',
    methods: 'get'
  });
}

export function getCommStatus(params) {
  return request({
    url: 'pdms/pdms/version1/intel/commStatus',
    method: 'post',
    data: params
  });
}

export function getIntelAlertLevel(params) {
  return request({
    url: 'pdms/pdms/version1/intel/getLevel',
    method: 'post',
    data: params
  })
}

// 国网标准xml
export function getSTATDetail_G(params) {
  return request({
    url: `pdms/pdms/version1/intel/detail/G/stat0${params.type - 7}`,
    method: 'get',
    params: params
  });
}
