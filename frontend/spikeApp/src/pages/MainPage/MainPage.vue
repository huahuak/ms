<template>
	<view class='main-body'>
		<text class="main-text" id='up' >
			Welcome
		</text>
		<text class="main-text" id="center" >
			欢迎使用通用商贸秒杀活动APP
		</text >
		<text class="main-text" id="down">
			点击导航栏"秒杀活动"\n即可查看当前已有秒杀活动
		</text>
	</view>
</template>

<script>
	import mytabBar from "@/components/mytabBar.vue"
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		},
	}
</script>

<style lang="scss">
#welcome{
	font-size: 30;
}
.main-body{
	background-color:#dfdfdf;
	margin-top: 40%;
	height: 100%;
	border-style: solid;
	border-width: 5rpx;
	box-shadow: 0px 5px 15px #c2c2c2;
	border-radius: 5%;
	margin-left: 15rpx;
	margin-right: 15rpx;
	
}
.main-text{
	display: block;
	flex: 1;
	margin-top: 10rpx;
	text-align: center;
	padding: 10rpx 10rpx 10rpx 10rpx;
	height: 100rpx;
}
#up{
	font-size: 100rpx;
	margin-bottom: 10%;
	
}
#center{
	font-size: 45rpx;
	margin-left: 20rpx;
	margin-right: 20rpx;
}
#down{
	font-size: 35rpx;
}

</style>
