(function(){
	
var app = angular.module("myApp", []);
app.controller("myController",function($scope, $http,$timeout) {
	
	$scope.btnPicUploadClick = function() {
		var files_1 = document.getElementById('file').files;
		if (files_1.length == 0) {
			alert("請選擇需上傳的文件");
			return false;
		} else {
			var vOriginalName = files_1[0].name;

			$.ajaxFileUpload({
						type : "POST",
						url : '../../FileUpDown/FileUploadAction!fileUpload.action',
						async : false,
						secureuri : false,
						fileElementId : 'file',// 此id的input元素的name和id必須相同
						dataType : 'json',
						data : {
							'file_origin_name' : vOriginalName
						},
						success : function(data) {
							var vData = data;

							if (vData.result == "0") {
								alert("附件上傳失敗");
								return false;
							}

							$scope.file_origin_name = vData.file_origin_name;
							$scope.file_save_name = vData.file_save_name;
							$scope.file_save_path = vData.file_save_path;
							
							$scope.$apply();
						},
						error : function(data) {
							alert("文件上傳異常");
						}
					});			
		}
	}
	
	// 文件下載
	$scope.downfile = function(file_save_name) {
		$.fileDownload('../../FileUpDown/FileDownloadAction.action', {
			httpMethod : "POST",
			data : {
				'fileName' : file_save_name,
				'filePath' : file_save_path
				}
		});
	};
	
	// 文件刪除
	$scope.delFile = function(file_save_name, file_save_path) {
		$.ajax({
			url : "../../FileUpDown/FileUploadAction!fileDelete.action",
			data : {
				'file_save_name' : file_save_name,
				'file_save_path' : file_save_path
			},
			dataType : 'json',
			type : 'post',
			async : true,
			success : function(data) {
				if(data.result == '1'){
					alert("刪除成功");
				}else{
					alert("刪除失敗");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(textStatus);
			}
		});
	};
	
});
	
})()