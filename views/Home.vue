<t<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item><i
          class="iconfont icon-home-menu"          style="margin: 5px; font-size: 22px"
      >
        首页</i></el-breadcrumb-item>
    </el-breadcrumb>
    <el-row style="background-color: white;padding: 0 0 50px 25px;">

      <el-col :span="18" style="text-align: center; position: relative;">
        <img :src="require('@/assets/bg.png')" style="height: 100vh; width: 85vw; display: block; margin: 0 auto;">
        <div class="welcome-text">
          <h1>欢迎访问作业管理系统</h1>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style>.welcome-text {
  position: absolute;
  top: 0%;
  left: 50%;

  color: white; /* 根据图片背景调整颜色 */
  font-size: 24px;
  text-align: center;
  background-color: rgba(0, 0, 0, 0.5); /* 半透明背景以提高可读性 */



}
</style>

<script>
import * as echarts from 'echarts'
import '@/utils/china.js'

export default {
  name: "Home",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      total: {},
      today: {},
      extData: {}
    }
  },
  mounted() {
    const options = {
      //标题样式
      title: {
        text: '中国疫情地图',
        x: "center",
        y: '0%',
        textStyle: {
          fontSize: 20,
          color: "#333"
        },
      },
      //这里设置提示框 (鼠标悬浮效果)
      tooltip: {
        //数据项图形触发
        trigger: 'item',
        //提示框浮层的背景颜色。 (鼠标悬浮后的提示框背景颜色)
        backgroundColor: "white",
        //字符串模板(地图): {a}（系列名称），{b}（区域名称），{c}（合并数值）,{d}（无）
        formatter: '地区：{b}<br/>现有确诊病例：{c}'
      },
      //视觉映射组件
      visualMap: {
        top: 'center',
        left: 'left',
        // 数据的范围
        min: 10,
        max: 100,
        text: ['高', '低'],
        realtime: true,  //拖拽时，是否实时更新
        calculable: true,  //是否显示拖拽用的手柄
        inRange: {
          // 颜色分布
          color: ['white', 'Tomato', 'orangered']
        }
      },
      series: [
        {
          name: '确诊',
          type: 'map',
          mapType: 'china',
          roam: true,
          //是否开启鼠标缩放和平移漫游
          itemStyle: {
            //地图区域的多边形 图形样式
            normal: {
              //是图形在默认状态下的样式
              label: {
                show: true,//是否显示标签
                textStyle: {
                  color: "black"
                }
              }
            },
            zoom: 1,
            //地图缩放比例,默认为1
            emphasis: {
              //是图形在高亮状态下的样式,比如在鼠标悬浮或者图例联动高亮时
              label: { show: true }
            }
          },
          top: '0%',
          left: '15%',
          data: []
        }
      ]
    }
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);

    // 获取数据
    this.request.get("/echarts/epidemic").then(res => {
      this.total = res.data.chinaTotal.total
      this.today = res.data.chinaTotal.today
      this.extData = res.data.chinaTotal.extData

      let data = [{ name: '南海诸岛', value: 0 }]
      res.data.areaTree[2].children.forEach(item => {
        data.push({ name: item.name, value: item.total.confirm - item.total.dead - item.total.heal })

        options.series[0].data = data
        myChart.setOption(options)
      })
    })

  }
}
</script>

<style>
.item {
  text-align: center;
  width: 150px;
  height: 150px;
  margin-right: 5px;
  margin-bottom: 5px;
}

.item:nth-child(3n) {
  margin-right: 0;
}

.item_title {
  font-size: 16px;
  font-weight: bold;
  margin-top: 20px;
}

.item_num {
  margin-top: 10px;
  font-size: 30px;
  font-weight: bold;
}

.item_num_plus {
  font-size: 16px;
  color: #666;
}

.welcome-one {
  font-size: 28px;
  padding-bottom: 20px;
  color: black;
  line-height: 500px;
  text-align: center;
}
</style>
