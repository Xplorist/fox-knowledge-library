---
title: SonarQube简易使用手册
date: 2020-12-22 18:46:00
categories: SonarQube
toc: true
---

# SonarQube简易使用手册

## 参考网址

> * [SonarScanner for Maven](https://docs.sonarqube.org/7.8/analysis/scan/sonarscanner-for-maven/)

## Maven项目的代码扫描

* 配置Maven的全局配置文件Setting.xml

``` xml
<settings>
    <pluginGroups>
        <pluginGroup>org.sonarsource.scanner.maven</pluginGroup>
    </pluginGroups>
    <profiles>
        <profile>
            <id>sonar</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <!-- Optional URL to server. Default value is http://localhost:9000 -->
                <sonar.host.url>
                  http://localhost:9000
                </sonar.host.url>
            </properties>
        </profile>
     </profiles>
</settings>
```

* 进入到需要扫描的Maven项目的根目录下，执行以下命令

``` sh
mvn clean verify sonar:sonar
```