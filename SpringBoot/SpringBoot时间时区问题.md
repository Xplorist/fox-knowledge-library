# SpringBoot时间时区问题

## 记录时间

> 2020.03.20

## 软件环境及版本

> * JDK 版本： 1.8.0_201
>
> * SpringBoot 版本： 2.2.2.RELEASE 

## 参考资料

> * 参考网址： [Spring Boot(Mybatis,Mysql) 查询时间类型的数据比数据库里的记录慢8个小时](http://lingxue.51so.info/entry/2c92abb864647cc40166aa5d615b22ed)

## 具体问题

* **问题描述**： SpringBoot 查询时间时区总是UTC(格林威治标准时)，肯定是Spring的Jackson框架转换Date类型时默认的时区是GMT

* **解决方案**：实体类添加Jackson的 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8") 注解注明时区

> * 代码如下： 

```

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
private Date createTime;

```