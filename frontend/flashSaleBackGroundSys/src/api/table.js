import request from '@/utils/axiosReq'
//抓取活动列表CRUD用

export function getList(params) {
	return request({
		url: '/vue3-admin-plus/table/list',
		method: 'get',
		params
  })
}


// 贷款相关
export function getLoanList(params) {
	return request({
		url:'/api/loan/',
		method:'get',
		data:params,
		bfLoading: true,
		isParams:true,
	})
}

export function submitNewLoanAct(data) {
	return request({
		url:'/api/loan/',
		method:'post',
		data:data,
		 isAlertErrorMsg: false
	})
}

export function deleteLoanAct(data) {
	return request({
		url:'/api/loan/'+data,
		method:'delete',
		bfLoading:true,
	})
}

export function changeLoanAct(data,id) {
	delete data.activity_id
	return request({
		url:'/api/loan/'+id,
		method:'put',
		data:data,
		bfLoading:true,
		isAlertErrorMsg:false
	})
}


//存款相关
export function getDepositList(params) {
	return request({
		url:'/api/deposit/',
		data:params,
		method:'get',
		bfLoading: true,
		isParams: true,
		isAlertErrorMsg:false

	})
}

export function submitNewDepositAct(data) {
	return request({
		url:'/api/deposit/',
		method:'post',
		data:data,
		 isAlertErrorMsg: false
	})
}

export function deleteDepositAct(data) {
	return request({
		url:'/api/deposit/'+data,
		method:'delete',
		bfLoading:true,
	})
}

export function changeDepositAct(data,id) {
	delete data.activity_id
	return request({
		url:'/api/deposit/'+id,
		method:'put',
		data:data,
		bfLoading:true,
		isAlertErrorMsg:false
	})
}
