### js為Object對象動態添加屬性和值

> 參考網址：
>
> https://blog.csdn.net/u011127019/article/details/59112030

方式1：

```javascript
var obj = {}; //或者 var obj=new Object();
var key = "name";
var value = "张三丰"
obj[key] = value;
console.info(obj);
```

方式2：

```javascript
var obj = {};
var key = "name";
var value = "张三丰"
eval("obj.p" + key + "='" + value + "'");
console.info(obj);
```

