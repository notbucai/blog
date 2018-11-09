<template>
    <div v-if="loading" class="loading">
        <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" width="24px" height="30px" viewBox="0 0 24 30" style="enable-background:new 0 0 50 50;" xml:space="preserve">
            <rect x="0" y="0" width="4" height="10" fill="#409eff" transform="translate(0 15.1665)">
                <animateTransform attributeType="xml" attributeName="transform" type="translate" values="0 0; 0 20; 0 0" begin="0" dur="0.9s" repeatCount="indefinite"></animateTransform>
            </rect>
            <rect x="10" y="0" width="4" height="10" fill="#409eff" transform="translate(0 11.5002)">
                <animateTransform attributeType="xml" attributeName="transform" type="translate" values="0 0; 0 20; 0 0" begin="0.3s" dur="0.9s" repeatCount="indefinite"></animateTransform>
            </rect>
            <rect x="20" y="0" width="4" height="10" fill="#409eff" transform="translate(0 1.83315)">
                <animateTransform attributeType="xml" attributeName="transform" type="translate" values="0 0; 0 20; 0 0" begin="0.6s" dur="0.9s" repeatCount="indefinite"></animateTransform>
            </rect>
        </svg>
    </div>
</template>

<script>
export default {
  name: "Loading",
  props: {
    loading: Boolean
  }
};
</script>

<style lang="scss" scoped>
.loading {
  overflow: hidden;
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: hsla(0,0%,100%,.9);
  z-index: 9999999999999999;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
