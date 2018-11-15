<template>
    <div class="links">
        <ViewHeader />
        <div class="links-main contain">
            <h1 class="links-title">dalao们</h1>
            <p class="links-info">dalao们的链接，每次刷新随机排序~</p>

            <transition-group tag="section" class="link-list" enter-active-class="animated fadeInDown" leave-active-class="animated fadeOutDown">
                <LinkItem v-for="(item) in LinksRand" :key="item.id" :link="item" />
            </transition-group>
        </div>
        <ViewFooter />
    </div>
</template>

<script>
import ViewHeader from "@/components/ViewHeader.vue";
import ViewFooter from "@/components/ViewFooter.vue";
import LinkItem from "@/components/LinkItem.vue";
import { mapState, mapActions } from "vuex";

export default {
  name: "links",
  async created() {
    this.$store.dispatch("showLoading");
    await this.getLinksRand();
    this.$store.dispatch("hideLoading");
  },
  components: {
    ViewHeader,
    ViewFooter,
    LinkItem
  },
  computed: {
    ...mapState(["LinksRand"])
  },
  methods: {
    ...mapActions(["getLinksRand"])
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.links {
  &-main {
      padding: 10px;
    min-height: 600px;
  }
  &-title {
    font-size: 20px;
    font-weight: normal;
    text-align: center;
    color: #333;
    padding: 10px;
    background-color: #f8f8f8;
  }
  &-info{
      font-size: 16px;
      color: #ccc;
      text-align: center;
      line-height: 4;
  }
}
.link {
  &-list {
    margin-top: 30px;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: space-around;
  }
}
</style>
