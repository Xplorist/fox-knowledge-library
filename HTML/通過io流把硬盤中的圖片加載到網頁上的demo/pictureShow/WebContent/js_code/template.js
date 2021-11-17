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
        headers: {
			'Content-Type': 'application/json'
		},
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

var app = angular.module('myApp', []);

app.controller('myCtrl', function($scope, $http) {
	/********************-初始化数据-***********************/
	$scope.xxx = "";
	$scope.newMouldList = [];
	$scope.contextPath = "";
	/********************-点击事件-***********************/
	$scope.test = function(){
		$http({
			method: 'POST',
			url: '../TestServlet',
			headers: {
				'Content-Type': 'application/json'
			},
			data:{
			}
		}).then(function successCallback(response) {
			$scope.newMouldList = response.data.newMouldList;
			$scope.contextPath = response.data.contextPath;
		}, function errorCallback(response) {
			// 请求失败执行代码
			alert("網絡出錯");
			console.log(response);
		});
	}
});

