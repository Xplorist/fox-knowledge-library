# SpringCloud_Eureka各服务修改为IP注册

## application.yml

```

spring:
  application:
    name: eureka-consumer
server:
  port: 9000
eureka:
  instance:
    prefer-ip-address: true
    ip-address: 10.244.186.86
    instance-id: ${eureka.instance.ip-address}:${spring.application.name}:${server.port}
  client:
    service-url:
      defaultZone: http://10.244.231.103:7000/eureka/ # 指定 Eureka 注册中心的地址

```