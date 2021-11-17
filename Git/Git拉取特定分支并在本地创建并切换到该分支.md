---
title: Git拉取特定分支并在本地创建并切换到该分支
date: 2021-01-18 13:28:00
categories: git
toc: true
---

# Git拉取特定分支并在本地创建并切换到该分支

## 拉取特定分支命令

``` shell
git fetch origin dev
```

## 本地创建分支并切换到该分支

``` shell
git checkout -b dev origin/dev
```

## 参考网址

* [git 拉取远程分支到本地](https://blog.csdn.net/carfge/article/details/79691360)