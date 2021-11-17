### Oracle中union使用

****

使用union進行自定數據加入查詢數據中合成一個結果集

示例：

```sql
-- 查詢廠區list
select txt, val, id
  from (select '全部' txt, '全部' val, 1 id
          from dual
        union
        select txt, val, rownum + 1 id
          from (select t.fctry_zone txt, t.fctry_zone val
                  from USER_INFO t
                 where t.f_valid = 'Y'
                   and t.fctry_zone is not null
                 group by t.fctry_zone
                 order by t.fctry_zone asc))
 order by id
```

****

.
