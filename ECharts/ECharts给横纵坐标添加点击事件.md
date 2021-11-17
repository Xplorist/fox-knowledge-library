---
title: ECharts给横纵坐标添加点击事件
date: 2020-12-09 14:52:00
tags:
  - ECharts
categories:
  - [ ECharts ]
---

# reference-site-list
**ECharts**
> * [ECharts 给X轴文字添加点击事件](https://blog.csdn.net/sophia_xiaoma/article/details/78055947)
> * [在Echarts区域的任何位置精准触发事件](https://www.cnblogs.com/liangsf/p/11592283.html)

# content

* 将xAxis或者yAxis的属性triggerEvent 置为 true;

* 点击事件，如代码：

``` javascript
    mybarDoubleChart.setOption(option);
    mybarDoubleChart.on('click', function (params) {
        if(params.componentType == "xAxis"){
            alert("单击了"+params.value+"x轴标签");
        }else{
            alert("单击了"+params.name+"柱状图");
        }
    });
```

# demo

* 实例

``` javascript

    //點擊echart事件
    chartClick(val) {
      let demandId = '';
      if (val.componentType === 'yAxis') {
        let index = val.value;
        for (let i = 0; i < this.dmOneDeptStatisticsVO.idTitleList.length; i++) {
          let idTitle = this.dmOneDeptStatisticsVO.idTitleList[i];
          if (idTitle.title === index) {
              demandId = idTitle.id;
              break;
          }
        }
      } else {
        demandId = val.data.demandId;
      }
    }
    
```