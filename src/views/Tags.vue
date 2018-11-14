<template>
  <div class="tags">
    <ViewHeader />
    <main class="tags-main">
      <div class="tags-title">
        <p>目前共计 {{ Tags.length }} 个标签</p>
      </div>
      <div class="tags-list">

        <transition-group 
          enter-active-class="animated slideInUp" 
          leave-active-class="animated slideOutUp">
          <template v-for="(item) in Tags">
            <router-link 
              class="tag" 
              :key="item.tID" 
              :to="`/tag/${item.tID}`" 
              v-weightStyle="item.count">{{ item.name }}</router-link>
          </template>
        </transition-group>

      </div>

    </main>
    <ViewFooter />
  </div>
</template>

<script>
import ViewHeader from "@/components/ViewHeader.vue";
import ViewFooter from "@/components/ViewFooter.vue";
import { mapActions, mapState } from "vuex";

export default {
  name: "Tags",
  components: { ViewHeader, ViewFooter },
  async created() {
    this.$store.dispatch("showLoading");
    await this.getTags();
    this.$store.dispatch("hideLoading");
    //   hideLoading
  },
  directives: {
    weightStyle(el, { value }) {
    //   console.log(el, value);
      let style = "color:#ccc;font-size:12px";
      if (value > 10) {
        style = "color:#000;font-size:28px";
      } else if (value > 8) {
        style = "color:#333;font-size:24px";
      } else if (value > 6) {
        style = "color:#555;font-size:20px";
      } else if (value > 4) {
        style = "color:#777;font-size:18px";
      } else if (value > 3) {
        style = "color:#999;font-size:16px";
      } else if (value > 2) {
        style = "color:#aaa;font-size:14px";
      } else {
        style = "color:#ccc;font-size:14px";
      }
      el.style = style;
    }
  },
  computed: {
    ...mapState(["Tags"])
  },
  methods: {
    ...mapActions(["getTags"])
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.tags {
  &-main {
    width: 900px;
    margin: 60px auto;
    min-height: 400px;
  }
  &-list {
    overflow: hidden;
    margin: 20px 0;
    .tag {
      display: flex;
      display: inline-block;
      margin: 4px 10px;
      padding: 4px;
      line-height: 2;
    }
  }
  &-title {
    color: #666;
    text-align: center;
    margin: 20px 0;
    font-weight: normal;
  }
}
</style>
