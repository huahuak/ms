<template>
	<view class="main">
		<view class="infor-container">
			<view class="colorBlock"></view>
			<view v-if="type==='loan'" class="infor-form">
				<uni-section  class="section" title="活动参数" type="line">
					<uni-list :border="false" >
						<uni-list-item :border="false" title="活动名称"  :rightText="spikeData.activity_name" />
						<uni-list-item :border="false" title="总额度"  :rightText="spikeData.activity_moneyLimit+'元'" />
						<uni-list-item :border="false" title="年利率"  :rightText="spikeData.activity_apr+percent" />
						<uni-list-item :border="false" title="借款期限"  note="可选的分期" :rightText="spikeData.activity_timeLimit+'期'" />
						<uni-list-item :border="false" title="还款期限"  note="贷款归还时间" :rightText="spikeData.activity_replayTime.toString()+'个月'" />
						<uni-list-item :border="false" title="每人限购"  :rightText="spikeData.activity_oneMaxAmount+'份'" />
						<uni-list-item :border="false" title="每份额度"  :rightText="spikeData.activity_perPrice+'元'" />
						<uni-list-item :border="false" title="抢购总数量"  :rightText="spikeData.activity_totalQuantity.toString()" />
						<uni-list-item :border="false" title="抢购总金额"  :rightText="spikeData.activity_totalAmount.toString()+'元'" />
						<uni-list-item :border="false" title="起贷金额"  :rightText="spikeData.activity_initMoney.toString()+'元'" />
						<uni-list-item :border="false" title="开始时间"  :rightText="spikeData.activity_startTime" />
						<uni-list-item :border="false" title="结束时间"  :rightText="spikeData.activity_endTime" />
					</uni-list>
					 <u-collapse :border="false">
					    <u-collapse-item
					      title="活动要求"
						  :border="false"
					    >
					      <text style="margin: 5rpx 5rpx 5rpx;">年龄：{{spikeData.rule.activity_ageFloor}}-{{spikeData.rule.activity_ageUp}}岁</text>
					      <text style="margin: 5rpx 5rpx 5rpx;" v-if="spikeData.rule.activity_checkDishonest">非失信人员</text>
					      <text style="margin: 5rpx 5rpx 5rpx;" v-if="spikeData.rule.activity_checkNation">中国籍人员</text>
					      <text style="margin: 5rpx 5rpx 5rpx;" v-if="spikeData.rule.activity_Overdual">近3年逾期还款2次以上(大于1000元)</text>
					      <text style="margin: 5rpx 5rpx 5rpx;" v-if="spikeData.rule.activity_checkwork">在职就业人员</text>
					      <text style="margin: 5rpx 5rpx 5rpx;" v-if="spikeData.rule.activtiy_guarantee">需要担保</text>
					      <text style="margin: 5rpx 5rpx 5rpx;" v-if="spikeData.rule.activtiy_pledge">需要抵押</text>
					    </u-collapse-item>
					</u-collapse>
					<view><text >{{ space }}</text></view>
				</uni-section>
				
			</view>
			<view v-else-if="type==='deposit'" class="infor-form">
				<uni-section  class="section" title="活动参数" type="line">
					<uni-list :border="false">
						<uni-list-item :border="false" title="活动名称"  :rightText="spikeData.activity_name" />
						<uni-list-item :border="false" title="存款期限"  :rightText="spikeData.activity_timeLimit+'个月'" />
						<uni-list-item :border="false" title="年利率"  :rightText="spikeData.activity_apr+percent" />
						<uni-list-item :border="false" title="每人限购"  :rightText="spikeData.activity_oneMaxAmount+'份'" />
						<uni-list-item :border="false" title="每份额度"  :rightText="spikeData.activity_perPrice+'元'" />
						<uni-list-item :border="false" title="抢购总人数"  :rightText="spikeData.activity_totalQuantity.toString()" />
						<uni-list-item :border="false" title="抢购总金额"  :rightText="spikeData.activity_totalAmount.toString()+'元'" />
						<uni-list-item :border="false" title="限存金额" note="个人抢购最大金额" :rightText="spikeData.activity_moneyLimit.toString()+'元'" />
						<uni-list-item :border="false" title="当日起息"  :rightText="spikeData.rule.activity_dateRate==true ? '是':'否'" />
						<uni-list-item :border="false" title="随存随取"  :rightText="spikeData.rule.activity_dawa==true ? '是':'否' " />
						<uni-list-item :border="false" title="开始时间"  :rightText="spikeData.activity_startTime" />
						<uni-list-item :border="false" title="结束时间"  :rightText="spikeData.activity_endTime" />
					</uni-list>
					<u-collapse :border="false">
					    <u-collapse-item
					      title="活动要求"
						  :border="false"
					    >
					      <text style="margin: 5rpx 5rpx 5rpx;">年龄：{{spikeData.rule.activity_ageFloor}}-{{spikeData.rule.activity_ageUp}}岁</text>
					    </u-collapse-item>
					</u-collapse>
					<view><text >{{ space }}</text></view>
				</uni-section>
			</view>
		</view>
		<!-- <view class='form-container'>
			<text>here</text>
			<u-button @click='testFormd5()' >test for md5</u-button>
		</view> -->
		<view class='form-container'>
				<!-- <u-button @click="acountShow = true">打开</u-button> -->
				<u-cell-group>
					<u-picker :show="acountShow" :columns="accountCol" :closeOnClickOverlay="true" @confirm="confirm" @cancel="cancel"></u-picker>
					<u-cell
					    size="large"
					    title="账户选择"
					    :value="bankAccountID"
					    isLink
						@click="acountShow=true"
					>
					</u-cell>
					<u-cell	size="large" title="购买份数">
						<u-number-box integer 
						:min="1" 
						:max="spikeData.activity_oneMaxAmount" 
						button-size="36" 
						slot="value" 
						v-model="goodsOrderAmount"
						></u-number-box>
					</u-cell>
					<u-cell	size="large" title="统计总金额">
						<text slot="value" class="showTotalMoney">{{spikeData.activity_perPrice*goodsOrderAmount}}</text>
						<text slot="value" class="yuan">元</text>
					</u-cell>
				</u-cell-group>
		
		</view>
		
		<view class="bar-style">
			<spikeBar 
			:timeShowFlag="timeShowFlag" 
			:timeDiffList="timeDiffList"
			:fill="true" 
			:button-group="buttonGroup" 
			@click='$u.debounce(Spike,400)' >
			</spikeBar>
		</view>
		<u-toast ref="WaitToast"></u-toast>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	import UniSection from '../../components/uni-section/uni-section.vue'
	import spikeBar from '../../components/spikeBar.vue'
	import timeDiff from '../../js_sdk/timeDiff.js'
	export default {
		onLoad(Option){
			setTimeout(()=>{
				this.$refs.uToast.show({
					type:'success',
					title:'恭喜您通过初筛',
					message:'恭喜您通过初筛',
					duration:800,
				})
			},500)
			this.type = Option.type;
			this.activity_id = Option.activity_id;
			this.accountCol = [uni.getStorageSync('bankAccountID')]
			console.log("在这李",this.accountCol)
			this.bankAccountID = this.accountCol[0][0]	//二元
			this.reqForActData()
		},
		created(){
			console.log("created !!!")
		},
		components:{
			UniSection,
			spikeBar
		},
		data() {
			return {
				type:'loan',
				space:' \n',
				testSign:"hello",
				countDownStamp:'',
				percent:'%',
				actKey:' ', 		//接收秒杀按钮的md5
				activity_id:'',
				goodsID:'', //商品的ID
				bankAccountID:'',	//当前所选择的消费账户
				acountShow:false,	//展示消费账户的选择框
				accountCol:'',		//账户的数组，二维数组
				goodsOrderAmount:1,	//购买份数
				actRandom:'',
				timeShowFlag:1,		//1展示开始时间，0展示结束时间，-1不展示时间
				resTimer:'', 		//定时器轮询结果的标志
				requestID:'',		//秒杀排队成功后返回的ID
				spikeData:{
							"activity_id": '',
					        "activity_name": "",
					        "activity_moneyLimit": '',
					        "activity_timeLimit": "",
					        "activity_replayTime": '',
					        "activity_apr": '',
					        "rule": {
							
							},
					        "activity_totalQuantity": '',
					        "activity_totalAmount": '',
					        "activity_initMoney": '',
					        "activity_startTime": " ",
					        "activity_endTime": " ",
							"activity_oneMaxAmount":'',
							"activity_perPrice":'',
				},
				buttonGroup: [
					{
						text: '立即秒杀',
						backgroundColor: 'linear-gradient(90deg, #cb0025, #d80186)',
						color: '#fff'
					}
				],
				depositRule:{
					activity_name:'',
					activity_timeLimit:'',
					activity_apr:'',
					activity_dateRate:'',
					activity_dawa:'',
					activity_totalQuantity:0,
					activity_totalAmount:0,
					activity_initMoney:0,
					activity_moneyLimit:'',
					activity_startTime:'',
					activity_endTIme:''
				},
				loanRule:{
					"activity_id": '',
					"activity_name": "",
					"activity_moneyLimit": '',
					"activity_timeLimit": "",
					"activity_replayTime": '',
					"activity_apr": '',
					"rule": {
						"activity_ageFloor":'',
						"activity_ageUp":'',
						"activity_dateRate":'',
						"activity_dawa":''
					},
					"activity_totalQuantity": '',
					"activity_totalAmount": '',
					"activity_initMoney": '',
					"activity_startTime": " ",
					"activity_endTime": " ",
					"activity_oneMaxAmount":'',
					"activity_perPrice":'',
				},
				timeDiffList:{ //保存现在时间与秒杀开始时间，结束时间的差值
					"startDiff":{
						
					},
					"endDiff":{
						
					}
				},	
			}
		},
		methods: {	
			reqForActData(){
				var id = this.activity_id;
				if(this.type==='loan'){
					// console.log("token is "+uni.getStorageSync('token'))
					uni.request({
						// url:"http://tguio.club:8849/api/loan/"+id,
						// url:"http://192.168.1.165:8848/api/loan/"+id,
						url:"/api1/api/loan/"+id,
						method:'GET',
						header:{'Authorization':uni.getStorageSync('token')},
						success:(res)=>{
							if(res.data.code==200) {
								console.log("Receive loan data success")
								// this.spikeData = JSON.stringify(res.data.content)
								this.spikeData = res.data.content
								console.log(this.spikeData)
								let timeData = {
									activity_startTime:res.data.content.activity_startTime,
									activity_endTime:res.data.content.activity_endTime,
									}
								this.computeTimeStamp(timeData)
								// 需补充：放md5等内容
								// uni.setStorageSync("actKey:"+data.activity_id.toString(), data.md5)
							} else if (res.data.code==401) {
								this.myShowToast("error","登陆已过期",1000)
								uni.reLaunch({
									url:'../public/login'
								})
							}
							// console.log(res.data)
						},
						fail:(err)=>{
							console.log(err)
						}
					})
				} else if (this.type==='deposit') {
					//此處添加存款頁面請求代碼
					uni.request({
						// url:"http://tguio.club:8849/api/deposit/"+id,
						// url:"http://192.168.1.165:8848/api/deposit/"+id,
						url:"/api1/api/deposit/"+id,
						method:'GET',
						header:{'Authorization':uni.getStorageSync('token')},
						success:(res)=>{
							if(res.data.code==200) {
								console.log("Receive loan data success")
								this.spikeData = res.data.content
								console.log(this.spikeData.activity_dawa)
								let timeData = {
									activity_startTime:res.data.content.activity_startTime,
									activity_endTime:res.data.content.activity_endTime,
									}
								this.computeTimeStamp(timeData)
								// 需补充：放md5等内容
								// uni.setStorageSync("actKey:"+data.activity_id.toString(), data.md5)
							} else if (res.data.code==401) {
								this.myShowToast("error","登陆已过期",1000)
								uni.reLaunch({
									url:'../public/login'
								})
							}
							// console.log(res.data)
						},
						fail:(err)=>{
							console.log(err)
						}
					})
				}
				//获取实时时间

			},
			
			
			//申请md5
			Spike(){
				// console.log("now the bankAcount is:"+this.bankAccountID)
				uni.request({
					// url:'http://tguio.club:8849/api/' + this.type +'/join',
					// url:'http://192.168.1.165:8848/api/' + this.type +'/join',
					url:'/api1/api/' + this.type +'/join',
					method:'GET',
					data:{
						account_id:this.bankAccountID,
						activity_id:parseInt(this.spikeData.activity_id),
						user_id:uni.getStorageSync('user_id'),
					},
					header:{"Authorization":uni.getStorageSync("token")},
					success: (res) => {
						if(res.data.code==200) {
							//result错误码 1：通过 2：已参加过 3：请求频繁 4：...
							var result = res.data.content.result
							if(result==1){
								console.log("请求秒杀成功");
								if(res.data.content.random!=null) {
									this.actKey = res.data.content.md5
									this.goodsID = res.data.content.goodId
									this.actRandom = res.data.content.random
									this.finalSpike()
								}
							} else if (result==2) {
								this.myShowToast("default","您已参加过本次活动",1500)
							} else if (result==3) {
								this.myShowToast("error","请勿频繁请求",3000)
							} else if (result==4) {
								this.myShowToast("error","活动没有开始",1500)
							}
							// console.log(res.data)
							// uni.setStorageSync("actKey:"+this.spikeData.activity_id.toString(),res.data.content.md5)
							// setTimeout(this.finalSpike(this.actKey),2000)
						} else if (res.data.code==401){
							this.myShowToast("error","登陆已过期",1000)
							uni.reLaunch({
								url:'../public/login'
							})
						} else {
							console.log("服务异常")
						}
					},
					fail: (err) => {
						console.log("请求md5失败")
					}
				})
			},
			//秒杀总函数
			finalSpike(){
				// this.myShowToast("loading","正在加载",3000)
				console.log()
				uni.request({
					// url:'http://192.168.1.165:8083/web/order/'+this.actRandom,
					url:'/api2/order/'+this.actRandom,
					method:'POST',
					header:{'Auth-Sign':this.actKey},
					data:{
						bankAccountID:this.bankAccountID,
						goodsID:this.goodsID,
						goodsOrderAmount:this.goodsOrderAmount,
						// requestID:'1',
						userID:uni.getStorageSync('user_id'),
					},
					// id: 
					success: (res) => {
						console.log(res)
						if(res.data.code=="SUCCESS") {
							let msg = JSON.parse(res.data.msg)
							let id = msg.id
							console.log(id)
							if (id==4||id==2||id==3) {
								this.myShowToast("error",msg.desc,2000)
							}
							else if (id==1||id==5){
								console.log("排队成功")
								this.requestID = res.data.requestID
								this.myShowToast("success",msg.desc,2000)
								this.resTimer = setTimeout(()=>{
									this.requestForRes()
								},1000)	//s查询一次
							}

						}
						else {
							// setInterval(this.requestForRes,1000,{})
							this.myShowToast("error","服务器异常",2000)
						}
					},
					fail: (err) => {
						console.log(err)
						this.$refs.uToast.show({
							type:'default',
							title:'网络故障',
							message:'网络出现问题'
						})
						console.log("请求失败")
						// console.log(this.actKey)
						
					}
					
				})
			},
			//轮询秒杀结果函数
			requestForRes(){
				uni.request({
					// url:'http://192.168.1.165:8083/web/order-result',
					url:'/api2/order-result',
					method:'POST',
					data:{
						bankAccountID:this.bankAccountID,
						goodsID:this.goodsID,
						goodsOrderAmount:this.goodsOrderAmount,
						userID:uni.getStorageSync('user_id'),
						requestID:this.requestID
					},	
					
					// 0:正在处理订单 
					//1:排队(请求)成功 2:排队(请求)失败：随机数//签名//开始时间// 
					// 3:处理完成:超过购买数 库存不足 订单完成 订单失败 余额不足
					// 4:字段非法 
					
					success: (res) => {
						if (res.data.code=="SUCCESS") {
							console.log("轮询1次成功,res is :",res)
							let data = null;
							if (res.data.data!=null) 
								 data = JSON.parse(res.data.data)
							if(data==null || data.id=="0" ) {
								this.resTimer = setTimeout(()=>{
									this.requestForRes()
								},1500)
								this.myShowToast("loading","正在处理","订单处理中",800)
							}
							else if (data.id=="1") {
								clearTimeout(this.resTimer)
								this.myShowToast("success",data.desc+':'+data.reason,3000)
							} else if (data.id=="2") {
								clearTimeout(this.resTimer)
								this.myShowToast("error",data.desc+':'+data.reason,2000)
							} else if (data.id=="3") {
								clearTimeout(this.resTimer)
								this.myShowToast("error",data.desc+':'+data.reason,2000)
							} else if (data.id=="4") {
								clearTimeout(this.resTimer)
								this.myShowToast("error",data.desc+':'+data.reason,2000)
							}
						}
						
					}
				})
			},
			myShowToast(type,message,time) {
				this.$refs.WaitToast.show({
					type:type,
					title:message,
					message:message,
					duration:time
				})
			},
			//计算时间戳的差值，timeDiffList 记录开始、结束时间与当前时间之差
			computeTimeStamp(timeData) {
				uni.request({
					// url:'http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp',
					url:'/api3/rest/api3.do?api=mtop.common.getTimestamp',
					method:'GET',
					success: (res) => {
						if(res.statusCode==200) {
							let nowTimeStamp = res.data.data.t
							if(uni.getSystemInfoSync().platform==='ios'){
								console.log("yes")
								var startTime = new Date(timeData.activity_startTime.replace(/-/g,'/'))
								var endTime = new Date(timeData.activity_endTime.replace(/-/g,'/'))
							}else{
								var startTime = new Date(timeData.activity_startTime)
								var endTime = new Date(timeData.activity_endTime)
							}
							this.timeDiffList = {
								"startDiff":timeDiff.getTimeDiff(startTime.getTime(),nowTimeStamp),
								"endDiff":timeDiff.getTimeDiff(endTime.getTime(),nowTimeStamp),
							}
								//判断当前应该显示什么时间
								if(this.timeDiffList.startDiff.statue==0 && this.timeDiffList.endDiff.statue==0) {
										this.timeShowFlag = -1;
								} else if (this.timeDiffList.startDiff.statue==0){ 
									this.timeShowFlag = 0;
								} else {
									this.timeShowFlag = 1;
								}
								console.log(JSON.stringify(this.timeDiffList))
								
						} else {
							this.myShowToast('error','获取实时时间失败',1000)
						}	
						 // console.log(this.nowTimeStamp)
					},
					fail: (err) => {
						console.log(err)
						this.myShowToast('error','获取实时时间失败',1000)
					}
				})
				// console.log(timeDiff.getTimeDiff(new Date("2022-4-10 00:00:00"),stamp))
			},
			testFormd5(){
				console.log(this.actKey)
				console.log("hello")
			},
			confirm(e) {
				 // console.log("accountCol",e)
				 this.bankAccountID = e.value[0]
			     this.acountShow = false
			},
			cancel(e) {
				this.acountShow = false
			},
		}
	}
