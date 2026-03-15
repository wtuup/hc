import axios from 'axios'
import { ElMessage } from 'element-plus'

const instance = axios.create({ baseURL: '/api', timeout: 15000 })

instance.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) config.headers.Authorization = 'Bearer ' + token
    return config
})

instance.interceptors.response.use(
    response => response.data,
    error => {
        const status = error.response?.status
        if (status === 401) {
            localStorage.removeItem('token')
            localStorage.removeItem('userInfo')
            window.location.href = '/login'
        } else if (status === 403) {
            ElMessage.error('无权限访问')
        } else {
            ElMessage.error(error.response?.data?.msg || '请求失败')
        }
        return Promise.reject(error)
    }
)

export default instance