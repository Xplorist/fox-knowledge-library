### Oracle分頁查詢SQL示例模板

***

示例SQL代碼：

```sql
select t2.*
  from (select t1.*, rownum row_num
          from (select t.*, count(*) over() row_total
                  from ACCOUNT_BROKEN t
                 order by t.id) t1) t2
 where t2.row_num between (3 - 1) * 10 + 1 and 3 * 10
```

說明：

 count(*) over() row_total 查詢出總條數
(3 - 1) * 10 + 1 等效于 (pageIndex - 1) * pageSize + 1
 3 * 10           等效于  pageIndex * pageSize

 t 層的目的是按照條件查出總條數
 t1層的目的是找出行號，給t2層提供查詢條件
 t2層的目的是按照分頁條件找出相應數據

 返回的數據中row_total是總行數
 總頁數是page_total
 page_total = row_total % pageSize == 0 ? row_total / pageSize : (row_total / pageSize) + 1;
 // 注意此行代碼後還有 + 1;

***

參數

 1.查詢條件中的參數：
	pageIndex -- （類型：Integer）第几頁 
	pageSize  -- （類型：Integer）每頁條數

 2.返回結果中的參數：
	row_num -- （類型：Integer）第几行
	row_total -- （類型：Integer）總行數
	page_total --（類型：Integer） 總頁數（java後台中進行計算）