<template>
  <el-menu unique-opened style="min-height: 100%; width: 170px; overflow-x: hidden" background-color="rgb(139,69,19)"
    text-color="#fff" active-text-color="#fff" :collapse-transition="false" :collapse="isCollapse" router>
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="@/assets/作业本.png" style="
          width: 21px;
          height: 21px;
          margin: 0 0 -3px 0;
          -webkit-user-drag: none;
          -khtml-user-drag: none;
          -moz-user-drag: none;
          user-drag: none;
      " />
      <b style="color: white; margin-left: 5px;font-size: 20px; font-weight: 500" v-show="logoTextShow">作业管理系统</b>
    </div>
    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon" style="font-size: 14px;color: white;"></i>
          <span slot="title" style="font-size: 14px"> {{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id + ''">
          <template slot="title">
            <i :class="item.icon" style="font-size: 14px;color: white;"></i>
            <span slot="title" style="font-size: 14px"> {{ item.name }}</span>
          </template>
          <div v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon" style="font-size: 14px;color: white;"></i>
              <span slot="title" style="font-size: 14px"> {{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  data() {
    return {
      menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
      opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : []
    }
  },
}
</script>

<style scoped>
.el-menu-item.is-active {
  background-color: #222b40 !important;
}

.el-menu{
  user-select: none;
  border: solid 0 grey;
  background-color: #222b40 !important;
}
.el-menu-item{
  background-color: #222b40 !important;
}

.el-submenu /deep/ .el-submenu__title {
  background-color: #222b40 !important;
}

.el-menu-item:hover {
  background-color: #222 !important;
}

.el-submenu /deep/ .el-submenu__title:hover {
  background-color: #222 !important;
}

/*解决收缩菜单文字不消失问题*/
.el-menu--collapse span {
  visibility: hidden;
}
</style>
