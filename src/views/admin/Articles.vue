<template>
  <div class="articles">
    <div class="toolbar">
      <el-button type="primary" @click="addArticle">添加文章</el-button>
    </div>
    <el-table border :data="ArticleList" height="680" v-loading="loading" style="width: 100%">
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
          <el-button size="mini" @click="addArticle(scope.row.id)">Edit</el-button>
          <el-button size="mini" type="danger" @click="deleteArticle(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { formatterTime } from "@/plugins/utils.js";
import { mapActions, mapState } from "vuex";
import { DeleteArticle } from "@/plugins/api.js";

export default {
  name: "AmdinArticles",
  async created() {
    const { code, message } = await this.getAdminArticle();
    if (code == 999) {
      this.$message.error(message);
      this.$router.push("/login");
    }
  },
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
    ...mapActions(["getAdminArticle"]),
    formatterTime(row, column) {
      // console.log(row, column);
      const tiem = row[column.property];

      return formatterTime(tiem);
    },
    addArticle(id) {
      if (Number(id)) {
        this.$router.push("/bucai_admin/addAndEditArticle?id=" + id);
      } else {
        this.$router.push("/bucai_admin/addAndEditArticle");
      }
    },
    deleteArticle(id) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          await DeleteArticle(id);
          this.getAdminArticle();
          this.$message({
            type: "success",
            message: "删除成功!"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
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
