<template>
  <div class="article">

    <main class="main">
      <h2 class="showUrl" v-if="formData.id">文章地址：<a :href="`/blog/${formData.id}`" target="_blank">/blog/{{formData.id}}</a></h2>
      <div class="form-item title">
        <el-input placeholder="请输入标题" v-model="formData.title" clearable>
          <template slot="prepend">标 题</template>
        </el-input>
      </div>

      <div class="form-item info">
        <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 8}" placeholder="请输入简介" v-model="formData.info"></el-input>
      </div>
      <div class="form-item content">
        <mavon-editor default-open="preview" v-model="formData.content" />
      </div>

    </main>
    <aside class="aside">
      <h3>文章标签</h3>
      <el-autocomplete v-model="tag" value-key="name" @keyup.enter="addTag" :fetch-suggestions="querySearchAsync" @select="handleSelect" placeholder="请输入内容"></el-autocomplete>
      <div class="tags">
        <div class="tag" v-for="(item,index) in formData.tags" :key="index">
          <i @click="delectTag(index)">x</i>
          <span>{{item}}</span>
        </div>
      </div>
      <div class="ok">
        <el-button type="primary" @click="onOk">提交</el-button>
        <br>
        <el-button type="info" @click="$router.push('/bucai_admin/article')">回到文章管理</el-button>

      </div>
    </aside>

  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { AddArticle, UpdateArticle, Article } from "@/plugins/api.js";
export default {
  name: "AddAndEditArticle",
  async created() {
    const id = (this.formData.id = this.$route.query.id);
    if (id) {
      const [error, res] = await Article(id);
      if (error) {
        this.$message.error(error);
        return;
      }
      const {
        data: {
          code,message,
          result: {
            article: { title, info, content },
            tags
          }
        }
      } = res;
      if (code === 0) {
        this.formData.title = title;
        this.formData.info = info;
        this.formData.content = content;
        this.formData.tags = tags.map(item => item.name);
      }
      if (code == 999) {
        this.$message.error(message);
        this.$router.push("/login");
      }
    }
    this.getTags();
  },
  data() {
    return {
      isEdit: false,
      tag: "",
      timeout: 0,
      formData: {
        id: null,
        title: "",
        info: "",
        content: "",
        tags: []
      }
    };
  },
  computed: {
    ...mapState(["Tags"])
  },
  watch: {
    "formData.id"() {
      this.isEdit = false;
      if (this.formData.id) {
        this.isEdit = true;
      }
    }
  },
  methods: {
    ...mapActions(["getTags"]),
    querySearchAsync(queryString, cb) {
      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        let { Tags } = this;
        const resultTags = Tags.filter(item => {
          const rex = new RegExp(`.*?${queryString}.*?`);
          return rex.test(item.name);
        });
        const endTags = [{ name: queryString }, ...resultTags];
        cb(endTags);
      }, 100);
    },
    addTag(name) {
      const { tags } = this.formData;

      this.formData.tags = [...new Set([name, ...tags])];
    },
    handleSelect(item) {
      // console.log(item);

      this.addTag(item.name);
      this.tag = "";
    },
    delectTag(i) {
      this.formData.tags.splice(i, 1);
    },
    async onOk() {
      let error, res;

      if (this.isEdit && this.formData.id) {
        [error, res] = await UpdateArticle(this.formData);
      } else {
        [error, res] = await AddArticle(this.formData);
      }
      // 提交

      if (error) {
        this.$message.error(error);
        return;
      }
      const {
        data: { message, code, result }
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
        const { id } = result;
        this.formData.id = id;
      }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.article {
  display: flex;
}
.main {
  flex-grow: 1;
  .form-item {
    margin: 40px 0;
  }
  .showUrl {
    margin: 40px 0;
    font-size: 18px;
    display: flex;
    align-items: center;
    > a {
      font-size: 18px;
    }
  }
}
.aside {
  width: 300px;
  margin: 40px;
  margin-left: 20px;
  min-height: 400px;
  h3 {
    margin-bottom: 20px;
  }
  .tags {
    width: 100%;
    height: 300px;
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    margin-bottom: 20px;
    .tag {
      margin: 10px;
      font-size: 12px;
      color: #666;
      > i {
        background-color: #000;
        color: #fff;
        margin: 2px;
        width: 14px;
        height: 14px;
        text-align: center;
        line-height: 14px;
        display: inline-block;
        border-radius: 50%;
        cursor: pointer;
      }
    }
  }
  .el-input,
  .el-button,
  .el-autocomplete {
    width: 100%;
    display: block;
    > input {
      width: 100%;
    }
  }
}
</style>
