<template>
	<view>
		<view>
			<u-loading-page :loading="loadingFlag"></u-loading-page>
		 <u-sticky bgColor="#fff">
		    <u-tabs 
			lineWidth="30"
			lineColor="#f56c6c"
			@click="click"
			:activeStyle="{
				color: '#303133',
				fontWeight: 'bold',
				transform: 'scale(1.8)'
						}"
			:inactiveStyle="{
				color: '#606266',
				transform: 'scale(1.2)'
						}"
			 itemStyle="padding-left: 20px; padding-right: 20px; height: 60px;margin-top:20rpx"
			:list="actNav"></u-tabs>
		  </u-sticky>
		  </view>
		  <view >
		        <u-notice-bar :text="notice" mode="closable"></u-notice-bar>
			</view>
			<view v-if='currentNav==0'>
				<u-cell-group>
						<u-cell 
						label='hello'
						size='large'
						v-for="(item,index) in loanlist" 
						icon="setting-fill" 
						:title="item.activity_name"
						@click="loanclick(item)"
						>
							<template slot="label">
								<view><text>活动编号：{{item.activity_id}}</text></view>
							</template>
						</u-cell>
					</u-cell-group>
			</view>
			<view v-if='currentNav==1'>
				<u-cell-group>
						<u-cell
						label='hello'
						size='large'
						v-for="(item,index) in depositlist" 
						icon="setting-fill" 
						:title="item.activity_name"
						@click="depositclick(item)"
						>
							<template slot="label">
								<view><text>活动编号：{{item.activity_id}}</text></view>
							</template>
						</u-cell>
					</u-cell-group>
			</view>
			
			<u-toast ref="loadingToast"></u-toast>
			<u-toast ref="failToast"></u-toast>
		  </view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				loadingFlag:false,
				actNav:[{name:'类型1'},{name:'类型2'}],
				currentNav:0,
				navStr:['loan','deposit'],	//用来插入URL
				notice:"亲爱的用户:第一次点击活动介绍将进行初筛以提醒您是否有资格参与该活动",
				popShow:false,
				loanlist:[
					// {
					// 	"activity_id":'',
					// 	"activity_name":"",
					// },
				],
				depositlist:[
					// {
					// 	"activity_id":'',
					// 	"activity_name":"",
					// },
				],
			};
		},
		onPullDownRefresh(){
			const type = this.navStr[this.currentNav]
			uni.request({
				// url:'http://tguio.club:8849/api/' + type + '/',
				// url:'http://192.168.1.165:8848/dec/api/' + type + '/',
				url:'/api1/api/' + type + '/',	
				method:'GET',
				header:{"Authorization":uni.getStorageSync("token")},
				data:{
					page_limit:10,
					page_num:1
				},
				success: (res) => {
					if (res.data.code==200) {
						if(type=='loan')
							this.loanlist = res.data.content.result
						else if (type=='deposit')
							this.depositlist = res.data.content.result
					} else if (res.data.code==401) {
						this.$refs.loadingToast.show({
							type:'defalut',
							title:'登录过期',
							message:'登陆过期',
							duration:1000,
							})
					} else {
						this.$refs.loadingToast.show({
							type:'defalut',
							title:'错误',
							message:res.data.message,
							duration:1000,
							})
					}
					setTimeout(function () {
						uni.stopPullDownRefresh();
							}, 600);
				},
				fail: (err) => {
					console.log(err)
					setTimeout(function () {
						uni.stopPullDownRefresh();
							}, 1000);
				}
			})
		},
		onLoad() {
			this.loadingFlag = true
			this.getLoanData()
			this.getDepositData()
		},
		methods:{
			click(item){
				this.currentNav = item.index
				console.log(this.currentNav)
				// console.log(this.currentNav)
			},
			
			getLoanData(){
				console.log("hello")
				uni.request({
					// url:'http://tguio.club:8849/api/loan/',
					// url:'http://192.168.1.165:8848/api/loan/',
					url:'/api1/api/loan/',
					method:'GET',
					header:{"Authorization":uni.getStorageSync("token")},
					data:{
						page_limit:10,
						page_num:1
					},
					success: (res) => {
						if(res.data.code==200) {
							this.loanlist = res.data.content.result
						} else if (res.data.code==401) {
							this.$refs.loadingToast.show({
								type:'defalut',
								title:'您未登录',
								message:'您未登录(即将跳转到登陆页面)',
								duration:1000,
								})
							setTimeout(()=>{
								uni.reLaunch({
									url:'../public/login'
								})
							},1500)
						} else {
							this.$refs.loadingToast.show({
								type:'defalut',
								title:'错误',
								message:res.data.message,
								duration:1000,
								})
						}
						this.loadingFlag = false
					},
					fail: (err) => {
						console.log(err)
						this.loadingFlag = false
					}
				})
			},
			getDepositData(){
				uni.request({
					// url:'http://tguio.club:8849/api/deposit/',
					// url:'http://192.168.1.165:8848/api/deposit/',
					url:'/api1/api/deposit/',
					method:'GET',
					header:{"Authorization":uni.getStorageSync("token")},
					data:{
						page_limit:10,
						page_num:1
					},
					success: (res) => {
						if(res.data.code==200) {
							this.depositlist = res.data.content.result
						} else if (res.data.code==401) {
							this.$refs.loadingToast.show({
								type:'defalut',
								title:'您未登录',
								message:'您未登录(即将跳转到登陆页面)',
								duration:1000,
								})
							setTimeout(()=>{
								uni.reLaunch({
									url:'../public/login'
								})
							},1500)
						} else {
							this.$refs.loadingToast.show({
								type:'defalut',
								title:'错误',
								message:res.data.message,
								duration:1000,
								})
						}
					},
					fail: (err) => {
						console.log(err)
					}
				})
			},
			
			//点击贷款列表
			loanclick(item){
				// console.log(item)
				//初筛，初筛后根据结果跳转
				this.$refs.loadingToast.show({
					type:'loading',
					title:'正在加载',
					message:'正在加载',
					duration:1000,
					})
				uni.request({
					// url:'http://tguio.club:8849/api/loan/check/',
					// url:'http://192.168.1.165:8848/api/loan/check/',
					url:'/api1/api/loan/check/',
					method:'GET',
					header:{"Authorization":uni.getStorageSync("token")},
					data:{
						user_id:uni.getStorageSync('user_id') || '',
						activity_id:item.activity_id
					},
					success: (res) => {
						if(res.data.code==200){
							if (res.data.content==false) {
								setTimeout(()=>{
									this.$refs.failToast.show({
										type:'error',
										message:res.data.message
									})
								},1000)
							} else if (res.data.content==true){
								uni.navigateTo({
									url:'../formPage/formPage?type=loan&activity_id='+item.activity_id,
								})
							}
						} else if(res.data.code==500) {
							this.$refs.failToast.show({
								type:'default',
								message:'请求异常',
								icon:'level',
							})
						} else if(res.data.code==401){
							this.$refs.failToast.show({
								type:'default',
								message:'未登录，请重新登陆',
								icon:'level',
							})
							setTimeout(()=>{
								uni.navigateTo({
									url:'../public/login',
								})
							},1000)
						} else {
							this.$refs.loadingToast.show({
								type:'error',
								title:'错误',
								message:res.data.message,
								duration:1200,
								})
						}

					},
					fail: (err) => {
						this.$refs.failToast.show({
							type:'default',
							message:'请求失败',
							icon:'level',
						})
					}
				})
			},
			depositclick(item){
				console.log(item)
				//初筛，初筛后根据结果跳转
				// console.log(item)
				//初筛，初筛后根据结果跳转
				this.$refs.loadingToast.show({
					type:'loading',
					title:'正在加载',
					message:'正在加载',
					duration:2000,
					})
				uni.request({
					// url:'http://tguio.club:8849/api/deposit/check',
					// url:'http://192.168.1.165:8848/api/deposit/check',
					url:'/api1/api/deposit/check',
					method:'GET',
					data:{
						user_id:uni.getStorageSync('user_id') || '',
						activity_id:item.activity_id
					},
					header:{"Authorization":uni.getStorageSync("token")},
					success: (res) => {
						if(res.data.code==200){
							if (res.data.content==false) {
								setTimeout(()=>{
									this.$refs.failToast.show({
										type:'error',
										message:res.data.message
									})
								},1000)
							} else if (res.data.content==true){
								uni.navigateTo({
									url:'../formPage/formPage?type=deposit&activity_id='+item.activity_id,
								})
							}
						} else if(res.data.code==500) {
							this.$refs.failToast.show({
								type:'default',
								message:'请求异常',
								icon:'level',
							})
						} else if(res.data.code==401){
							this.$refs.failToast.show({
								type:'default',
								message:'未登录，请重新登陆',
								icon:'level',
							})
							setTimeout(()=>{
								uni.navigateTo({
									url:'../public/login',
								})
							},1000)
						} else {
							this.$refs.loadingToast.show({
								type:'error',
								title:'错误',
								message:res.data.message,
								duration:1200,
								})
						}
					},
					fail: (err) => {
						this.$refs.failToast.show({
							type:'default',
							message:'请求失败',
							icon:'level',
						})
					}
				})
			},
	
		}
		
	}
</script>

<style lang="scss">

</style>
