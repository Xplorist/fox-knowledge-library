# Vue使用v-for循环二维数组或List

> * 参考网址： [使用vue中的v-for遍历二维数组](https://blog.csdn.net/qq_39685062/article/details/77053833)

* 核心机制：使用&lt;template&gt;标签

* 示例代码：

* html

```

	<table class="table table-bordered">
		<tbody>
			<tr v-for="x in menuList">
				<td>{{ x.menu_name }}</td>
				<template v-for="y in x.deptList">
					<td v-for="z in y.operateList">{{ z.auFlag }}</td>
				</template>
			</tr>
		</tbody>
		<thead>
			<tr>
				<th>部门</th>
				<th colspan="4" v-for="x in deptList">{{ x.dept_name }}</th>
			</tr>
			<tr>
				<th>菜单|操作</th>
				<template v-for="x in deptList">
					<th v-for="y in x.operateList">{{ y.operate_name }}</th>
				</template>
			</tr>
		</thead>
	</table>

```

* js

```
var app = new Vue({
	el: '#app',
	data: {
		title: '角色权限表',
		tableHead: [],// 表头数据
		tableBody: [],// 表体数据
		deptList: [],// 单位list
		menuList: [],// 菜单list
		
	},
	computed: {
		
	},
	methods: {
		// 查询所有表格数据
		queryTableData: function () {
			var _self = this;
			
			axios({
			  method: 'post',
			  url: '/ePDMWeb/PdList/queryTableData.x'
			}).then(function(response) {
				var data = response.data;
				if(data.code === '1') {
					var t = data.t;
					_self.tableHead = t.tableHead;
					_self.tableBody = t.tableBody;
				} else {
					alert(data.msg);
				}
			});
		},
		// 查询表头数据
		queryTableHead: function () {
			var _self = this;
			
			axios({
			  method: 'post',
			  url: '/ePDMWeb/PdList/queryTableHead.x',
			}).then(function(response) {
				var data = response.data;
				if(data.code === '1') {
					var t = data.t;
					_self.deptList = t.deptList;
					_self.menuList = t.menuList;
					
					for (var i = 0; i < _self.menuList.length; i++) {
						var menu = _self.menuList[i];
						_self.queryMenuByNo(menu);
					}
				} else {
					alert(data.msg);
				}
			});
		},
		// 【03】根据菜单编号查询菜单数据
		queryMenuByNo: function (menu) {
			var _self = this;
			
			axios({
			  method: 'post',
			  url: '/ePDMWeb/PdList/queryMenuByNo.x?menu_no=' + menu.menu_no,
			}).then(function(response) {
				var data = response.data;
				if(data.code === '1') {
					var t = data.t;
					menu.deptList = t.deptList;
				} else {
					alert(data.msg);
				}
			});
		}
	},
	created: function () {
		//this.queryTableData();
		this.queryTableHead();
	}
	
});

```