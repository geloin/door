Insert Into door_menu(Id,c_name,c_sort,c_url,c_parent_id) Values(1,'系统管理',1,'#',Null);
Commit;
Insert Into door_menu(Id,c_name,c_sort,c_url,c_parent_id) Values(11,'用户管理',11,'admin/user/list.html',1);
Commit;
Insert Into door_menu(Id,c_name,c_sort,c_url,c_parent_id) Values(12,'菜单管理',12,'admin/menu/list.html',1);
Commit;

Create Or Replace Trigger door_menu_id_generator
-- 触发器，用于生成sort
Before Insert On door_menu For Each Row
Declare
meSort Number(10);
Begin
     Select Max(c_sort) + 1 Into meSort From door_menu Where c_parent_id = :New.c_parent_id;
     :New.c_sort := meSort;
End;