---
title: Oracle之触发器示例
date: 2020-12-10 11:59:00
tags:
  - Oracle
categories:
  - [ Oracle ]
---

# reference-site-list

**Oracle**
> * [CSDN Oracle触发器用法实例详解](https://blog.csdn.net/shmilychan/article/details/53787933)

# demo

* 创建删除数据行级触发器
``` sql
create or replace trigger INDIVIDUAL_AUTH_DEL_TRG
  after delete
  on individual_auth 
  for each row
declare
  -- local variables here
begin
insert into INDIVIDUAL_AUTH_DEL_LOG t
  (t.individual_auth_id,
   t.entity_uid,
   t.entityversion,
   t.entitystate,
   t.accountbroken_id,
   t.project_id,
   t.eneitycreator,
   t.entitymodifier,
   t.entitycreated,
   t.entitymodified,
   t.individual_auth_form,
   t.role_group,
   t.modifier,
   t.modified,
   t.modifier_person_name)
values
  (:old.id,
   :old.entity_uid,
   :old.entityversion,
   :old.entitystate,
   :old.accountbroken_id,
   :old.project_id,
   :old.eneitycreator,
   :old.entitymodifier,
   :old.entitycreated,
   :old.entitymodified,
   :old.individual_auth_form,
   :old.role_group,
   :old.modifier,
   :old.modified,
   :old.modifier_person_name);
end INDIVIDUAL_AUTH_DEL_TRG;

```