### SpringBoot同時實現jar和war部署

***

> 參考網址：https://blog.csdn.net/wolfcode_cn/article/details/83780832
>
> 英文原文鏈接：http://dolszewski.com/spring/dual-jar-war-build-for-spring-boot/

1.修改SpringBoot啟動類,繼承SpringBootServletInitializer

```java
@SpringBootApplication
public class XxxApplication extends SpringBootServletInitializer {
 
    public static void main(String[] args) {
        SpringApplication.run(XxxApplication.class, args);
    }
 
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(XxxApplication.class);
    }
}
```

2.修改pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
 
    <groupId>com.dolszewski</groupId>
    <artifactId>spring-boot-dual-build</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>${project.packaging}</packaging>
 
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.3.3.RELEASE</version>
    </parent>
 
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
 
    <properties>
        <java.version>1.8</java.version>
    </properties>
 
    <!-- 開發時，dev為true，release為false -->
    <!-- 打包部署時，release為true,dev為false -->
    <profiles>
        <profile>
            <id>dev</id>
            <activation>
                <activeByDefault>false</activeByDefault>
            </activation>
            <properties>
                <project.packaging>jar</project.packaging>
            </properties>
        </profile>
        <profile>
            <id>release</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <project.packaging>war</project.packaging>
            </properties>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-web</artifactId>
                    <exclusions>
                        <exclusion>
                            <artifactId>spring-boot-starter-tomcat</artifactId>
                            <groupId>org.springframework.boot</groupId>
                        </exclusion>
                    </exclusions>
                </dependency>
                <dependency>
                    <groupId>javax.servlet</groupId>
                    <artifactId>javax.servlet-api</artifactId>
                    <version>3.1.0</version>
                    <scope>provided</scope>
                </dependency>
            </dependencies>
        </profile>
    </profiles>
 
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <configuration>
                    <warSourceExcludes>src/main/resources/**</warSourceExcludes>
                    <warName>bidding</warName>
                </configuration>
           </plugin>
        </plugins>
        
        <finalName>bidding</finalName>
    </build>
</project>
```