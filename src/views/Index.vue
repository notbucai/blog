<template>
  <div class="index">
    <ViewHeader />
    <main class="index-main contain">
      <section id="posts">

        <transition-group enter-active-class="animated fadeInDown" leave-active-class="animated fadeOutDown">
          <IndexPost v-for="item in blogArticles" :key="item.id" :post="item" />
        </transition-group>
        <div v-if="loadFlag" class="loading">
          <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" width="24px" height="30px" viewBox="0 0 24 30" style="enable-background:new 0 0 50 50;" xml:space="preserve">
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
        </div>
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
       await this.nextArticlePage();

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
    // 监听滚动条
  },
  computed: {
    ...mapState(["blogArticles", "loading"])
  },
  methods: {
    ...mapActions(["nextArticlePage"]),
    scrollBottem() { // 滚动条触发事件
    
        // 获取一下必要的参数
      let winHeight = window.innerHeight, //窗口高度
        bodyHeight = document.documentElement.scrollHeight,// 页面高度
        boduTop = document.documentElement.scrollTop;// 滚动条上的高度

      // 如果 页面高度 减去 滚动条上的区域 再减去 可视区高度 小于等于100就说明到了底部
      if (bodyHeight - boduTop - winHeight <= 100) {
        // 如果正再加载就跳出
        if (this.loadFlag) {
          return;
        }
        // 将标志位置为真 表示真正加载
        this.loadFlag = true;
        // 延时加载 ,,,,,因为本地加载速度太快  ... 就延时了一下
        setTimeout(async () => {
          // 之前的bug 
          // boduTop = document.documentElement.scrollTop;
          // 获取下一页的数据 ajax  nextArticlePage 
         const {code} = await this.nextArticlePage();
          // 进行一系列的xxoo
          // console.log(code, message);
          // 如果后端传递的code等于3 说明数据加载完成 就将 事件移出 不再监听
          if (code == 3) {
            window.removeEventListener("scroll", this.scrollBottem);
          }
          //  将标志位置为假 表示完成一次加载
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
    padding: 10px;
  }
}
#posts {
  margin-top: -20px;
}
.loading {
  text-align: center;
}
</style>
