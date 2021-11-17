---
title: SpringBoot中JSON转化Date类型配置
date: 2020-12-10 09:55:00
tags:
  - Java
  - SpringBoot
categories:
  - [ Java, SpringBoot ]
---

# content

* application.properties中配置jackson格式化JSON的属性

``` yml
#spring.jackson.default-property-inclusion=NON_NULL
spring.jackson.time-zone=GMT+8
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
```

* 解释：spring.jackson.default-property-inclusion=NON_NULL 被注释掉是
因为前端如果传递null的时间，jackson会拦截，导致前端需要额外操作进行时间处理，徒增麻烦
* 不过还是把这个注释掉的配置写上，说不定有的地方需要将其打开