import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

const api_name = '/user'
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
  save(user) {
    return request({
      url: api_name,
      method: 'post',
      data: user
    })
  },
  getById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  },
  updateById(user) {
    return request({
      url: `${api_name}/${user.id}`,
      method: 'put',
      data: user
    })
  },
  getList() {
    return request({
      url: api_name,
      method: 'get'
    })
  },
  updatePwd(password) {
    return request({
      url: `/user/updatePwd/${password}`,
      method: 'put'
    })
  }
}
