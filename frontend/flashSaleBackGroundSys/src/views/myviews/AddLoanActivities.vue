<template>
  <div class="add">
    <el-card class="add-body">
		<div class="add-container">
		<!-- <div class="add-left-form">		 -->
			<el-form :model="goodForm" :rules="rules" ref="goodRef" label-width="150px" size='large' class="add-left-form">
			  <el-form-item label="产品名称">
			    <el-input   style="width: 300px" v-model="goodForm.activity_name" placeholder="请输入产品名称"></el-input>
			  </el-form-item>
			  <el-form-item label="产品限购总人数">
			    <el-input-number style="width: 300px" :min="0" :max="100000000" v-model="goodForm.activity_totalQuantity" placeholder="选择数量" :step="50" @change="changeQuantity" />
			  </el-form-item>
			  <el-form-item label="产品限购份数(每人)">
			    <el-input-number style="width: 300px" :min="0" :max="100000000" v-model="goodForm.activity_oneMaxAmount" placeholder="选择数量" :step="1" @change="changeOneMax" />
			  </el-form-item>
			  <el-form-item label="产品每份价值">
			    <el-input-number style="width: 300px" :min="0" :max="100000000" v-model="goodForm.activity_perPrice" placeholder="选择数量" :step="100" @change="changePerPrice" />
			  </el-form-item>
			  <el-form-item label="产品总金额(元)">
			    <el-input style="width: 300px" v-model="goodForm.activity_totalAmount" disabled></el-input>
			  </el-form-item>
			  <el-form-item label="贷款额度(每人-元)">
			    <!-- <el-input-number style="width: 300px" :min="0" :max="1000000" v-model="goodForm.activity_moneyLimit" placeholder="选择额度" :step="100" @change="changeAmount" /> -->
				<el-input style="width: 300px" v-model="goodForm.activity_moneyLimit" disabled></el-input>
			  </el-form-item>
			  <el-form-item  label="分期期限">
			    <el-select v-model="goodForm.activity_timeLimit" style="width: 300px; vertical-align: middle" placeholder="Select">
			      <el-option
			          v-for="item in timeLimiteOption"
			          :key="item.value"
			          :label="item.label"
			          :value="item.value"
			      >
			      </el-option>
			    </el-select>
			  </el-form-item>
			  <el-form-item label="还款期限(月)">
			    <el-input-number style="width: 300px" v-model="goodForm.activity_replayTime" :step="1" :min="1" :max="100" step-strictly />
			  </el-form-item>
			  <el-form-item label="年利率(%)">
			    <el-input-number style="width: 300px" v-model="goodForm.activity_apr" :precision="2" :step="0.1" :max="6.00" :min="0"/>
			  </el-form-item>
			  <el-form-item label="年龄上限">
			    <el-input-number style="width: 300px" :min="goodForm.rule.activity_ageFloor" :max="200" v-model="goodForm.rule.activity_ageUp" placeholder="选择最大年龄" :step="5"  step-strictly />
			  </el-form-item>
			  <el-form-item label="年龄下限">
			    <el-input-number style="width: 300px" :min="0" :max="goodForm.rule.activity_ageUp" v-model="goodForm.rule.activity_ageFloor" placeholder="选择最小年龄" :step="5" step-strictly />
			  </el-form-item>
			</el-form>
		<!-- </div> -->
		
		<div class="add-right-form">
			<el-form size='large' label-width="100px">
				<el-form-item label="是否需要担保">
				  <el-select style="width: 300px" v-model="goodForm.rule.activity_guarantee" placeholder="选择">
				    <el-option label="是" value="true"></el-option>
				    <el-option label="否" value="false"></el-option>
				  </el-select>
				</el-form-item>
				<el-form-item label="是否需要抵押">
				  <el-select style="width: 300px" v-model="goodForm.rule.activity_pledge" placeholder="选择">
				    <el-option label="是" value="true"></el-option>
				    <el-option label="否" value="false"></el-option>
				  </el-select>
				</el-form-item>
				<el-form-item label="是否检查在职" >
				    <el-select style="width: 300px" v-model="goodForm.rule.activity_checkwork" placeholder="选择">
				      <el-option label="是" value="true"></el-option>
				      <el-option label="否" value="false"></el-option>
				    </el-select>
				  </el-form-item>
				  <el-form-item label="是否检查失信" >
				    <el-select style="width: 300px" v-model="goodForm.rule.activity_checkDishonest" placeholder="选择">
				      <el-option label="是" value="true"></el-option>
				      <el-option label="否" value="false"></el-option>
				    </el-select>
				  </el-form-item>
				  <el-form-item label="是否检查逾期" >
				    <el-select style="width: 300px" v-model="goodForm.rule.activity_checkOverdual" placeholder="选择">
				      <el-option label="是" value="true"></el-option>
				      <el-option label="否" value="false"></el-option>
				    </el-select>
				  </el-form-item>
				  <el-form-item label="是否限制国内" >
				    <el-select style="width: 300px" v-model="goodForm.rule.activity_checkNation" placeholder="选择">
				      <el-option label="是" value="true"></el-option>
				      <el-option label="否" value="false"></el-option>
				    </el-select>
				  </el-form-item>
				  <el-form-item label="起贷金额" >
				    <el-input-number 
						style="width: 300px" 
						:min="goodForm.activity_perPrice" 
						:max="goodForm.activity_moneyLimit" 
						v-model="goodForm.activity_initMoney" 
						placeholder="选择金额" :step="100"  
						/>
				  </el-form-item>
				  <el-form-item label="秒杀时间" >
				    <el-date-picker
				        style="width: 500px"
				        v-model="formTime"
				        type="datetimerange"
				        start-placeholder="开始时间"
				        end-placeholder="结束时间"
				        range-separator="至"
				        value-format="YYYY-MM-DD HH:mm:ss"
				    >
				    </el-date-picker>
				  </el-form-item>
				
				  <el-form-item>
				    <!-- <el-button type="primary" @click="submitNewActivity">立即创建</el-button> -->
				    <el-button type="primary" v-debounce="submitNewActivity" >立即创建</el-button>
				  </el-form-item>
				</el-form>
			</div>
     
        </div>
    </el-card>
  </div>
