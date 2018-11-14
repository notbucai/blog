<template>
  <div 
    class="comment" 
    ref="comment">
    <h2 class="comment-title">评论留言</h2>
    <form 
      action="" 
      method="post">
      <textarea 
        name="comment" 
        class="comment-content" 
        v-form_v 
        maxlength="450" 
        :placeholder="info" 
        v-model="commentForm.content" />
      <!-- {"bId":1,"rId":-1,"name":"1111111","email":"","content":""} -->
      <input 
        type="text" 
        name="name" 
        maxlength="10" 
        autocomplete="off" 
        v-form_v 
        class="comment-input" 
        placeholder="* 评论昵称" 
        v-model="commentForm.name">
      <input 
        type="text" 
        name="email" 
        maxlength="30" 
        autocomplete="off" 
        v-form_v 
        class="comment-input" 
        placeholder="评论邮箱" 
        v-model="commentForm.email">
      <button 
        class="comment-submit" 
        :class="{'disabled':commentForm.sending}" 
        @click.stop.prevent="onSubmitComment" 
        :disabled="commentForm.sending">提交</button>
    </form>
    <section class="comment-list">
      <h2 class="comment-list-title">{{ currentComment.length }}条评论</h2>
      <article 
        class="comment-list-item" 
        v-for="(item) in currentComment" 
        :key="item.id">
        <div class="comment-list-item-img">
          <img 
            :src="`https://cdn.v2ex.com/gravatar/${toAscii(item.name)}?s=48&d=retro`" 
            alt="" 
            srcset="">
        </div>
        <div class="comment-list-item-main">
          <div class="comment-list-item-main-info">
            <p class="comment-list-item-main-info-name">{{ item.name }}</p>
            <p class="comment-list-item-main-info-time">{{ item.commentTime | timeFormat }}</p>
          </div>
          <div class="comment-list-item-main-content">
            <span 
              class="at" 
              v-if="item.rName" 
              v-text="`@${item.rName || 'PASSERBY A'}`"/>
            <pre v-text="item.content"/>
          </div>
          <div 
            class="comment-list-item-main-reply" 
            @click="reply(item.id)">回复</div>
        </div>
      </article>
    </section>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "Comment",
  props: {
    id_o: {
      type: Number,
      required: true,
      default: 0
    }
  },
  components: {},
  directives: {
    form_v: {
      inserted(el) {
        console.log(111);
      },
      update(el) {
        const { value, name } = el;

        if (!value) {
          return;
        }

        switch (name) {
          case "email":
            let rex = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;

            if (rex.test(value)) {
              el.style = "";
              return;
            }
            break;
          case "name":
          case "comment":
            if (value && value.length > 0) {
              el.style = "";
              return;
            }
            break;

          default:
            break;
        }
        el.style = "border-color:#ff9800";
      }
    }
  },
  async created() {
    // console.log(this.$route.params.id);
    const { id_o } = this;
    if (id_o) {
      this.getCurrentComment(id_o);
    }
  },
  data() {
    return {
      commentForm: {
        rId: -1,
        name: "",
        email: "",
        content: "",
        sending: false
      },
      ReplyName: ""
    };
  },
  computed: {
    ...mapState(["currentComment"]),
    // 这里输入留言内容
    info() {
      const rId = this.commentForm.rId;
      console.log(rId);

      if (rId === -1) {
        return "这里输入评论内容";
      }
      return `回复 ${this.ReplyName} 的评论`;
    }
  },
  methods: {
    ...mapActions(["getCurrentComment", "addComment"]),
    async onSubmitComment() {
      const { rId, name, email, content, sending } = this.commentForm;

      if (sending == true) {
        return;
      }
      this.commentForm.sending = true;
      if (!name || !content) {
        console.log(name, content);

        this.$toast("名字和内容都不能为空");
        this.commentForm.sending = false;
        return;
      }

      this.$toast("评论中");

      const { code, message } = await this.addComment({
        bId: this.id_o,
        rId,
        name,
        email,
        content,
        sending
      });

      setTimeout(() => {
        this.commentForm.sending = false;
        if (code === 0) {
          this.getCurrentComment(this.id_o);
          this.commentForm.name = "";
          this.commentForm.email = "";
          this.commentForm.content = "";
          this.commentForm.rId = -1;
        }
        this.$toast(message);
      }, 700);
      // console.log(this.$toast);
    },
    reply(id) {
      console.log(this.currentComment);

      const replyObj = this.currentComment.find(item => {
        console.log(item);

        return item.id == id;
      });
      if (replyObj) {
        document.documentElement.scrollTop = this.$refs["comment"].offsetTop;
        this.ReplyName = replyObj.name;
        this.commentForm.rId = id;
      }
    },
    toAscii(str) {
      let res = "";
      for (let i = 0; i < str.length; i++) {
        res += String(str.charCodeAt(i));
      }
      return res;
    }
  }
};
</script>

