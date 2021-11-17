### Nginx部署多个项目(最好的配置)

****

> 参考网址：
> 
> * [https://blog.csdn.net/weixin_38023551/article/details/88640939](https://blog.csdn.net/weixin_38023551/article/details/88640939)

****

##### 核心配置

```
		#root         html;

		location / {
			root E:\frontEndProjects/xplorist;
			try_files $uri $uri/ @router;
            index  index.html index.htm;
		}
		
		location /test {
			alias E:\frontEndProjects/test/;
			try_files $uri $uri/ /test/index.html;
            index  index.html index.htm;
		}
		
		location @router {
            rewrite ^.*$ /index.html last;
        }
```

****

关键在于

1. #root         html;

2. alias

3. @router

****

这种配置的优点：

1. Nginx的根项目和其他项目在目录上是平级的，这样就能够从目录上区分出多个项目，如果不是平级的话，就需要将其他项目放在根项目的目录之中，作为根项目的目录的子目录，很明显这种子目录不直观，很容易弄混，如果是SPA项目的话这样做根本就行不通。

****

。
