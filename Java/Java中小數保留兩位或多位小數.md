### Java中小數保留兩位或多位小數

****

> 參考網址：
> 
> * [https://blog.csdn.net/qq_25184739/article/details/51470048](https://blog.csdn.net/qq_25184739/article/details/51470048)
> 
> * 

****

示例：

```java
            // 統計接單方競標成功量
            Integer win_bid_amount = mapper.queryRecvUserWinBidAmount(pkid);
            Float win_bid_rate = 0f;
            if (recv_amount != 0) {
                win_bid_rate = (float)win_bid_amount / recv_amount;
                win_bid_rate = (float)Math.round(win_bid_rate * 10000) /  10000;
            }
            recvUserStatistics.setWin_bid_rate(win_bid_rate);
```

核心：

Math.round()方法中是對double類型的小數四捨五入進行取整，先放大某個倍數再同樣倍數縮小，就間接達到了保留小數的效果，至於小數保留的位數，就取決於放大的倍數的0的個數。

保留兩位小數：Math.round(num * 100) / 100;

保留四位小數：Math.round(num * 10000)  / 10000;

**** 

.
