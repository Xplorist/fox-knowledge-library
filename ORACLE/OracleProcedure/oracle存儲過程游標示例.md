### oracle存儲過程游標示例

***

 代碼示例：

 ```sql
 create or replace procedure auto_choose_bill is
   TYPE t_cursor IS REF CURSOR; --定义游标变量类型
   v_cur_bill_id t_cursor; --声明游标变量 訂單id
 
   t_sqlcode         varchar2(1000); --異常SQL語句
   v_bill_pkid       varchar2(50);
   v_give_price_pkid varchar2(50);
   v_count           number;
   v_recv_user_pkid  varchar2(50);
 begin
   -- 查詢訂單表中訂單狀態為發佈和競價結束時間小於等於當前時間的訂單id
   OPEN v_cur_bill_id FOR
     select t.pkid
       from BILL t
      where t.bill_status = '1'
        and t.bid_end_date <= sysdate
      order by t.create_date desc; -- 開啟游標
 
   loop
     FETCH v_cur_bill_id
       INTO v_bill_pkid;
   
     select count(*)
       into v_count
       from GIVE_PRICE_MSTR t
      where t.bill_pkid = v_bill_pkid
      order by t.total_price desc, t.deliver_date asc, t.create_date asc;
     if v_count > 0 then
       -- 查詢訂單的報價單並排序,查出排序後的第一條數據的pkid
       select pkid
         into v_give_price_pkid
         from (select t.pkid
                 from GIVE_PRICE_MSTR t
                where t.bill_pkid = v_bill_pkid
                order by t.total_price  desc,
                         t.deliver_date asc,
                         t.create_date  asc)
        where rownum = 1;
     
       -- 查詢接單用戶id
       select recv_user_pkid
         into v_recv_user_pkid
         from GIVE_PRICE_MSTR
        where pkid = v_give_price_pkid;
     
       -- 更新訂單表的中標報價表id,中標接單方用戶id和訂單狀態為中標
       update BILL
          set bill_status          = '2',
              give_price_mstr_pkid = v_give_price_pkid,
              recv_user_pkid       = v_recv_user_pkid
        where pkid = v_bill_pkid;
       -- 更新報價主表為中標 
       update GIVE_PRICE_MSTR
          set f_win_bid = 'Y'
        where pkid = v_give_price_pkid;
     end if;
     EXIT WHEN v_cur_bill_id%NOTFOUND;
   end loop;
 
   COMMIT;
 Exception
   When others then
     Rollback;
     t_sqlcode := '存儲過程出錯異常：' || t_sqlcode || '|' || sqlerrm;
 end auto_choose_bill;
 
 ```

 