import request from '@/utils/request';

/**
 * 查询定值详细信息
 * @param query
 * @returns {AxiosPromise}
 */
export function getSettingFileData(params) {
  return request({
    url: 'pdms/pdms/version1/settingFile/getSettingFileData/',
    method: 'post',
    data: params
  })
}
