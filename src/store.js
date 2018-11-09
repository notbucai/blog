import Vue from 'vue'
import Vuex from 'vuex'

import { ArticlePageServlet } from "@/plugins/api";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    blogArticles: [],
    blogArticlesTotal: -1,
    loading: true,
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
    async nextArticlePageServlet({ commit, state }) {
      const { blogArticles, blogArticlesTotal } = state;

      if (blogArticlesTotal != -1 && blogArticles.length >= blogArticlesTotal) {
        return {
          code: 0,
          message: "没有更多数据了"
        }
      }

      const [error, res] = await ArticlePageServlet(10, blogArticles.length);
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
          const blogArticles_t = list.concat(blogArticles);
          commit('BLOGARTICLES', blogArticles_t);
        }

      }

      return {
        message,
        code
      };

    }
  }
})
