<template>
  <div>
    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :span="8">
        <el-card shadow="hover" style="text-align:center">
          <div style="font-size:36px;color:#409EFF;font-weight:bold">{{ totalUsers }}</div>
          <div style="color:#999;margin-top:8px">总用户数</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" style="text-align:center">
          <div style="font-size:36px;color:#e74c3c;font-weight:bold">¥{{ totalSales }}</div>
          <div style="color:#999;margin-top:8px">总销售额</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" style="text-align:center">
          <div style="font-size:36px;color:#67c23a;font-weight:bold">{{ ranking.length }}</div>
          <div style="color:#999;margin-top:8px">在售商品数</div>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="hover" style="margin-bottom:20px">
      <template #header>销量 Top 10 商品</template>
      <div ref="barRef" style="height:350px"></div>
    </el-card>

    <el-card shadow="hover">
      <template #header>
        近
        <el-select v-model="trendDays" @change="loadTrend" style="width:80px;margin:0 8px">
          <el-option :value="7" label="7天" />
          <el-option :value="30" label="30天" />
        </el-select>
        天销售额趋势
      </template>
      <div ref="lineRef" style="height:350px"></div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'
import api from '@/api'

const totalUsers = ref(0)
const totalSales = ref('0.00')
const ranking = ref([])
const trendDays = ref(30)
const barRef = ref(null)
const lineRef = ref(null)
let barChart = null
let lineChart = null

onMounted(async () => {
  const [u, s, r] = await Promise.all([
    api.get('/admin/analytics/total-users'),
    api.get('/admin/analytics/total-sales'),
    api.get('/admin/analytics/sales-ranking', { params: { top: 10 } })
  ])
  totalUsers.value = u.data
  totalSales.value = Number(s.data).toFixed(2)
  ranking.value = r.data || []

  await nextTick()

  // 初始化柱状图
  barChart = echarts.init(barRef.value)
  barChart.setOption({
    tooltip: { trigger: 'axis' },
    grid: { bottom: 80 },
    xAxis: {
      type: 'category',
      data: ranking.value.map(p => p.name),
      axisLabel: { rotate: 30, interval: 0 }
    },
    yAxis: { type: 'value', name: '销量（件）' },
    series: [{
      type: 'bar',
      data: ranking.value.map(p => p.sales),
      itemStyle: { color: '#409EFF' },
      label: { show: true, position: 'top' }
    }]
  })

  await loadTrend()

  // 响应窗口大小变化
  window.addEventListener('resize', () => {
    barChart?.resize()
    lineChart?.resize()
  })
})

const loadTrend = async () => {
  const res = await api.get('/admin/analytics/sales-trend', { params: { days: trendDays.value } })
  const trend = res.data || []

  await nextTick()

  if (!lineChart) lineChart = echarts.init(lineRef.value)
  lineChart.setOption({
    tooltip: { trigger: 'axis', formatter: (p) => `${p[0].name}<br/>销售额：¥${p[0].value}` },
    xAxis: {
      type: 'category',
      data: trend.map(t => t.date),
      axisLabel: { rotate: 30, interval: Math.floor(trend.length / 7) }
    },
    yAxis: { type: 'value', name: '销售额（元）' },
    series: [{
      type: 'line',
      data: trend.map(t => Number(t.sales).toFixed(2)),
      smooth: true,
      areaStyle: { color: 'rgba(64,158,255,0.15)' },
      itemStyle: { color: '#409EFF' },
      lineStyle: { width: 2 }
    }]
  })
}
</script>