</script>

<style lang="scss">
.main{
	// background-color: #55ff00;	
}
.colorBlock{
	position: absolute;
	height: 40%;
	width: 100%;
	background-color: #dc3134;
	z-index:-1;
}

.infor-container{
	// align-items: center;
	// // width: 10;
	// height: 900rpx;
	
	// background-color: #1636ea;
	

}
.infor-form{
	// border-style:inset;
	// padding-top: 20rpx;
	padding-top: 20rpx;
	border-color:#ca675e;
	border-width: 10rpx;
	margin-right: 20rpx;
	margin-left: 20rpx;
	z-index: 1;
	 .section{
	 	 border-radius: 40rpx;
	 	 z-index: auto;
		 box-shadow: 0 5rpx 15rpx #949494;
	 }
}
.bar-style{
			/* #ifndef APP-NVUE */
			display: flex;
			/* #endif */
			flex-direction: column;
			position: fixed;
			left: 0;
			right: 0;
			/* #ifdef H5 */
			left: var(--window-left);
			right: var(--window-right);
			/* #endif */
			bottom: 0;
}
.form-container{
	margin-top: 80rpx;
	margin-bottom: 200rpx;
	height: 800rpx;
}
.showTotalMoney{
	font-size: 40rpx;
	font:  "Helvetica",Helvetica,Arial,"Microsoft Yahei","Hiragino Sans GB","Heiti SC","WenQuanYi Micro Hei",sans-serif;
	color: #d52a1a;
	background-color: #e6e6e6;
	font-weight: bold;
}
.yuan{
	margin-left: 10rpx;
	font-size: 20rpx;
}
</style>
