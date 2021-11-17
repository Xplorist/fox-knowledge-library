---
title: jQuery设置disabled属性与移除disabled属性
date: 2021-02-26 17:27:00
categories: JQuery
toc: true
---

# jQuery设置disabled属性与移除disabled属性

## 参考网址

* [使用jQuery设置disabled属性与移除disabled属性](https://www.imooc.com/article/14003)

## 代码

``` javascript
//两种方法设置disabled属性
$('#areaSelect').attr("disabled",true);
$('#areaSelect').attr("disabled","disabled");
//三种方法移除disabled属性
$('#areaSelect').attr("disabled",false);
$('#areaSelect').removeAttr("disabled");
$('#areaSelect').attr("disabled","");
```