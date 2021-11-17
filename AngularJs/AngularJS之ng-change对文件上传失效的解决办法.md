---
title: AngularJS之ng-change对文件上传失效的解决办法
date: 2021-02-24 17:16:00
categories: AngularJs
toc: true
---

# AngularJS之ng-change对文件上传失效的解决办法

## 参考网址

* google关键词： angularjs file change event
* [](https://stackoverflow.com/questions/20146713/ng-change-on-input-type-file/41557378)

## 核心代码

* js

``` javascript
function myCtrl($scope) {
    $scope.uploadImage = function () {
        console.log("Changed");
    }
}
```

* html

``` html
<div ng-app ng-controller="myCtrl">
    <input type="file" ng-model="image" onchange="angular.element(this).scope().uploadImage()" />
</div>
```