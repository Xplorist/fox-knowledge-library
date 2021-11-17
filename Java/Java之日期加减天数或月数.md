# Java之日期加减天数或月数

## 示例：

``` java
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
LocalDate localDate = LocalDate.now();

LocalDate startLocal = localDate.minusMonths(i);

int yearValue = startLocal.getYear();
int monthValue = startLocal.getMonthValue();
String monthStr = monthValue < 10 ? monthStr = "0" + monthValue : "" + monthValue;

startLocal = LocalDate.parse(yearValue + "-" + monthStr + "-01", dtf);
LocalDate endLocal = startLocal.plusMonths(1);

// StatisticMapper【01】查询某系统截止某天总用户数量
Long userSum = mapper.queryRegisterUserSum(systemId, dtf.format(endLocal));

// StatisticMapper【03】查询某系统某个时间段内活跃用户数量
Long activeUserSum = mapper.queryActiveUserSumByTimePeriod(systemId, dtf.format(startLocal), dtf.format(endLocal));

```

## 解释：

* JDK1.8中新增的LocalDate类处理时间加减非常好用

* 日期转换为字符串：DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());

* 字符串转换为日期：LocalDate.parse('2020-07-04', DateTimeFormatter.ofPattern("yyyy-MM-dd"));

* 时间加一天：LocalDate.now().plusDays(1);

* 时间减一天：LocalDate.now().minusDays(1);

* 时间加一月：LocalDate.now().plusMonths(1);

* 时间减一月：LocalDate.now().minusMonths(1);