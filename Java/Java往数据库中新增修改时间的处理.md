---
title: Java往数据库中新增修改时间的处理
date: 2020-12-10 09:36:00
tags:
  - Java
categories:
  - [ Java ]
---

# content

* 在MyBatis中的SQL语句中添加判断 

``` java
date != null and date.getTime() != 0L

```
# demo

``` xml
    <!-- UserDemandMapper【22】开发者保存 -->
<update id="devSave" parameterType="com.foxconn.mcebg.portal.model.query.DevSaveQuery">
    update DM_MAIN t
    set t.system_info_id   = #{systemInfoId},
        t.demand_type_id   = #{demandTypeId},
        t.demand_status_id = #{demandStatusId},
      <!--t.rank             = #{rank},-->
        <if test="realStartTime != null and realStartTime.getTime() != 0L">
      t.real_start_time  = #{realStartTime},
        </if>
        <if test="realEndTime != null and realEndTime.getTime() != 0L">
      t.real_end_time    = #{realEndTime},
        </if>
      t.dev_memo         = #{devMemo}
  where t.id = #{id}
</update>

```