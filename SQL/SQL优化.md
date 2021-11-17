---
title: SQL优化
date: 2020-11-02
tags: 
  - SQL
categories:
  - [SQL]
---

# SQL优化

## 目录

* [参考网址](#参考网址)
* [2021-01-05_参考网址](#2021-01-05_参考网址)

## 参考网址

> * [数据库SQL优化大总结之 百万级数据库优化方案](https://www.cnblogs.com/yunfeifei/p/3850440.html)
> * [SQL 索引](https://wiki.jikexueyuan.com/project/sql/indexes.html)
> * [一文搞懂各种数据库SQL执行计划：MySQL、Oracle等](https://database.51cto.com/art/201912/608403.htm)
> * [深入理解MySQL索引原理和实现——为什么索引可以加速查询？](https://blog.csdn.net/tongdanping/article/details/79878302)
> * [抓包工具：fiddler和wireshark对比](https://zhuanlan.zhihu.com/p/44912855)
> * [Blob.type](https://developer.mozilla.org/zh-CN/docs/Web/API/Blob/type)
> * [File.File()](https://developer.mozilla.org/zh-CN/docs/Web/API/File/File)
> * [FileReader](https://developer.mozilla.org/zh-CN/docs/Web/API/FileReader)
> * [Html5——File、FileReader、Blob、Fromdata对象](https://blog.csdn.net/mr_wuch/article/details/70141674)
> * [FormData](https://developer.mozilla.org/zh-CN/docs/Web/API/FormData)
> * [FormData.append()](https://developer.mozilla.org/zh-CN/docs/Web/API/FormData/append)
> * [MIME 类型](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Basics_of_HTTP/MIME_types)
> * [收获，不止SQL优化--抓住SQL的本质 .pdf 免费下载](https://my.oschina.net/u/3415536/blog/2244475)
> * [文件下载：收获，不止SQL优化--抓住SQL的本质 .pdf](https://306t.com/file/1475340-237210650)

## 2021-01-05_参考网址

> * [mysql多表关联查询关联条件不走索引的问题](https://blog.csdn.net/d19901217/article/details/86636562)
> * [Mysql 用户权限管理--从 xxx command denied to user xxx](https://www.cnblogs.com/smallrookie/p/7552097.html)
> * [多表关联和事务及索引](https://www.cnblogs.com/markshui/p/12967778.html)
> * [没内鬼，来点干货！SQL优化和诊断](https://juejin.cn/post/6844904135964229646)
> * [Mysql多表连接查询的执行细节（一）](https://www.cnblogs.com/xueqiuqiu/articles/10517498.html)

## 2021-01-07_经验谈

优化了portal的系统统计，SQL语句里面做了索引优化，将不走索引的SQL语句优化为走索引后，查询效率的提升是肉眼可见，以前查询一个SQL需要12s,
加了索引后，查询时间缩短到300多ms,第一个优化成功时，自己真的震惊了，那种喜悦真的是无法表达。sql优化后，虽然效率提升了很多，但是仍然不能
够及时显示数据，因为只用一个查询来返回数据，全部数据返回时，仍然需要十几秒甚至三十几秒（SQL优化前的查询返回时间是几分钟！！！），
后面自己把前端的echarts需要的数据结构看了一下，把数据划分维度来查询返回，本来想做成异步并发的查询，但是前端显示的时候异步不好处理，
最后还是有序的链式同步查询，先显示查询时间最短的数据马上返回然后页面上进行显示，然后依次查询后面其他维度的数据，这样做其实并不算最
理想的情况，但是目前来说，用户体验提升了很多，自己也满足了，不追求完美，一步一步来，估计以后会发现更好的方法，异步并发非阻塞这块的东西
还需要多了解。