### group by t.id 後order by count(t.id)後再獲取這兩個值作為子查詢

****

```
-- 查詢本年發單用戶id和發單數
select u.*
  from USER_INFO u
 where u.pkid in
       (select id
          from (select t.send_user_pkid id, count(t.send_user_pkid) num
                  from BILL t
                 where t.publish_date < sysdate
                   and t.publish_date > (sysdate - 365)
                 group by t.send_user_pkid
                 order by count(t.send_user_pkid) desc))
```

****

> 解釋：直接將group by 後查詢的值作為子查詢時不行的，再oracle中可以再將其作為外層包一層select id from ()就可以獲取其中的值再作為後面的子查詢了。

****

.
