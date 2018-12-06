<template>
  <div class="login">
    <main class="login-main">
      <div class="logo">
        <img
          src="@/assets/logo.png"
          alt=""
        >
      </div>
      <input
        type="text"
        name="sname"
        v-model="sname"
        placeholder="账号"
      >
      <input
        type="password"
        name="spwd"
        v-model="spwd"
        placeholder="密码"
      >
      <button @click="login">登陆</button>
    </main>
  </div>
</template>

<script>
import { Login } from "@/plugins/api.js";

export default {
  name: "login",
  data() {
    return {
      sname: "",
      spwd: ""
    };
  },
  created() {
    this.$store.dispatch("hideLoading");
  },
  methods: {
    async login() {
      const { sname, spwd } = this;

      if (!sname || !spwd) {
        this.$toast("不能为空");
        return;
      }
      const [error, res] = await Login({ sname, spwd });

      if (error) {
        this.$toast(error);
        return;
      }
      const {
        data: { code, message }
      } = res;

      this.$toast(message);
      if (code === 0) {
        this.$router.push("/bucai_admin");
      }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
$bgColor: #f9a7b5;
$bgImg: "https://acg.moedragon.com/api.php";
.login {
  position: fixed;
  width: 100%;
  height: 100%;
  overflow: hidden;
  box-sizing: border-box;

  background: {
    image: url($bgImg);
    color: $bgColor;
    repeat: no-repeat;
    size: 100% 100%;
    position: center center;
  }
  display: flex;
  align-items: center;
  justify-content: center;
  &-main {
    width: 400px;
    background: #fffe;
    border-radius: 4px;
    padding: 50px 40px 40px;
    box-sizing: border-box;
    .logo {
      width: 68px;
      height: 68px;
      margin: 0 auto;
      img {
        width: 100%;
      }
    }
    h1 {
      text-align: center;
      margin: 20px 0;
    }
    input,
    button {
      display: block;
      width: 100%;
      padding: 0;
      margin: 0;
      box-sizing: border-box;
      border: none;
      border-bottom: 1px solid #e4eaec;
      background-color: transparent;
      height: 48px;
      padding: 4px 8px;
      margin: 40px auto;
      &:focus {
        transition: all .8s;
        outline: none;
        border-color: $bgColor;
        border-bottom-width: 2px;
      }
    }

    button {
      cursor: pointer;
      background-color: $bgColor;
      color: #fff;
      border-radius: 4px;
      font-size: 18px;
    }
  }
}
</style>
