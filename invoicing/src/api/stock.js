import request from '@/utils/request'

const api_name = '/stock'
export default {
  list(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  getById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  },
  getList() {
    return request({
      url: api_name,
      method: 'get'
    })
  }
}
