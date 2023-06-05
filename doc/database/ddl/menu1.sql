drop table if exists menu cascade;

-- 메뉴
create table menu(
	menu_id				integer,
	menu_type			char(1)									default '0',
	menu_target			char(1),
	name				varchar(100)							not null,
	name_en				varchar(30)								not null,
	lang				varchar(10)								default 'ko',
	ancestor			integer,
	parent				integer									default 1,
	depth				integer									default 1,
    previous_depth		integer									default 0,
	view_order			integer									default 1,
	url					varchar(256),
	url_alias			varchar(256),
	alias_menu_id		integer,
	html_id				varchar(100),
	html_content_id		varchar(100),
	image				varchar(256),
	image_alt			varchar(100),
	css_class			varchar(30),
	default_yn			char(1)									default 'N',
	use_yn				char(1)									default 'Y',
	display_yn			char(1)									default 'Y',
	description			varchar(256),
	insert_date			timestamp with time zone				default now(),
	constraint menu_pk primary key (menu_id)	
);


comment on table menu is '菜单';
comment on column menu.menu_id is '唯一编号';
comment on column menu.menu_type is '菜单类型， 0 ： 基于 URL（默认）， 1 ： HTML ID';
comment on column menu.menu_target is '菜单目标，0：用户站点，1：管理员网站';
comment on column menu.name is '菜单名称';
comment on column menu.name_en is '英文菜单名称';
comment on column menu.lang is '语言';
comment on column menu.ancestor is '祖先';
comment on column menu.parent is '父唯一编号';
comment on column menu.depth is '深度';
comment on column menu.previous_depth is '上一个深度（用于屏幕显示）';
comment on column menu.view_order is '列出顺序';
comment on column menu.url is 'URL';
comment on column menu.url_alias is 'URL Alias';
comment on column menu.alias_menu_id is 'URL Alias Menu id, 显示当前选择菜单';
comment on column menu.html_id is '如果菜单类型为 HTML ID，则 ID 值';
comment on column menu.html_content_id is '如果菜单类型为 HTML ID，则与菜单捆绑在一起的 content id 值';
comment on column menu.image is '图片';
comment on column menu.image_alt is '图片 Alt';
comment on column menu.css_class is 'css class名称';
comment on column menu.default_yn is '默认显示菜单， Y ： 基本， N ： 选择';
comment on column menu.use_yn is '启用，Y ：使用，N：禁用';
comment on column menu.display_yn is '是否显示， Y ： 显示， N ： 不可见';
comment on column menu.description is '描述';
comment on column menu.insert_date is '注册日期';
