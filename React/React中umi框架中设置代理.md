---
title: React中umi框架中设置代理
date: 2021-01-28 15:43:00
categories: React
toc: true
---

# React中umi框架中设置代理

## 参考网址

> * [proxy](https://umijs.org/config#proxy)

## 示例代码

``` javascript
export default {
  proxy: {
    '/api': {
      'target': 'http://jsonplaceholder.typicode.com/',
      'changeOrigin': true,
      'pathRewrite': { '^/api' : '' },
    },
  },
}
```

然后访问 /api/users 就能访问到 http://jsonplaceholder.typicode.com/users 的数据。

> 注意：proxy 配置仅在 dev 时生效。