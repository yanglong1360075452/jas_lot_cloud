/**
 * Created by yonghuapro on 2017/4/28.
 */
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


export default new Vuex.Store({
  state:{
    jas_test:100,
  },
  mutations: {
    num_add (state,num2) {
      state.jas_test+=num2
    },
  }
})
