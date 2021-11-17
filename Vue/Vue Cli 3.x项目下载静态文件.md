# Vue Cli 3.x项目下载静态文件

## 代码示例：

```
<a href="./favicon.ico" download="favicon.ico">download</a>
```

## 说明

* 因为Vue Cli 3.x项目是单页应用，最终的文件都是整合到index.html中，
所以文件路径直接用public包下的index.htnl的路径作为基础路径，
然后找到静态资源文件的相对地址
* 用a标签进行下载，href属性中添加相对地址，download属性中添加文件名称，
在页面上直接点击a标签就可以下载静态资源文件了。