</template>

<script>
import { reactive, ref, toRefs, onMounted, onBeforeUnmount, getCurrentInstance } from 'vue'
// import axios from '@/utils/axios'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { submitNewLoanAct } from '@/api/table'

export default {
  name: 'AddLoanActivities',
  setup() {
    const { proxy } = getCurrentInstance()
    console.log('proxy', proxy)
    const editor = ref(null)
    const goodRef = ref(null)
    const route = useRoute()
    const router = useRouter()
    const timeLimiteOption = [
      {
        value: '3',
        label: '3期',
      },
      {
        value: '6',
        label: '6期',
      },
      {
        value: '12',
        label: '12期',
      },
      {
        value: '3/6',
        label: '3/6期',
      },
      {
        value: '3/12',
        label: '3/12期',
      },
      {
        value: '6/12',
        label: '6/12期',
      },
      {
        value: '3/6/12',
        label: '3/6/12期',
      },

    ]
    const state = reactive({
      // uploadImgServer,
      // token: localGet('token') || '',
      // id: id,
      formTime:[],
      defaultCate: '',
      goodForm: {
        "activity_name":" ",
        "activity_moneyLimit":0,
        "activity_timeLimit":' ',
		"activity_oneMaxAmount":0,
		"activity_perPrice":0,
        "activity_replayTime":0,
        "activity_apr":1,
        "activity_startTime":" ",
        "activity_endTime":" ",
        "rule":{
          "activity_guarantee": ' ',
          "activity_pledge": ' ',
          "activity_ageUp":0,
          "activity_ageFloor":0,
          "activity_checkwork": ' ',
          "activity_checkDishonest":' ',
          "activity_checkOverdual":' ',
          "activity_checkNation":' ',
        },
        "activity_totalQuantity":0,
        "activity_totalAmount":0,
        "activity_initMoney":0
      },
		rules:{
			
		}
    })
    const submitNewActivity = () => {
      state.goodForm.activity_startTime = state.formTime[0]
      state.goodForm.activity_endTime = state.formTime[1]

		submitNewLoanAct(state.goodForm).then((res)=>{
			if(res.code==200) {
				ElMessage({
					message:res.message,
					type:'success',
					center:true
				})
			} else if (res.code==401) {
				ElMessage({
					message:res.message,
					type:'error',
					center:true
				})
			} else {
				ElMessage({
					message:res.message,
					type:'error',
					center:true
				})
			}
		}).catch((err)=>{
			ElMessage(err.message,'error')
			console.log(err)
		})		
    }
    const changeQuantity = (val) => {
		state.goodForm.activity_moneyLimit = state.goodForm.activity_oneMaxAmount * state.goodForm.activity_perPrice
		state.goodForm.activity_totalAmount = val * state.goodForm.activity_moneyLimit
    }
	const changePerPrice = (val) => {
		state.goodForm.activity_moneyLimit = state.goodForm.activity_oneMaxAmount * val
		state.goodForm.activity_totalAmount = state.goodForm.activity_totalQuantity * state.goodForm.activity_moneyLimit
	}
	const changeOneMax = (val) => {
		state.goodForm.activity_moneyLimit = val * state.goodForm.activity_perPrice
		state.goodForm.activity_totalAmount = state.goodForm.activity_totalQuantity * state.goodForm.activity_moneyLimit
	}
	
    return {
      ...toRefs(state),
      timeLimiteOption,
      changeQuantity,
	  changePerPrice,
	  changeOneMax,
      submitNewActivity

    }
  }
}
</script>

<style scoped>
.add {
  height: 100%;
  width: 100%;
}
.add-body{
	height: 100%;
	width: 100%;
  }
.add-container {
  display: flex;
  height: 100%;
  width: 80%;
  /* display: inline-flex; */
  
}
.add-right-form{
	width: 40%;
	flex: 1 1 auto;
	display: flex;
	/* flex-direction: column; */
}
.add-left-form{
	width: 40%;
	height: 100%;
	flex: 1 1 auto;
	display: flex;
	flex-direction: column;
	/* margin-right:100px ; */
}
.goodForm{
	display: flex;
	flex-direction: column;
}
.add-form-item{
	height: 10%;
}
.avatar-uploader {
  width: 100px;
  height: 100px;
  color: #ddd;
  font-size: 30px;
}
.avatar-uploader-icon {
  display: block;
  width: 100%;
  height: 100%;
  border: 1px solid #e9e9e9;
  padding: 32px 17px;
}
</style>