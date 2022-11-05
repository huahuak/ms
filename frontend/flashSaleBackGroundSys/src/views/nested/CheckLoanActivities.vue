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
			size='default'
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
	          width="130"
	      >
	      </el-table-column>
		  <el-table-column
		      prop="activity_oneMaxAmount"
		      label="限购份数(每人)"
		      width="100"
		  >
		  </el-table-column>
		  <el-table-column
		      prop="activity_perPrice"
		      label="价值(每份)"
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
	          width="140"
	          fixed="right"
	      >
	        <template #default="scope">
	          <a style="cursor: pointer; margin-right: 10px; color: #3888FA;" @click="checkPass(scope.row.activity_id)">通过</a>
	          <a style="cursor: pointer; margin-right: 10px; color: #3888FA" @click="checkFail(scope.row.activity_id)">未通过</a>
	        </template>
	     </el-table-column>
	   <el-table-column
	          label="操作"
	          width="120"
	          fixed="right"
	      >
	        <template #default="scope">
	          <a style="cursor: pointer; margin-right: 10px;color: #3888FA;" @click="modifyAct(scope.row)">修改</a>
	          <a style="cursor: pointer; margin-right: 10px;color: #3888FA;" @click="deleteAct(scope.row)">删除</a>
	        </template>
	      </el-table-column>
	    </el-table>
	    <!--总数超过一页，再展示分页器-->
	   <!-- <el-pagination
	        background
	        layout="prev, pager, next"
	        :page-size="pageSize"
	        :current-page="currentPage"
	        @current-change="changePage"
	    /> -->
		<view style="padding: 10px;">
			<el-pagination
			    background
				:page-size="pageSize"
				:current-page="currentPage"
			  layout="prev, pager, next"
				@current-change="changePage"
        :page-count="pageCount"
			  />
		</view>
	  </el-card>
  </div>
</template>



<script>
import {onBeforeMount, onMounted, onUnmounted, reactive, ref, toRefs} from 'vue';
import { ElMessage } from 'element-plus';
import {getLoanList,deleteLoanAct} from '@/api/table';

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
				"activity_name": "",
				"activity_id": "",
				"activity_oneMaxAmout":"",
				"activity_perPrice":"",
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
      pageSize: 15, // 分页大小
      pageCount:0,
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
	  deleteLoanAct(row.activity_id)
	  .then((res)=>{
		  if(res.code==200){
			  ElMessage({
				  message:'活动删除成功',
				  type:'success',
				  center:true,
			  })
			  this.getLoanData(state.pageSize,state.currentPage)
		  } else {
			  ElMessage({
				  message:res.message,
				  type:'error',
				  center:true
			  })
		  }
	  }).catch((err)=>{
		  ElMessage({
		  	message:err.message,
		  	type:'error',
		  	center:true
		  })
	  })
    }
    const getLoanData = (pagelimit,currentPage) =>{
		let params = {page_limit:pagelimit, page_num:currentPage}
		getLoanList(params)
		.then((res)=>{
			if(res.code==200){
				state.tableData = res.content.result
        state.pageCount = res.content.pages
				console.log(state.tableData)
			}else if(res.code==400) {
				ElMessage({
					message:res.message,
					type:'error',
					center:true
				})
			}
			console.log(res)
		})
		.catch((err)=>{
			ElMessage({
				message:err.message,
				type:'error',
				center:true
			})
		})
    }
	const changePage = (val) => {
		getLoanData(state.pageSize,val)
		state.currentPage = val
	}

    onBeforeMount(()=>{
      getLoanData(state.pageSize,state.currentPage)
    })

    return {
      ...toRefs(state),
      checkPass,
      checkFail,
      modifyAct,
      deleteAct,
      getLoanData,
	  changePage,
    }
  },

}
</script>

<style scope>
.index-container {
  min-height: 100%;
}
.el-card.is-always-shadow {
  min-height: 100%!important;
}
</style>
