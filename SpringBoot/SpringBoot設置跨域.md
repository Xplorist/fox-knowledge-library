## SpringBoot 設置跨域

在Controller中添加註解@CrossOrigin就行了

#### 示例：

```java
package com.foxconn.bidding.controller;

import com.foxconn.bidding.model.BaseInfoParam;
import com.foxconn.bidding.model.ResultParam;
import com.foxconn.bidding.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/baseInfo")
@CrossOrigin
public class BaseInfoController {
    @Autowired
    private BaseInfoService svc;

    // 模板
    @RequestMapping("/template")
    public ResultParam template(@RequestBody BaseInfoParam param, HttpServletRequest request) {
        ResultParam result = ResultParam.of("", "");

        return result;
    }

    // 查詢廠區list
    @RequestMapping("/query_factory_list")
    public ResultParam query_factory_list(@RequestBody BaseInfoParam param, HttpServletRequest request) {
        ResultParam result = svc.query_factory_list(param, request);

        return result;
    }

    // 查詢次集團list
    @RequestMapping("/query_SECN_CMPY_list")
    public ResultParam query_SECN_CMPY_list(@RequestBody BaseInfoParam param, HttpServletRequest request) {
        ResultParam result = svc.query_SECN_CMPY_list(param, request);

        return result;
    }

    // 查詢事業群list
    @RequestMapping("/query_ENTRPS_GROUP_list")
    public ResultParam query_ENTRPS_GROUP_list(@RequestBody BaseInfoParam param, HttpServletRequest request) {
        ResultParam result = svc.query_ENTRPS_GROUP_list(param, request);

        return result;
    }

    // 查詢產品處list
    @RequestMapping("/query_PD_OFFICE_list")
    public ResultParam query_PD_OFFICE_list(@RequestBody BaseInfoParam param, HttpServletRequest request) {
        ResultParam result = svc.query_PD_OFFICE_list(param, request);

        return result;
    }
}
```

