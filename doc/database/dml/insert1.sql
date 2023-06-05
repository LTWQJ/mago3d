-- 输入用户组表的默认值
insert into user_group(	user_group_id, user_group_key, user_group_name, ancestor, parent, depth, view_order, basic, available, description)
values
    (1, 'SUPER_ADMIN', '超级管理人员', 1, 0, 1, 1, 'Y', 'Y', '默认值'),
    (2, 'USER', '用户', 1, 0, 1, 2, 'Y', 'Y', '默认值'),
    (3, 'GUEST', 'GUEST', 1, 0, 1, 3, 'Y', 'Y', '默认值');

-- 超级管理人员注册
insert into user_info(
    user_id, user_group_id, membership_id, user_name, password, user_role_check_yn, last_signin_date)
values
    ('admin', 1, 4, '超级管理人员', '$2a$10$KFr/2p5Og2jBy8NkTaEb/eoUna6AVlQ.A7s4YpPJ9A8dZwLYum5f.', 'N', now()),
    ('mago3d', 2, 4, 'mago3D', '$2a$10$lmYPqp2UJm4lHuF57Rs.wuzX034x7y/21jlCc8OQ4yFxbZt6Iich2', 'Y', now());

-- 관리자 메뉴
insert into menu(menu_id, menu_type, menu_target, name, name_en, ancestor, parent, depth, previous_depth, view_order, url, url_alias, html_id, css_class, default_yn, use_yn, display_yn)
values
    (1, '0', '1', 'home', 'HOME', 0, 0, 1, 0, 1, '/main/index', null, null, 'glyph-home', 'N', 'N', 'N'),
    (2, '0', '1', '用户', 'USER', 2, 0, 1, 0, 2, '/user/list', null, null, 'glyph-users', 'Y', 'Y', 'Y'),
    (21, '0', '1', '用户组', 'USER', 2, 2, 2, 1, 1, '/user-group/list', null, null, 'glyph-users', 'Y', 'Y', 'Y'),
    (22, '0', '1', '注册用户组', 'USER', 2, 2, 2, 2, 2, '/user-group/input', '/user-group/list', null, 'glyph-users', 'N', 'Y', 'N'),
    (23, '0', '1', '修改用户组', 'USER', 2, 2, 2, 2, 3, '/user-group/modify', '/user-group/list', null, 'glyph-users', 'N', 'Y', 'N'),
    (24, '0', '1', '用户组菜单', 'USER', 2, 2, 2, 2, 4, '/user-group/menu', '/user-group/list', null, 'glyph-users', 'N', 'Y', 'N'),
    (25, '0', '1', '用户组Role', 'USER', 2, 2, 2, 2, 5, '/user-group/role', '/user-group/list', null, 'glyph-users', 'N', 'Y', 'N'),
    (26, '0', '1', '用户列表', 'USER', 2, 2, 2, 2, 6, '/user/list', null, null, 'glyph-users', 'Y', 'Y', 'Y'),
    (27, '0', '1', '用户注册', 'USER', 2, 2, 2, 2, 7, '/user/input', '/user/list', null, 'glyph-users', 'N', 'Y', 'N'),
    (28, '0', '1', '修改用户信息', 'USER', 2, 2, 2, 2, 8, '/user/modify', '/user/list', null, 'glyph-users', 'N', 'Y', 'N'),
    (29, '0', '1', '用户详细信息', 'USER', 2, 2, 2, 2, 9, '/user/detail', '/user/list', null, 'glyph-users', 'N', 'Y', 'N'),

    (3, '0', '1', '数据', 'DATA', 3, 0, 1, 2, 3, '/tile/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (31, '0', '1', '智能瓦片', 'TILE', 3, 3, 2, 1, 1, '/tile/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (32, '0', '1', '智能瓦片注册', 'TILE', 3, 3, 2, 2, 2, '/tile/input', '/tile/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
    (33, '0', '1', '修正智能瓦片', 'TILE', 3, 3, 2, 2, 3, '/tile/modify', '/tile/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
    (34, '0', '1', '数据组', 'DATA', 3, 3, 2, 2, 4, '/data-group/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (35, '0', '1', '数据组注册', 'DATA', 3, 3, 2, 2, 5, '/data-group/input', '/data-group/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
    (36, '0', '1', '修改数据组', 'DATA', 3, 3, 2, 2, 6, '/data-group/modify', '/data-group/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
    (37, '0', '1', '用户数据组', 'DATA', 3, 3, 2, 2, 7, '/data-group/list-user', null, null, 'glyph-monitor', 'Y', 'N', 'Y'),
    (38, '0', '1', '数据列表', 'DATA', 3, 3, 2, 2, 8, '/data/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (39, '0', '1', '数据详细信息', 'DATA', 3, 3, 2, 2, 9, '/data/detail', '/data/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
    (40, '0', '1', '修正数据', 'DATA', 3, 3, 2, 2, 10, '/data/modify', '/data/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
    (41, '0', '1', '上传', 'DATA', 3, 3, 2, 2, 11, '/upload-data/input', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (42, '0', '1', '上传列表', 'DATA', 3, 3, 2, 2, 12, '/upload-data/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (43, '0', '1', '修改上传', 'DATA', 3, 3, 2, 2, 13, '/upload-data/modify', '/upload-data/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
    (44, '0', '1', '数据转换结果', 'DATA', 3, 3, 2, 2, 14, '/converter/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (45, '0', '1', '数据转换详细列表', 'DATA', 3, 3, 2, 2, 15, '/converter/converter-job-file-list', null , null ,'glyph-monitor', 'Y', 'Y', 'Y'),
    (46, '0', '1', '数据变更记录', 'DATA', 3, 3, 2, 2, 16, '/data-log/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (47, '0', '1', '数据位置变更申请记录', 'DATA', 3, 3, 2, 2, 17, '/data-adjust-log/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),

    (5, '0', '1', '图层', 'LAYER', 5, 0, 1, 2, 5, '/layer-group/list', null, null, 'glyph-stack', 'Y', 'Y', 'Y'),
    (51, '0', '1', '2D层', 'LAYER', 5, 5, 2, 1, 1, '/layer-group/list', null, null, 'glyph-stack', 'Y', 'Y', 'Y'),
    (52, '0', '1', '2D图层组注册', 'LAYER', 5, 5, 2, 2, 2, '/layer-group/input', '/layer-group/list', null, 'glyph-stack', 'N', 'Y', 'N'),
    (53, '0', '1', '修正2D图层组', 'LAYER', 5, 5, 2, 2, 3, '/layer-group/modify', '/layer-group/list', null, 'glyph-stack', 'N', 'Y', 'N'),
    (54, '0', '1', '2D图层列表', 'LAYER', 5, 5, 2, 2, 4, '/layer/list', null, null, 'glyph-stack', 'Y', 'Y', 'Y'),
    (55, '0', '1', '2D图层注册', 'LAYER', 5, 5, 2, 2, 5, '/layer/input', '/layer/list', null, 'glyph-stack', 'N', 'Y', 'N'),
    (56, '0', '1', '修正2D图层', 'LAYER', 5, 5, 2, 2, 6, '/layer/modify', '/layer/list', null, 'glyph-stack', 'N', 'Y', 'N'),

    (7, '0', '1', '3D库', '3D LIBRARY', 7, 0, 1, 2, 7, '/data-library-group/list', null, null, 'glyph-office', 'Y', 'Y', 'Y'),
    (71, '0', '1', '3D库组', '3D LIBRARY', 7, 7, 2, 1, 1, '/data-library-group/list', null, null, 'glyph-office', 'Y', 'Y', 'Y'),
    (72, '0', '1', '注册3D库组', '3D LIBRARY', 7, 7, 2, 2, 2, '/data-library-group/input', '/data-library-group/list', null, 'glyph-office', 'N', 'Y', 'N'),
    (73, '0', '1', '修正3D库组', '3D LIBRARY', 7, 7, 2, 2, 3, '/data-library-group/modify', '/data-library-group/list', null, 'glyph-office', 'N', 'Y', 'N'),
    (74, '0', '1', '上传3D库', '3D LIBRARY', 7, 7, 2, 2, 4, '/data-library-upload/input', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (75, '0', '1', '3D库上传列表', '3D LIBRARY', 7, 7, 2, 2, 5, '/data-library-upload/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (76, '0', '1', '修正3D库上传', '3D LIBRARY', 7, 7, 2, 2, 6, '/data-library-upload/modify', '/data-library-upload/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
    (77, '0', '1', '3D库转换结果', '3D LIBRARY', 7, 7, 2, 2, 7, '/data-library-converter/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
    (78, '0', '1', '3D库列表', '3D LIBRARY', 7, 7, 2, 2, 8, '/data-library/list', null, null, 'glyph-desktop', 'Y', 'Y', 'Y'),
    (79, '0', '1', '修正3D库', '3D LIBRARY', 7, 7, 2, 2, 9, '/data-library/modify', '/data-library/list', null, 'glyph-desktop', 'N', 'Y', 'N'),

    (101, '0', '1', '业务规则', 'BRMS', 101, 0, 1, 2, 8, '/rule-group/list', null, null, 'glyph-desktop', 'Y', 'Y', 'Y'),
    (111, '0', '1', '组实现', 'BRMS', 101, 101, 2, 1, 1, '/rule-group/list', null, null, 'glyph-desktop', 'Y', 'Y', 'Y'),
    (112, '0', '1', '注册规则组', 'BRMS', 101, 101, 2, 2, 2, '/rule-group/input', '/rule-group/list', null, 'glyph-desktop', 'N', 'Y', 'N'),
    (113, '0', '1', '修正规则组', 'BRMS', 101, 101, 2, 2, 3, '/rule-group/modify', '/rule-group/list', null, 'glyph-desktop', 'N', 'Y', 'N'),
    (114, '0', '1', '规则列表', 'BRMS', 101, 101, 2, 2, 4, '/rule/list', null, null, 'glyph-desktop', 'Y', 'Y', 'Y'),
    (116, '0', '1', '规则登记', 'BRMS', 101, 101, 2, 2, 6, '/rule/input', '/rule/list', null, 'glyph-desktop', 'N', 'Y', 'N'),
    (117, '0', '1', '规则修改', 'BRMS', 101, 101, 2, 2, 7, '/rule/modify', '/rule/list', null, 'glyph-desktop', 'N', 'Y', 'N'),

    (201, '0', '1', '数字双引擎服务', 'DIGITALTWIN SERVICE', 201, 0, 1, 2, 9, '/micro-service/list', null, null, 'glyph-tree', 'Y', 'Y', 'Y'),
    (211, '0', '1', '数字双引擎服务目录', 'DIGITALTWIN SERVICE', 201, 201, 2, 1, 1, '/micro-service/list', null, null, 'glyph-tree', 'Y', 'Y', 'Y'),
    (212, '0', '1', '数字双引擎服务请求履历', 'DIGITALTWIN SERVICE', 201, 201, 2, 2, 2, '/micro-service-log/list', null, null, 'glyph-tree', 'Y', 'Y', 'Y'),
    (213, '0', '1', '服务健康检查', 'DIGITALTWIN SERVICE', 201, 201, 2, 2, 3, '/health-check-log/list', null, null, 'glyph-tree', 'Y', 'Y', 'Y'),
    (214, '0', '1', '注册数字双引擎服务', 'DIGITALTWIN SERVICE', 201, 201, 2, 2, 4, '/micro-service/input', '/micro-service/list', null, 'glyph-desktop', 'N', 'Y', 'N'),
    (215, '0', '1', '修正数码双引擎服务', 'DIGITALTWIN SERVICE', 201, 201, 2, 2, 5, '/micro-service/modify', '/micro-service/list', null, 'glyph-desktop', 'N', 'Y', 'N'),

    (301, '0', '1', '会员资格', 'MEMBERSHIP', 301, 0, 1, 2, 10, '/membership/usage/list', null, null, 'glyph-tree', 'Y', 'Y', 'Y'),
    (311, '0', '1', '会员使用量清单', 'MEMBERSHIP', 301, 301, 2, 1, 1, '/membership/usage/list', null, null, 'glyph-tree', 'Y', 'Y', 'Y'),
    (312, '0', '1', '变更会员资格申请履历书', 'MEMBERSHIP', 301, 301, 2, 2, 2, '/membership/log/list', null, null, 'glyph-tree', 'Y', 'Y', 'Y'),

    (401, '0', '1', '首选项', 'CONFIGURATION', 401, 0, 1, 2, 11, '/policy/modify', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
    (411, '0', '1', '一般运营政策', 'CONFIGURATION', 401, 401, 2, 1, 1, '/policy/modify', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
    (412, '0', '1', '空间资讯运营政策', 'CONFIGURATION', 401, 401, 2, 2, 2, '/geopolicy/modify', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
    (413, '0', '1', '管理员菜单', 'ADMIN MENU', 401, 401, 2, 2, 3, '/menu/admin-menu', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
    (414, '0', '1', '用户菜单', 'USER MENU', 401, 401, 2, 2, 4, '/menu/user-menu', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
    (415, '0', '1', '部件', 'WIDGET', 401, 401, 2, 2, 5, '/widget/modify', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
    (416, '0', '1', '权限', 'ROLE', 401, 401, 2, 2, 6, '/role/list', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
    (417, '0', '1', '注册权限', 'ROLE', 401, 401, 2, 2, 7, '/role/input', '/role/list', null, 'glyph-settings', 'N', 'Y', 'N'),
    (418, '0', '1', '权限修改', 'ROLE', 401, 401, 2, 2, 8, '/role/modify', '/role/list', null, 'glyph-settings', 'N', 'Y', 'N'),
    (420, '0', '1', '地形目录', 'TERRAIN', 401, 401, 2, 2, 9, '/terrain/list', null, null, 'glyph-desktop', 'Y', 'Y', 'Y'),
    (421, '0', '1', '地形注册', 'TERRAIN', 401, 401, 2, 2, 10, '/terrain/input', '/terrain/list', null, 'glyph-desktop', 'N', 'Y', 'N'),
    (422, '0', '1', '地形修改', 'TERRAIN', 401, 401, 2, 2, 11, '/terrain/modify', '/terrain/list', null, 'glyph-desktop', 'N', 'Y', 'N'),

    (501, '0', '1', '日程', 'SCHEDULE', 501, 0, 1, 2, 12, '/schedule/list', null, null, 'glyph-tree', 'Y', 'Y', 'Y'),
    (511, '0', '1', '日程安排清单', 'SCHEDULE', 501, 501, 2, 1, 1, '/schedule/list', null, null, 'glyph-tree', 'Y', 'Y', 'Y'),
    (512, '0', '1', '注册日程', 'SCHEDULE', 501, 501, 2, 2, 2, '/schedule/input', '/schedule/list', null, 'glyph-desktop', 'N', 'Y', 'N'),
    (513, '0', '1', '修改日程安排', 'SCHEDULE', 501, 501, 2, 2, 3, '/schedule/modify', '/schedule/list', null, 'glyph-desktop', 'N', 'Y', 'N');


-- 用户菜单
insert into menu(menu_id, menu_type, menu_target, name, name_en, ancestor, parent, depth, previous_depth, view_order, url, url_alias, html_id, html_content_id,
                 css_class, default_yn, use_yn, display_yn)
values
--    (1001, '1', '0', '데이터', 'DATA', 1001, 0, 1, 0, 1, '/data/map', null, 'dataMenu', 'dataContent', 'data', 'Y', 'Y', 'Y'),
    (1001, '1', '0', '数据', 'DATA', 1001, 0, 1, 0, 1, '/data/map', null, 'data-menu', 'data-wrap-content', 'data', 'Y', 'Y', 'Y'),
    (1002, '1', '0', '变换', 'CONVERTER', 1002, 0, 1, 1, 2, '/upload-data/list', null, 'converterMenu', 'converterContent', 'converter', 'Y', 'Y', 'Y'),
    (1003, '1', '0', '模拟', 'SIMULATION', 1003, 0, 1, 1, 3, '/simulation', null, 'simulationMenu', 'simulationContent', 'simulation', 'Y', 'Y', 'Y'),
    (1004, '1', '0', 'IOT', 'IOT', 1004, 0, 1, 1, 4, '/iot', null, 'iotMenu', 'iotContent', 'iot', 'Y', 'Y', 'Y'),
    (1005, '1', '0', '分析', 'ANALYSIS', 1005, 0, 1, 1, 5, '/spatial', null, 'spatialMenu', 'spatialContent', 'spatial', 'Y', 'Y', 'Y'),
--     (1006, '1', '0', '레이어', 'LAYER', 1006, 0, 1, 1, 6, '/layer/list', null, 'layerMenu', 'layerContent', 'layer', 'Y', 'Y', 'Y'),
    (1007, '1', '0', '我的网页', 'MYPAGE', 1007, 0, 1, 1, 7, '/mypage/user-modify', null, 'mypageMenu', 'mypageContent', 'mypagePolicy', 'Y', 'Y', 'Y');


-- 按用户组选择菜单
insert into user_group_menu(user_group_menu_id, user_group_id, menu_id, previous_depth, all_yn)
values
    (1, 1, 1, 0, 'Y'),
    (2, 1, 2, 0, 'Y'),
    (21, 1, 21, 1, 'Y'),
    (22, 1, 22, 2, 'Y'),
    (23, 1, 23, 2, 'Y'),
    (24, 1, 24, 2, 'Y'),
    (25, 1, 25, 2, 'Y'),
    (26, 1, 26, 2, 'Y'),
    (27, 1, 27, 2, 'Y'),
    (28, 1, 28, 2, 'Y'),
    (29, 1, 29, 2, 'Y'),

    (3, 1, 3, 2, 'Y'),
    (31, 1, 31, 1, 'Y'),
    (32, 1, 32, 2, 'Y'),
    (33, 1, 33, 2, 'Y'),
    (34, 1, 34, 2, 'Y'),
    (35, 1, 35, 2, 'Y'),
    (36, 1, 36, 2, 'Y'),
    (37, 1, 37, 2, 'Y'),
    (38, 1, 38, 2, 'Y'),
    (39, 1, 39, 2, 'Y'),
    (40, 1, 40, 2, 'Y'),
    (41, 1, 41, 2, 'Y'),
    (42, 1, 42, 2, 'Y'),
    (43, 1, 43, 2, 'Y'),
    (44, 1, 44, 2, 'Y'),
    (45, 1, 45, 2, 'Y'),
    (46, 1, 46, 2, 'Y'),
    (47, 1, 47, 2, 'Y'),

    (5, 1, 5, 2, 'Y'),
    (51, 1, 51, 1, 'Y'),
    (52, 1, 52, 2, 'Y'),
    (53, 1, 53, 2, 'Y'),
    (54, 1, 54, 2, 'Y'),
    (55, 1, 55, 2, 'Y'),
    (56, 1, 56, 2, 'Y'),

    (7, 1, 7, 2, 'Y'),
    (71, 1, 71, 1, 'Y'),
    (72, 1, 72, 2, 'Y'),
    (73, 1, 73, 2, 'Y'),
    (74, 1, 74, 2, 'Y'),
    (75, 1, 75, 2, 'Y'),
    (76, 1, 76, 2, 'Y'),
    (77, 1, 77, 2, 'Y'),
    (78, 1, 78, 2, 'Y'),
    (79, 1, 79, 2, 'Y'),

    (101, 1, 101, 2, 'Y'),
    (111, 1, 111, 1, 'Y'),
    (112, 1, 112, 2, 'Y'),
    (113, 1, 113, 2, 'Y'),
    (114, 1, 114, 2, 'Y'),
    (116, 1, 116, 2, 'Y'),
    (117, 1, 117, 2, 'Y'),

    (201, 1, 201, 2, 'Y'),
    (211, 1, 211, 1, 'Y'),
    (212, 1, 212, 2, 'Y'),
    (213, 1, 213, 2, 'Y'),
    (214, 1, 214, 2, 'Y'),
    (215, 1, 215, 2, 'Y'),

    (301, 1, 301, 2, 'Y'),
    (311, 1, 311, 1, 'Y'),
    (312, 1, 312, 2, 'Y'),

    (401, 1, 401, 2, 'Y'),
    (411, 1, 411, 1, 'Y'),
    (412, 1, 412, 2, 'Y'),
    (413, 1, 413, 2, 'Y'),
    (414, 1, 414, 2, 'Y'),
    (415, 1, 415, 2, 'Y'),
    (416, 1, 416, 2, 'Y'),
    (417, 1, 417, 2, 'Y'),
    (418, 1, 418, 2, 'Y'),
    (420, 1, 420, 2, 'Y'),
    (421, 1, 421, 2, 'Y'),
    (422, 1, 422, 2, 'Y'),

    (501, 1, 501, 2, 'Y'),
    (511, 1, 511, 1, 'Y'),
    (512, 1, 512, 2, 'Y'),
    (513, 1, 513, 2, 'Y'),

    (NEXTVAL('user_group_menu_seq'), 1, 1001, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 1, 1002, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 1, 1003, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 1, 1004, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 1, 1005, 1, 'Y'),
--     (NEXTVAL('user_group_menu_seq'), 1, 1006, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 1, 1007, 1, 'Y'),

    (NEXTVAL('user_group_menu_seq'), 2, 1001, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 2, 1002, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 2, 1003, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 2, 1004, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 2, 1005, 1, 'Y'),
--     (NEXTVAL('user_group_menu_seq'), 2, 1006, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 2, 1007, 1, 'Y'),

    (NEXTVAL('user_group_menu_seq'), 3, 1001, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 3, 1002, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 3, 1003, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 3, 1004, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 3, 1005, 1, 'Y'),
--     (NEXTVAL('user_group_menu_seq'), 3, 1006, 1, 'Y'),
    (NEXTVAL('user_group_menu_seq'), 3, 1007, 1, 'Y');

insert into user_group_role(user_group_role_id, user_group_id, role_id)
values
    (NEXTVAL('user_group_role_seq'), 1, 1),
    (NEXTVAL('user_group_role_seq'), 1, 2),
    (NEXTVAL('user_group_role_seq'), 1, 3),
    (NEXTVAL('user_group_role_seq'), 1, 1001),
    (NEXTVAL('user_group_role_seq'), 1, 1002),
    (NEXTVAL('user_group_role_seq'), 1, 1003),
    (NEXTVAL('user_group_role_seq'), 2, 1001),
    (NEXTVAL('user_group_role_seq'), 2, 1002),
    (NEXTVAL('user_group_role_seq'), 2, 1003);


-- 首页部件
insert into widget(widget_id, widget_name, widget_key, view_order, user_id)
values
    (NEXTVAL('widget_seq'), '用户现状', 'userWidget', 1, 'admin' ),
    (NEXTVAL('widget_seq'), '不同类型的数据现况', 'dataTypeWidget', 2, 'admin' ),
    (NEXTVAL('widget_seq'), '数据转换现况', 'converterWidget', 3, 'admin' ),
    (NEXTVAL('widget_seq'), '最近热门', 'issueWidget', 4, 'admin' ),
    (NEXTVAL('widget_seq'), '要求更改数据位置', 'dataAdjustLogWidget', 5, 'admin' ),
    (NEXTVAL('widget_seq'), '资源现状', 'systemResourceWidget', 6, 'admin' ),
    (NEXTVAL('widget_seq'), '行程执行结果', 'scheduleWidget', 7, 'admin' ),
    (NEXTVAL('widget_seq'), 'api请求', 'apiLogWidget', 8, 'admin' );


-- 运营政策
insert into policy(	policy_id, password_exception_char)
values( 1, '<>&''"');

-- 2D, 3D 运营政策
insert into geopolicy(	geopolicy_id)
values( 1 );

-- Role
insert into role(role_id, role_name, role_key, role_target, role_type, use_yn, default_yn)
values
    (1, '[管理员专用] 管理员页面SIGN IN权限', 'ADMIN_SIGNIN', '1', '0', 'Y', 'Y'),
    (2, '[管理员专用] 管理员页面用户管理权限', 'ADMIN_USER_MANAGE', '1', '0', 'Y', 'Y'),
    (3, '[管理员专用] 管理器页面Layer的权限', 'ADMIN_LAYER_MANAGE', '1', '0', 'Y', 'Y'),

    (1001, '[用户专用]用户页面SIGN IN权限', 'USER_SIGNIN', '0', '0', 'Y', 'Y'),
    (1002, '[用户专用]用户页面DATA注册权限', 'USER_DATA_CREATE', '0', '0', 'Y', 'Y'),
    (1003, '[用户专用]用户页面DATA浏览权限', 'USER_DATA_READ', '0', '0', 'Y', 'Y');


INSERT INTO data_group (
    data_group_id, data_group_key, data_group_name, data_group_path, data_group_target, sharing, user_id,
    ancestor, parent, depth, view_order, children, basic, available, tiling, data_count, metainfo)
values
    (1, 'basic', '基本', 'infra/data/basic/', 'admin', 'common', 'admin', 1, 0, 1, 1, 0, true, true, false, 0,  TO_JSON('{"isPhysical": false}'::json));

INSERT INTO data_library_group (
    data_library_group_id, data_library_group_key, data_library_group_name, data_library_group_path, data_library_group_target, sharing, user_id,
    ancestor, parent, depth, view_order, children, basic, available, data_library_count)
values
    (1, 'basic', '基本', 'infra/data-library/basic/', 'admin', 'common', 'admin', 1, 0, 1, 1, 0, true, true, 0);

INSERT INTO rule_group (
    rule_group_id, rule_group_name, rule_group_key, rule_type, rule_inherit_type, user_id, ancestor, parent, depth, view_order, basic, available, description)
VALUES
    (1, '数据规则组', 'data', 'data', true, 'admin', 1, 0, 1, 1, true, true, '数据规则组'),
    (2, '3D 库规则组', 'data_library', 'data_library', true, 'admin', 2, 0, 1, 2, true, true, '3D库Rule组'),
    (3, '图层组', 'layer', 'layer', true, 'admin', 3, 0, 1, 3, true, true, '图层Rule组'),
    (4, '模拟规则组', 'simulation', 'simulation', true, 'admin', 4, 0, 1, 4, true, true, '模拟Rule组');


INSERT INTO membership(
    membership_id, membership_name, upload_file_size, convert_file_count)
values
    (1, 'BASIC', 102400, 10),
    (2, 'SILVER', 1024000, 30),
    (3, 'GOLD', 5125000, 50),
    (4, 'PLATINUM', 10240000, 100);

INSERT INTO membership_usage(
    membership_usage_id, membership_id, user_id, update_date)
values
    (1, 4, 'admin', NOW()),
    (2, 4, 'mago3d', NOW());

INSERT INTO membership_log(
    membership_log_id, current_membership_id, request_membership_id, user_id, status)
values
    (1, 4, 4, 'admin', 'APPROVAL'),
    (2, 4, 4, 'mago3d', 'APPROVAL');

/*INSERT INTO qrtz_simple_trigger(
    qrtz_simple_trigger_id, qrtz_simple_trigger_name, qrtz_simple_trigger_group, executor_name, repeat_count, repeat_interval, times_triggered)
values
(1, 'TRIGGER_TEST_1', 'TRIGGER_TEST', 'TEST', 5, 100, 0);*/
INSERT INTO schedule_info(
    schedule_id, job_name, job_group, trigger_name, trigger_group, cron_schedule, data)
values
(1, 'job01', 'jobGroup01', 'trigger01', 'triggerGroup01', '0 0/1 8-19 * * ?', 'admin' );

commit;
