<template>
  <div id="app">
    <router-view></router-view>
    <Loading :loading="$store.state.loading" />
    <vue-canvas-nest
      :config="{color:'249,167,181', count: 300}"
      :el="'#nest'"
    ></vue-canvas-nest>
    <Music/>
  </div>
</template>

<script>
import Loading from "@/components/Loading.vue";
import vueCanvasNest from "vue-canvas-nest";
import Music from "@/components/Music.vue";

export default {
  components: {
    Loading,
    vueCanvasNest,
    Music
  },
  data() {
    return {};
  }
};
</script>
