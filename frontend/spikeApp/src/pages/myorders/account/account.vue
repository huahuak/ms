<template>
	<view class="order-main">
		<u-gap height="10rpx" bgColor="#f8f8f8"></u-gap>
		<uni-card  v-for="(item,index) in  orderlist" 
			:title="'银行账户号：'+item.bankAccountSN"
			extra="余额"
			>
			<view>
				<view class="order-body">
					<text>{{item.balance+'.00'}}</text>
					<text>￥</text>
				</view>
			</view>
		</uni-card>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orderlist:[
					 {
					       "balance":'',
					       "bankAccountSN": '',
					       
					     }
				]
			}
		},
		onLoad() {
			uni.request({
				// url:'http://tguio.club:8849/bank-account/'+uni.getStorageSync('user_id'),
				// url:'http://192.168.1.165:8848/bank-account/'+uni.getStorageSync('user_id'),
				url:'/api1/bank-account/'+uni.getStorageSync('user_id'),
				method:'POST',
				header:{Authorization:uni.getStorageSync('token')},
				success: (res) => {
					if(res.data.code==200) {
						this.orderlist = res.data.content
						this.myShowToast('success',res.data.message,1000)
					} else {
						this.myShowToast('error',res.data.message,1000)
					}
				},
				fail: (err) => {
					this.myShowToast('error',res.data.message,1000)
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
