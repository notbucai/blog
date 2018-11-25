<template>
  <div class="login">
    <h1>登 陆</h1>
    <input type="text" name="sname" v-model="sname" placeholder="账号">
    <input type="password" name="spwd" v-model="spwd" placeholder="密码">
    <button @click="login">登陆</button>
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
.login {
  width: 600px;
  height: 400px;
  margin: 200px auto;
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
    border: 1px solid #ddd;
    height: 48px;
    padding: 4px 8px;
    margin: 50px auto;
    &:focus {
      outline: none;
      border-color: #000;
    }
  }
  button {
    cursor: pointer;
    background-color: #000;
    color: #ddd;
  }
}
</style>
