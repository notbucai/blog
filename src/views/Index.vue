<template>
  <div class="index">
    <ViewHeader />
    <main class="index-main">
      <section id="posts">
        <IndexPost v-for="item in blogArticles" :key="item.id" :post="item" />
      </section>

      <section class="loading">
        <svg v-if="loadFlag" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" width="24px" height="30px" viewBox="0 0 24 30" style="enable-background:new 0 0 50 50;" xml:space="preserve">
          <rect x="0" y="0" width="4" height="10" fill="#409eff" transform="translate(0 15.1665)">
            <animateTransform attributeType="xml" attributeName="transform" type="translate" values="0 0; 0 20; 0 0" begin="0" dur="0.9s" repeatCount="indefinite"></animateTransform>
          </rect>
          <rect x="10" y="0" width="4" height="10" fill="#409eff" transform="translate(0 11.5002)">
            <animateTransform attributeType="xml" attributeName="transform" type="translate" values="0 0; 0 20; 0 0" begin="0.3s" dur="0.9s" repeatCount="indefinite"></animateTransform>
          </rect>
          <rect x="20" y="0" width="4" height="10" fill="#409eff" transform="translate(0 1.83315)">
            <animateTransform attributeType="xml" attributeName="transform" type="translate" values="0 0; 0 20; 0 0" begin="0.6s" dur="0.9s" repeatCount="indefinite"></animateTransform>
          </rect>
        </svg>
      </section>

    </main>

    <ViewFooter />

  </div>
</template>

<script>
import ViewHeader from "@/components/ViewHeader.vue";
import ViewFooter from "@/components/ViewFooter.vue";

import IndexPost from "@/components/IndexPost.vue";

import { ArticlePageServlet } from "@/plugins/api";
import { mapState, mapActions } from "vuex";

export default {
  name: "Index",
  components: { ViewHeader, ViewFooter, IndexPost },
  async created() {
    const { length } = this.blogArticles;
    if (length <= 0) {
      const { code, message } = await this.nextArticlePageServlet();

      this.$store.dispatch("hideLoading");
    }
  },
  data() {
    return {
      loadFlag: false,
    };
  },
  mounted() {
    window.addEventListener("scroll", this.scrollBottem);
    

  },
  computed: {
    ...mapState(["blogArticles", "loading"])
  },
  methods: {
    ...mapActions(["nextArticlePageServlet"]),
    async scrollBottem(e) {
      let winHeight = window.innerHeight,
        bodyHeight = document.documentElement.scrollHeight,
        boduTop = document.documentElement.scrollTop;
      
      if (bodyHeight - boduTop - winHeight <= 100) {
        if (this.loadFlag) {
          return;
        }

        this.loadFlag = true;

        setTimeout(async() => {
          boduTop = document.documentElement.scrollTop;
          const { code, message } = await this.nextArticlePageServlet();
          // 进行一系列的xxoo
          console.log(code,message);
          this.loadFlag = false;
          document.documentElement.scrollTop = boduTop;

        }, 600);
        
      }
    }
  }
};
</script>

<style lang="scss">
.index {
  &-main {
    width: 900px;
    margin: 0 auto;
  }
}
#posts {
  margin-top: -20px;
}
.loading {
  text-align: center;
}
</style>
