<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2>this is the result: {{ result }}</h2>
    <div>
      <h2 v-for="(x, index) in list" :key="index">{{ x.name}} ({{ x.pkid }})</h2>
    </div>
    <p>
      For a guide and recipes on how to configure / customize this project,<br>
      check out the
      <a href="https://cli.vuejs.org" target="_blank" rel="noopener">vue-cli documentation</a>.
    </p>
    <h3>Installed CLI Plugins</h3>
    <ul>
      <li><a href="https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-babel" target="_blank" rel="noopener">babel</a></li>
      <li><a href="https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-eslint" target="_blank" rel="noopener">eslint</a></li>
    </ul>
    <h3>Essential Links</h3>
    <ul>
      <li><a href="https://vuejs.org" target="_blank" rel="noopener">Core Docs</a></li>
      <li><a href="https://forum.vuejs.org" target="_blank" rel="noopener">Forum</a></li>
      <li><a href="https://chat.vuejs.org" target="_blank" rel="noopener">Community Chat</a></li>
      <li><a href="https://twitter.com/vuejs" target="_blank" rel="noopener">Twitter</a></li>
      <li><a href="https://news.vuejs.org" target="_blank" rel="noopener">News</a></li>
    </ul>
    <h3>Ecosystem</h3>
    <ul>
      <li><a href="https://router.vuejs.org" target="_blank" rel="noopener">vue-router</a></li>
      <li><a href="https://vuex.vuejs.org" target="_blank" rel="noopener">vuex</a></li>
      <li><a href="https://github.com/vuejs/vue-devtools#vue-devtools" target="_blank" rel="noopener">vue-devtools</a></li>
      <li><a href="https://vue-loader.vuejs.org" target="_blank" rel="noopener">vue-loader</a></li>
      <li><a href="https://github.com/vuejs/awesome-vue" target="_blank" rel="noopener">awesome-vue</a></li>
    </ul>
  </div>
</template>

<script>
import  Vue  from  'vue' 
import  VueJsonp  from  'vue-jsonp'

Vue.use(VueJsonp)

export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data: function() {
    return {
      list: [],
      result: ''
    };
  },
  methods: {
      query_test: function() {
        var _self = this;

        _self.axios({
          method: 'post',
          //url: 'http://10.244.186.86:8080/api/demo/queryResult',
          url: '/api/demo/queryResult',
          data: '',
          withCredentials: true
        }).then(function(response) {
          _self.result = response.data.t;
          document.cookie = _self.result + "; domain='10.244.168.180'";
          alert(document.cookie);
          window.console.log(_self.result);
        });
      },
      testJsonp: function () {
        var _self = this;
        Vue.jsonp('http://10.244.186.86:8080/api/demo/jsonpTest', {
          callbackName: 'jsonpFunc'
        }).then(json => {
         window.console.log(json);
         _self.result = 'code=' + json.code + ',msg=' + json.msg + ',t=' + json.t;
         _self.jsonpFunc(json);
          //jsonpFunc(json);
        }).catch(err => {
          // Failed.
          alert(err);
        })
      },
      jsonpFunc: function(json) {
        alert('code=' + json.code + ',msg=' + json.msg + ',t=' + json.t);
      }
  },
  created: function() {
      var _self = this;
      //_self.query_test();
      _self.testJsonp();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
