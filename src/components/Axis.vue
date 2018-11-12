<template>
  <article class="axis-posts">
    <div 
      class="axis-posts-year" 
      :key="pigeonhole.year">
      <h2>{{ pigeonhole.year }}</h2>
    </div>
    <template v-for="archive in pigeonhole.list">
      <article 
        class="axis-post" 
        :key="archive.id">
        <span class="axis-post-date">{{ archive.timestamp }}</span>
        <router-link 
          :to="`blog/${ archive.id }`" 
          tag="h1" 
          class="axis-post-title" 
          v-text="archive.title"/>
      </article>
    </template>
  </article>
</template>

<script>
export default {
  name: "Axis",
  data() {
    return {};
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
  &-year {
    position: relative;
    background-color: #fff;
    margin: 20px;
    font-size: 16px;
    &::before {
      z-index: 10;
      content: "";
      position: absolute;
      left: -23px;
      top: 50%;
      margin-top: -3px;
      width: 10px;
      height: 10px;
      border-radius: 50%;
      background-color: #aaa;
    }
  }
}
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
