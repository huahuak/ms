import { createRouter, createWebHashHistory } from 'vue-router'
import Layout from '@/layout'

/* Router Modules */
import chartsRouter from './modules/charts'

export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/Login.vue'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404.vue'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401.vue'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Home',
        component: () => import('@/views/dashboard/index.vue'),
        //using el svg icon, the elSvgIcon first when at the same time using elSvgIcon and icon
        meta: { title: 'Home', elSvgIcon: 'Fold' }
      },
    ]
  },

  {
    path: '/ModifyLoanActivities',
  	component: Layout,
  	name: 'ModifyLoanActivities',
    redirect: '/ModifyLoanActivities',
    hidden: true,
		meta: {
		title: '秒杀活动修改',
		icon: 'nested'
		},
		children:[
			{
				path: '',
				component: () => import('@/views/myviews/ModifyLoanActivities.vue'),
				name: '修改活动',
				meta: { title: '修改活动' },
			},
		]
  },
	{
	  path: '/ModifyDepositActivities',
		component: Layout,
		name: 'ModifyDepositActivities',
	  redirect: '/ModifyDepositActivities',
	  hidden: true,
		meta: {
		title: '秒杀活动修改',
		icon: 'nested'
		},
		children:[
			{
				path: '',
				component: () => import('@/views/myviews/ModifyDepositActivities.vue'),
				name: '修改活动',
				meta: { title: '修改活动' },
			},
		]
	},
  {
    path: '/Search',
  	component: Layout,
  	name: 'Search',
    redirect: '/Search',
    hidden: true,
  	meta: {
  		title: '秒杀活动修改',
  		icon: 'nested'
  	},
  	children:[
  		{
  			path: '',
  			component: () => import('@/views/myviews/Search.vue'),
  			name: '查询初筛人员',
  			meta: { title: '查询初筛人员' },
  		},
  	]
  },

  {
    path: '/nested',
    component: Layout,
    redirect: '/nested/CheckLoanActivities',
    name: 'Nested',
    meta: {
      title: '秒杀活动管理',
      icon: 'nested'
    },
    children: [
      {
      path: 'CheckDepositActivities',
      component: () => import('@/views/nested/CheckDepositActivities.vue'),
      // name: '存款活动',
      // meta: { title: '存款活动' },
        name:'国内活动',
        meta:{ title:'国内活动' },
      },
		{
			path: 'CheckLoanActivities',
			component: () => import('@/views/nested/CheckLoanActivities.vue'),
			// name: '贷款活动',
			// meta: { title: '贷款活动' },
      name: '海外活动',
			meta: { title: '海外活动' },
		},
    ]
  },

  {
    path: '/myviews',
    component: Layout,
    redirect: '/myviews/AddLoanActivities',
    name: 'AddLoanAct',
    meta: {
      title: '秒杀活动发布',
      icon: 'nested'
    },
	children: [
	  {
	    path: 'AddDepositActivities',
	    component: () => import('@/views/myviews/AddDepositActivities.vue'), // Parent router-view
	    name: 'AddDepositActivities',
	    meta: { title: '发布国内活动' },
	  },
    {
	    path: 'AddLoanActivities',
	    component: () => import('@/views/myviews/AddLoanActivities.vue'), // Parent router-view
	    name: 'AddLoanActivities',
	    meta: { title: '发布海外活动' },
		},
		]
  },

]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/permission',
    component: Layout,
    redirect: '/permission/page',
    alwaysShow: true, // will always show the root menu
    name: 'Permission',
    meta: {
      title: '权限',
      icon: 'lock',
      roles: ['admin', 'editor'] // you can set roles in root nav
    },
    children: [
      {
        path: 'roleIndex',
        component: () => import('@/views/permission'),
        name: 'Permission',
        meta: {
          title: 'Role Index'
          //roles: ['admin'] // or you can only set roles in sub nav
        }
      },
      {
        path: 'page',
        component: () => import('@/views/permission/page.vue'),
        name: 'PagePermission',
        meta: {
          title: 'Page Permission',
          roles: ['admin'] // or you can only set roles in sub nav
        }
      },
      {
        path: 'directive',
        component: () => import('@/views/permission/directive.vue'),
        name: 'DirectivePermission',
        meta: {
          title: 'Directive Permission'
          // if do not set roles, means: this page does not require permission
        }
      },
      {
        path: 'code-index',
        component: () => import('@/views/permission/CodePermission.vue'),
        name: 'CodePermission',
        meta: {
          title: 'Code Index'
        }
      },
      {
        path: 'code-page',
        component: () => import('@/views/permission/CodePage.vue'),
        name: 'CodePage',
        meta: {
          title: 'Code Page',
          code: 1
        }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  // using pathMatch install of "*" in vue-router 4.0
  { path: '/:pathMatch(.*)', redirect: '/404', hidden: true }
]

const router = createRouter({
  history: createWebHashHistory(),
  scrollBehavior: () => ({ top: 0 }),
  routes: constantRoutes
})

export default router
