import Vue from 'vue'
import App from './App.vue'
import router from './views/router/index'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
Vue.prototype.$http= axios;

axios.defaults.baseURL = '/';

Vue.use(ElementUI)
Vue.config.productionTip = false


new Vue({
  // 把router挂载到vue实例
  router,
  render: h => h(App),
}).$mount('#app')
