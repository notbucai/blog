<template>
  <article class="axis-post" >
    <span class="axis-post-date">{{ archive.timestamp }}</span>
    <router-link 
      :to="`/blog/${ archive.id }`" 
      tag="h1" 
      class="axis-post-title animated fadeInRight" 
      v-text="archive.title" />
  </article>
</template>

<script>
export default {
  name: "ArchiveItem",
  props: {
    archive:{
      type: Object,
      required: true,
      default() {
        return {
          id: -1,
          timestamp: "",
          title: ""
        };
      }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

.axis-post {
  display: flex;
  align-items: center;
  border-bottom: 1px dashed #ccc;
  padding: 20px 20px 10px;
  margin: 20px 0;
  position: relative;
  transition: border-bottom-color 0.4s;

  &:hover {
    border-bottom-color: #666;
    &::before {
      background-color: #000;
    }
  }
  &::before {
    content: "";
    position: absolute;
    left: -1px;
    z-index: 9;
    width: 6px;
    height: 6px;
    border-radius: 50%;
    transition: background-color 0.8s;
    background-color: #ccc;
  }
  &-date {
    min-width: 50px;
    padding: 0 20px;
    color: #666;
    font-size: 12px;
  }
  &-title {
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    font-size: 16px;
    color: #666;
    font-weight: normal;
    cursor: pointer;
  }
}
</style>
