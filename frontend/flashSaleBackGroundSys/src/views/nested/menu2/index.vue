<template>
  <div style="padding: 15px">
    <!-- <el-alert :closable="false" title="menu 2" /> -->
	<el-card class="index-container">
	<!--    <div>{{title}}</div>-->
	    <el-table
	        v-loading="loading"
	        ref="multipleTable"
	        :data="tableData"
	        tooltip-effect="dark"
	        style="width: 100%"
	        @selection-change="">
	      <el-table-column
	          prop="activity_id"
	          label="活动序号"
	          width="90"
	      >
	      </el-table-column>
	      <el-table-column
	          prop="activity_name"
	          label="活动名称"
	          width="100"
	      >
	      </el-table-column>
	      <el-table-column
	          prop="activity_moneyLimit"
	          label="借款额度"
	          width="100"
	      >
	      </el-table-column>
	      <el-table-column
	          prop="activity_timeLimit"
	          label="借款期限"
	          width="100"
	      >
	      </el-table-column>
	      <el-table-column
	          prop="activity_replayTime"
	          label="还款期限"
	          width="100"
	      >
	      </el-table-column>
	       <el-table-column
	            prop="rule.activity_guarantee"
	            label="担保需否"
	            width="100"
	        >
	        </el-table-column>
	        <el-table-column
	            prop="rule.activity_pledge"
	            label="抵押需否"
	            width="100"
	        >
	        </el-table-column>
	        <el-table-column
	            prop="rule.activity_ageUp"
	            label="年龄上限"
	            width="100"
	        >
	        </el-table-column>
	        <el-table-column
	            prop="rule.activity_ageFloor"
	            label="年龄下限"
	            width="100"
	        >
	        </el-table-column>
	        <el-table-column
	            prop="rule.activity_ageUp"
	            label="年龄上限"
	            width="100"
	        >
	        </el-table-column>
	        <el-table-column
	            prop="rule.activity_checkwork"
	            label="是否检查在职"
	            width="120"
	        >
	        </el-table-column>
	        <el-table-column
	            prop="rule.activity_checkDishonest"
	            label="是否检查失信"
	            width="120"
	        >
	        </el-table-column>
	        <el-table-column
	            prop="rule.activity_checkOverdual"
	            label="是否检查逾期"
	            width="120"
	        >
	        </el-table-column>
	        <el-table-column
	            prop="rule.activity_checkNation"
	            label="是否限制国内"
	            width="120"
	        >
	        </el-table-column>
	      <el-table-column
	          prop="activity_totalQuantity"
	          label="产品总数量"
	          width="110"
	      >
	      </el-table-column>
	      <el-table-column
	          prop="activity_totalAmount"
	          label="产品总金额"
	          width="110"
	      >
	      </el-table-column>
	        <el-table-column
	            prop="activity_initMoney"
	            label="起贷金额"
	            width="110"
	        >
	        </el-table-column>
	        <el-table-column
	          prop="activity_startTime"
	          label="产品秒杀开始时间"
	          width="200"
	      >
	      </el-table-column>
	      <el-table-column
	          prop="activity_endTime"
	          label="产品秒杀结束时间"
	          width="200"
	      >
	      </el-table-column>
	
	      <el-table-column
	          label="查看已初筛客户"
	          width="120"
	          fixed="right"
	      >
	        <template #default="scope">
	          <a style="cursor: pointer; margin-right: 10px; color: #3888FA;" @click="checkPass(scope.row.activity_id)">通过</a>
	          <a style="cursor: pointer; margin-right: 10px; color: #3888FA" @click="checkFail(scope.row.activity_id)">未通过</a>
	        </template>
	     </el-table-column>
	   <el-table-column
	          label="操作"
	          width="100"
	          fixed="right"
	      >
	        <template #default="scope">
	          <a style="cursor: pointer; margin-right: 10px;color: #3888FA;" @click="modifyAct(scope.row)">修改</a>
	          <a style="cursor: pointer; margin-right: 10px;color: #3888FA;" @click="deleteAct(scope.row)">删除</a>
	        </template>
	      </el-table-column>
	    </el-table>
	    <!--总数超过一页，再展示分页器-->
	  <!--  <el-pagination
	        background
	        layout="prev, pager, next"
	        :total="total"
	        :page-size="pageSize"
	        :current-page="currentPage"
	        @current-change="changePage"
	    /> -->
	  </el-card>
  </div>
