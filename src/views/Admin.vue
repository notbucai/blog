<template>
  <div class="admin">
    <el-container>
      <el-header>
        <h1 class="logo">
          Admin
        </h1>
      </el-header>
      <el-container>
        <el-aside width="240px">
          <el-menu :default-active="$route.path" :router="true" class="el-menu-vertical-demo" background-color="#000" text-color="#fff" active-text-color="#ffd04b">

            <el-menu-item index="/bucai_admin">
              <i class="iconfont o icon-home"></i>
              <span slot="title">首页</span>
            </el-menu-item>

            <el-menu-item index="/bucai_admin/article">
              <i class="el-icon-menu"></i>
              <span slot="title">文章管理</span>
            </el-menu-item>
            <el-menu-item index="/bucai_admin/links">
              <i class="el-icon-menu"></i>
              <span slot="title">友链管理</span>
            </el-menu-item>

          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/bucai_admin' }">后台首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{$route.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import "@/plugins/element-ui.js";

export default {
  name: "Admin",
  created() {
    this.$store.dispatch("hideLoading");
  },
  components: {},
  methods: {
    // handleOpen(key, keyPath) {
    // console.log(key, keyPath);
    // },
    // handleClose(key, keyPath) {
    // console.log(key, keyPath);
    // }
  }
};
</script>

<style lang="scss" scoped>
$Main-color: #909399;
.el {
  &-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    box-shadow: 10px 1px 15px rgba(0, 0, 0, 0.2);
    z-index: 10;
  }
  &-aside {
    background-color: #000;
  }
  &-menu {
    border-right: none;
  }
  &-breadcrumb {
    margin-bottom: 20px;
  }
}
.iconfont {
  &.o {
    margin-right: 5px;
    width: 24px;
    text-align: center;
    font-size: 18px;
    vertical-align: middle;
    display: inline-block;
  }
}
.admin {
  > .el-container {
    position: absolute;
    height: 100%;
    min-height: 600px;
    min-width: 900px;
    width: 100%;
  }
}

.logo {
  color: $Main-color * 0.6;
  font-size: 22px;
  letter-spacing: 1px;
}
</style>
