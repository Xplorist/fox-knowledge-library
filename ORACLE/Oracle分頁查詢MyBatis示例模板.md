### Oracle分頁查詢MyBatis示例模板

***

MyBatis中分頁自定義SQL模板

```sql
select t2.* from(select t1.*,rownum row_num from(
        select t.*, count(*) over() row_total
         /* 自定義SQL開始 */
          from RECV_EVAL t
         where t.send_user_pkid = #{send_user_pkid}
         order by t.create_date desc
         /* 自定義SQL結束 */
         ) t1) t2 
     where t2.row_num between (#{pageIndex} - 1) * #{pageSize} + 1 
     and #{pageIndex} * #{pageSize}
```

MyBatis中SQL完整代碼示例：

```sql
select t2.* from(select t1.*,rownum row_num from(
    select t.*, count(*) over() row_total 
      from SOP_BASEINFO_MAIN t
     where t.is_use = 'Y' 
       and t.is_new_ver = 'Y'
    order by t.create_date desc) t1) t2 
     where t2.row_num between (#{pageIndex} - 1) * #{pageSize} + 1 
     and #{pageIndex} * #{pageSize}
```

查詢參數字段

| 字段        | 類型      | 描述   |
| --------- | ------- | ---- |
| pageIndex | Integer | 第几頁  |
| pageSize  | Integer | 每頁條數 |

查詢結果字段

| 字段         | 類型      | 描述               |
| ---------- | ------- | ---------------- |
| row_num    | Integer | 第几行              |
| row_total  | Integer | 總行數              |
| page_total | Integer | 總頁數（java後台中進行計算） |

row_total總行數在java中計算代碼

```java
page_total = row_total % pageSize == 0 ? row_total / pageSize : (row_total / pageSize) + 1;
```

****

.
