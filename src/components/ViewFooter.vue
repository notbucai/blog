<template>
  <footer class="footer">
    <div class="footer-contain">
      <div class="footer-info">
        &copy; 2018 ❤ Bucai's blog
      </div>
      <div class="gov">
        <a href="http://www.miitbeian.gov.cn/" target="_blank">赣ICP备15001741号-3</a>
      </div>
    </div>
  </footer>
</template>

<style  lang="scss" scoped>
.footer {
  background-color: #f1f1f1;
  margin-top: 60px;
  &-contain {
    width: 900px;
    margin: 0 auto;
    padding: 10px 0;
  }
  &-info {
    color: #666;
    font-size: 14px;
  }
  .gov {
    > a {
      font-size: 12px;
    }
  }
}
</style>

