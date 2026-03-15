import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/api'

export const useUserStore = defineStore('user', () => {
    const token = ref(localStorage.getItem('token') || '')
    const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))

    function setToken(t) {
        token.value = t
        localStorage.setItem('token', t)
    }
    function setUserInfo(info) {
        userInfo.value = info
        localStorage.setItem('userInfo', JSON.stringify(info))
    }
    async function fetchUserInfo() {
        const res = await api.get('/user/info')
        setUserInfo(res.data)
        return res.data
    }
    function logout() {
        token.value = ''
        userInfo.value = null
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
    }
    const isAdmin = () => userInfo.value?.role === 'ADMIN'
    const isLoggedIn = () => !!token.value

    return { token, userInfo, setToken, setUserInfo, fetchUserInfo, logout, isAdmin, isLoggedIn }
})