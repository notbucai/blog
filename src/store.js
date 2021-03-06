import Vue from 'vue'
import Vuex from 'vuex'

import {
  ArticlePage,
  Article,
  CommentCurrent,
  Pigeonhole,
  AddComment,
  Tags,
  Tag,
  LinksRand,
  AdminArticle,
  Links
} from "@/plugins/api";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loading: true,
    blogArticles: [],
    blogArticlesTotal: -1,
    Article: {
      article: {
        title: "",
        hits: 0,
        id: 0,
        info: "",
        timestamp: 0
      },
      tags: [],
    },
    currentComment: [

    ],
    Pigeonhole: [],
    Tags: [],
    TagArticle: {
      list: [],
      t_name: ""
    },
    LinksRand: [],
    Links: [],
    dataLine: [
      {
        date: 1542351311142,
        value: {
          cpu: 0,
          ram: 0
        }
      }
    ],
    ArticleList: []
  },
  mutations: {
    BLOGARTICLESTOTAL(state, data) {
      state.blogArticlesTotal = data;
    },
    BLOGARTICLES(state, data) {
      state.blogArticles = data;
    },
    LOADING(state, data = false) {
      state.loading = data;
    },
    ARTICLE(state, data) {
      state.Article = data;
    },
    CURRENTCOMMENT(state, data) {
      state.currentComment = data;
    },
    PIGEONHOLE(state, data) {
      state.Pigeonhole = data;
    },
    TAGS(state, data) {
      state.Tags = data;
    },
    TAGARTICLE(state, data) {
      state.TagArticle = data;
    },
    LINKSRAND(state, data) {
      state.LinksRand = data;
    },
    LINKS(state, data) {
      state.Links = data;
    },
    DATALINE(state, data) {
      state.dataLine = data;
    },
    ARTICLELIST(state, data) {
      state.ArticleList = data;
    }
  },
  actions: {
    async showLoading({ commit }) {
      commit('LOADING', true);
    },
    async hideLoading({ commit }) {
      setTimeout(() => {
        commit('LOADING', false);
      }, 500);
    },
    async nextArticlePage({ commit, state }) {
      const { blogArticles, blogArticlesTotal } = state;

      if (blogArticlesTotal != -1 && blogArticles.length >= blogArticlesTotal) {
        return {
          code: 3,
          message: "没有更多数据了"
        }
      }

      const [error, res] = await ArticlePage(10, blogArticles.length);
      if (error) {
        return {
          code: -1,
          message: error.message
        };
      }

      const { data: { code, message } } = res;

      if (code === 0) {
        const { total, list } = res.data;
        commit('BLOGARTICLESTOTAL', total);

        if (list.length != 0) {
          const blogArticles_t = blogArticles.concat(list);
          commit('BLOGARTICLES', blogArticles_t);
        }

      }

      return {
        message,
        code
      };

    },
    async getArticle({ commit }, id) {

      const [error, res] = await Article(id);

      if (error) {
        return {
          code: -1,
          message: error.message
        };
      }

      const { data: { code, message } } = res;

      if (code === 0) {
        commit('ARTICLE', res.data.result);
      }

      return {
        code, message
      }

    },
    async getCurrentComment({ commit }, id) {

      const [error, res] = await CommentCurrent(id);

      if (error) {
        return {
          code: -1,
          message: error.message
        };
      }

      const { data: { code, message } } = res;
      commit('CURRENTCOMMENT', res.data.result || []);

      return {
        code, message
      }

    },
    async getPigeonhole({ commit }) {

      const [error, res] = await Pigeonhole();

      if (error) {
        return {
          code: -1,
          message: error.message
        };
      }

      const { data: { code, message } } = res;
      commit('PIGEONHOLE', res.data.result || []);

      return {
        code, message
      }

    },
    async addComment({ commit }, {
      bId, rId, name, email, content
    } = {}) {
      if (!commit) return;

      if (!name || !content) {
        return {
          code: -1,
          message: "不能为空"
        }
      }

      // {"bId":1,"rId":-1,"name":"1111111","email":"","content":""}
      const [error, res] = await AddComment({
        bId, rId, name, email, content
      });

      if (error) {
        return {
          code: -1,
          message: error.message
        };
      }

      const { data: { code, message } } = res;

      return {
        code, message
      }

    },
    async getTags({ commit }) {

      const [error, res] = await Tags();
      if (error) {
        return {
          code: -1,
          message: error.message
        };
      }

      const { data: { code, message } } = res;

      if (code === 0) {

        const { result } = res.data;

        if (result.length != 0) {
          commit('TAGS', result);
        }
      }

      return {
        message,
        code
      };
    },
    async getTagArticles({ commit }, id) {

      const [error, res] = await Tag(id);

      if (error) {
        return {
          code: -1,
          message: error.message
        };
      }

      const { data: { code, message } } = res;

      if (code === 0) {
        commit('TAGARTICLE', res.data.result);
      }

      return {
        code, message
      }

    },
    async getLinksRand({ commit }) {

      const [error, res] = await LinksRand();

      if (error) {
        return {
          code: -1,
          message: error.message
        };
      }

      const { data: { code, message } } = res;

      if (code === 0) {
        commit('LINKSRAND', res.data.result);
      }

      return {
        code, message
      }

    },
    async initDataLine({ commit }, data) {
      commit('DATALINE', data);
    },
    getDataLine({ commit, state }, data) {
      const { dataLine } = state;
      const dataLine_ok = [
        ...dataLine.filter((v, i) => {
          return i;
        }),
        data
      ];
      commit('DATALINE', dataLine_ok);
    },

    async getAdminArticle({ commit }) {
      const [error, res] = await AdminArticle();

      if (error) {
        return {
          code: -1,
          message: error.message
        };
      }

      const { data: { code, message } } = res;

      if (code === 0) {
        commit('ARTICLELIST', res.data.result);
      }

      return {
        code, message
      }

    },


    async getLinks({ commit }) {

      const [error, res] = await Links();

      if (error) {
        return {
          code: -1,
          message: error.message
        };
      }

      const { data: { code, message } } = res;

      if (code === 0) {
        commit('LINKS', res.data.result);
      }

      return {
        code, message
      }

    },

  }
})
