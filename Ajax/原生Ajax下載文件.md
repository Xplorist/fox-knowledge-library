### 原生Ajax下載文件

***

> 參考網址：https://www.jianshu.com/p/22f82be980fb
>
> https://www.jianshu.com/p/1e189c14aa98
>
> https://gitee.com/ruihaozhai/codes/807mpnrikfawzets6xo1920

核心在於：

```
//设置请求结果类型为blob
xhr.responseType = 'blob';

var fileName = 'LOGO.png';
var link = document.createElement('a');
link.href = window.URL.createObjectURL(blob);
link.download = fileName;
link.click();
```

示例代碼：

```js
function getDownload() {
		//创建XMLHttpRequest对象
	var xhr = new XMLHttpRequest();
	//配置请求方式、请求地址以及是否同步
	xhr.open('GET', 'http://10.244.186.86:8081/api/pic_show/load?file_save_path=user_pic/default&file_save_name=LOGO.png&file_origin_name=LOGO.png', true);
	//设置请求结果类型为blob
	xhr.responseType = 'blob';
	//请求成功回调函数
	xhr.onload = function (e) {
		if (this.status == 200) {//请求成功
			//获取blob对象
			var blob = this.response;
			alert("ok");
			var img = document.createElement('img');
            img.src = window.URL.createObjectURL(this.response); // 2
            img.onload = function() {
                window.URL.revokeObjectURL(this.src); //3
            };
            document.body.appendChild(img);
			
			var fileName = 'LOGO.png';
			var link = document.createElement('a');
			link.href = window.URL.createObjectURL(blob);
			link.download = fileName;
			link.click();
			//window.URL.revokeObjectURL(link.href);
			//window.open(window.URL.createObjectURL(blob));
			
			//获取blob对象地址，并把值赋给容器
			//document.querySelector('video').src=URL.createObjectURL(blob);
		}
	};
	xhr.send();
}
```