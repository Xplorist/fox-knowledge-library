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

var myApp = angular.module('myApp', []);

myApp.controller('MainController', function($scope) {
	$scope.box1 = $scope.box2 = $scope.box3 = true;
  });

myApp.directive('slideToggle', function() {  
	return {
	  restrict: 'A',      
	  scope:{
		isOpen: "=slideToggle"
	  },  
	  link: function(scope, element, attr) {
		var slideDuration = parseInt(attr.slideToggleDuration, 10) || 200;      
		scope.$watch('isOpen', function(newVal,oldVal){
		  if(newVal !== oldVal){ 
			element.stop().slideToggle(slideDuration);
		  }
		});
	  }
	};  
  });