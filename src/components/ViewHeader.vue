<template>
  <header class="header">
    <div class="header-contain">
      <div class="header-logo">
        Bucai's Blog
      </div>
      <div class="header-nav">
        <ul>
          <li>
            <router-link to="/">首页</router-link>
          </li>
          <li>
            <router-link to="/archives">归档</router-link>
          </li>
        </ul>
      </div>
    </div>
  </header>
</template>

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
      animation-delay: .8s;
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
          &:hover {
            background-color: #ddd;
            color: #333;
          }
        }
      }
    }
  }
}
</style>

