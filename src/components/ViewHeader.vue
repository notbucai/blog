<template>
  <header class="header">
    <div class="header-contain">
      <div class="nav-header">
        <div class="header-logo">
          Bucai's Blog
        </div>
        <div class="header-nav-open" @click="onShowNav"><i class="iconfont icon-caidan"></i></div>
      </div>
      <div class="animated fadeInDown header-nav" ref="nav">
        <ul>
          <li>
            <router-link to="/">首页</router-link>
          </li>
          <li>
            <router-link to="/archives">归档</router-link>
          </li>
          <li>
            <router-link to="/tags">标签</router-link>
          </li>
          <li>
            <router-link to="/links">友情链接</router-link>
          </li>
        </ul>
      </div>
    </div>
  </header>
</template>
<script>
export default {
  name: "viewHeader",
  data() {
    return {};
  },
  methods: {
    onShowNav() {
      const nav = this.$refs['nav'];
      nav.classList.toggle("collapse");
    }
  }
};
</script>

<style  lang="scss" scoped>
@keyframes logoBorderScale {
  from {
    transform: scaleX(0);
  }
  to {
    transform: scaleX(1);
  }
}
@keyframes webStage {
  0% {
    opacity: 0;
    transform: translateY(-30px);
  }
  100% {
    opacity: 1;
    transform: translateY(0px);
  }
}

.header {
  background-color: #f1f1f1;
  &-contain {
    width: 940px;
    height: 130px;
    box-sizing: border-box;
    margin: 0 auto;
    display: flex;
    align-items: center;
  }
  &-logo {
    position: relative;
    z-index: 12;
    font-size: 22px;
    font-weight: bolder;
    animation: webStage 1s;
    &::after,
    &::before {
      position: absolute;
      left: 0;
      content: "";
      width: 80%;
      margin-left: 10%;
      transform: scaleX(1);
      height: 2px;
      background-color: #000;
      animation-name: logoBorderScale;
      animation-duration: 1s;
      animation-delay: 0.8s;
    }
    &::after {
      bottom: -6px;
    }
    &::before {
      top: -6px;
    }
  }
  &-nav {
    margin-left: 40px;
    margin-bottom: -4px;
    &-open {
      display: none;
    }
    > ul {
      display: flex;
      > li {
        list-style: none;
        margin: 1px;
        > a {
          display: block;
          padding: 10px;
          font-size: 12px;
          color: #555;
          text-decoration: none;
          border: none;
          transition: background-color 0.8s ease-out, color 0.8s ease-out;
          &.router-link-exact-active {
             background-color: #ddd;
            color: #333;
          }
          &:hover {
            background-color: #ddd;
            color: #333;
          }
        }
      }
    }
  }
}

@media screen and (max-width: 900px) {
  .nav-header {
    display: flex;
    height: 60px;
    align-items: center;
    border-bottom: 1px solid #ddd;
  }
  .header {
    &-contain {
      width: 100%;
      min-width: 300px;
      padding: 0 10px;
      display: inline-block;
      box-sizing: border-box;
      position: relative;
      height: auto;
    }
    &-logo {
      font-size: 16px;
    }
    &-nav {
      &.collapse {
        display: block;
      }
      display: none;
      padding: 10px;
      margin: 0;
      > ul {
        display: block;
        box-sizing: border-box;
      }
      &-open {
        display: block;
        position: absolute;
        right: 10px;
        text-align: center;
        background-color: transparent;
        width: 60px;
        height: 34px;
        > i {
          font-size: 26px;
          color: #333;
        }
      }
    }
  }
}
</style>

