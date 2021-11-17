### JS實現選擇文件後上傳文件

****

html代碼：

```
<input type="file" id="file" name="file" onchange ="uploadFile()">
```

js代碼：

```
function uploadFile() {
 
$.ajaxFileUpload({
				type : "POST",
				url : '../../FileUpDown/FileUploadAction!commonFileUpload',
				async : false,
				secureuri : false,
				fileElementId : 'file',// input[type='file']中name必須和id相同不然後台取不到文件
				dataType : 'json',
				data : {
					//'file_origin_name' : $scope.fileName,
					'moduleType': $scope.type,
					'file_type' : $scope.doc_type
				},
				success : function(response) {
					var data = response;

					if (data.result != "1") {
						alert(data.errorInfo);
						return false;
					}else{
						alert("上傳成功");
					}

					$scope.fileName = data.file_origin_name;
					$scope.file_origin_name = data.file_origin_name;
					$scope.file_save_name = data.file_save_name;
					$scope.file_save_path = data.file_save_path;
					//$('#table-modal1').modal('hide');
					$scope.$apply();
				},
				error : function(data) {
					alert("文件上傳異常");
				}
			});
}

```

當選擇了文件之後就會觸發<input type="file">的onchange事件，該事件就是一個文件上傳的ajax請求。

* 清空input框中的文件方法：

```
$("#file").val("");

```