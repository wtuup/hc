import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    { path: '/login', component: () => import('@/views/Login.vue') },
    {
        path: '/',
        component: () => import('@/views/Layout.vue'),
        children: [
            { path: '', redirect: '/home' },
            { path: 'home', component: () => import('@/views/user/Home.vue') },
            { path: 'product/:id', component: () => import('@/views/user/ProductDetail.vue') },
            { path: 'cart', component: () => import('@/views/user/Cart.vue'), meta: { requiresAuth: true } },
            { path: 'checkout', component: () => import('@/views/user/Checkout.vue'), meta: { requiresAuth: true } },
            { path: 'orders', component: () => import('@/views/user/OrderList.vue'), meta: { requiresAuth: true } },
            { path: 'profile', component: () => import('@/views/user/Profile.vue'), meta: { requiresAuth: true } },
        ]
    },
    {
        path: '/admin',
        component: () => import('@/views/admin/AdminLayout.vue'),
        meta: { requiresAdmin: true },
        children: [
            { path: '', redirect: '/admin/products' },
            { path: 'products', component: () => import('@/views/admin/ProductManage.vue') },
            { path: 'orders',   component: () => import('@/views/admin/OrderManage.vue') },
            { path: 'stock',    component: () => import('@/views/admin/StockManage.vue') },
            { path: 'stats',    component: () => import('@/views/admin/Stats.vue') },
            { path: 'users',    component: () => import('@/views/admin/UserManage.vue') },
        ]
    }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    const userInfo = JSON.parse(localStorage.getItem('userInfo') || 'null')
    if (to.meta.requiresAuth && !token) return next('/login')
    if (to.meta.requiresAdmin && userInfo?.role !== 'ADMIN') return next('/')
    next()
})

export default router