<style lang="scss" scoped>
.comment {
  margin-top: 20px;
  background-color: #f9f9f9;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;

  &-content {
    font-family: "Source Sans Pro", "Helvetica Neue", Arial, sans-serif;
    width: 100%;
    height: 200px;
    border: 2px solid #f1f1f1;
    background-color: #fff;
    border-radius: 4px;
    font-size: 16px;
    letter-spacing: 1px;
    line-height: 20px;
    color: #444;
    font-weight: 400;
    padding: 10px;
    box-sizing: border-box;
    resize: none;
    &:focus {
      outline: none;
    }
    &.error {
      border-color: #ff9800;
    }
  }
  &-input {
    width: 200px;
    padding: 10px 16px;
    margin-top: 20px;
    margin-right: 30px;
    font-size: 16px;
    border: 2px solid #f1f1f1;
    background-color: #fff;
    color: #666;
    &:focus {
      outline: none;
    }
    &.error {
      border-color: #ff9800;
    }
  }
  &-title {
    margin-bottom: 20px;
  }

  &-submit {
    padding: 14px 16px;
    border: none;
    background-color: #000;
    color: #fff;
    display: block;
    width: 100%;
    margin-top: 30px;
    cursor: pointer;
    &.disabled {
      background-color: #888;
      cursor: no-drop;
    }
  }

  &-list {
    margin: 20px 0;

    &-title {
      margin-top: 40px;
      margin-bottom: 20px;
      border-left: 6px solid #000;
      padding-left: 16px;
    }
    &-item {
      margin-bottom: 20px;
      background-color: #fff;
      box-shadow: 2px 2px 8px #dddd;
      box-sizing: border-box;
      // border-radius: 6px;
      width: 100%;
      position: relative;
      padding: 20px;
      &-img {
        position: absolute;
        left: 20px;
        top: 20px;
        width: 48px;
        height: 48px;
        > img {
          border-radius: 6px;
          width: 100%;
          height: 100%;
        }
      }
      &-main {
        margin-left: 70px;
        width: 80%;
        &-info {
          display: flex;
          align-items: flex-end;
          &-name {
            font-size: 16px;
            color: #35aadc;
          }
          &-time {
            font-size: 12px;
            color: #888;
            margin-left: 10px;
          }
        }
        &-content {
          margin-top: 6px;
          font-size: 14px;
          line-height: 24px;
          overflow: hidden;
          pre {
            font-family: "Source Sans Pro", "Helvetica Neue", Arial, sans-serif;
            color: #444;
            white-space: pre-wrap;
            letter-spacing: 1px;
          }
          > .at {
            font-size: 12px;
            color: #35aadc;
            margin-right: 10px;
          }
        }
        &-reply {
          position: absolute;
          bottom: 0;
          right: 0;
          padding: 6px 12px;
          font-size: 14px;
          color: #999;
          cursor: pointer;
          background-color: #f1f1f1;
          &:hover {
            color: #fff;
            background-color: #35aadc;
          }
        }
      }
    }
  }
}
</style>

