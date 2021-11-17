### Java中實現日期向後加一天

****

在new Date()后增加一天，即往后延一天

```
import java.util.Date;

Date date = new Date(); //取时间 
Calendar calendar = new GregorianCalendar(); 
calendar.setTime(date); 
calendar.add(calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动 
date=calendar.getTime(); //这个时间就是日期往后推一天的结果
```

****

.
