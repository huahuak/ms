<template>
	<view class="uni-goods-nav">
		<!-- 底部占位 -->
		<view class="uni-tab__seat" />
		<view class="uni-tab__cart-box flex">
			<view class="flex uni-tab__cart-sub-left" style="display: flex;justify-content: center; align-items: center;">
				<text v-if="timeFlag==1" style="font-size: 30rpx;">距离开始</text>
				<text v-else-if="timeFlag==0" style="font-size: 30rpx;">距离结束</text>
			</view>
			<view class="flex uni-tab__cart-sub-left">
				<uni-countdown v-if="timeFlag==1" 
					:day="timeDiffList.startDiff.day" 
					:hour="timeDiffList.startDiff.hour" 
					:minute="timeDiffList.startDiff.minute" 
					:second="timeDiffList.startDiff.second" 
					color="#ffffff" 
					startc
					@timeup='ableThebutton'
					background-color="#760d0d" />
				<uni-countdown v-else-if="timeFlag==0" 
					:day="timeDiffList.endDiff.day"
					:hour="timeDiffList.endDiff.hour" 
					:minute="timeDiffList.endDiff.minute" 
					:second="timeDiffList.endDiff.second" 
					color="#FFFFFF" 
					start
					@timeup='ableThebutton'
					background-color="#760d0d" />
			</view >
			<view class="flex uni-tab__cart-sub-left"></view>
		
			<view :class="{'uni-tab__right':fill}" class="flex uni-tab__cart-sub-right ">
				<!-- <view v-if="timeFlag==1" v-for="(item,index) in buttonGroup" :key="index" :style="{background:item.backgroundColor,color:item.color}"
				 class="flex uni-tab__cart-button-right-active" >
					<text :style="{color:item.color}" class="uni-tab__cart-button-right-text">活动未开始</text>
				 </view>
				 <view v-else-if="timeFlag==0"  v-for="(item,index) in buttonGroup" :key="index" :style="{background:item.backgroundColor,color:item.color}"
				  class="flex uni-tab__cart-button-right" @click="onClick(index,item)">
				 	<text :style="{color:item.color}" class="uni-tab__cart-button-right-text">{{ item.text }}</text>
				  </view>
				  <view v-else  v-for="(item,index) in buttonGroup" :key="index" :style="{background:item.backgroundColor,color:item.color}"
				   class="flex uni-tab__cart-button-right-active">
				  	<text :style="{color:item.color}" class="uni-tab__cart-button-right-text">活动已结束</text>
				   </view> -->
				   <u-button 
					v-if="timeFlag==0"
				    text="立即秒杀"
				   	color="linear-gradient(to right, #df522e, #EF1224)"
				   	shape="circle"
					@click="onClick()"
				   ></u-button>
				   <u-button
				   	v-else-if="timeFlag==1"
				    text="活动已结束"
					disabled
				   	color="linear-gradient(to right, #ea5630, #EF1224)"
				   	shape="circle"
				   ></u-button>
				   <u-button
				   	v-else
				    text="活动未开始"
					disabled
				   	color="linear-gradient(to right, #d34d2c, #EF1224)"
				   	shape="circle"
				   ></u-button>
				   <!-- <u-button
				   	v-else
				    text="活动已结束"
				   	@click="onClick(index,item)" 	//H5写法
				   	color="linear-gradient(to right, #d34d2c, #EF1224)"
				   	shape="circle"
				   ></u-button> -->
			</view>
			<!-- <button class='real-button' type="default"><text >秒杀</text></button> -->

		</view>
	</view>
</template>

