### SpringBoot定時任務

****

> 參考網址：
> 
> * SpringBoot定時任務的几种實現方式：[https://blog.csdn.net/wqh8522/article/details/79224290](https://blog.csdn.net/wqh8522/article/details/79224290)
> 
> * cron表達式詳細介紹：[https://blog.csdn.net/zyb2017/article/details/76223385](https://blog.csdn.net/zyb2017/article/details/76223385)
> 
> * cron表達式工具網站：[http://qqe2.com/cron](http://qqe2.com/cron)

****

1. 項目依賴
   
   pom.xml中需要加入lombok
   
   ```xml
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <optional>true</optional>
           </dependency>
   ```

2. 創建任務類
   
   ```java
   package com.foxconn.bidding.util;
   
   import com.foxconn.bidding.service.EmailService;
   import lombok.extern.slf4j.Slf4j;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.scheduling.annotation.Scheduled;
   import org.springframework.stereotype.Component;
   
   /**
    * 定時任務工具類
    */
   @Slf4j
   @Component
   public class ScheduledUtil {
       @Autowired
       private EmailService emailService;
   
       // 每天早上9點發送發單和中標匯總情況郵件
       @Scheduled(cron = "0 0 9 * * *")
       public void sendStatisticsEmailTask() {
           //log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
           emailService.sendStatisticsEmail();
       }
   }
   ```

3. 在主类上使用@EnableScheduling注解
   
   ```java
   package com.foxconn.bidding;
   
   import org.mybatis.spring.annotation.MapperScan;
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.boot.builder.SpringApplicationBuilder;
   import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
   import org.springframework.scheduling.annotation.EnableScheduling;
   import org.springframework.transaction.annotation.EnableTransactionManagement;
   
   @SpringBootApplication
   @EnableScheduling
   @EnableTransactionManagement
   @MapperScan("com.foxconn.bidding.mapper")
   public class BiddingApplication extends SpringBootServletInitializer {
   
       public static void main(String[] args) {
           SpringApplication.run(BiddingApplication.class, args);
       }
   
       @Override
       protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
           return builder.sources(BiddingApplication.class);
       }
   }
   ```

.