</template>



<script>
import {onBeforeMount, onMounted, onUnmounted, reactive, ref, toRefs} from 'vue';
import { ElMessage } from 'element-plus';
// import DialogAddGood from '@/components/DialogAddGood.vue'
import { useRoute, useRouter } from 'vue-router';
// import axiosReq from "@/utils/axiosReq";
import {getLoanList} from '@/api/table';
// import {localGet} from "../utils";
// 首页配置类型参数
const configTypeMap = {
  hot: 3,
  new: 4,
  recommend: 5
}
export default {
  name: 'CheckLoanActivities',
  // components: {
  //   DialogAddGood
  // },
  setup() {
    const router = useRouter()
    const route = useRoute()
    const multipleTable = ref(null)
    const addGood = ref(null)
    const state = reactive({
      loading: false,
      tableData: [
        {
				"activity_id": '',
				"activity_name": "",
                  "activity_moneyLimit": '',
                  "activity_timeLimit": "",
                  "activity_replayTime": '',
                  "activity_apr": '',
                  "rule": {
                    "activity_guarantee": '',
                    "activity_pledge": '',
                    "activity_ageUp": '',
                    "activity_ageFloor": '',
                    "activity_checkwork": '',
                    "activity_checkDishonest": '',
                    "activity_checkOverdual": '',
                    "activity_checkNation": ''
                  },
                  "activity_totalQuantity": '',
                  "activity_totalAmount": '',
                  "activity_initMoney": '',
                  "activity_startTime": "",
                  "activity_endTime": "",
        }
      ], // 数据列表
      multipleSelection: [], // 选中项
      total: 10, // 总条数
      currentPage: 1, // 当前页
      pageSize: 10, // 分页大小
      type: 'add', // 操作类型
      configType: 3 // 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
    })
    function checkPass(id) {
      router.push({path:'/Search',query:{"activity_id":id,"Pass":"1","type":"loan"}})
    }
    function checkFail(id){
      router.push({path:'/Search',query:{"activity_id":id,"Pass":"0","type":"loan"}})
    }
    function modifyAct(row){
      let data = JSON.stringify(row)
      router.push({path:'/ModifyLoanActivities',query:{data}})
    }
    function deleteAct(row){
      axios.delete('/activity/loan/'+row.activity_id)
	  // axiosReq([
		 //  url:'/api/activity/loan'
	  // ])
    }
    const getLoanData = () =>{
		//    axios.get('/activity/loan/full', {
		//      // header:{"Authorization":localGet('')}
		//      params: {
		//        // pageNumber: state.currentPage,
		//        // pageSize: state.pageSize,
		//        // configType: state.configType
		//        page_limit:5,
		//        page_num:1
		//      }
		//    }).then(res => {
		//       state.tableData = res.data.content.result
				 // console.log(res.data.content.result)
				 // console.log("hello")
		//    })
		getLoanList
		.then((res)=>{
			if(res.code==200) {
				console.log(res.content.result)
				state.tableData = res.content?.result
			}
		})
		.catch((err)=>{
			console.log(err)
		})
    }
    onMounted(()=>{
      getLoanData()
    })

    return {
      ...toRefs(state),
      checkPass,
      checkFail,
      modifyAct,
      deleteAct,
      getLoanData,
    }
  },

}
</script>

<style scoped>
.index-container {
  min-height: 100%;
}
.el-card.is-always-shadow {
  min-height: 100%!important;
}
</style>