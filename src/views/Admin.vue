<template>
  <div class="admin">
    <el-container>
      <el-header>
        <h1 class="logo">
          admin
        </h1>
      </el-header>
      <el-container>
        <el-aside width="240px">
          <el-menu default-active="2" class="el-menu-vertical-demo" background-color="#202125" text-color="#efefef" active-text-color="#f44336">
            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>导航一</span>
              </template>
              <el-menu-item-group>
                <template slot="title">分组一</template>
                <el-menu-item index="1-1">选项1</el-menu-item>
                <el-menu-item index="1-2">选项2</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="分组2">
                <el-menu-item index="1-3">选项3</el-menu-item>
              </el-menu-item-group>
              <el-submenu index="1-4">
                <template slot="title">选项4</template>
                <el-menu-item index="1-4-1">选项1</el-menu-item>
              </el-submenu>
            </el-submenu>
            <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </el-menu-item>
            <el-menu-item index="3" disabled>
              <i class="el-icon-document"></i>
              <span slot="title">导航三</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-setting"></i>
              <span slot="title">导航四</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
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
$Main-color:#f44336;
.el {
  &-header {
    background-color: #000;
    display: flex;
    align-items: center;
  }
  &-aside {
    background-color: #202125;
  }
  &-menu {
    border-right: none;
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
  color: $Main-color;
  font-size: 22px;
}
</style>
