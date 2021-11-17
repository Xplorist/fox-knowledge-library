var app = new Vue({
	el: '#app',
	data: {
		title: '品管文件發行',
		dataList: [],
		pageSize: 10,
	},
	methods: {
		// 新增
		add: function () {
			
		},
		// 查询
		query: function (pageIndex) {
			var _self = this;
			axios({
			  method: 'post',
			  url: 'query.x',
			  data: ''
			}).then(function(response) {
				var data = response.data;
				_self.dataList = data.list;
				window.console.log(data);
				
				var row_total = data.row_total;
				var page_total = data.page_total;
				
				myPagi = new myPagination({
					id: 'pagination',
					curPage: pageIndex, //初始页码
					pageTotal: page_total, //总页数
					pageAmount: _self.pageSize, //每页多少条
					dataTotal: row_total, //总共多少条数据
					pageSize: 5, //可选,分页个数
					showPageTotalFlag: true, //是否显示数据统计
					showSkipInputFlag: true, //是否支持跳转
					getPage: function(pageIndex) {
						// 点击分页按钮请求数据
						_self.query(pageIndex);
					}
				});
			}); 
		},
	},
	created: function () {
		
	}
});

// 声明分页组件示例
var myPagi;

window.onload = function(){
    var tableCont = document.querySelector('#table_cont');// #table-cont为包含table的div
    function scrollHandle (e) {
        var scrollTop = this.scrollTop;
		var scrollLeft = this.scrollLeft;
		this.querySelector('thead').style.transform = 'translateY(' + scrollTop + 'px)';
		
		var tbody_lefts = this.querySelectorAll('.tbody_left');
		for(var i = 0; i < tbody_lefts.length; i++) {
			tbody_lefts[i].style.transform = 'translateX(' + scrollLeft + 'px)';
		}
	
		var thead_top_lefts = this.querySelectorAll('.thead_top_left');
		for(var i = 0; i < thead_top_lefts.length; i++) {
			thead_top_lefts[i].style.transform = 'translate(' + scrollLeft + 'px, 0px)';
		}
    }

    tableCont.addEventListener('scroll', scrollHandle);
}