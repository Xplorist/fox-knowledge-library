### SpringBoot日誌

****

> 參考網址：
> 
> * 很詳細的配置，主要參考這篇博客[https://blog.csdn.net/Inke88/article/details/75007649](https://blog.csdn.net/Inke88/article/details/75007649)
> 
> * [https://blog.csdn.net/lchq1995/article/details/80080642](https://blog.csdn.net/lchq1995/article/details/80080642)
> 
> * [https://www.cnblogs.com/huanzi-qch/p/11041300.html](https://www.cnblogs.com/huanzi-qch/p/11041300.html)

****

我自己的配置：

1. application.properties
   
   ```properties
   # logback configuration
   logging.config=classpath:configuration/logback-spring.xml
   logback.appname=bidding
   logback.logdir=/SpringBoot/bidding/log
   ```

2. logback-spring.xml
   
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <configuration scan="true" scanPeriod="60 seconds" debug="false">
   
       <springProperty scope="context" name="logback.appname" source="logback.appname"/>
       <springProperty scope="context" name="logback.logdir" source="logback.logdir"/>
   
       <contextName>${logback.appname}</contextName>
   
       <!--输出到控制台 ConsoleAppender-->
       <appender name="consoleLog1" class="ch.qos.logback.core.ConsoleAppender">
           <!--展示格式 layout-->
           <layout class="ch.qos.logback.classic.PatternLayout">
               <pattern>
                   <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %contextName [%thread] %-5level %logger{36} - %msg%n</pattern>
               </pattern>
           </layout>
           <!--
           <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
                <level>ERROR</level>
           </filter>
            -->
       </appender>
   
       <!--输出到控制台 ConsoleAppender-->
       <appender name="consoleLog2" class="ch.qos.logback.core.ConsoleAppender">
           <encoder>
               <pattern>%d -2 %msg%n</pattern>
           </encoder>
       </appender>
   
       <appender name="fileInfoLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
           <!--如果只是想要 Info 级别的日志，只是过滤 info 还是会输出 Error 日志，因为 Error 的级别高，
           所以我们使用下面的策略，可以避免输出 Error 的日志-->
           <filter class="ch.qos.logback.classic.filter.LevelFilter">
               <!--过滤 Error-->
               <level>ERROR</level>
               <!--匹配到就禁止-->
               <onMatch>DENY</onMatch>
               <!--没有匹配到就允许-->
               <onMismatch>ACCEPT</onMismatch>
           </filter>
           <!--日志名称，如果没有File 属性，那么只会使用FileNamePattern的文件路径规则
               如果同时有<File>和<FileNamePattern>，那么当天日志是<File>，明天会自动把今天
               的日志改名为今天的日期。即，<File> 的日志都是当天的。
           -->
           <File>${logback.logdir}/info.${logback.appname}.log</File>
           <!--滚动策略，按照时间滚动 TimeBasedRollingPolicy-->
           <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
               <!--文件路径,定义了日志的切分方式——把每一天的日志归档到一个文件中,以防止日志填满整个磁盘空间-->
               <FileNamePattern>${logback.logdir}/info.${logback.appname}.%d{yyyy-MM-dd}.log</FileNamePattern>
               <!--只保留最近90天的日志-->
               <maxHistory>90</maxHistory>
               <!--用来指定日志文件的上限大小，那么到了这个值，就会删除旧的日志-->
               <!--<totalSizeCap>1GB</totalSizeCap>-->
           </rollingPolicy>
           <!--日志输出编码格式化-->
           <encoder>
               <charset>UTF-8</charset>
               <pattern>%d [%thread] %-5level %logger{36} %line - %msg%n</pattern>
           </encoder>
       </appender>
   
       <appender name="fileErrorLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
           <!--如果只是想要 Error 级别的日志，那么需要过滤一下，默认是 info 级别的，ThresholdFilter-->
           <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
               <level>Error</level>
           </filter>
           <!--日志名称，如果没有File 属性，那么只会使用FileNamePattern的文件路径规则
               如果同时有<File>和<FileNamePattern>，那么当天日志是<File>，明天会自动把今天
               的日志改名为今天的日期。即，<File> 的日志都是当天的。
           -->
           <File>${logback.logdir}/error.${logback.appname}.log</File>
           <!--滚动策略，按照时间滚动 TimeBasedRollingPolicy-->
           <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
               <!--文件路径,定义了日志的切分方式——把每一天的日志归档到一个文件中,以防止日志填满整个磁盘空间-->
               <FileNamePattern>${logback.logdir}/error.${logback.appname}.%d{yyyy-MM-dd}.log</FileNamePattern>
               <!--只保留最近90天的日志-->
               <maxHistory>90</maxHistory>
               <!--用来指定日志文件的上限大小，那么到了这个值，就会删除旧的日志-->
               <!--<totalSizeCap>1GB</totalSizeCap>-->
           </rollingPolicy>
           <!--日志输出编码格式化-->
           <encoder>
               <charset>UTF-8</charset>
               <pattern>%d [%thread] %-5level %logger{36} %line - %msg%n</pattern>
           </encoder>
       </appender>
   
       <!--指定最基础的日志输出级别-->
       <root level="INFO">
           <!--appender将会添加到这个loger-->
           <appender-ref ref="consoleLog1"/>
           <!-- <appender-ref ref="consoleLog2"/> -->
           <appender-ref ref="fileInfoLog"></appender-ref>
           <appender-ref ref="fileErrorLog"></appender-ref>
       </root>
   
   </configuration>
   ```

.
