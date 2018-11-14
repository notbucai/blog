<template>
  <article class="axis-posts">

    <AxisTitle :title="pigeonhole.year"/>

    <AxisItem 
      v-for="archive in pigeonhole.list" 
      :key="archive.id" 
      :archive="archive" />
    
  </article>
</template>

<script>
import AxisItem from "@/components/AxisItem.vue";
import AxisTitle from "@/components/AxisTitle.vue";

export default {
  name: "Axis",
  data() {
    return {};
  },
  components: {
    AxisItem,AxisTitle
  },
  props: {
    pigeonhole: {
      type: Object,
      required: true,
      default() {
        return {
          count: -1,
          list: [],
          year: -1
        };
      }
    }
  }
};
</script>

<style lang="scss">
.axis-posts {
  position: relative;
  &::after {
    content: "";
    position: absolute;
    top: 16px;
    left: 0;
    width: 4px;
    height: 100%;
    background-color: #f1f1f1;
  }
  
}
</style>
