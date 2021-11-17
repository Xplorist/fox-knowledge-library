### Java中處理金額數字每隔三位加逗號

****

工具類

```java
package com.foxconn.bidding.util;

/**
 * 金額數字工具類
 */
public class MoneyNumberUtil {
    // 給金額加逗號
    public static String addDot(Long moneyNum) {
        String moneyStr = moneyNum.toString();
        String moneyStrRev = new StringBuffer(moneyStr).reverse().toString();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < moneyStrRev.length(); i++) {
            char c = moneyStrRev.charAt(i);
            sb.append(c);
            if(i != moneyStrRev.length() -1 && i % 3 == 2) {
                sb.append(',');
            }
        }
        moneyStr = sb.reverse().toString();

        return moneyStr;
    }
}
```

.
