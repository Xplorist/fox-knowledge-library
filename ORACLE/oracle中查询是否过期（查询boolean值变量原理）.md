# oracle中查询是否过期（查询boolean值变量原理）

## 示例：

```

select *
  from ((select '过期' expire_flag
           from (select t.last_valid_time
                   from MB_MESSAGE t
                  where t.id = #{messageId})
          where sysdate > last_valid_time) union
        (select '未过期' expire_flag
           from (select t.last_valid_time
                   from MB_MESSAGE t
                  where t.id = #{messageId})
          where sysdate <= last_valid_time))
 where expire_flag is not null


```

## 核心原理：将多种情况作为条件分别去查询，将查询结果求并集，然后在最外层查询is not null就找到了真正的结果