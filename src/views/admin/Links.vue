<template>
  <div class="articles">
    <div class="toolbar">
      <el-button type="primary" @click="addLink">添加友链</el-button>
    </div>
    <el-table border :data="Links" height="680" v-loading="loading" style="width: 100%">
      <el-table-column prop="id" label="ID" width="100" align="center">
      </el-table-column>

      <el-table-column label="标题" prop="title">
      </el-table-column>
      <el-table-column label="图标">
        <template slot-scope="scope">
          <img :src="scope.row.img_path" alt="" width="64" height="64">
        </template>
      </el-table-column>

      <!--img_path hits -->
      <el-table-column label="备注" prop="info">
      </el-table-column>
      <el-table-column label="地址" prop="link">
      </el-table-column>

      <el-table-column align="left">
        <template slot="header" slot-scope="scope">
          <el-input v-model="search" size="medium" placeholder="输入关键字搜索" />
        </template>
        <template slot-scope="scope">
          <el-button size="mini" @click="editLink(scope.row)">Edit</el-button>
          <el-button size="mini" type="danger" @click="deleteArticle(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="添加/修改友情链接" :visible.sync="dialogFormVisible">
      <el-form :model="fromData" ref="form">
        <el-form-item prop="title" :rules="{ required: true, message: '不能为空', trigger: 'blur' }">
          <el-input v-model="fromData.title" autocomplete="off">
            <template slot="prepend">标题</template>
          </el-input>
        </el-form-item>
        <el-form-item prop="info" :rules="{ required: true, message: '不能为空', trigger: 'blur' }">
          <el-input v-model="fromData.info" autocomplete="off">
            <template slot="prepend">备注</template>
          </el-input>
        </el-form-item>
        <el-form-item prop="link" :rules="{ required: true, message: '不能为空', trigger: 'blur' }">
          <el-input v-model="fromData.link" autocomplete="off">
            <template slot="prepend">链接地址</template>

          </el-input>
        </el-form-item>
        <el-form-item prop="img_path" :rules="{ required: true, message: '不能为空', trigger: 'blur' }">
          <el-input v-model="fromData.img_path" autocomplete="off">
            <template slot="prepend">图标地址</template>
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="AddAndEdit('form')">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { formatterTime } from "@/plugins/utils.js";
import { mapActions, mapState } from "vuex";
import { DeleteLink, AddLink, UpdateLink } from "@/plugins/api.js";

export default {
  name: "AmdinArticles",
  async created() {
    const { code, message } = await this.getLinks();
    if (code == 999) {
        this.$message.error(message);
        this.$router.push("/login");
      }
  },
  data() {
    return {
      loading: false,
      search: "",
      dialogFormVisible: false,
      fromData: {
        id: null,
        title: "",
        img_path: "",
        info: "",
        link: ""
      }
    };
  },
  computed: {
    ...mapState(["Links"])
  },
  methods: {
    ...mapActions(["getLinks"]),
    formatterTime(row, column) {
      // console.log(row, column);
      const tiem = row[column.property];

      return formatterTime(tiem);
    },
    addLink() {
      this.fromData = {
        id: null,
        title: "",
        img_path: "",
        info: "",
        link: ""
      };
      this.dialogFormVisible = true;
    },
    editLink(row){
      this.fromData = row;
      this.dialogFormVisible = true;
    },
    AddAndEdit(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          let error, res;
          if (this.fromData.id) {
            [error, res] = await UpdateLink(this.fromData);
          } else {
            [error, res] = await AddLink(this.fromData);
          }
          if (error) {
            this.$message.error(error);
            return;
          }
          const {
            data: { message, code }
          } = res;
          if (code !== 0) {
            this.$message({
              message: message,
              type: "warning"
            });
          } else {
            this.$message({
              message: message,
              type: "success"
            });
            this.getLinks();
            this.dialogFormVisible = false;
          }
        } else {
          return false;
        }
      });
    },

    deleteArticle(id) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          await DeleteLink(id);
          this.getLinks();
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
