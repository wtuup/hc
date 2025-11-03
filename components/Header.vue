<template>
  <div style="line-height: 100px;height: 100px; display: flex">
    <div style="flex: 1;height: 100px;">
      <!-- <div :class="collapseBtnClass" style="cursor: pointer;margin: 0 0 20px 0; font-size: 18px" @click="collapse"></div> -->

      <el-breadcrumb separator="/" style="display: inline-block; margin: 0 0 15px 10px">
        <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 150px; cursor: pointer; text-align: right;">

      <div style="display: inline-block;">
        <img :src="$store.state.baseApi + user.avatarUrl" alt=""
             style="width: 60px; border-radius: 50%;border: lightgrey 1px solid; position: relative; top: 1px; right: 5px">
        <span style="height: 60px; position: relative; top: -20px; line-height: 60px display: inline-block;">{{
            user.nickname
          }}</span><i
          class="el-icon-arrow-down" style="margin-left: 5px; position: relative; top: -20px;"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px;">
          <router-link to="/password">修改密码</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px;">
          <router-link to="/person">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px;">
          <div style="text-decoration: none" @click="logout">退出</div>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    user: Object
  },
  computed: {
    currentPathName() {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  },
  data() {
    return {}
  },
  methods: {
    collapse() {
      // this.$parent.$parent.$parent.$parent.collapse()  // 通过4个 $parent 找到父组件，从而调用其折叠方法
      this.$emit("asideCollapse")
    },
    logout() {
      this.$store.commit("logout")
      this.$message.success("退出成功")
    }
  }
}
</script>

<style scoped>
</style>
