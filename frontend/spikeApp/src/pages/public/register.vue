<template>
	<view>
			<view class="container">
				<u-toast ref="uToast"></u-toast>
				<!--顶部返回按钮-->
				<text class="back-btn iconfont iconzuo" @tap="navBack"></text>
				<view class="right-top-sign"></view>
				<view class="wrapper">
					<view class="left-top-sign">REGISTER</view>
					<view class="welcome"> 注册账号 </view>
					
					<form @submit="formSubmit">
					<view class="input-content">
						<view class="input-item">
							<text class="tit">身份证</text>
							<input
								name="user_IDnumber"
								type="idcard"
								placeholder="请输入身份证号码"
								maxlength="18"
							/>
						</view>
						<view class="input-item">
							<text class="tit">姓名</text>
							<input
								required
								auto-blur
								name="user_name"
								type="text"
								placeholder="请输入姓名"
								maxlength="10"
							/>
						</view>
						<view class="input-item">
							<text class="tit">性别</text>
							<radio-group name="user_gender">
								<label>
									<radio value="Male" color="#61d09c" checked="true" style="transform:scale(0.8);;"/>
									<text class="input" style="margin-right: 50upx;">男</text>
								</label>
								<label>
									<radio  value="Female" color="#61d09c" style="transform:scale(0.8);" />
									<text class="input">女</text>
								</label>
							</radio-group>
						</view>
						<view class="input-item">
							<text class="tit">国籍</text>
							<radio-group name="user_nation" >
								<label>
									<radio value="中国" color="#61d09c" checked="true" style="transform:scale(0.8);;"/>
									<text class="input" style="margin-right:25upx;">中国</text>
								</label>
								<label>
									<radio  value="其他" color="#61d09c" style="transform:scale(0.8);" />
									<text class="input">其他</text>
								</label>
							</radio-group>
						</view>
						<view class="input-item">
							<text class="tit">年龄</text>
							<input
								required
								auto-blur
								name="user_age"
								type="number"
								placeholder="请输入年龄"
								maxlength="3"
								max="999"
								min="1"
							/>
						</view>
						<!-- <view class="input-item input-item-sms-code">
							<view class="input-wrapper">
								<view class="oa-input-wrapper">
									<view class="tit">验证码</view>
									<input
										type="number"
										placeholder="请输入验证码"
										maxlength="4"
										data-key="mobile"
									/>
								</view>
								<button
									class="sms-code-btn"
								>
									<text >获取验证码</text>
								</button>
							</view>
						</view> -->
						<view class="input-item">
							<text class="tit">账户名</text>
							<input
								required
								auto-blur
								name="username"
								type="text"
								placeholder="请输入个性账户名"
								maxlength="10"
							/>
						</view>
						<view class="input-item">
							<text class="tit">密码</text>
							<input
								required
								auto-blur
								name="password"
								type="password"
								placeholder="请输入密码"
								maxlength="18"
							/>
						</view>
						<view class="input-item">
							<text class="tit">确认密码</text>
							<input
								required
								auto-blur
								type="password"
								placeholder="请输入确认密码"
							/>
						</view>
						
						<view class="input-item">
							<text class="tit">就业状态</text>
							<radio-group name="user_employment">
								<label>
									<radio value="Employed" color="#61d09c" checked="true" style="transform:scale(0.8);;"/>
									<text class="input" style="margin-right: 30upx;">在职</text>
								</label>
								<label>
									<radio  value="Unemployed" color="#61d09c" style="transform:scale(0.8);" />
									<text class="input" style="margin-right:30upx;">失业</text>
								</label>
								<label>
									<radio  value="Retired" color="#61d09c" style="transform:scale(0.8);" />
									<text class="input" style="margin-right:30upx;">退休</text>
								</label>
								<label>
									<radio  value="Other" color="#61d09c" style="transform:scale(0.8);" />
									<text class="input" style="margin-right:30upx;">其他</text>
								</label>
							</radio-group>
						</view>
						
						<view class="input-item">
							<text class="tit">是否被列于黑名单</text>
							<radio-group name="user_dishonest">
								<label>
									<radio value="true" color="#61d09c" checked="true" style="transform:scale(0.8);;"/>
									<text class="input"style="margin-right: 50upx;">是</text>
								</label>
								<label>
									<radio  value="false" color="#61d09c" style="transform:scale(0.8);" />
									<text class="input">否</text>
								</label>
							</radio-group>
						</view>
						<view class="input-item">
							<text class="tit">近3年贷款逾期次数</text>
							<input
								required
								auto-blur
								name="user_overdual"
								type="number"
								placeholder="请输入次数"
								maxlength="3"
								min="0"
							/>
							</view>
						</view>
						<button
							class="confirm-btn"
							style="width: 500upx; margin-top: 40rpx;"
							form-type="submit">
							注册
						</button>
					</form>
				</view>
				<view class="register-section" >
					已有账号？
					<text @tap="navToLogin()">马上登录</text>
					<!-- 或者
					<text @tap="toHome">返回主页</text> -->
				</view>
				
			</view>
		
	</view>
