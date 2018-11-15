<template>
  <article class="post">
    <header class="post-header">
      <router-link 
        :to="`blog/${ post.id }`" 
        class="post-title" 
        tag="h1" 
        v-text="post.title"/>
      <div class="post-meta">
        <span>发表于 {{ post.timestamp | timeFormat }}</span>
        <span> | </span>
        <span>阅读次数 {{ post.hits }}</span>
      </div>
    </header>
    <main 
      class="post-body" 
      v-text="post.info"/>
  </article>
</template>

<script>

export default {
  props: {
    post: {
      // 必须提供字段
      required: true,
      type: Object,
      default() {
        return {
          id: -1,
          timestamp: Date.now(),
          hits: 0,
          info: ""
        };
      }
    }
  },
  data() {
    return {};
  },
  methods: {
    
  }
};
</script>

<style lang="scss" scoped>
@keyframes logoBorderScale {
  from {
    transform: scaleX(0);
  }
  to {
    transform: scaleX(1);
  }
}
.post {
  margin-top: 100px;
  // &-header {
  // }
  &-title {
    font-size: 26px;
    font-weight: normal;
    color: #444;
    cursor: pointer;
    position: relative;
    display: inline;
    transition: color 0.8s;
    &:hover {
      color: #000;
      &::before {
        content: "";
        position: absolute;
        bottom: -4px;
        animation: logoBorderScale 0.5s;
        width: 100%;
        height: 2px;
        background-color: #000;
      }
    }
  }
  &-meta {
    font-size: 12px;
    color: #999;
    margin: 18px 0 24px;
    > span {
      margin-right: 5px;
    }
  }
  &-body {
    font-size: 14px;
    color: #666;
    line-height: 28px;
  }
}
@media screen and (max-width: 900px) {
  .post {
    margin-top: 50px;
  }
}
</style>
