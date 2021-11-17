### Nginx通過try_files方法獲取靜態資源

****

> 參考網址：
> 
> * [https://www.cnblogs.com/a-flydog/p/8619763.html](https://www.cnblogs.com/a-flydog/p/8619763.html)

****

配置文件

nginx.conf

```
        location / {
            try_files $uri $uri/ /index.html;
        }
		
		# 靜態資源
		location ~ .*\.(png|jpeg|jpg|gif|css|js)$ {
			try_files $uri =404;
		}
		
		#項目配置
		location ^~ /bid {
			try_files $uri $uri/ /bid/index.html;
		}
```



****

.


