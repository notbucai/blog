<template>
  <div class="articles">
    <div class="toolbar">
      <el-button type="primary">添加文章</el-button>
    </div>
    <el-table border :data="ArticleList" v-loading="loading" style="width: 100%">
      <el-table-column prop="id" label="文章ID" width="100" align="center">
      </el-table-column>

      <el-table-column label="标题" prop="title">
      </el-table-column>

      <el-table-column label="点击量" prop="hits">
      </el-table-column>

      <!-- hits -->
      <el-table-column label="发布时间" prop="timestamp" :formatter="formatterTime">
      </el-table-column>
      <el-table-column label="修改时间" prop="end_timestamp" :formatter="formatterTime">
      </el-table-column>

      <el-table-column align="left">
        <template slot="header" slot-scope="scope">
          <el-input v-model="search" size="medium" placeholder="输入关键字搜索" />
        </template>
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { formatterTime } from "@/plugins/utils.js";
import {  mapState } from "vuex";

export default {
  name: "AmdinArticles",
  data() {
    return {
      loading: false,
      search: ""
    };
  },
  computed: {
    ...mapState(["ArticleList"])
  },
  methods: {
    formatterTime(row, column) {
      // console.log(row, column);
      const tiem = row[column.property];

      return formatterTime(tiem);
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.articles {
  padding: 20px 10px;
  box-sizing: border-box;
  width: 100%;
  .toolbar {
    margin-bottom: 24px;
  }
}
</style>
