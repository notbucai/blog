<template>
  <div class="tag">
    <ViewHeader />
    <main class="contain tag-main">

      <AxisTitle :title="TagArticle.t_name || `错误`" subhead="标签" />

      <AxisItem v-for="archive in TagArticle.list" :key="archive.id" :archive="archive" />

    </main>
    <ViewFooter />
  </div>
</template>

<script>
import ViewHeader from "@/components/ViewHeader.vue";
import ViewFooter from "@/components/ViewFooter.vue";

import AxisItem from "@/components/AxisItem.vue";
import AxisTitle from "@/components/AxisTitle.vue";

import { mapActions, mapState } from "vuex";

export default {
  name: "Tag",
  components: { ViewHeader, ViewFooter, AxisItem, AxisTitle },
  async created() {
    // console.log(this.$route);

    const { id } = this.$route.params;

    this.$store.dispatch("showLoading");

    if (id > 0) {
      await this.getTagArticles(id);
      document.title = this.TagArticle.t_name + " " + document.title;
    }

    this.$store.dispatch("hideLoading");
    //   hideLoading
  },
  computed: {
    ...mapState(["TagArticle"])
  },
  methods: {
    ...mapActions(["getTagArticles"])
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.tag-main{
  padding: 10px;
}
</style>
