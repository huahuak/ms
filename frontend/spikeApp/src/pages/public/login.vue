<template>
	<view class="login">
		<u-toast ref="uToast"></u-toast>
		<view class="container">
			<!--顶部返回按钮-->
			<text class="back-btn iconfont iconzuo" @tap="navBack"></text>
			<!--插画-->
			<view class="right-top-sign"></view>
			<view class="wrapper">
				<view class="left-top-sign">通用商贸</view>
				<view class="welcome">
					欢迎登录
				</view>
				<view class="input-content">
					
					<form @submit="formSubmit">
					<view class="input-item">
						<text class="tit">账号</text>
						<input name='username' type="text"  placeholder="请输入账号" maxlength="11"  />
					</view>
					<view class="input-item" >
						<text class="tit">密码</text>
						<input name='password' type="password" placeholder="请输入密码" maxlength="20" />
					</view>
					<button class="confirm-btn" form-type="submit" style="background-color: #9a0101; border-radius: 30rpx;">
						<text style="color: #eeeeee;">登录</text>
					</button>
					</form>
				</view>

			</view>
			<view class="register-section">
				没有账户？<text @tap="navToRegis">注册</text>
				<!-- 或者
				<text @tap="toHome">返回主页</text> -->
			</view>
		</view>
	</view>
	</view>
</template>
<script>
	import MD5 from '../../js_sdk/md5/MD5.js'
	export default {
		components: {
			MD5
		},
		data() {
			return {
				smsCodeBtnDisabled: false,
				formData:{
					user_name:'',
					user_password:'',
				}
			};
		},
		methods: {
			//提交表单请求
			formSubmit: function(e){
				// console.log(MD5.hex_md5_16("11"))
				// console.log(e.detail.value)
				const data = e.detail.value
				uni.request({
					// url:'http://192.168.1.165:8848/api/auth/signin',
					// url:'http://tguio.club:8849/api/auth/signin',
					url:'/api1/api/auth/signin',
					method:'POST',
					data:{"username":data.username,"password":data.password},
					success: (res) => {
						// console.log("Now the token is :",res.data.content.token)
						if(res.data.code==200) {
							let data = res.data.content
							console.log(data)
							this.myShowToast('success','登陆成功',1000)
								uni.setStorageSync('username',data.username)
								uni.setStorageSync('user_id',data.user_id)
								uni.setStorage({
									key:'bankAccountID',
									data:data.account_sn
								})
								uni.setStorage({
									key:'token',
									data:data.token,
									success:function() {
										setTimeout(function(){
											uni.reLaunch({
												url:'../spikePage/spikePage'
											})
										},1000)
									},
									fail:function(){
										this.myShowToast('error',"登录失败",1000)
									}
								})
								
						} else if (res.data.code==403) {
							this.myShowToast("error",res.data.message,2000)
						}
					},
					fail: (err) => {
						console.log(err)
						this.myShowToast("error",'登陆失败',2000)
					}
				})
			},
			navToMain(){
				uni.reLaunch({
					url:'../spikePage/spikePage'
				})
			},
			navToRegis(){
				uni.redirectTo({
					url:'./register'
				})
			},
			// 返回上一页
			navBack() {
				this.$mRouter.back();
			},
			// 统一跳转路由
			navTo(route) {
				this.$mRouter.push({ route });
			},
			// 返回主页
			toHome() {
				this.$mRouter.reLaunch({ route: '/pages/index/index' });
			},
			myShowToast(type,message,time) {
				this.$refs.uToast.show({
					type:type,
					title:message,
					message:message,
					duration:time
				})
			}
		}
	}
