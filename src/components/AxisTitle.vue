<template>
  <div class="axis-posts-title">
    <h2 class="animated fadeInRight">{{ title }}</h2>
    <p v-if="subhead">{{ subhead }}</p>
  </div>
</template>

<script>
export default {
  name: "ArchiveItem",
  props: {
    title: Number|String,
    subhead: String|Number
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.axis-posts-title {
  position: relative;
  z-index: 10;
  background-color: #fff;
  margin: 20px;
  font-size: 16px;
  max-width: 200px;
  display: flex;
  align-items: center;
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
  >h2{
      margin-right: 10px;
  }
  p{
      font-size: 20px;
      font-weight: bold;
      color: #888;
  }
}
</style>
