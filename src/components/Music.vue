<template>
  <div class="music">
    <aplayer
      :autoplay="true"
      :list="musics"
      :music="musics[0]"
      :float="true"
      :mini="true"
      :volume="0.5"
    >
    </aplayer>
  </div>
</template>

<script>
import Aplayer from "vue-aplayer";
export default {
  name: "Music",
  components: {
    Aplayer
  },
  mounted() {},
  data() {
    return {
      // SILENT SIREN (サイレント・サイレン) - secret base ~君がくれたもの~ (secret base ~你给我的所有~)
      musics: [
        {
          title: "secret base ~君がくれたもの~",
          author: "secret base ~君がくれたもの~",
          url:
            "http://fs.open.kugou.com/e2824658f0754d20fc7bc742b56c5083/5c093990/G012/M05/0E/04/TA0DAFUHca-Afk1DAFYYaiaECms593.mp3",
          pic:
            "http://bucai-blog.oss-cn-beijing.aliyuncs.com/18-12-6/26125296.jpg"
        },
        {
          title: "secret base ~君がくれたもの~",
          author: "secret base ~君がくれたもの~",
          url:
            "http://fs.open.kugou.com/e2824658f0754d20fc7bc742b56c5083/5c093990/G012/M05/0E/04/TA0DAFUHca-Afk1DAFYYaiaECms593.mp3",
          pic:
            "http://bucai-blog.oss-cn-beijing.aliyuncs.com/18-12-6/26125296.jpg"
        }
      ]
    };
  },
  methods: {

  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.music {
  position: fixed;
  width: 400px;
  z-index: 10;
  bottom: 200px;
  left: 100px;
}
</style>
