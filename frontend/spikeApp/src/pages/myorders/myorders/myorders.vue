<template>
	<view class="order-main">
		<u-gap height="10rpx" bgColor="#f8f8f8"></u-gap>
		<uni-card  v-for="(item,index) in  orderlist" 
			:title="'活动：'+item.activity_name"
			:sub-title="item.create_time"
			:extra="'活动序号:'+item.order_id"
			>
			<view>
				<text style="font-size: 20rpx;color: #939393;">订单状态：{{item.order_result}}</text>
				<!-- <text style="font-size: 10rpx;color: #939393;">活动序号</text> -->
				<view  class="order-body">
					<text>{{item.goods_price}}</text>
					<text>￥</text>
				</view>
			</view>
		</uni-card>
		<u-toast ref="uToast"></u-toast>
		<u-loading-page :loading="loadingFlag" loading-text='订单加载中...'></u-loading-page>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				loadingFlag:false,
				orderlist:[
					 {
					       "user_id": '',
					       "activity_id": '',
					       "goods_id": '',
					       "order_id": '',
					       "activity_name": " Test",
					       "bank_account": '',
					       "goods_price": '',
					       "order_result": "",
					       "create_time": ""
					     }
				]
			}
		},
		onLoad() {
			this.loadingFlag = true
			uni.request({
				// url:'http://tguio.club:8849/api/activity/orders',
				// url:'http://192.168.1.165:8848/api/activity/orders',
				url:'/api1/api/activity/orders',
				method:'GET',
				data:{
					username:uni.getStorageSync('username')
				},
				header:{Authorization:uni.getStorageSync('token')},
				success: (res) => {
					this.loadingFlag = false
					if(res.data.code==200) {
						this.orderlist = res.data.content
						this.myShowToast('success',res.data.message,1000)
					} else {
						this.myShowToast('error',res.data.message,1000)
					}
				},
				fail: (err) => {
					this.loadingFlag = false
					this.myShowToast('error',err,1000)
				},
				
			})
		},
		methods: {
			myShowToast(type,message,time) {
				this.$refs.uToast.show({
					type:type,
					title:message,
					message:message,
					duration:time
				})
			},
		},
	}
</script>

<style>
.order-main{
	height: 1500rpx;
	width: 100%;
	background-color: #ffffff;
}
.order-body{
	float: right;
	margin-bottom: 30rpx;
	color: #4d4d4d;
	font-size: large;
	font-family: STXihei, "华文细黑", "Microsoft YaHei", "微软雅黑";
	font-weight: bolder;
	background-color: #f1f1f1;
	padding: 8rpx 8rpx 8rpx 8rpx;
	border-radius: 10%;
}

</style>
