<template>
  <transition name="fade">
    <div 
      class="mask-info" 
      v-if="show">
      <div class="info-text">{{ msg }}</div>
    </div>
  </transition>
</template>

<script>
export default {
  name: "MaskInfo",
  data() {
    return {
      msg: "",
      show: true
    };
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
.mask-info {
  position: fixed;
  z-index: 1600;
  width: 100%;
  top: 50%;
  left: 0;
  min-width: 100px;
  text-align: center;
  > .info-text {
    padding: 0 10px;
    display: inline-block;
    line-height: 40px;
    font-size: 14px;
    min-width: 100px;
    color: #f6f6f6;
    height: 40px;
    margin-top: -20px;
    border-radius: 2px;
    background-color: rgba($color: #000000, $alpha: 0.7);
  }
}
</style>
