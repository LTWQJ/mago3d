-- 사용자 그룹 테이블 기본값 입력
insert into user_group(	user_group_id, user_group_key, user_group_name, ancestor, parent, depth, view_order, basic, available, description)
values
	(1, 'SUPER_ADMIN', '슈퍼 관리자', 1, 0, 1, 1, 'Y', 'Y', '기본값'),
	(2, 'USER', '사용자', 1, 0, 1, 2, 'Y', 'Y', '기본값'),
	(3, 'GUEST', 'GUEST', 1, 0, 1, 3, 'Y', 'Y', '기본값');

-- 슈퍼 관리자 등록
insert into user_info(
	user_id, user_group_id, user_name, password, user_role_check_yn, last_signin_date)
values
    ('admin', 1, '超级管理人员', '$2a$10$KFr/2p5Og2jBy8NkTaEb/eoUna6AVlQ.A7s4YpPJ9A8dZwLYum5f.', 'N', now()),
    ('mago3d', 2, 'mago3D', '$2a$10$lmYPqp2UJm4lHuF57Rs.wuzX034x7y/21jlCc8OQ4yFxbZt6Iich2', 'Y', now());

-- 관리자 메뉴
insert into menu(menu_id, menu_type, menu_target, name, name_en, ancestor, parent, depth, previous_depth, view_order, url, url_alias, html_id, css_class, default_yn, use_yn, display_yn)
values
	(1, '0', '1', 'home', 'HOME', 0, 0, 1, 0, 1, '/main/index', null, null, 'glyph-home', 'N', 'N', 'N'),
	(2, '0', '1', '用户', 'USER', 2, 0, 1, 0, 2, '/user/list', null, null, 'glyph-users', 'Y', 'Y', 'Y'),
	(21, '0', '1', '用户组', 'USER', 2, 2, 2, 1, 1, '/user-group/list', null, null, 'glyph-users', 'Y', 'Y', 'Y'),
	(22, '0', '1', '注册用户组', 'USER', 2, 2, 2, 2, 2, '/user-group/input', null, null, 'glyph-users', 'Y', 'Y', 'Y'),
	(23, '0', '1', '修改用户组', 'USER', 2, 2, 2, 2, 3, '/user-group/modify', '/user-group/list', null, 'glyph-users', 'N', 'Y', 'N'),
	(24, '0', '1', '用户组菜单', 'USER', 2, 2, 2, 2, 4, '/user-group/menu', '/user-group/list', null, 'glyph-users', 'N', 'Y', 'N'),
	(25, '0', '1', '用户组 Role', 'USER', 2, 2, 2, 2, 5, '/user-group/role', '/user-group/list', null, 'glyph-users', 'N', 'Y', 'N'),
	(26, '0', '1', '用户列表', 'USER', 2, 2, 2, 2, 6, '/user/list', null, null, 'glyph-users', 'Y', 'Y', 'Y'),
	(27, '0', '1', '注册用户', 'USER', 2, 2, 2, 2, 7, '/user/input', null, null, 'glyph-users', 'Y', 'Y', 'Y'),
	(28, '0', '1', '修改用户信息', 'USER', 2, 2, 2, 2, 8, '/user/modify', '/user/list', null, 'glyph-users', 'N', 'Y', 'N'),
	(29, '0', '1', '用户详细信息', 'USER', 2, 2, 2, 2, 9, '/user/detail', '/user/list', null, 'glyph-users', 'N', 'Y', 'N'),

    (3, '0', '1', '数据', 'DATA', 3, 0, 1, 2, 3, '/data-group/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
	(31, '0', '1', '数据组', 'DATA', 3, 3, 2, 1, 1, '/data-group/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
	(32, '0', '1', '注册数据组', 'DATA', 3, 3, 2, 2, 2, '/data-group/input', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
	(33, '0', '1', '修改数据组', 'DATA', 3, 3, 2, 2, 3, '/data-group/modify', '/data-group/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
	(34, '0', '1', '用户数据组', 'DATA', 3, 3, 2, 2, 4, '/data-group/list-user', null, null, 'glyph-monitor', 'Y', 'N', 'Y'),
	(35, '0', '1', '数据列表', 'DATA', 3, 3, 2, 2, 5, '/data/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
	(36, '0', '1', '数据详细信息', 'DATA', 3, 3, 2, 2, 6, '/data/detail', '/data/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
	(37, '0', '1', '修改数据', 'DATA', 3, 3, 2, 2, 7, '/data/modify', '/data/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
	(40, '0', '1', '上传', 'DATA', 3, 3, 2, 2, 8, '/upload-data/input', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
	(41, '0', '1', '上传列表', 'DATA', 3, 3, 2, 2, 9, '/upload-data/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
	(42, '0', '1', '修改上传', 'DATA', 3, 3, 2, 2, 10, '/upload-data/modify', '/upload-data/list', null, 'glyph-monitor', 'N', 'Y', 'N'),
	(43, '0', '1', '数据转换结果', 'DATA', 3, 3, 2, 2, 11, '/converter/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
	(44, '0', '1', '数据转换详细信息列表', 'DATA', 3, 3, 2, 2, 12, '/converter/converter-job-file-list', null , null ,'glyph-monitor', 'Y', 'Y', 'Y'),
	(45, '0', '1', '数据更改历史记录', 'DATA', 3, 3, 2, 2, 13, '/data-log/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
	(46, '0', '1', '数据位置更改请求历史记录', 'DATA', 3, 3, 2, 2, 14, '/data-adjust-log/list', null, null, 'glyph-monitor', 'Y', 'Y', 'Y'),
	(5, '0', '1', '层', 'LAYER', 5, 0, 1, 2, 5, '/layer-group/list', null, null, 'glyph-check', 'Y', 'Y', 'Y'),
	(51, '0', '1', '2D图层', 'LAYER', 5, 5, 2, 1, 1, '/layer-group/list', null, null, 'glyph-check', 'Y', 'Y', 'Y'),
	(52, '0', '1', '2D图层组注册', 'LAYER', 5, 5, 2, 2, 2, '/layer-group/input', null, null, 'glyph-check', 'Y', 'Y', 'Y'),
	(53, '0', '1', '修正2D图层组', 'LAYER', 5, 5, 2, 2, 3, '/layer-group/modify', '/layer-group/list', null, 'glyph-check', 'N', 'Y', 'N'),
	(54, '0', '1', '2D图层列表', 'LAYER', 5, 5, 2, 2, 4, '/layer/list', null, null, 'glyph-check', 'Y', 'Y', 'Y'),
	(55, '0', '1', '2D图层注册', 'LAYER', 5, 5, 2, 2, 5, '/layer/input', null, null, 'glyph-check', 'Y', 'Y', 'Y'),
	(56, '0', '1', '修正2D图层', 'LAYER', 5, 5, 2, 2, 6, '/layer/modify', '/layer/list', null, 'glyph-check', 'N', 'Y', 'N'),
	(8, '0', '1', '首选项', 'CONFIGURATION', 8, 0, 1, 2, 8, '/policy/modify', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
	(81, '0', '1', '一般运营政策', 'CONFIGURATION', 8, 8, 2, 1, 1, '/policy/modify', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
	(82, '0', '1', '空间资讯运营政策', 'CONFIGURATION', 8, 8, 2, 2, 2, '/geopolicy/modify', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
	(83, '0', '1', '管理员菜单', 'ADMIN MENU', 8, 8, 2, 2, 3, '/menu/admin-menu', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
	(84, '0', '1', '用户菜单', 'USER MENU', 8, 8, 2, 2, 4, '/menu/user-menu', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
	(85, '0', '1', '部件', 'WIDGET', 8, 8, 2, 2, 5, '/widget/modify', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
	(86, '0', '1', '权限', 'ROLE', 8, 8, 2, 2, 6, '/role/list', null, null, 'glyph-settings', 'Y', 'Y', 'Y'),
	(87, '0', '1', '登记权限', 'ROLE', 8, 8, 2, 2, 7, '/role/input', '/role/list', null, 'glyph-settings', 'N', 'Y', 'N'),
	(88, '0', '1', '权限修改', 'ROLE', 8, 8, 2, 2, 8, '/role/modify', '/role/list', null, 'glyph-settings', 'N', 'Y', 'N');


-- 用户菜单
insert into menu(menu_id, menu_type, menu_target, name, name_en, ancestor, parent, depth, previous_depth, view_order, url, url_alias, html_id, html_content_id,
    css_class, default_yn, use_yn, display_yn)
values
    (1001, '1', '0', '검색', 'SEARCH', 1001, 0, 1, 1, 1, '/search', null, 'searchMenu', 'searchContent', 'search', 'Y', 'Y', 'Y'),
    (1002, '1', '0', '데이터', 'DATA', 1002, 0, 1, 1, 2, '/data/map', null, 'dataMenu', 'dataContent', 'data', 'Y', 'Y', 'Y'),
    (1003, '1', '0', '변환', 'CONVERTER', 1003, 0, 1, 1, 3, '/upload-data/list', null, 'converterMenu', 'converterContent', 'converter', 'Y', 'Y', 'Y'),
    --(1004, '1', '0', '공간분석', 'SPATIAL', 1004, 0, 1, 4, '/spatial', null, 'spatialMenu', 'spatialContent', 'spatial', 'Y', 'Y', 'Y'),
    --(1005, '1', '0', '시뮬레이션', 'SIMULATION', 1005, 0, 1, 5, '/simulation', null, 'simulationMenu', 'simulationContent', 'simulation', 'Y', 'Y', 'Y'),
    (1007, '1', '0', '레이어', 'LAYER', 1007, 0, 1, 1, 7, '/layer/list', null, 'layerMenu', 'layerContent', 'layer', 'Y', 'Y', 'Y'),
    (1008, '1', '0', '환경설정', 'USER POLICY', 1008, 0, 1, 1, 8, '/user-policy/modify', null, 'userPolicyMenu', 'userPolicyContent', 'userPolicy', 'Y', 'Y', 'Y');


-- 사용자 그룹별 메뉴
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
	(40, 1, 40, 2, 'Y'),
	(41, 1, 41, 2, 'Y'),
	(42, 1, 42, 2, 'Y'),
	(43, 1, 43, 2, 'Y'),
	(44, 1, 44, 2, 'Y'),
	(45, 1, 45, 2, 'Y'),
	(46, 1, 46, 2, 'Y'),
	(5, 1, 5, 2, 'Y'),
	(51, 1, 51, 1, 'Y'),
	(52, 1, 52, 2, 'Y'),
	(53, 1, 53, 2, 'Y'),
	(54, 1, 54, 2, 'Y'),
	(55, 1, 55, 2, 'Y'),
	(56, 1, 56, 2, 'Y'),
	(8, 1, 8, 2, 'Y'),
	(81, 1, 81, 1, 'Y'),
	(82, 1, 82, 2, 'Y'),
	(83, 1, 83, 2, 'Y'),
	(84, 1, 84, 2, 'Y'),
	(85, 1, 85, 2, 'Y'),
	(86, 1, 86, 2, 'Y'),
	(87, 1, 87, 2, 'Y'),
	(88, 1, 88, 2, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 1, 1001, 1, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 1, 1002, 1, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 1, 1003, 1, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 1, 1004, 1, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 1, 1005, 1, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 1, 1007, 1, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 1, 1008, 1, 'Y'),

	(NEXTVAL('user_group_menu_seq'), 2, 1001, 1, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 2, 1002, 1, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 2, 1003, 1, 'Y'),
	--(NEXTVAL('user_group_menu_seq'), 2, 1004, 'Y'),
	--(NEXTVAL('user_group_menu_seq'), 2, 1005, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 2, 1007, 1, 'Y'),
	(NEXTVAL('user_group_menu_seq'), 2, 1008, 1, 'Y');



-- 메인 화면 위젯
insert into widget(widget_id, widget_name, widget_key, view_order, user_id)
values
	(NEXTVAL('widget_seq'), '사용자 현황', 'userWidget', 1, 'admin' ),
	(NEXTVAL('widget_seq'), '데이터 타입별 현황', 'dataTypeWidget', 2, 'admin' ),
	(NEXTVAL('widget_seq'), '데이터 변환 현황', 'converterWidget', 3, 'admin' ),
	(NEXTVAL('widget_seq'), '최근 이슈', 'issueWidget', 4, 'admin' ),
	(NEXTVAL('widget_seq'), '데이터 위치 정보 변경 요청', 'dataAdjustLogWidget', 5, 'admin' ),
	(NEXTVAL('widget_seq'), '리소스 현황', 'systemResourceWidget', 6, 'admin' ),
	(NEXTVAL('widget_seq'), '스케줄 실행 결과', 'scheduleWidget', 7, 'admin' ),
	(NEXTVAL('widget_seq'), 'API 요청', 'apiLogWidget', 8, 'admin' );


-- 운영 정책
insert into policy(	policy_id, password_exception_char)
			values( 1, '<>&''"');

-- 2D, 3D 운영 정책
insert into geopolicy(	geopolicy_id)
			values( 1 );

-- Role
insert into role(role_id, role_name, role_key, role_target, role_type, use_yn, default_yn)
values
    (1, '[관리자 전용] 관리자 페이지 SIGN IN 권한', 'ADMIN_SIGNIN', '1', '0', 'Y', 'Y'),
    (2, '[관리자 전용] 관리자 페이지 사용자 관리 권한', 'ADMIN_USER_MANAGE', '1', '0', 'Y', 'Y'),
    (3, '[관리자 전용] 관리자 페이지 Layer 관리 권한', 'ADMIN_LAYER_MANAGE', '1', '0', 'Y', 'Y'),

	(4, '[사용자 전용] 사용자 페이지 SIGN IN 권한', 'USER_SIGNIN', '0', '0', 'Y', 'Y'),
	(5, '[사용자 전용] 사용자 페이지 DATA 등록 권한', 'USER_DATA_CREATE', '0', '0', 'Y', 'Y'),
	(6, '[사용자 전용] 사용자 페이지 DATA 조회 권한', 'USER_DATA_READ', '0', '0', 'Y', 'Y');


INSERT INTO data_group (
    data_group_id, data_group_key, data_group_name, data_group_path, data_group_target, sharing, user_id,
    ancestor, parent, depth, view_order, children, basic, available, tiling, data_count, metainfo)
values
(1, 'basic', '기본', 'infra/data/basic/', 'admin', 'common', 'admin', 1, 0, 1, 1, 0, true, true, false, 0,  TO_JSON('{"isPhysical": false}'::json));



insert into user_group_role(user_group_role_id, user_group_id, role_id)
values
	(NEXTVAL('user_group_role_seq'), 1, 1),
	(NEXTVAL('user_group_role_seq'), 1, 2),
	(NEXTVAL('user_group_role_seq'), 1, 3),
	(NEXTVAL('user_group_role_seq'), 1, 4),
	(NEXTVAL('user_group_role_seq'), 1, 5),
	(NEXTVAL('user_group_role_seq'), 1, 6),
	(NEXTVAL('user_group_role_seq'), 2, 4),
	(NEXTVAL('user_group_role_seq'), 2, 5),
    (NEXTVAL('user_group_role_seq'), 2, 6);
