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
    padding: 10px 10px;
    box-sizing: border-box;
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
@media screen and (max-width: 900px) {
  .footer {
    &-contain {
      width: 100%;
    }
  }
}
</style>

