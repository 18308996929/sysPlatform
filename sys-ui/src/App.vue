<template>
  <div id="app">
    <router-link to='/login'></router-link>
<!--    只要在项目中安装和配置了vue-router，就可以使用router-view这个组件-->
<!--     作用 : 占位符，表示渲染的内容的位置-->
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
