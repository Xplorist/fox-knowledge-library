function jQueryAjaxTemplate(){
	$.ajax({
		url: 'xxx',
		type: 'post',
		async: 'true', 
		data:{
			'xxx': xxx,
			'xxx': xxx
		},
		error:function(){
			alert("网络中断");
		},
		success:function(result){
			result.xxx;
		}
	});
}

function angularJsHttpTemplate(){
	$http({
        method: 'POST',
        url: 'xxx!xxx.action',
        data:{
        	'xxx': xxx,
        	'xxx': xxx
        }
    }).then(function successCallback(response) {
            $scope.xxx = response.data.xxx;
        }, function errorCallback(response) {
            // 请求失败执行代码
    });
}

$(document).ready(function(){
	
});

var app = angular.module('myApp', ['tm.pagination']);

app.controller('myCtrl', function($scope, $http) {
	/********************-初始化数据-***********************/
	$scope.nameList = [];
	$scope.nameShowList = [];
	// 分頁配置
	$scope.paginationConf = {
            currentPage: 1,
            totalItems: 0,
            itemsPerPage: 15,
            pagesLength: 15,
            perPageOptions: [10, 20, 30, 40, 50],
            rememberPerPage: 'perPageItems',
            onChange: function(){
            	// 分頁核心代碼
            	if($scope.nameList != []){
            		$scope.paginationConf.totalItems = $scope.nameList.length;
            		var currentPageStartItem = ($scope.paginationConf.currentPage - 1) * $scope.paginationConf.itemsPerPage;
            		var currentPageEndItem = currentPageStartItem + $scope.paginationConf.itemsPerPage;
            		$scope.nameShowList = $scope.nameList.slice(currentPageStartItem, currentPageEndItem);
            	}
            }
        };
	
	/********************-点击事件-***********************/
	$scope.queryNames = function(){
		for(var i = 0; i < 100; i++){
			var temp = {
					"name" : "name" + i
			};
			$scope.nameList.push(temp);
		}
		
		$scope.paginationConf.totalItems = $scope.nameList.length;
		$scope.paginationConf.currentPage = 1;
		$scope.nameShowList = $scope.nameList.slice(0, $scope.paginationConf.itemsPerPage);
	}
});

