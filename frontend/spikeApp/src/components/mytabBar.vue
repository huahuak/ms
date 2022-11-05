<template name='mytabBar'>
	<view>
		<u-tabbar
		:placeholder="false"
		:value="value"
		@change="name => value = name"
		:fixed="true"
		:safeAreaInsetBottom="false"
		activeColor="#e50000"
		>
			<u-tabbar-item text="首页" icon="home" name="home"></u-tabbar-item>
			<u-tabbar-item text="秒杀活动" icon="star" name="star" ></u-tabbar-item>
			<u-tabbar-item text="我的" name="account" icon="account" ></u-tabbar-item>
		</u-tabbar>
	</view>
</template>

<script>
	export default {
		name:'mytabBar',
		data(){
			return{
				"value":""
			}
		},
		// props: {
		//             movie: {
		//                 type: Object,
		//                 value: null
		//             }
		//         }
	}
</script>

<style scoped>

</style>
