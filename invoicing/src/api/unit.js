import request from '@/utils/request'

const api_name = '/unit'
export default {
  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'delete'
    })
  },
  save(unit) {
    return request({
      url: api_name,
      method: 'post',
      data: unit
    })
  },
  getById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  },
  updateById(unit) {
    return request({
      url: `${api_name}/${unit.id}`,
      method: 'put',
      data: unit
    })
  },
  getList() {
    return request({
      url: api_name,
      method: 'get'
    })
  }
}
