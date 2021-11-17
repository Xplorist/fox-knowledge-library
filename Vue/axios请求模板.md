# axios请求模板

****

## 普通json请求

* 一般json數據請求，默认的responseType: 'json'

``` js
axios({
  method: 'post',
  url: '/ePDMWeb/QM_DOC/xxx.x',
  data: ''
}).then(function(response) {
	var data = response.data;
	if(data.code === '1') {
		var t = data.t;
	} else {
		alert(data.msg);
	}
});
```

* params方式传参数，相当于是url?pram1=xxx&param2=yyy的方式

``` javascript
axios({
	method: 'post',
	url: '/ePDMWeb/jQueryAjax/AuthorityAction!fileSafeAuRej.action',
	params: {
		id: _self.id,
		rej_memo: encodeURIComponent(_self.rej_memo)
	}
}).then(function(response) {
	var data = response.data;
	alert(data.resultMsg);
	if(data.resultFlag === 1) {
		// 删除文件
		_self.deleteFile();
	}
});
```

****

## 文件下载请求

* 文件下載請求，responseType: 'blob'

``` js

downloadFile: function (file_origin_name, file_save_name, file_save_path) {
	var _self = this;

	// encodeURIComponent() 函數對參數進行編碼
	// encodeURIComponent() 非常重要，不然某些字符如‘+’号（加号）就会被转换成空格，导致文件名出错，下载失败
	// 通过url方式传参时，如果参数中涉及到特殊符号，必须使用encodeURIComponent()函数对参数进行编码
	axios({
		url: '/ePDMWeb/file/download.x?file_origin_name=' + encodeURIComponent(file_origin_name) + '&file_save_name=' + encodeURIComponent(file_save_name) + '&file_save_path=' + encodeURIComponent(file_save_path),
		method: 'POST',
		responseType: 'blob'
	}).then(function(response){
		var url = window.URL.createObjectURL(new Blob([response.data]));
		var link = document.createElement('a');
		link.href = url;
		link.setAttribute('download', file_origin_name); 
		document.body.appendChild(link);
		link.click();
	});
},

```

## 文件上传请求

* 文件上传请求，headers: {'Content-Type':'multipart/form-data'}

``` js

update_file: function (event) {
	var _self = this;
	var file = event.target.files[0];
	var param = new FormData();
	param.append('file', file);
	param.append('file_type', 'user_pic');

	axios({
		method: 'post',
		url: '/api/ftp_file/upload',
		headers: {'Content-Type':'multipart/form-data'},
		data: param
	}).then(function(response) {
		var data = response.data;
		if(data.code == "1") {
			var t = data.t;
			// encodeURIComponent() 函數對參數進行編碼
			// encodeURIComponent() 非常重要，不然後端服務器無法處理
			_self.file_save_path = encodeURIComponent(t.file_save_path);
			_self.file_save_name = encodeURIComponent(t.file_save_name);
			_self.file_origin_name = encodeURIComponent(t.file_origin_name);
			_self.build_img_src();
			//alert(_self.img_src);
			alert("上傳成功, 文件名:" +  _self.file_origin_name);
		} else {
			alert("上傳失敗," + data.msg + data.t);
		}
	});
},

```