<script>
	export default {
		name:"spikeBar",
		emits:['click','buttonClick'],
		props: {
			options: {
				type: Array,
				default () {
					return [{
						icon: 'shop',
						// text: t("uni-goods-nav.options.shop"),
					}, {
						icon: 'cart',
						// text: t("uni-goods-nav.options.cart")
					}]
				}
			},
			buttonGroup: {
				type: Array,
				default () {
					return [{
							// text: t("uni-goods-nav.buttonGroup.addToCart"),
							backgroundColor: 'linear-gradient(90deg, #FFCD1E, #FF8A18)',
							color: '#fff'
						},
						{
							// text: t("uni-goods-nav.buttonGroup.buyNow"),
							backgroundColor: 'linear-gradient(90deg, #FE6035, #EF1224)',
							color: '#fff'
						}
					]
				}
			},
			fill: {
				type: Boolean,
				default: false
			},
			timeShowFlag:{
				type: Number,		//1时，为秒杀前，0时为秒杀中，-1时为秒杀结束
				// default: 1
			},
			timeDiffList:{
				type:Object,
				default:()=>({
						"startDiff":{
							// "day":0,"hour":0,"minute":0,"second":0,"minsecond":0,"statue":1
						},
						"endDiff":{
							// "day":0,"hour":0,"minute":0,"second":0,"minsecond":0,"statue":0
						}
				})
			}
		},
		data: function () {
			return {
				timeFlag:this.timeShowFlag 	//1表示秒杀正在进行，按钮可以点击，0表示秒杀尚未开始，显示别的
			}
		},
		watch:{
			timeShowFlag:function(){
				this.timeFlag = this.timeShowFlag
			}
		},
		methods: {
			
			// onClick(index, item) {
			// 	console.log("子组件的时间：",this.timeDiffList)
			// 	this.$emit('click', {
			// 		index,
			// 		content: item,
			// 	})
			// },
			onClick(index, item) {
				this.$emit('click', {
					// index,
					// content: item,
				})
			},
			buttonClick(index, item) {
				if (uni.report) {
					uni.report(item.text, item.text)
				}
				this.$emit('buttonClick', {
					index,
					content: item
				})
			},
			ableThebutton() {
				if(this.timeFlag==1) this.timeFlag = 0
				else if(this.timeFlag==0) this.timeFlag = -1
			}
		}
		
	}
</script>

<style lang="scss">
.flex {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
	}

	.uni-goods-nav {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex: 1;
		flex-direction: row;
		// border:1rpx solid #909399;
		box-shadow: 10rpx 10rpx 10rpx 10rpx #9c9c9c;
	}

	.uni-tab__cart-box {
		flex: 1;
		height: 50px;
		background-color: #fff;
		z-index: 900;
	}

	.uni-tab__cart-sub-left {
		padding: 0 5px;
	}

	.uni-tab__cart-sub-right {
		flex: 1;
	}

	.uni-tab__right {
		z-index: 99;
		margin: 5px 0;
		margin-right: 10px;
		border-radius: 100px;
		overflow: hidden;
		// background-color: #aa00ff;
	}

	.uni-tab__cart-button-left {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		// flex: 1;
		position: relative;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		margin: 0 10px;
		/* #ifdef H5 */
		cursor: pointer;
		/* #endif */
	}

	.uni-tab__icon {
		width: 18px;
		height: 18px;
	}

	.image {
		width: 18px;
		height: 18px;
	}

	.uni-tab__text {
		margin-top: 3px;
		font-size: 12px;
		color: #646566;
	}

	.uni-tab__cart-button-right {
		/* #ifndef APP-NVUE */
		display: flex;
		flex-direction: column;
		/* #endif */
		flex: 1;
		justify-content: center;
		align-items: center;
		/* #ifdef H5 */
		cursor: pointer;
		/* #endif */
	}
	.uni-tab__cart-button-right-active {
		/* #ifndef APP-NVUE */
		display: flex;
		flex-direction: column;
		/* #endif */
		flex: 1;
		justify-content: center;
		align-items: center;
		/* #ifdef H5 */
		cursor: pointer;
		/* #endif */
		opacity: 0.7;
		
	}

	.uni-tab__cart-button-right-text {
		font-size: 20px;
		color: #fff;
	}

	.uni-tab__cart-button-right:active {
		opacity: 0.7;
	}

	.uni-tab__dot-box {
		/* #ifndef APP-NVUE */
		display: flex;
		flex-direction: column;
		/* #endif */
		position: absolute;
		right: -2px;
		top: 2px;
		justify-content: center;
		align-items: center;
		// width: 0;
		// height: 0;
	}

	.uni-tab__dot {
		// width: 30rpx;
		// height: 30rpx;
		padding: 0 4px;
		line-height: 15px;
		color: #ffffff;
		text-align: center;
		font-size: 12px;
		background-color: #ff0000;
		border-radius: 15px;
	}

	.uni-tab__dots {
		padding: 0 4px;
		// width: auto;
		border-radius: 15px;
	}
.real-button{
	background-color: linear-gradient(90deg, #FFCD1E, #FF8A18);
	border-radius: 5%;
	margin-top: 10rpx;
	margin-bottom: 10rpx;
	
}
</style>