</script>
<style lang="scss">
	page {
		background: $color-white;
	}

	.container {
		padding-top: 115px;
		position: relative;
		width: 100vw;
		overflow: hidden;
		background: #fff;

		.wrapper {
			position: relative;
			z-index: 90;
			background: #fff;
			padding-bottom: 40upx;
		}

		.back-btn {
			position: absolute;
			left: 40upx;
			z-index: 9999;
			padding-top: var(--status-bar-height);
			top: 40upx;
			font-size: 40upx;
			color: $font-color-dark;
		}

		.left-top-sign {
			font-size: 120upx;
			color: $page-color-base;
			position: relative;
			left: -16upx;
		}

		.right-top-sign {
			position: absolute;
			top: 120upx;
			right: -250upx;
			z-index: 95;

			&:before,
			&:after {
				display: block;
				content: '';
				width: 800upx;
				height: 80upx;
				background: #b30303;
			}

			&:before {
				transform: rotate(50deg);
				border-radius: 0 50px 0 0;
			}

			&:after {
				position: absolute;
				right: -198upx;
				top: 0;
				transform: rotate(50deg);
				border-radius: 50px 0 0 0;
				/* background: pink; */
			}
		
		}

		.left-bottom-sign {
			position: absolute;
			left: -270upx;
			bottom: -320upx;
			border: 100upx solid #d0d1fd;
			border-radius: 50%;
			padding: 180upx;
		}

		.welcome {
			position: relative;
			left: 50upx;
			top: -90upx;
			font-size: 46upx;
			color: #555;
			text-shadow: 1px 0px 1px rgba(0, 0, 0, 0.3);
		}

		.input-content {
			padding: 0 60upx;
		}

		.input-item {
			display: flex;
			flex-direction: column;
			align-items: flex-start;
			justify-content: center;
			padding: 0 30upx;
			background: $page-color-light;
			height: 120upx;
			border-radius: 4px;
			margin-bottom: 50upx;

			&:last-child {
				margin-bottom: 0;
			}

			.tit {
				height: 50upx;
				line-height: 56upx;
				font-size: $font-sm + 2upx;
				color: $font-color-base;
			}

			input {
				height: 60upx;
				font-size: $font-base + 2upx;
				color: $font-color-dark;
				width: 100%;
			}
		}

		.input-item-sms-code {
			position: relative;
			width: 100%;

			.sms-code-btn {
				position: absolute;
				color: #111;
				right: 20upx;
				bottom: 20upx;
				font-size: 28upx;
			}

			.sms-code-resend {
				color: #999;
			}

			.sms-code-btn:after {
				border: none;
				background-color: transparent;
			}
		}

		.forget-section {
			font-size: $font-sm + 2upx;
			color: $font-color-spec;
			text-align: center;
			margin-top: 40upx;
		}

		.register-section {
			margin: 30upx 0 50upx 0;
			width: 100%;
			font-size: $font-sm + 2upx;
			color: $font-color-base;
			text-align: center;

			text {
				color: $font-color-spec;
				margin-left: 10upx;
			}

			text:first-child {
				margin-right: 10upx;
			}
		}

		.btn-group {
			display: flex;
			margin-bottom: 20upx;
		}
	}

	.login-type-2 {
		width: 100%;
		position: relative;

		.back-btn {
			position: absolute;
			left: 40upx;
			z-index: 9999;
			padding-top: var(--status-bar-height);
			top: 40upx;
			font-size: 48upx;
			color: $color-white;
		}

		.login-top {
			height: 460upx;
			position: relative;

			.desc {
				position: absolute;
				top: 200upx;
				left: 40upx;
				font-size: 48upx;

				.title {
					font-size: 48upx;
				}
			}

			.login-pic {
				position: absolute;
				width: 220upx;
				height: 270upx;
				right: 30upx;
				top: 100upx;
			}
		}

		.login-type-content {
			position: relative;
			top: -72upx;

			.login-bg {
				width: 94vw;
				height: 94vw;
				margin: 0 3vw;
			}

			.main {
				width: 94vw;
				position: absolute;
				top: 0;
				left: 3vw;

				.nav-bar {
					display: flex;
					height: 100upx;
					justify-content: center;
					align-items: center;
					position: relative;
					z-index: 10;

					.nav-bar-item {
						flex: 1;
						display: flex;
						height: 100%;
						line-height: 96upx;
						font-size: $font-lg;
						display: flex;
						margin: 0 120upx;
						justify-content: center;
					}

					.nav-bar-item-active {
						border-bottom: 5upx solid;
					}
				}

				.login-type-form {
					width: 80%;
					margin: 50upx auto;

					.input-item {
						position: relative;
						height: 90upx;
						line-height: 90upx;
						margin-bottom: $spacing-lg;

						.iconfont {
							font-size: 50upx;
							position: absolute;
							left: 0;
						}

						.login-type-input {
							height: 90upx;
							padding-left: 80upx;
							border-bottom: 1upx solid rgba(0, 0, 0, .1);
						}

						.sms-code-btn,
						sms-code-resend {
							width: 240upx;
							font-size: $font-base - 2upx;
						}
					}
				}

				.login-type-tips {
					padding: 0 50upx;
					display: flex;
					justify-content: space-between;
				}

				.confirm-btn {
					height: 80upx;
					line-height: 80upx;
					background-color: #b30303; 
				}
			}
		}

		.login-type-bottom {
			width: 100%;
			padding-bottom: 30upx;
			text-align: center;
			font-size: $font-lg;
		}
	}
</style>
