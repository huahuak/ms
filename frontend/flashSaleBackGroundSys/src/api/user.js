import request from '@/utils/axiosReq'
//用户登录用APP

export function loginReq(data) {

  return request({
    url: '/api/auth/signin',
    // url: '/dec/api/auth/signin',
    data:{"username":data.username,"password":data.password},
    method: 'post',
    bfLoading: false,
    isParams: false,
    isAlertErrorMsg: false
  })
}

export function getInfoReq() {
  return request({
    url: '/integration-front/user/getUserInfo',
    bfLoading: false,
    method: 'post',
    isAlertErrorMsg: false
  })
}

export function logoutReq() {
  return request({
    // url: '/dec/api/auth/logout',
    url: '/api/auth/logout',
    method: 'get'
  })
}



