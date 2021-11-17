# sheetjs前端解析excel的demo

* html

```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PDTList</title>
<!-- css library -->
<link rel="stylesheet" href="./lib/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./lib/bootstrap-3.3.7-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="./lib/font-awesome-4.7.0/css/font-awesome.min.css">
<!-- custom css -->
<link rel="stylesheet" href="./css/PDTList.css">
</head>
<body>
<div id="app">
	<h1 style="text-align: center;">{{ title }}</h1>
	<a href="/ePDMWeb/Project/PdList/resource/test.xlsx">test.xlsx</a>
	<input type="file" id="excelFile" v-on:change="fileChange($event)" />
</div>
</body>
<!-- JS library -->
<script type="text/javascript" src="./lib/jquery-1.12.4/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="./lib/axios-0.19.1-dist/axios.min.js"></script>
<script type="text/javascript" src="./lib/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./lib/sheetjs-0.15.5/xlsx.full.min.js"></script>
<script type="text/javascript" src="./lib/vue-2.6.11/vue.min.js"></script>
<!-- custom JS -->
<script type="text/javascript" src="./js/PDTList.js"></script>
</html>

```

* js

```
var app = new Vue({
	el: '#app',
	data: {
		title: 'PDTList',
		excelArray: [],// excel数组
		excelJsonKey: ['project_name', 'dept_name', 'emp_no'],// excel数据的key
	},
	computed: {
		
	},
	methods: {
		// 文件变更触发事件，解析excel
		fileChange: function (e) {
			var _self = this;
			var files = e.target.files, f = files[0];
			var reader = new FileReader();
			reader.onload = function(e) {
				var data = new Uint8Array(e.target.result);
				var workbook = XLSX.read(data, {type: 'array'});
			
				var fromTo = '';
				for (var sheet in workbook.Sheets) {
					if (workbook.Sheets.hasOwnProperty(sheet)) {
						fromTo = workbook.Sheets[sheet]['!ref'];
						console.log(fromTo);
						// XLSX.utils.sheet_to_json中的配置说明：header: ['project_name', 'dept_name', 'emp_no']中的数组定义了json数据中的每个对象的属性名，range：1 表示从第2行开始取值，去除表头数据
						_self.excelArray = _self.excelArray.concat(XLSX.utils.sheet_to_json(workbook.Sheets[sheet], {header: _self.excelJsonKey, range: 1}));
						// break; // 如果只取第一张表，就取消注释这行
					}
				}
				
				window.console.log(_self.excelArray);
		    };
		    reader.readAsArrayBuffer(f);
			
		},
		// 向后台发送数据
		sendData: function () {
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
		},
	},
	created: function () {
		
	}
});

```