<template>
  <el-card class="index-container">

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
          width="120"
      >
      </el-table-column>
      <el-table-column
          prop="activity_timeLimit"
          label="期限(月)"
          width="90"
      >
      </el-table-column>
    <!-- <el-table-column
          prop="activity_initMoney"
          label="起存金额"
          width="90"
      >
      </el-table-column> -->
      <el-table-column
          prop="activity_apr"
          label="年利率"
          width="80"
      >
	  </el-table-column>
	  <el-table-column
	      prop="activity_oneMaxAmount"
	      label="限购份数(每人)"
	      width="130"
	  >
      </el-table-column>
	  <el-table-column
	      prop="activity_perPrice"
	      label="每份价值(元)"
	      width="110"
	  >
      </el-table-column>
	  <el-table-column
	      prop="activity_totalQuantity"
	      label="产品参加总人数"
	      width="130"
	  >
	  </el-table-column>
	  <el-table-column
	      prop="activity_totalAmount"
	      label="产品总金额"
	      width="120"
	  >
	  </el-table-column>
      <el-table-column
          prop="rule.activity_dateRate"
          label="是否当日起息"
          width="120"
      >
      </el-table-column>
      <el-table-column
          prop="rule.activity_dawa"
          label="是否随存随取"
          width="120"
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
          width="150"
          fixed="right"
      >
        <template #default="scope">
          <a style="cursor: pointer; margin-right: 10px; color: #3888FA;" @click="checkPass(scope.row.activity_id)">通过者</a>
          <a style="cursor: pointer; margin-right: 10px; color: #3888FA;" @click="checkFail(scope.row.activity_id)">未通过者</a>
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
</template>

<script>
import { onMounted, onUnmounted, reactive, ref, toRefs,onBeforeMount } from 'vue'
import { ElMessage } from 'element-plus'
import { getDepositList,deleteDepositAct } from '@/api/table';

export default {
  name: 'CheckDepositActivities',
  components: {
  },
  setup() {
    const router = useRouter()
    const route = useRoute()
    const multipleTable = ref(null)
    const addGood = ref(null)
    const state = reactive({
      loading: false,
      tableData: [
        {
          "activity_name":"",
          "activity_timeLimit":'',
          "activity_apr":'',
          "activity_totalQuantity":'',
          "activity_oneMaxAmount":'',
          "activity_perPrice":'',
          "activity_moneyLimit":'',
          "activity_totalAmount":'',
          "activity_startTime":"",
          "activity_endTime":"",
          "rule":{
          	"activity_dateRate":'',
          	"activity_dawa":'',
          	"activity_ageFloor":'',
          	"activity_ageUp":'',
          },
        }
      ], // 数据列表
      multipleSelection: [], // 选中项
      total: 0, // 总条数
      currentPage: 1, // 当前页
      pageSize: 15, // 分页大小
      pageCount:0,
    })
    function checkPass(id) {
      router.push({path:'/Search',query:{"activity_id":id,"Pass":"1","type":"deposit"}})
    }
    function checkFail(id){
      router.push({path:'/Search',query:{"activity_id":id,"Pass":"0","type":"deposit"}})
    }
	function modifyAct(row){
	  let data = JSON.stringify(row)
	  router.push({path:'/ModifyDepositActivities',query:{data}})
	}
	function deleteAct(row){
	  deleteDepositAct(row.activity_id)
	  .then((res)=>{
		  if(res.code==200){
			  ElMessage({
				  message:'活动删除成功',
				  type:'success',
				  center:true,
			  })
			  this.getDepositData(state.pageSize,state.currentPage)
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

	const getDepositData = (pagelimit,currentPage) =>{
		let params = {page_limit:pagelimit, page_num:currentPage}
		getDepositList(params)
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
		getDepositData(state.pageSize,val)
		state.currentPage = val
	}

	onBeforeMount(()=>{
	  getDepositData(state.pageSize,state.currentPage)
	})


    return {
      ...toRefs(state),
      checkPass,
      checkFail,
      modifyAct,
      deleteAct,
      getDepositData,
      changePage,

    }
  }
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
