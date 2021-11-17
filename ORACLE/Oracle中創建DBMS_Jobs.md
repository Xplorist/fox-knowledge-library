### Oracle中創建DBMS_Jobs

> 參考網址：
>
> https://blog.csdn.net/qq_40709468/article/details/81876828
>
> https://www.cnblogs.com/linjiqin/p/3152638.html

### 关于job运行时间

1:每分钟执行
Interval => TRUNC(sysdate,'mi') + 1/(24*60)
2:每天定时执行
例如：每天的凌晨1点执行
Interval => TRUNC(sysdate) + 1 +1/(24)
3:每周定时执行
例如：每周一凌晨1点执行
Interval => TRUNC(next_day(sysdate,'星期一'))+1/24
4:每月定时执行
例如：每月1日凌晨1点执行
Interval =>TRUNC(LAST_DAY(SYSDATE))+1+1/24
5:每季度定时执行
例如每季度的第一天凌晨1点执行
Interval => TRUNC(ADD_MONTHS(SYSDATE,3),'Q') + 1/24
6:每半年定时执行
例如：每年7月1日和1月1日凌晨1点
Interval => ADD_MONTHS(trunc(sysdate,'yyyy'),6)+1/24
7:每年定时执行
例如：每年1月1日凌晨1点执行
Interval =>ADD_MONTHS(trunc(sysdate,'yyyy'), 12)+1/24

job的运行频率设置
1.每天固定时间运行，比如早上8:10分钟：Trunc(Sysdate+1) + (8*60+10)/24*60
2.Toad中提供的：
每天：trunc(sysdate+1)
每周：trunc(sysdate+7)
每月：trunc(sysdate+30)
每个星期日：next_day(trunc(sysdate),'星期日')
每天6点：trunc(sysdate+1)+6/24
半个小时：sysdate+30/(24 * 60)
3.每个小时的第15分钟运行，比如：8:15，9:15，10:15…：trunc(sysdate,'hh')+(60+15)/(24*60)