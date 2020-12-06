import request from '@/utils/request'

const api_name = '/good'
export default {
  // getPageList(page, limit, searchObj) {
  //   return request({
  //     url: `${api_name}/${page}/${limit}`,
  //     method: 'get',
  //     params: searchObj
  //   })
  // },
  list(page, limit, searchObj) {
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
  save(good) {
    return request({
      url: api_name,
      method: 'post',
      data: good
    })
  },
  getById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  },
  updateById(good) {
    return request({
      url: `${api_name}/${good.goodid}/${good.unitname}`,
      method: 'put',
      data: good
    })
  },
  getList() {
    return request({
      url: api_name,
      method: 'get'
    })
  },
  getUnit() {
    return request({
      url: api_name,
      method: 'get'
    })
  }
}
