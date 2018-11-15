<template>
    <article class="link-item">
        <a :href="link.link" target="_blank">
            <div class="link-img">
                <img :src="link.img_path" alt="">
            </div>
            <div class="link-main">
                <p class="link-title">{{link.title}}</p>
                <p class="link-info">{{link.info}}</p>
            </div>
        </a>
    </article>
</template>

<script>
export default {
  name: "LinkItem",
  props: {
    link: {
        type:Object,
        required:true,
        default:{
            title:"",
            info:"",
            img_path:"",
            link:""
        }
    }
  }
};
</script>

<style  lang="scss" scoped>
.link {
  &-item {
    margin: 20px;
    cursor: pointer;
    > a {
      display: block;
      border: none;
      display: flex;
      padding: 20px;
    }
    &:hover {
      transition: all 0.4s ease-out;
      background-color: #f6f6f6;
    }
  }
  &-main {
    width: 200px;
    box-sizing: border-box;
    margin-left: 20px;
  }
  &-title {
    font-size: 18px;
    line-height: 1.8;
    color: #333;
    overflow: hidden;
  }
  &-info {
    font-size: 12px;
    color: #888;
    line-height: 1.6;
  }
  &-img {
    width: 60px;
    height: 60px;
    > img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
    }
  }
}
</style>

