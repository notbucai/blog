<template>
  <div id="app">
    <router-view></router-view>
    <Loading :loading="$store.state.loading"/>
  </div>
</template>

<script>
import Loading from "@/components/Loading.vue";
export default {
  components: {
    Loading
  },
  data() {
    return {
      
    };
  }
};
</script>