</template>

<script>
export default {
	data() {
		return {
			formData:{
				"password": "",
				"user_IDnumber": "",
				"user_age": 0,
				"user_dishonest": true,
				"user_employment": "",
				"user_gender": "male",
				"user_name": "",
				"user_nation": "",
				"user_overdual": 0,
				"username": ""
			},
			
			
		};
	},
	
	onLoad(options) {
		
	},
	methods: {
		formSubmit:function(e){
			console.log(e.detail.value)
			const data = e.detail.value
			uni.request({
				// url:'http://tguio.club:8849/api/user/',
				// url:'http://192.168.1.165:8848/api/user/',
				url:'/api1/api/user/',
				method:'POST',
				data:data,
				success: (res) => {
					if(res.data.code==200) {
						this.myShowToast('success',res.data.message,2000)
						setTimeout(()=>{
							uni.reLaunch({
								url:'../login/login'
							})
						},1000)
					} else {
						this.myShowToast('warning',res.data.message,3000)
					}
					console.log(res)
					
				},
				fail: (err) => {
					console.log(err)
				}
			})
			
		},
		navToLogin(){
			uni.redirectTo({
				url:'./login'
			})
		},
		navtoMain(){
			uni.redirectTo({
				url:'../MainPage/MainPage'
			})
		},
		navBack() {
			this.$mRouter.back();
		},
		navTo(route) {
			this.$mRouter.push({ route });
		},
		toHome() {
			this.$mRouter.reLaunch({ route: '/pages/index/index' });
		},
		returnMale() {
			// this.formData = 
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
};
</script>

<style lang="scss">
page {
	background: $color-white;
}
.container {
	padding-top: 60px;
	position: relative;
	width: 100vw;
	overflow: hidden;
	.wrapper {
		position: relative;
		width: 100vw;
		z-index: 90;
		background: #fff;
		padding-bottom: 40upx;

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

			.input {
				height: 60upx;
				font-size: $font-base + 2upx;
				color: $font-color-dark;
				width: 100%;
			}
		}

		.input-item-sms-code {
			position: relative;

			.sms-code-btn {
				position: absolute;
				right: 20upx;
				color: #111;
				bottom: 20upx;
				font-size: 28upx;
			}

			.sms-code-resend {
				color: #999;
			}
		}

		.forget-section {
			font-size: $font-sm + 2upx;
			color: $font-color-spec;
			text-align: center;
			margin-top: 40upx;
		}
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
		top: 80upx;
		right: -30upx;
		z-index: 95;

		&:before,
		&:after {
			display: block;
			content: '';
			width: 400upx;
			height: 80upx;
			background: #b4f3e2;
		}

		&:before {
			transform: rotate(50deg);
			border-radius: 0 50px 0 0;
		}

		&:after {
			position: absolute;
			right: -198upx;
			top: 0;
			transform: rotate(-50deg);
			border-radius: 50px 0 0 0;
			/* background: pink; */
		}
	}

	.register-section {
		margin: 50upx 0 50upx 0;
		width: 100%;
		font-size: $font-sm + 2upx;
		color: $font-color-base;
		text-align: center;

		text {
			color: $font-color-spec;
			margin-left: 10upx;
			margin-right: 10upx;
		}
	}
}
.password-type-2 {
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
			height: 114vw;
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
					.sms-code-btn, sms-code-resend {
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
radio-group{
	margin-top: 5upx;
	margin-bottom: 5upx;
}
</style>
