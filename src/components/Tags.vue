<template>
  <section class="tags">
        
    <article 
      class="tag" 
      v-for="item in tags" 
      :key="item.id">
      <router-link 
        :to="`/tag/${item.id}`" 
        tag="a">#{{ item.name }}</router-link>
    </article>
  </section>
</template>

<script>
export default {
  name: "Tags",
  props: {
      tags:Array
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.tags {
  margin: 20px 0;
  display: flex;
  .tag {
    margin-right: 10px;
    > a {
      font-size: 14px;
      padding: 6px 10px;
      background-color: #f5f5f5;
      border-bottom: none;
      &:hover {
        transition: all 0.8s;
        background-color: #ddd;
      }
    }
  }
}
</style>
