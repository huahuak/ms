
var timeDiff = {
		
		getTimeDiff(dateBegin,dateNow) {
			//Begin 减 Now 两个都为时间戳
			
		  //如果时间格式是正确的，那下面这一步转化时间格式就可以不用了
		  // var dateEnd = new Date();//获取当前时间
		  // var dateDiff = dateNow.getTime() - dateBegin;//时间差的毫秒数
		  var dateDiff = dateBegin - dateNow;//时间差的毫秒数
		  var dayDiff = Math.floor(dateDiff / (24 * 3600 * 1000));//计算出相差天数
		  var leave1=dateDiff%(24*3600*1000)  //计算天数后剩余的毫秒数
		  var hours=Math.floor(leave1/(3600*1000))//计算出小时数
		  //计算相差分钟数
		  var leave2=leave1%(3600*1000)  //计算小时数后剩余的毫秒数
		  var minutes=Math.floor(leave2/(60*1000))//计算相差分钟数
		  //计算相差秒数
		  var leave3=leave2%(60*1000)   //计算分钟数后剩余的毫秒数
		  var seconds=Math.round(leave3/1000)
		  var leave4=leave3%(60*1000)   //计算分钟数后剩余的毫秒数
		  var minseconds=Math.round(leave4/1000)
		  // var timeFn = "耗时："+dayDiff+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒"+minseconds+"毫秒";
		  
		  var statue = (minseconds > 0 ) ? 1 : 0 ;			//1表示正数 0表示负数 目标时间距离现在时间已是过去式
		  
		  var timeFn = {
			  "day": dayDiff,
			  "hour": hours,
			  "minute":minutes,
			  "second": seconds,
			  "minsecond":minseconds,
			  "statue":statue
		  }
		  return timeFn;
		}
	
}


export default timeDiff;
