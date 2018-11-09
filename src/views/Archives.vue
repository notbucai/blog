<template>
  <div class="archives">
    <ViewHeader />
    <main class="archives-main">

      <Axis />

    </main>
    <ViewFooter />
  </div>
</template>


<script>
import ViewHeader from "@/components/ViewHeader.vue";
import ViewFooter from "@/components/ViewFooter.vue";
import Axis from "@/components/Axis.vue";

export default {
  name: "archives",
  components: { ViewHeader, ViewFooter, Axis },
  data() {
    return {
      
    };
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
