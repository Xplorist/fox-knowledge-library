### Nginx一台服務器中配置多個vue項目

***

> 參考網址：https://juejin.im/post/5cfe23b3e51d4556f76e8073

nginx.conf核心配置

```
    server {
        #listen       80;
		listen        8086;
        server_name  localhost;
		root         E:\static;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            try_files $uri $uri/ /index.html;
        }
		
		#項目配置
		location ^~ /bidding {
			try_files $uri $uri/ /bidding/index.html;
		}
		location ^~ /send {
			try_files $uri $uri/ /send/index.html;
		}
		
		#項目對應api代理配置
		location /bidding/api/ {
			proxy_pass http://10.244.231.103:8081/api/;
		}
	}
```

vue Cli3項目中的配置：

```js
module.exports = {
  // 部署应用包时的基本 URL
  publicPath: process.env.NODE_ENV === 'production'? '/bidding/': '/',
};
```

vue Cli3項目中的請求一律為以  /bidding/api/  格式開頭

