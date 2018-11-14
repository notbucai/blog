<template>
  <div class="blog">
    <ViewHeader />
    <!-- {{JSON.stringify($route)}} -->
    <main class="blog-content">
      <header class="blog-header">
        <h1 class="blog-title animated rubberBand">{{ Article.article.title || `发生错误` }}</h1>
        <p class="blog-meta">
          <span>发布与 {{ Article.article.timestamp | timeFormat }}</span>
          <span>|</span>
          <span>阅读次数 {{ Article.article.hits }}</span>
        </p>
      </header>
      <main class="blog-main animated zoomIn">
        <mavon-editor 
          default-open="preview" 
          :box-shadow="false" 
          :toolbars-flag="false" 
          :subfield="false" 
          :preview="false" 
          v-model="Article.article.content" />
      </main>
      <Tags :tags="Article.tags"/>
      <Comment :id_o="id" />
    </main>
    <ViewFooter />
    <BackTop/>
  </div>
</template>

<script>
import ViewHeader from "@/components/ViewHeader.vue";
import ViewFooter from "@/components/ViewFooter.vue";
import Comment from "@/components/Comment.vue";
import Tags from "@/components/Tags.vue";

import BackTop from "@/components/BackTop.vue";

import { mapState, mapActions } from "vuex";

export default {
  name: "Blog",
  components: {
    ViewHeader,
    ViewFooter,
    Comment,Tags,BackTop
  },
  async created() {
    // console.log(this.$route.params.id);
    const id = this.$route.params.id;
    const id_o = Number(id);
    this.id = id_o;
    if (id_o) {
      this.$store.dispatch("showLoading");
      const { code, message } = await this.getArticle(id_o);
    }
    document.title = this.Article.article.title +" - "+document.title ;
    this.$store.dispatch("hideLoading");
  },
  data() {
    return {
      id: 0
      // id_o
    };
  },
  computed: {
    ...mapState(["Article"])
  },
  methods: {
    ...mapActions(["getArticle"])
  }
};
</script>

<style lang="scss" scoped>

.blog {
  &-content {
    width: 900px;
    margin: 60px auto 30px;
  }
  &-header {
    text-align: center;
  }
  &-title {
    font-size: 26px;
    font-weight: normal;
  }
  &-meta {
    font-size: 12px;
    margin: 20px 0;
    span {
      margin-right: 10px;
      color: #888;
    }
  }
}
.comment {
  margin-top: 20px;
  background-color: #f9f9f9;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;

  &-content {
    width: 100%;
    height: 200px;
    border: 2px solid #f1f1f1;
    background-color: #fff;
    border-radius: 4px;
    font-size: 14px;
    color: #444;
    font-weight: 400;
    padding: 10px;
    box-sizing: border-box;
    resize: none;
    &:focus {
      outline: none;
    }
  }
  &-uname {
    width: 200px;
    padding: 10px 16px;
    margin-top: 20px;
    font-size: 16px;
    border: 2px solid #f1f1f1;
    background-color: #fff;
    color: #666;
    &:focus {
      outline: none;
    }
  }
  &-title {
    margin-bottom: 20px;
  }
  &-submit {
    margin-left: 20px;
    padding: 10px 16px;
    border: none;
    background-color: #000;
    color: #fff;
    cursor: pointer;
  }

  &-list {
    margin: 20px 0;

    &-title {
      margin-top: 40px;
      margin-bottom: 20px;
      border-left: 6px solid #000;
      padding-left: 16px;
    }
    &-item {
      margin-bottom: 20px;
      background-color: #fff;
      box-shadow: 2px 2px 8px #dddd;
      box-sizing: border-box;
      // border-radius: 6px;
      width: 100%;
      position: relative;
      padding: 20px;
      &-img {
        position: absolute;
        left: 20px;
        top: 20px;
        width: 48px;
        height: 48px;
        > img {
          border-radius: 6px;
          width: 100%;
          height: 100%;
        }
      }
      &-main {
        margin-left: 70px;
        width: 80%;
        &-info {
          display: flex;
          align-items: flex-end;
          &-name {
            font-size: 16px;
            color: #35aadc;
          }
          &-time {
            font-size: 12px;
            color: #888;
            margin-left: 10px;
          }
        }
        &-content {
          margin-top: 6px;
          font-size: 14px;
          line-height: 24px;
          overflow: hidden;
          pre {
            color: #444;
            white-space: pre-wrap;
            letter-spacing: 1px;
          }
          > .at {
            font-size: 12px;
            color: #35aadc;
            margin-right: 10px;
          }
        }
        &-reply {
          position: absolute;
          bottom: 0;
          right: 0;
          padding: 6px 12px;
          font-size: 14px;
          color: #999;
          cursor: pointer;
          background-color: #f1f1f1;
          &:hover {
            color: #fff;
            background-color: #35aadc;
          }
        }
      }
    }
  }
}
</style>

