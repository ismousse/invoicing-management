import request from '@/utils/request'

const api_name = '/import'
export default {
  list(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  save(data) {
    return request({
      url: api_name,
      method: 'post',
      data: data
    })
  },
  // getById(id) {
  //   return request({
  //     url: `${api_name}/${id}`,
  //     method: 'get'
  //   })
  // },
  getList() {
    return request({
      url: api_name,
      method: 'get'
    })
  },
  getGood() {
    return request({
      url: api_name,
      method: 'get'
    })
  }
}
