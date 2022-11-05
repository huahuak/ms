<template>
  <el-card class="index-container">
   <div>
      <el-divider content-position="left" class='divider'><div class='divider-font'>{{title}}</div></el-divider>
    </div>
    <div style="float:right;padding-bottom: 10px">
      <el-input
          v-model="targetName"
          placeholder="按姓名查找用户"
          class="input-with-select"
          style="width: 300px;"
		  size='large'
      >
        <template #append>
          <el-button @click="searchByName">Search </el-button >
        </template>
      </el-input>
    </div>
    <el-table
		class="table"
        v-loading="loading"
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width:100%"
        @selection-change=""
		size="default"
		>
      <el-table-column
          prop="user_id"
          label="用户序号"
          width="150"
      >
      </el-table-column>
      <el-table-column
          prop="user_name"
          label="姓名"
          width="100"
      >
      </el-table-column>
      <el-table-column
          prop="user_gender"
          label="性别"
          width="100"
      >
      </el-table-column>
      <el-table-column
          prop="user_IDnumber"
          label="身份证号"
          width="150"
      >
      </el-table-column>
      <el-table-column
          prop="user_nation"
          label="国家"
          width="100"
      >
      </el-table-column>
      <el-table-column
          prop="user_age"
          label="年龄"
          width="100"
      >
      </el-table-column>
      <el-table-column
          prop="user_overdual"
          label="近三年逾期还款数"
          width="200"
      >
      </el-table-column>
      <el-table-column
          prop="user_overdual"
          label="是否在职"
          width="100"
      >
      </el-table-column>
      <el-table-column
          prop="user_dishonest"
          label="是否被列入失信人名单"
          width="200"
      ></el-table-column>
    </el-table>
    <!--总数超过一页，再展示分页器-->
    <!-- <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="changePage"
    /> -->
  </el-card>
</template>

<script>
import { onBeforeMount,onMounted, onUnmounted, reactive, ref, toRefs } from 'vue'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import axiosReq from '@/utils/axiosReq'
// 首页配置类型参数
const configTypeMap = {
  hot: 3,
  new: 4,
  recommend: 5
}
export default {
  name: 'Search',
  components: {

  },
  setup() {
    const router = useRouter()
    const route = useRoute()
    const title = ref('')
    const multipleTable = ref(null)
    const state = reactive({
      loading: false,
      targetName:'',
      tableData: [
        {
          "user_id":"",
          "user_name":"",
          "user_gender":"",
          "user_IDnumber":"",
          "user_nation":"",
          "user_age":'',
          "user_overdual":'',
          "user_employment":"1",
          "user_dishonest":'',
        },
      ], // 数据列表
      multipleSelection: [], // 选中项
      total: 0, // 总条数
      currentPage: 1, // 当前页
      pageSize: 10, // 分页大小
      type: 'add', // 操作类型
      configType: 3 // 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
    })
    onMounted(()=>{
      let temp1 = route.query.Pass;
      let temp2 = route.query.activity_id;
      let temp3 = route.query.type;
      if (temp3=='deposit') {
        temp3 = '存款产品'
      }else if(temp3=='loan'){
        temp3 = '贷款产品'
      }
      if (temp1=="1") {
        title.value = "序号为\"" + temp2 + "\"的" + temp3 + "秒杀活动初筛通过者";
        getPassed()
      } else if (temp1=="0") {
        title.value = "序号为\"" + temp2 + "\"的" + temp3 + "秒杀活动初筛未通过者";
        getUnpassed()
      }
    });
    const getPassed = () => {
			axiosReq({
			    url: 'api/activity/' + route.query.activity_id + '/passed',
			    data:{pageNumber: state.currentPage, pageSize: state.pageSize},
			    method:'get',
			    isParams:false,
			    bfLoading:true,
			  })
			  .then((res) => {
			  		state.tableData = res.content
			  		if(res.code==200) {
			  			state.tableData = res.content
			  			ElMessage({
			  				message:res.message,
			  				type:'success',
			  				center:true
			  			})
			  		} else {
			  			ElMessage({
			  				message:res.message,
			  				type:'success',
			  				center:true
			  			})
			  		}
			  })
			    .catch((err) => {
			  	ElMessage({
			  		message:err.message,
			  		type:'error',
			  		center:true
			  	})
			  })
    }
    const getUnpassed = () => {
		  axiosReq({
		      url: '/api/activity/'+  route.query.activity_id + '/unpassed',
		      data:{pageNumber: state.currentPage, pageSize: state.pageSize},
		      method:'get',
		      isParams:false,
		      bfLoading:true,
		    })
		    .then((res) => {
		  		state.tableData = res.content
				if(res.code==200) {
					state.tableData = res.content
					ElMessage({
						message:res.message,
						type:'success',
						center:true
					})
				} else {
					ElMessage({
						message:res.message,
						type:'success',
						center:true
					})
				}
		  })
		    .catch((err) => {
		  	ElMessage({
		  		message:err.message,
		  		type:'error',
		  		center:true
		  	})
		  })
    }
    const searchByName = () => {
      let temp1 =  route.query.Pass
      let temp2 = ''
      if (temp1 === "1"){
        temp2 = '/passed'
      }else if(temp1 === "0"){
        temp2 = '/unpassed'
      }

	  axiosReq({
	      url: '/activity/'+route.query.activity_id+temp2,
	      data:{name:state.targetName},
	      method:'get',
	      isParams:false,
	      bfLoading:true,
	    })
	    .then((res) => {
			if(res.code==200) {
				ElMessage({
					message:res.message,
					type:'success',
					center:true
				})
			} else {
				ElMessage({
					message:res.message,
					type:'error',
					center:true
				})
			}
			console.log(res)
		})
	    .catch((err) => {
			ElMessage({
				message:err.message,
				type:'error',
				center:true
			})
		})
    }


    return {
      ...toRefs(state),
      title,
      getPassed,
      getUnpassed,
      searchByName,
    }
  }

}
</script>

<style>

.index-container {
  height: 100%;
  width:100%;
  display:flex;
}
.divider{
	font-family:YouYuan;

}
.divider-font{
	background-color:#f4f4f4;
	font-size:20px;
}
.el-card__body{
	  height: 100%;
	  width:100%;
  }
/* .el-card.is-always-shadow {
  min-height: 100%!important;
} */
.input-with-select{
	float:left;
}
.table{
	width:100%;
	height:100%;
	flex:1;
}
</style>
