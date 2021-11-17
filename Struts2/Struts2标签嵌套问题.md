---
title: Struts2标签嵌套问题
date: 2021-02-26 17:02:00
categories: Struts2
toc: true
---

# Struts2标签嵌套问题

## 参考网址

* google关键词: struts2 标签嵌套
* [Struts2标签嵌套问题](https://blog.csdn.net/z_play_du/article/details/7437685)

## 代码

``` jsp
<s:textfield name="orderInfo.num" size="20" value="%{count}" οnchange="changeCount(this.value,'%{foodid}')"></s:textfield>
```