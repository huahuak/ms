<template>
	<view class="main">
		<image class='bg' src="../../static/user/user-bg.png" ></image>
		<view class='user-info'>
			<view class="exitImageContainer">
				<u-button  @click="exit" class="exitImageButton">
					<image class="exitImage"  src="../../static/user/exit.png"></image>
				</u-button>
			</view>
			<view class="user-info-box">
				<view class="user-info-inbox">
					<image class='user-info-inbox-image' src="../../static/user/missing-face.png"></image>
					<view class='user-info-box-message-box' >
						<text class="user-info-box-username">{{username}}\n</text>
						<text class="user-info-box-userid">序号:{{user_id}}</text>
					</view>
				</view>
				<view class="user-info-box-card">
					<view class="user-info-box-card-text">
						秒杀测试系统
					</view>
					<view class="user-info-box-card-btn">
						<text class="user-info-box-card-btn-text">普通用户</text>
					</view>
				</view>
			</view>
			<view class="user-cell-container">
				<image  src='../../static/user/arc.png' class="user-cell-image"></image>
				<view class="user-cells">
					<view class="user-cells-center">
						<u-cell
							class='user-cells-section'
						    size="large"
						    title="个人中心"
						></u-cell>
						<u-grid :border="false" col="3" @click='toOrder'>
						    <u-grid-item
					             v-for="(listItem,listIndex) in gridlist"
			                    :key="listIndex"
					            >
								<u-icon
					               class="user-cells-center-icon"
									:name="listItem.name"
						            :size="30"
						        ></u-icon>
						        <text class="grid-text">{{listItem.title}}</text>
							</u-grid-item>
						</u-grid>
					</view>
					<view class="user-cells-info">
						<u-cell-group class="user-cells-info-cells-1">
							<u-cell
							    size="large"
							    title="个人资料"
							    isLink
							></u-cell>
							<u-cell
							    size="large"
							    title="修改密码"
							    isLink
							></u-cell>
						</u-cell-group>
						<u-cell-group class="user-cells-info-cells-2">
							<u-cell
							    size="large"
							    title="关于我们"
							    isLink
							></u-cell>
							<u-cell
							    size="large"
							    title="站点帮助"
							    isLink
							></u-cell>
							<u-cell
							    size="large"
							    title="意见反馈"
							    isLink
							></u-cell>
						</u-cell-group>
					</view>
				</view>
			</view>
		</view>
		<u-toast ref="loadingToast"></u-toast>
	</view>
</template>

<script>
	export default {
		onShow() {
			this.username = uni.getStorageSync('username')
			this.user_id = uni.getStorageSync('user_id')
		},
		data() {
			return {
				title: 'Hello',
				username:'',
				user_id:'',
				gridlist:[
					{
						name:'order',
						title:'我的订单'
					},
					{
						name:'account',
						title:'账户'
					},
					{
						name:'chat',
						title:'客服'
					}
				]
			}
		},
		onLoad() {

		},
		methods: {
			exit(){
				console.log("exit")
				uni.request({
					// url:'http://tguio.club:8849/api/auth/logout',
					// url:'192.168.1.165:8848/api/auth/logout',
					url:'/api1/api/auth/logout',
					header:{'Authorization':uni.getStorageSync('token')},
					success: (res) => {
						if(res.data.code==200) {
							this.myShowToast("success","登出成功",1000)
							setTimeout(()=>{
								uni.reLaunch({
									url:'../public/login'
								})
							},2000)
						}
					}
				})
			},
			toOrder(name){
				console.log(name)
				if(name=='0') {
					uni.navigateTo({
						url:'../myorders/myorders/myorders'
					})
				} else if(name=='1') {
					uni.navigateTo({
						url:'../myorders/account/account'
					})
				}
			},
			myShowToast(type,message,time) {
				this.$refs.loadingToast.show({
					type:type,
					title:message,
					message:message,
					duration:time
				})
			},
		}
	}
</script>

<style lang="scss">
	.main{
		// background-color: #61d09c;
		background-color:#b30000;
		height: 1000rpx;
		.bg{
			position: absolute;
			left: 0;
			top: 0;
			width: 100vw;
			height: 60vw;
			opacity: 1;
		}
		.user-info{
			.exitImageContainer{
				.exitImageButton{
					float: right;
					margin-right: 30rpx;
					margin-top: 100rpx;
					z-index: 1;
					height: 70rpx;
					width: 90rpx;
					background-color: #da6e64;
					border-color: #da6e64;
					border:hidden;
					.exitImage{
						width: 50rpx;
						height: 50rpx;
					}	
				}
			}
			.user-info-box{
				// background-color: #2979FF;
				// height: 200rpx;
				display: block;
				align-items: center;
				position: relative;
				// z-index: 1;
				justify-content: space-between;
				margin-left: 40rpx;
				margin-right: 40rpx;
				padding-top: 120rpx;
				
				.user-info-inbox{
					display: flex;
					align-items: center;
					
					.user-info-inbox-image{
						height: 120rpx;
						width: 120rpx;
						border: 2px solid #ffffff;
						// border-color: #0c0c0c;
						// border-width: 100rpx;
						border-radius: 100%;
					}
					.user-info-box-message-box{
						display: block;
						.user-info-box-username{
							font-size: 40rpx;
							color: #c7c7c7;
							margin-left: 20rpx;
						}
						.user-info-box-userid{
							font-size: 26rpx;
							color: #c7c7c7;
							margin-left: 20rpx;
							opacity: 0.8;
						}
					}
				}
				.user-info-box-card{
					background: url(../../static/user/vip-card.png);
					background-size: 100% 100%;
					padding: 30rpx 30rpx;
					height: 500rpx;
					border-radius: 40rpx 40rpx 0 0;
					margin-top: 45rpx;
					display: flex;
					// justify-content:flex-end;
					.user-info-box-card-text{
						font-size: 30rpx;
						color: #FFDF00;
						font-family: "FangSong","Microsoft YaHei","黑体","宋体",sans-serif;
					}
					.user-info-box-card-btn{
						font-size: 20rpx;
						position: absolute;
						right: 50rpx;
						.user-info-box-card-btn-text{
							background: linear-gradient(to left,#f9e6af,#FFDF00);
							padding: 5rpx 20rpx 5rpx 20rpx;
							border-radius: 20px;
						}
					}
					
				}
			
			
			}
			.user-cell-container{
				margin-top: -450rpx;
				padding: 0 30upx 20upx;
				position: relative;
				// z-index: 1;
				background-color: #f3f3f3;
				height: 1200rpx;
				.user-cell-image{	
					position: absolute;
					left: 0;
					top: -34upx;
					width: 100%;
					height: 36upx;
				}
				.user-cells{
					display: block;
					background-color: #f3f3f3;
					// height: 900px;
					.user-cells-center{
						background-color: #FFFFFF;
						.user-cells-section{
							// margin-top: 50rpx;
						}
						.grid-text{
							margin-bottom: 30rpx;
							font-size: 30rpx;
						}
						.user-cells-center-icon{
							padding-top: 30rpx;
							padding-bottom: 10rpx;
						}
					}
					.user-cells-info{
						background-color: #f3f3f3;
						margin-top: 35rpx;
						.user-cells-info-cells-1{
							background-color: #FFFFFF;
							// padding-top: 20rpx;
						}
						.user-cells-info-cells-2{
							margin-top: 50rpx;
							background-color: #FFFFFF;
							// padding-top: 20rpx;
						}
					}
					
				}
				
			}
		
		}
	
		
	}
	
</style>
