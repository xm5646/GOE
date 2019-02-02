import Main from '@/views/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'Login - 登录'
    },
    component: () => import('@/views/login.vue')
};

export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: () => import('@/views/error-page/404.vue')
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: () => import('@//views/error-page/403.vue')
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: () => import('@/views/error-page/500.vue')
};

export const preview = {
    path: '/preview',
    name: 'preview',
    component: () => import('@/views/form/article-publish/preview.vue')
};

export const locking = {
    path: '/locking',
    name: 'locking',
    component: () => import('@/views/main-components/lockscreen/components/locking-page.vue')
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    component: Main,
    children: [
        {path: 'home', title: {i18n: 'home'}, name: 'home_index', component: () => import('@/views/home/MyHome.vue')},
        {
            path: 'ownspace',
            title: '个人中心',
            name: 'ownspace_index',
            component: () => import('@/views/own-space/own-space.vue')
        },
        {
            path: 'order/:order_id',
            title: '订单详情',
            name: 'order-info',
            component: () => import('@/views/advanced-router/component/order-info.vue')
        }, // 用于展示动态路由
        {
            path: 'shopping',
            title: '购物详情',
            name: 'shopping',
            component: () => import('@/views/advanced-router/component/shopping-info.vue')
        }, // 用于展示带参路由
        {path: 'message', title: '消息中心', name: 'message_index', component: () => import('@/views/message/message.vue')}
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    {
        path: '/noticeManagement',
        icon: 'clipboard',
        name: 'noticeManagement',
        title: '公告管理',
        component: Main,
        children: [
            {
                path: 'addNotice',
                title: '新增公告',
                name: 'noticeInfo_addNotice',
                component: () => import('@/views/notice/AddNotice.vue')
            },
            {
                path: 'noticeList',
                title: '公告列表',
                name: 'noticeInfo_noticeList',
                component: () => import('@/views/notice/NoticeList.vue')
            }
        ]
    },
    {
        path: '/userManagement',
        icon: 'ios-people',
        name: 'userManagement',
        title: '会员管理',
        component: Main,
        children: [
            {
                path: 'userInfo',
                title: '会员列表',
                name: 'userInfo_userManagement',
                component: () => import('@/views/user-management/UserInfo.vue')
            },
            {
                path: 'organizationView',
                title: '组织结构图',
                name: 'organizationView_userManagement',
                component: () => import('@/views/user-management/OrganizationViewBody.vue')
            },
            {
                path: 'userEarning',
                title: '会员收入记录',
                name: 'userEarning_userManagement',
                component: () => import('@/views/user-management/UserEarning.vue')
            }
        ]
    },
    {
        path: '/transferManagement',
        icon: 'arrow-swap',
        name: 'transferManagement',
        title: '交易记录',
        component: Main,
        children: [
            {
                path: 'companyToUser',
                title: '公司拨发报单币',
                name: 'companyToUser_transfer',
                component: () => import('@/views/transfer-management/CompanyToUser.vue')
            },
            {
                path: 'userToUser',
                title: '会员间转账记录',
                name: 'userToUser_transfer',
                component: () => import('@/views/transfer-management/UserToUser.vue')
            },
            {
                path: 'userReport',
                title: '会员报单记录',
                name: 'userReport_transfer',
                component: () => import('@/views/transfer-management/UserReport.vue')
            },
            {
                path: 'convertConsume',
                title: '奖金兑换报单币',
                name: 'convertConsume_transfer',
                component: () => import('@/views/transfer-management/BonusConvertToConsume.vue')
            }
        ]
    },
    {
        path: '/bonusManagement',
        icon: 'ios-paper',
        name: 'bonusManagement',
        title: '奖金管理',
        component: Main,
        children: [
            {
                path: 'bonusPayList',
                title: '奖金发放记录',
                name: 'bonusPayList_bonusManagement',
                component: () => import('@/views/bonus-management/BonusPayList.vue')
            },
            {
                path: 'payBonusByAdmin',
                title: '手工分红',
                name: 'payBonusByAdmin_bonusManagement',
                component: () => import('@/views/bonus-management/PayBonusByAdmin.vue')
            }
        ]
    },
    {
        path: '/cashManagement',
        icon: 'cash',
        name: 'cashManagement',
        title: '提现管理',
        component: Main,
        children: [
            {
                path: 'getCashManagement',
                title: '待处理提现',
                name: 'getCashManagement_cashManagement',
                component: () => import('@/views/cash-management/GetCashManagement.vue')
            },
            {
                path: 'batchCashManagement',
                title: '批量处理提现',
                name: 'batchCashManagement_cashManagement',
                component: () => import('@/views/cash-management/BatchHandle.vue')
            },
            {
                path: 'getCashList',
                title: '提现记录',
                name: 'getCashList_cashManagement',
                component: () => import('@/views/cash-management/GetCashList.vue')
            }
        ]
    },
    {
        path: '/orderManagement',
        icon: 'ios-briefcase',
        name: 'orderManagement',
        title: '订单管理',
        component: Main,
        children: [
            {
                path: 'reConsumeWaitSendList',
                title: '待处理重销订单',
                name: 'reConsumeWaitSendList_orderManagement',
                component: () => import('@/views/order-management/ReConsumeWaitSendList.vue')
            },
            {
                path: 'exchangeProductWaitSendList',
                title: '待处理积分兑换订单',
                name: 'exchangeProductWaitSendList_orderManagement',
                component: () => import('@/views/order-management/ExchangeProductWaitSendList.vue')
            },
            {
                path: 'orderList',
                title: '订单记录',
                name: 'orderList_orderManagement',
                component: () => import('@/views/order-management/GetOrderList.vue')
            }
        ]
    },
    // {
    //     path: '/access',
    //     icon: 'key',
    //     name: 'access',
    //     title: '权限管理',
    //     component: Main,
    //     children: [
    //         {path: 'index', title: '权限管理', name: 'access_index', component: () => import('@/views/access/access.vue')}
    //     ]
    // },
    // {
    //     path: '/access-test',
    //     icon: 'lock-combination',
    //     title: '权限测试页',
    //     name: 'accesstest',
    //     access: 0,
    //     component: Main,
    //     children: [
    //         {
    //             path: 'index',
    //             title: '权限测试页',
    //             name: 'accesstest_index',
    //             access: 0,
    //             component: () => import('@/views/access/access-test.vue')
    //         }
    //     ]
    // },
    // {
    //     path: '/international',
    //     icon: 'earth',
    //     title: {i18n: 'international'},
    //     name: 'international',
    //     component: Main,
    //     children: [
    //         {
    //             path: 'index',
    //             title: {i18n: 'international'},
    //             name: 'international_index',
    //             component: () => import('@/views/international/international.vue')
    //         }
    //     ]
    // },
    // {
    //     path: '/component',
    //     icon: 'social-buffer',
    //     name: 'component',
    //     title: '组件',
    //     component: Main,
    //     children: [
    //         {
    //             path: 'text-editor',
    //             icon: 'compose',
    //             name: 'text-editor',
    //             title: '富文本编辑器',
    //             component: () => import('@/views/my-components/text-editor/text-editor.vue')
    //         },
    //         {
    //             path: 'md-editor',
    //             icon: 'pound',
    //             name: 'md-editor',
    //             title: 'Markdown编辑器',
    //             component: () => import('@/views/my-components/markdown-editor/markdown-editor.vue')
    //         },
    //         {
    //             path: 'image-editor',
    //             icon: 'crop',
    //             name: 'image-editor',
    //             title: '图片预览编辑',
    //             component: () => import('@/views/my-components/image-editor/image-editor.vue')
    //         },
    //         {
    //             path: 'draggable-list',
    //             icon: 'arrow-move',
    //             name: 'draggable-list',
    //             title: '可拖拽列表',
    //             component: () => import('@/views/my-components/draggable-list/draggable-list.vue')
    //         },
    //         {
    //             path: 'area-linkage',
    //             icon: 'ios-more',
    //             name: 'area-linkage',
    //             title: '城市级联',
    //             component: () => import('@/views/my-components/area-linkage/area-linkage.vue')
    //         },
    //         {
    //             path: 'file-upload',
    //             icon: 'android-upload',
    //             name: 'file-upload',
    //             title: '文件上传',
    //             component: () => import('@/views/my-components/file-upload/file-upload.vue')
    //         },
    //         {
    //             path: 'count-to',
    //             icon: 'arrow-graph-up-right',
    //             name: 'count-to',
    //             title: '数字渐变',
    //             // component: () => import('@/views/my-components/count-to/count-to.vue')
    //             component: () => import('@/views/my-components/count-to/count-to.vue')
    //         },
    //         {
    //             path: 'split-pane-page',
    //             icon: 'ios-pause',
    //             name: 'split-pane-page',
    //             title: 'split-pane',
    //             component: () => import('@/views/my-components/split-pane/split-pane-page.vue')
    //         }
    //     ]
    // },
    // {
    //     path: '/form',
    //     icon: 'android-checkbox',
    //     name: 'form',
    //     title: '表单编辑',
    //     component: Main,
    //     children: [
    //         {
    //             path: 'artical-publish',
    //             title: '文章发布',
    //             name: 'artical-publish',
    //             icon: 'compose',
    //             component: () => import('@/views/form/article-publish/article-publish.vue')
    //         },
    //         {
    //             path: 'workflow',
    //             title: '工作流',
    //             name: 'workflow',
    //             icon: 'arrow-swap',
    //             component: () => import('@/views/form/work-flow/work-flow.vue')
    //         }
    //
    //     ]
    // },
    // // {
    // //     path: '/charts',
    // //     icon: 'ios-analytics',
    // //     name: 'charts',
    // //     title: '图表',
    // //     component: Main,
    // //     children: [
    // //         { path: 'pie', title: '饼状图', name: 'pie', icon: 'ios-pie', component: resolve => { require('@/views/access/access.vue') },
    // //         { path: 'histogram', title: '柱状图', name: 'histogram', icon: 'stats-bars', component: resolve => { require('@/views/access/access.vue') }
    //
    // //     ]
    // // },
    // {
    //     path: '/tables',
    //     icon: 'ios-grid-view',
    //     name: 'tables',
    //     title: '表格',
    //     component: Main,
    //     children: [
    //         {
    //             path: 'dragableTable',
    //             title: '可拖拽排序',
    //             name: 'dragable-table',
    //             icon: 'arrow-move',
    //             component: () => import('@/views/tables/dragable-table.vue')
    //         },
    //         {
    //             path: 'editableTable',
    //             title: '可编辑表格',
    //             name: 'editable-table',
    //             icon: 'edit',
    //             component: () => import('@/views/tables/editable-table.vue')
    //         },
    //         {
    //             path: 'searchableTable',
    //             title: '可搜索表格',
    //             name: 'searchable-table',
    //             icon: 'search',
    //             component: () => import('@/views/tables/searchable-table.vue')
    //         },
    //         {
    //             path: 'exportableTable',
    //             title: '表格导出数据',
    //             name: 'exportable-table',
    //             icon: 'code-download',
    //             component: () => import('@/views/tables/exportable-table.vue')
    //         },
    //         {
    //             path: 'table2image',
    //             title: '表格转图片',
    //             name: 'table-to-image',
    //             icon: 'images',
    //             component: () => import('@/views/tables/table-to-image.vue')
    //         }
    //     ]
    // },
    // {
    //     path: '/advanced-router',
    //     icon: 'ios-infinite',
    //     name: 'advanced-router',
    //     title: '高级路由',
    //     component: Main,
    //     children: [
    //         {
    //             path: 'mutative-router',
    //             title: '动态路由',
    //             name: 'mutative-router',
    //             icon: 'link',
    //             component: () => import('@/views/advanced-router/mutative-router.vue')
    //         },
    //         {
    //             path: 'argument-page',
    //             title: '带参页面',
    //             name: 'argument-page',
    //             icon: 'android-send',
    //             component: () => import('@/views/advanced-router/argument-page.vue')
    //         }
    //     ]
    // },
    // {
    //     path: '/error-page',
    //     icon: 'android-sad',
    //     title: '错误页面',
    //     name: 'errorpage',
    //     component: Main,
    //     children: [
    //         {
    //             path: 'index',
    //             title: '错误页面',
    //             name: 'errorpage_index',
    //             component: () => import('@/views/error-page/error-page.vue')
    //         }
    //     ]
    // }
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter,
    preview,
    locking,
    ...appRouter,
    page500,
    page403,
    page404
];
