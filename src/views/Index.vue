<template>
  <div class="index">
    <ViewHeader />
    <main class="index-main">
      <section id="posts">

        <transition-group 
          enter-active-class="animated fadeInDown" 
          leave-active-class="animated fadeOutDown">
          <IndexPost 
            v-for="item in blogArticles" 
            :key="item.id" 
            :post="item" />
        </transition-group>

      </section>
    </main>

    <ViewFooter />
    <BackTop />
  </div>
</template>

<script>
import ViewHeader from "@/components/ViewHeader.vue";
import ViewFooter from "@/components/ViewFooter.vue";

import IndexPost from "@/components/IndexPost.vue";
import BackTop from "@/components/BackTop.vue";

import { ArticlePage } from "@/plugins/api";
import { mapState, mapActions } from "vuex";

export default {
  name: "Index",
  components: {
    ViewHeader,
    ViewFooter,
    IndexPost,
    BackTop
  },
  async created() {
    const { length } = this.blogArticles;
    if (length <= 0) {
      const { code, message } = await this.nextArticlePage();

      this.$store.dispatch("hideLoading");
    }
  },
  data() {
    return {
      loadFlag: false
    };
  },
  mounted() {
    window.addEventListener("scroll", this.scrollBottem);
  },
  computed: {
    ...mapState(["blogArticles", "loading"])
  },
  methods: {
    ...mapActions(["nextArticlePage"]),
    scrollBottem(e) {
      let winHeight = window.innerHeight,
        bodyHeight = document.documentElement.scrollHeight,
        boduTop = document.documentElement.scrollTop;

      if (bodyHeight - boduTop - winHeight <= 100) {
        if (this.loadFlag) {
          return;
        }

        this.loadFlag = true;

        setTimeout(async () => {
          boduTop = document.documentElement.scrollTop;
          const { code, message } = await this.nextArticlePage();
          // 进行一系列的xxoo
          console.log(code, message);

          if (code == 3) {
            window.removeEventListener("scroll", this.scrollBottem);
          }

          this.loadFlag = false;
          // document.documentElement.scrollTop = boduTop;
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
