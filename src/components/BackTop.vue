<template>

  <div 
    class="back-top" 
    v-if="backTopShow" 
    @click="backTop">
    <i class="iconfont icon-icon3"/>
  </div>

</template>

<script>
export default {
  name: "BackTop",
  created() {
    window.addEventListener("scroll", this.backTopShowFun);
  },
  data() {
    return {
      backTopIng: 0,
      backTopSpeed: 30,
      backTopShow: false,
      backTopDist: 300
    };
  },
  methods: {
    backTop() {
      clearInterval(this.backTopIng);
      let backTopSpeed = this.backTopSpeed;
      this.backTopIng = setInterval(() => {
        document.documentElement.scrollTop -= backTopSpeed++;
        if (document.documentElement.scrollTop <= 0) {
          clearInterval(this.backTopIng);
          document.documentElement.scrollTop = 0;
          return;
        }
      }, 10);
    },
    backTopShowFun() {
      if (document.documentElement.scrollTop >= this.backTopDist) {
        this.backTopShow = true;
      } else {
        this.backTopShow = false;
      }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

.back-top {
  position: fixed;
  bottom: 10%;
  right: 20%;
  width: 40px;
  height: 40px;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: background-color 0.6s;
  &:hover {
    background-color: rgba(0, 0, 0, 0.7);
  }

  .iconfont {
    color: #fff;
    font-size: 16px;
    font-weight: bold;
  }
}
</style>
