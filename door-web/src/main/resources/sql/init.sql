
prompt
prompt drop
prompt ========================
prompt
drop table door_menu;
drop sequence MENU_SEQ;
commit;

prompt
prompt Creating table DOOR_MENU
prompt ========================
prompt
create table DOOR_MENU
(
  ID          NUMBER(19) not null,
  C_NAME      VARCHAR2(255),
  C_SORT      NUMBER(19),
  C_URL       VARCHAR2(255),
  C_PARENT_ID NUMBER(19)
);
alter table DOOR_MENU
  add primary key (ID)
  using index 
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table DOOR_MENU
  add constraint FK5502C750D0A6CD30 foreign key (C_PARENT_ID)
  references DOOR_MENU (ID);


prompt
prompt Creating sequence MENU_SEQ
prompt ==========================
prompt
create sequence MENU_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 113
increment by 1
cache 20;

prompt
prompt Creating trigger DOOR_MENU_ID_GENERATOR
prompt =======================================
prompt
Create Or Replace Trigger door_menu_id_generator
-- 触发器，用于生成sort
Before Insert On door_menu For Each Row
Declare
meSort Number(10);
Begin
     Select Max(c_sort) + 1 Into meSort From door_menu Where c_parent_id = :New.c_parent_id;
     :New.c_sort := meSort;
End;
/

insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL, C_PARENT_ID)
values (1, 'DOOR', 1, '#', null);
commit;
insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL, C_PARENT_ID)
values (11, '系统管理', 11, '#', 1);
commit;
insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL, C_PARENT_ID)
values (111, '用户管理', 111, 'admin/user/list.html', 11);
commit;
insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL, C_PARENT_ID)
values (112, '菜单管理', 112, 'admin/menu/list.html', 11);
commit;
update DOOR_MENU set C_SORT = ID;
commit;

