<template>
  <div class="archives">
    <ViewHeader />
    <main class="archives-main">

      <section>
        <template v-for="item in Pigeonhole">
          <Axis 
            :pigeonhole="item" 
            :key="item.year" />
        </template>

      </section>

    </main>
    <ViewFooter />
  </div>
</template>


<script>
import ViewHeader from "@/components/ViewHeader.vue";
import ViewFooter from "@/components/ViewFooter.vue";
import Axis from "@/components/Axis.vue";

import { mapState, mapActions } from "vuex";

export default {
  name: "Archives",
  components: { ViewHeader, ViewFooter, Axis },
  data() {
    return {};
  },
  async created() {
    const { length } = this.Pigeonhole;
    if (length <= 0) {
      const { code, message } = await this.getPigeonhole();

      this.$store.dispatch("hideLoading");
    }
  },

  computed: {
    ...mapState(["Pigeonhole"])
  },
  methods: {
    ...mapActions(["getPigeonhole"])
  }
};
</script>

<style lang="scss">
.archives {
  &-main {
    width: 900px;
    margin: 40px auto;
  }
}
</style>
