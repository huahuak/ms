<template>
  <div class="add">
    <el-card class="add-container">
      <el-form :model="goodForm" :rules="rules" ref="goodRef" label-width="200px" class="goodForm" size="large">
        <el-form-item  label="产品名称">
          <el-input   style="width: 300px" v-model="goodForm.activity_name" placeholder="请输入产品名称"></el-input>
        </el-form-item>
        <el-form-item  label="产品限购总人数">
          <el-input-number style="width: 300px" :min="0" :max="100000000" v-model="goodForm.activity_totalQuantity" placeholder="选择数量" :step="100" @change="changeQuantity" />
        </el-form-item>
        <el-form-item  label="产品限购份数">
          <el-input-number style="width: 300px" :min="0" :max="100000000" v-model="goodForm.activity_oneMaxAmount" placeholder="选择数量" :step="100" @change="changeOneMax" />
        </el-form-item>
        <el-form-item  label="产品每份价值">
          <el-input-number style="width: 300px" :min="0" :max="100000000" v-model="goodForm.activity_perPrice" placeholder="选择数量" :step="100" @change="changePerPrice" />
        </el-form-item>
        <el-form-item  label="产品总金额(元)">
          <el-input style="width: 300px" v-model="goodForm.activity_totalAmount" disabled></el-input>
        </el-form-item>
        <el-form-item  label="存款额度(每人-元)">
        <el-input style="width: 300px" v-model="goodForm.activity_moneyLimit" disabled></el-input>
        </el-form-item>
        <el-form-item  label="存款期限(月)">
           <el-input-number style="width: 300px" v-model="goodForm.activity_timeLimit" :step="1" :min="1" :max="100" step-strictly />
        </el-form-item>
        <el-form-item label="年利率(%)">
          <el-input-number style="width: 300px" v-model="goodForm.activity_apr" :precision="2" :step="0.1" :max="6.00" :min="0"/>
        </el-form-item>
		<el-form-item require label="是否当日起息" >
		  <el-select style="width: 300px" v-model="goodForm.rule.activity_dateRate" placeholder="选择">
		    <el-option label="是" value="true"></el-option>
		    <el-option label="否" value="false"></el-option>
		  </el-select>
		</el-form-item>
		<el-form-item require label="是否随存随取" >
		  <el-select style="width: 300px" v-model="goodForm.rule.activity_dawa" placeholder="选择">
		    <el-option label="是" value="true"></el-option>
		    <el-option label="否" value="false"></el-option>
		  </el-select>
		</el-form-item>
		<el-form-item require label="年龄上限">
		  <el-input-number style="width: 300px" :min="goodForm.rule.activity_ageFloor" :max="200" v-model="goodForm.rule.activity_ageUp" placeholder="选择最大年龄" :step="5"  step-strictly />
		</el-form-item>
		<el-form-item require label="年龄下限">
		  <el-input-number style="width: 300px" :min="0" :max="goodForm.rule.activity_ageUp" v-model="goodForm.rule.activity_ageFloor" placeholder="选择最小年龄" :step="5" step-strictly />
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
         <el-button type="primary" v-debounce="submitNewActivity" >立即创建</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { reactive, ref, toRefs, onMounted, onBeforeUnmount,  } from 'vue'
import { ElMessage } from 'element-plus'
// import { useRoute, useRouter } from 'vue-router'
import { submitNewDepositAct } from '@/api/table'
export default {
  name: 'AddDepositActivities',
  setup() {
    const { proxy } = getCurrentInstance()
    console.log('proxy', proxy)
    const editor = ref(null)
    const goodRef = ref(null)
    const route = useRoute()
    const router = useRouter()
    const { id } = route.query
    const state = reactive({
      id: id,
	  formTime:[],
      goodForm: {
        "activity_name":"",
        "activity_timeLimit":5,
        "activity_apr":1,
        "activity_totalQuantity":0,
        "activity_oneMaxAmount":0,
        "activity_perPrice":0,
		"activity_moneyLimit":0,
		"activity_totalAmount":0,
        "activity_startTime":"2000-1-1 00-00-00",
        "activity_endTime":"2000-1-1 01-00-00",
		"rule":{
			"activity_dateRate":'',
			"activity_dawa":'',
			"activity_ageFloor":0,
			"activity_ageUp":0,
		},
      },
      rules: {
        // goodsCoverImg: [
        //   { required: 'true', message: '请上传主图', trigger: ['change'] }
        // ],
        // goodsName: [
        //   { required: 'true', message: '请填写商品名称', trigger: ['change'] }
        // ],
        // originalPrice: [
        //   { required: 'true', message: '请填写商品价格', trigger: ['change'] }
        // ],
        // sellingPrice: [
        //   { required: 'true', message: '请填写商品售价', trigger: ['change'] }
        // ],
        // stockNum: [
        //   { required: 'true', message: '请填写商品库存', trigger: ['change'] }
        // ],
      },
	})
	const submitNewActivity = () => {
	  state.goodForm.activity_startTime = state.formTime[0]
	  state.goodForm.activity_endTime = state.formTime[1]
	  
		submitNewDepositAct(state.goodForm).then((res)=>{
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
	  changeQuantity,
	  changePerPrice,
	  changeOneMax,
	  submitNewActivity,
	  
		
    }
  }
}
</script>

<style scoped>
.add {
  display: flex;
}
.add-container {
  flex: 1;
  height: 100%;
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