
prompt
prompt drop
prompt ========================
prompt
drop table door_menu;
drop table door_article;
drop table door_channel;
drop table door_attachment;
drop table door_user;
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
  C_PARENT_ID NUMBER(19),
  C_CREATE_TIME TIMESTAMP(6)
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

prompt
prompt Creating table DOOR_CHANNEL
prompt ===========================
prompt
create table DOOR_CHANNEL
(
  ID          NUMBER(19) not null,
  C_NAME      VARCHAR2(255),
  C_SORT      NUMBER(19),
  C_PARENT_ID NUMBER(19)
);
alter table DOOR_CHANNEL
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
alter table DOOR_CHANNEL
  add constraint FKC1BF3D527EC1F3FA foreign key (C_PARENT_ID)
  references DOOR_CHANNEL (ID);
  
prompt
prompt Creating table DOOR_ARTICLE
prompt ===========================
prompt
create table DOOR_ARTICLE
(
  ID            NUMBER(19) not null,
  C_ATTACHMENTS CLOB,
  C_CONTENT     CLOB,
  C_SORT        NUMBER(19),
  C_TITLE       VARCHAR2(1000),
  C_CHANNEL_ID  NUMBER(19)
);
alter table DOOR_ARTICLE
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
alter table DOOR_ARTICLE
  add constraint FK6A0C8D458F24A339 foreign key (C_CHANNEL_ID)
  references DOOR_CHANNEL (ID);
  
prompt
prompt Creating table DOOR_ATTACHMENT
prompt ==============================
prompt
create table DOOR_ATTACHMENT
(
  ID          NUMBER(19) not null,
  C_MIME_TYPE VARCHAR2(255),
  C_NAME      VARCHAR2(255),
  C_PATH      VARCHAR2(255),
  C_SIZE      NUMBER(19)
);
alter table DOOR_ATTACHMENT
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
  
prompt
prompt Creating table DOOR_USER
prompt ========================
prompt
create table DOOR_USER
(
  ID           NUMBER(19) not null,
  C_FIRST_NAME VARCHAR2(255),
  C_FULL_NAME  VARCHAR2(255),
  C_LAST_NAME  VARCHAR2(255),
  C_LOGIN_NAME VARCHAR2(255),
  C_PASSWORD   VARCHAR2(255)
);
alter table DOOR_USER
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
     Select Max(c_sort) Into meSort From door_menu Where c_parent_id = :New.c_parent_id;
     if (meSort is null) then
     	meSort := 0;
     end if;
     meSort := meSort + 1;
     :New.c_sort := meSort;
End;
/

prompt
prompt Creating trigger DOOR_CHANNEL_ID_GENERATOR
prompt =======================================
prompt
Create Or Replace Trigger door_channel_id_generator
-- 触发器，用于生成sort
Before Insert On door_channel For Each Row
Declare
meSort Number(10);
Begin
     If :New.c_parent_id Is Null Then
         Select Max(c_sort) Into meSort From door_channel Where c_parent_id Is Null;
     Else
          Select Max(c_sort) Into meSort From door_channel Where c_parent_id = :New.c_parent_id;
     End If;
     if (meSort is null) then
     	meSort := 0;
     end if;
     meSort := meSort + 1;
     :New.c_sort := meSort;
End;
/

prompt
prompt Creating trigger DOOR_ARTICLE_ID_GENERATOR
prompt =======================================
prompt
Create Or Replace Trigger door_article_id_generator
-- 触发器，用于生成sort
Before Insert On door_article For Each Row
Declare
meSort Number(10);
Begin
     Select Max(c_sort) Into meSort From door_article Where c_channel_id = :New.c_channel_id;
     if (meSort is null) then
     	meSort := 0;
     end if;
     meSort := meSort + 1;
     :New.c_sort := meSort;
End;
/

insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL)
values (1, '系统管理', 0, '#');
commit;
insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL, C_PARENT_ID)
values (2, '内容管理', 1, '#', 1);
commit;
insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL, C_PARENT_ID)
values (3, '用户管理', 111, 'admin/user/list.html', 11);
commit;
insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL, C_PARENT_ID)
values (4, '菜单管理', 112, 'admin/menu/list.html', 11);
commit;
insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL, C_PARENT_ID)
values (5, '栏目管理', 121, 'admin/channel/list.html', 12);
commit;
insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL, C_PARENT_ID)
values (6, '文章管理', 122, 'admin/article/list.html', 12);
commit;
insert into DOOR_MENU (ID, C_NAME, C_SORT, C_URL, C_PARENT_ID)
values (7, '资源管理', 123, 'admin/attachment/list.html', 12);
commit;
update DOOR_MENU set C_SORT = ID;
commit;

