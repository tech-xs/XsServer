-- SYS模块
CREATE TABLE IF NOT EXISTS sys_user
(
    id             BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    company_id     BIGINT COMMENT '公司ID',
    user_name      VARCHAR(32) UNIQUE NOT NULL COMMENT '用户名',
    nick_name      VARCHAR(32) COMMENT '昵称',
    login_password CHAR(32) COMMENT '密码',
    phone          VARCHAR(32) UNIQUE COMMENT '手机号',
    email          VARCHAR(128) UNIQUE COMMENT '用户邮箱',
    sex            int                NOT NULL DEFAULT '3' COMMENT '用户性别（1男 2女 3 未知）',
    account_status int                NOT NULL DEFAULT '1' COMMENT '帐号状态（1正常 2禁用）',
    avatar_url     VARCHAR(1024) COMMENT '头像地址',
    id_number      VARCHAR(32) COMMENT '身份证号',
    birth_date     DATETIME COMMENT '出生日期',
    create_user    VARCHAR(64) COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_user    VARCHAR(64) COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '用户表';

CREATE TABLE IF NOT EXISTS sys_user_delete
(
    id             BIGINT COMMENT '原表唯一标识符',
    company_id     BIGINT COMMENT '公司ID',
    user_name      VARCHAR(32) COMMENT '用户名',
    nick_name      VARCHAR(32) COMMENT '昵称',
    login_password CHAR(32) COMMENT '密码',
    phone          VARCHAR(32) COMMENT '手机号',
    email          VARCHAR(128) COMMENT '用户邮箱',
    sex            INT COMMENT '用户性别（1男 2女 3 未知）',
    account_status INT COMMENT '帐号状态（1正常 2禁用）',
    avatar_url     VARCHAR(1024) COMMENT '头像地址',
    id_number      VARCHAR(32) COMMENT '身份证号',
    birth_date     DATETIME COMMENT '出生日期',
    create_user    VARCHAR(64) COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_user    VARCHAR(64) COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '用户表';

CREATE TABLE IF NOT EXISTS sys_page
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    page_name   VARCHAR(128)  NOT NULL COMMENT '页面名称',
    page_uri    VARCHAR(1024) NOT NULL COMMENT 'URI',
    father_id    BIGINT  COMMENT '父页面ID',
    page_remark VARCHAR(1024) COMMENT '备注',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '用户角色关联表';

CREATE TABLE IF NOT EXISTS sys_page_delete
(
    id          BIGINT COMMENT '原表唯一标识符',
    page_name   VARCHAR(64) COMMENT '页面名称',
    page_uri    VARCHAR(1024) COMMENT 'URI',
    father_id    BIGINT  COMMENT '父页面ID',
    page_remark VARCHAR(1024) COMMENT '备注',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time datetime COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '用户角色关联表';

CREATE TABLE IF NOT EXISTS sys_page_permission
(
    id                BIGINT PRIMARY KEY auto_increment COMMENT '原表唯一标识符',
    page_id           BIGINT       NOT NULL COMMENT '页面ID',
    permission_code   VARCHAR(128) NOT NULL COMMENT '唯一标识符',
    permission_name   VARCHAR(128) NOT NULL COMMENT '权限名',
    permission_remark VARCHAR(1024) COMMENT '备注',
    create_user       VARCHAR(64) COMMENT '创建者',
    create_time       datetime COMMENT '创建时间',
    update_user       VARCHAR(64) COMMENT '更新者',
    update_time       datetime COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '页面权限表';

CREATE TABLE IF NOT EXISTS sys_page_permission_delete
(
    id                BIGINT COMMENT '原表唯一标识符',
    page_id           BIGINT COMMENT '页面ID',
    permission_code   VARCHAR(128) COMMENT '唯一标识符',
    permission_name   VARCHAR(128) COMMENT '权限名',
    permission_remark VARCHAR(1024) COMMENT '备注',
    create_user       VARCHAR(64) COMMENT '创建者',
    create_time       datetime COMMENT '创建时间',
    update_user       VARCHAR(64) COMMENT '更新者',
    update_time       datetime COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '页面权限表';

CREATE TABLE IF NOT EXISTS sys_dict
(
    id              BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    dict_code       VARCHAR(128) UNIQUE NOT NULL COMMENT '唯一标识符',
    dict_name       VARCHAR(128) UNIQUE NOT NULL COMMENT '字典名称',
    structure_type  INT                 NOT NULL DEFAULT 1 COMMENT '1 列表 2 树状结构',
    value_type      INT                 NOT NULL DEFAULT 1 COMMENT '1 string 2 int',
    dict_remark     VARCHAR(1024) COMMENT '备注',
    create_user     VARCHAR(64) COMMENT '创建者',
    create_time     DATETIME COMMENT '创建时间',
    update_user     VARCHAR(64) COMMENT '更新者',
    update_time     DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '字典表';

CREATE TABLE IF NOT EXISTS sys_dict_delete
(
    id              BIGINT COMMENT '原表唯一标识符',
    dict_code       VARCHAR(128) COMMENT '唯一标识符',
    dict_name       VARCHAR(128) COMMENT '字典名称',
    structure_type  INT COMMENT '1 列表 2 树状结构',
    value_type      INT COMMENT '1 string 2 int',
    dict_remark     VARCHAR(1024) COMMENT '备注',
    create_user     VARCHAR(64) COMMENT '创建者',
    create_time     DATETIME COMMENT '创建时间',
    update_user     VARCHAR(64) COMMENT '更新者',
    update_time     DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '字典表';

CREATE TABLE IF NOT EXISTS sys_dict_value
(
    id                BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    dict_id           BIGINT        NOT NULL COMMENT '字典ID',
    dict_value        VARCHAR(1024) NOT NULL COMMENT 'value',
    dict_value_name   VARCHAR(128)  NOT NULL COMMENT '名称',
    dict_value_sort   INT           NOT NULL COMMENT '排序',
    father_id         BIGINT COMMENT '父节点ID',
    dict_value_remark VARCHAR(1024) COMMENT '备注',
    create_user       VARCHAR(64) COMMENT '创建者',
    create_time       DATETIME COMMENT '创建时间',
    update_user       VARCHAR(64) COMMENT '更新者',
    update_time       DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '字典值表';

CREATE TABLE IF NOT EXISTS sys_dict_value_delete
(
    id                BIGINT COMMENT '唯一标识符',
    dict_id           BIGINT COMMENT '字典ID',
    dict_value        VARCHAR(1024) COMMENT 'value',
    dict_value_name   VARCHAR(128) COMMENT '名称',
    dict_value_sort   int COMMENT '排序',
    father_id         BIGINT COMMENT '父节点ID',
    dict_value_remark VARCHAR(1024) COMMENT '备注',
    create_user       VARCHAR(64) COMMENT '创建者',
    create_time       datetime COMMENT '创建时间',
    update_user       VARCHAR(64) COMMENT '更新者',
    update_time       datetime COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '字典值表';

CREATE TABLE IF NOT EXISTS sys_interface_log
(
    id               BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    company_id       BIGINT COMMENT '组织机构id',
    business_key     varchar(64) COMMENT '业务key',
    request_url      LONGTEXT COMMENT '请求地址',
    request_content  LONGTEXT COMMENT '请求内容',
    response_code    VARCHAR(64) COMMENT '响应码',
    response_content LONGTEXT COMMENT '响应内容',
    create_user      VARCHAR(64) COMMENT '创建者',
    create_time      datetime not null COMMENT '创建时间',
    update_user      VARCHAR(64) COMMENT '更新者',
    update_time      datetime COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口调用日志表';

CREATE TABLE IF NOT EXISTS sys_interface_log_delete
(
    id               BIGINT COMMENT '唯一标识符',
    company_id       BIGINT COMMENT '组织机构id',
    business_key     varchar(64) COMMENT '业务key',
    request_url      LONGTEXT COMMENT '请求地址',
    request_content  LONGTEXT COMMENT '请求内容',
    response_code    VARCHAR(64) COMMENT '响应码',
    response_content LONGTEXT COMMENT '响应内容',
    create_user      VARCHAR(64) COMMENT '创建者',
    create_time      DATETIME COMMENT '创建时间',
    update_user      VARCHAR(64) COMMENT '更新者',
    update_time      DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口调用日志表';

CREATE TABLE IF NOT EXISTS sys_api_group
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    group_name  VARCHAR(128) NOT NULL COMMENT '组名',
    class_name  VARCHAR(1024) COMMENT '组类名',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT 'Api分组';

CREATE TABLE IF NOT EXISTS sys_api_group_delete
(
    id          BIGINT COMMENT '唯一标识符',
    group_name  VARCHAR(128) COMMENT '组名',
    class_name  VARCHAR(1024) COMMENT '组类名',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT 'Api分组';

CREATE TABLE IF NOT EXISTS sys_api
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    group_id    BIGINT COMMENT '组ID',
    api_name    VARCHAR(128) COMMENT '名称',
    uri         VARCHAR(2048) NOT NULL COMMENT '接口路径',
    method      INT COMMENT '请求方式 1: get 2: head 3: post 4: put 5: delete 6: connect 7: options 8: trace',
    api_exist   INT           NOT NULL COMMENT '接口是否存在 1 存在  2 不存在',
    api_remark  VARCHAR(1024) COMMENT '备注',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT 'uri资源';

CREATE TABLE IF NOT EXISTS sys_api_delete
(
    id          BIGINT COMMENT '唯一标识符',
    group_id    BIGINT COMMENT '组ID',
    api_name    VARCHAR(128) COMMENT '名称',
    uri         VARCHAR(2048) COMMENT '接口路径',
    method      INT COMMENT '请求方式 1: get 2: head 3: post 4: put 5: delete 6: connect 7: options 8: trace',
    api_exist   INT COMMENT '接口是否存在 1 存在  2 不存在',
    api_remark  VARCHAR(1024) COMMENT '备注',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT 'uri资源';

CREATE TABLE IF NOT EXISTS sys_page_api
(
    id              BIGINT PRIMARY KEY auto_increment COMMENT '原表唯一标识符',
    page_id         BIGINT NOT NULL COMMENT '页面ID',
    api_id     BIGINT NOT NULL COMMENT '接口ID',
    permission_id   BIGINT COMMENT '权限ID',
    api_remark VARCHAR(1024) COMMENT '备注',
    create_user     VARCHAR(64) COMMENT '创建者',
    create_time     DATETIME COMMENT '创建时间',
    update_user     VARCHAR(64) COMMENT '更新者',
    update_time     DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '页面接口表';

CREATE TABLE IF NOT EXISTS sys_page_api_delete
(
    id              BIGINT COMMENT '原表唯一标识符',
    page_id         BIGINT COMMENT '页面ID',
    api_id     BIGINT COMMENT '接口ID',
    permission_id   BIGINT COMMENT '权限ID',
    api_remark VARCHAR(1024) COMMENT '备注',
    create_user     VARCHAR(64) COMMENT '创建者',
    create_time     DATETIME COMMENT '创建时间',
    update_user     VARCHAR(64) COMMENT '更新者',
    update_time     DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '页面接口表';

CREATE TABLE IF NOT EXISTS sys_role
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    role_code   VARCHAR(128) UNIQUE COMMENT '唯一标识符',
    role_name   VARCHAR(128) UNIQUE NOT NULL COMMENT '角色名',
    role_status VARCHAR(128) DEFAULT 1 COMMENT '1 正常  2禁用',
    role_remark VARCHAR(1024) COMMENT '备注',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '角色表';

CREATE TABLE IF NOT EXISTS sys_role_delete
(
    id          BIGINT COMMENT '原表唯一标识符',
    role_code   VARCHAR(128) COMMENT '唯一标识符',
    role_name   VARCHAR(128) COMMENT '角色名',
    role_status VARCHAR(128) DEFAULT 1 COMMENT '1 正常  2禁用',
    role_remark VARCHAR(1024) COMMENT '备注',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '角色表';

CREATE TABLE IF NOT EXISTS sys_user_role
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    user_id     BIGINT NOT NULL COMMENT '用户ID',
    role_id     BIGINT NOT NULL COMMENT '角色ID',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '用户角色关联表';

CREATE TABLE IF NOT EXISTS sys_user_role_delete
(
    id          BIGINT COMMENT '原表唯一标识符',
    user_id     BIGINT COMMENT '用户ID',
    role_id     BIGINT COMMENT '角色ID',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '用户角色关联表';

CREATE TABLE IF NOT EXISTS sys_menu
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    menu_code        VARCHAR(128) COMMENT '菜单编码',
    father_id   BIGINT COMMENT '父菜单',
    menu_name   VARCHAR(128) NOT NULL COMMENT '名称',
    menu_sort   INT          NOT NULL default 0 COMMENT '排序',
    page_id     BIGINT COMMENT '页面ID',
    icon        VARCHAR(128) COMMENT '图标',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '菜单表';

CREATE TABLE IF NOT EXISTS sys_menu_delete
(
    id          BIGINT COMMENT '原表唯一标识符',
    code        VARCHAR(128) COMMENT '菜单编码',
    father_id   BIGINT COMMENT '父菜单',
    menu_name   VARCHAR(128) COMMENT '名称',
    menu_sort   INT COMMENT '排序',
    page_id     BIGINT COMMENT '页面ID',
    icon        VARCHAR(128) COMMENT '图标',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '菜单表';

CREATE TABLE IF NOT EXISTS sys_role_menu
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    role_id     BIGINT NOT NULL COMMENT '角色ID',
    menu_id     BIGINT NOT NULL COMMENT '菜单ID',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '角色-菜单关联表';

CREATE TABLE IF NOT EXISTS sys_role_menu_delete
(
    id          BIGINT COMMENT '唯一标识符',
    role_id     BIGINT COMMENT '角色ID',
    menu_id     BIGINT COMMENT '菜单ID',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '角色-菜单关联表';

CREATE TABLE IF NOT EXISTS sys_role_menu_page
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    role_id     BIGINT NOT NULL COMMENT '角色ID',
    menu_id     BIGINT NOT NULL COMMENT '菜单ID',
    page_id     BIGINT NOT NULL COMMENT '子页面ID',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
    COLLATE utf8mb4_unicode_ci
    ENGINE = INNODB COMMENT '角色-菜单-子页面关联表';

CREATE TABLE IF NOT EXISTS sys_role_menu_page_delete
(
    id          BIGINT COMMENT '唯一标识符',
    role_id     BIGINT COMMENT '角色ID',
    menu_id     BIGINT COMMENT '菜单ID',
    page_id     BIGINT COMMENT '子页面ID',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
    COLLATE utf8mb4_unicode_ci
    ENGINE = INNODB COMMENT '角色-菜单-子页面关联表';

CREATE TABLE IF NOT EXISTS sys_role_menu_page_permission
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    role_id     BIGINT NOT NULL COMMENT '角色ID',
    menu_id     BIGINT NOT NULL COMMENT '菜单ID',
    page_id     BIGINT NOT NULL COMMENT '页面ID',
    permission_id     BIGINT NOT NULL COMMENT '权限ID',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
    COLLATE utf8mb4_unicode_ci
    ENGINE = INNODB COMMENT '角色-菜单权限关联表';

CREATE TABLE IF NOT EXISTS sys_role_menu_page_permission_delete
(
    id          BIGINT COMMENT '唯一标识符',
    role_id     BIGINT COMMENT '角色ID',
    menu_id     BIGINT COMMENT '菜单ID',
    page_id     BIGINT COMMENT '页面ID',
    permission_id     BIGINT NOT NULL COMMENT '权限ID',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
    COLLATE utf8mb4_unicode_ci
    ENGINE = INNODB COMMENT '角色-菜单权限关联表';

CREATE TABLE IF NOT EXISTS sys_role_page
(
    id            BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    role_id       BIGINT NOT NULL COMMENT '角色ID',
    page_id       BIGINT NOT NULL COMMENT '页面ID',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
    COLLATE utf8mb4_unicode_ci
    ENGINE = INNODB COMMENT '角色-页面关联表';

CREATE TABLE IF NOT EXISTS sys_role_page_delete
(
    id            BIGINT COMMENT '唯一标识符',
    role_id       BIGINT COMMENT '角色ID',
    page_id       BIGINT COMMENT '页面ID',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
    COLLATE utf8mb4_unicode_ci
    ENGINE = INNODB COMMENT '角色-页面关联表';

-- CREATE TABLE IF NOT EXISTS sys_role_page_permission
-- (
--     id            BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
--     role_id       BIGINT NOT NULL COMMENT '角色ID',
--     page_id       BIGINT NOT NULL COMMENT '页面ID',
--     permission_id BIGINT NOT NULL COMMENT '权限ID',
--     create_user   VARCHAR(64) COMMENT '创建者',
--     create_time   DATETIME COMMENT '创建时间',
--     update_user   VARCHAR(64) COMMENT '更新者',
--     update_time   DATETIME COMMENT '更新时间'
-- ) charset utf8mb4
--   COLLATE utf8mb4_unicode_ci
--   ENGINE = INNODB COMMENT '角色-页面权限关联表';
--
-- CREATE TABLE IF NOT EXISTS sys_role_page_permission_delete
-- (
--     id            BIGINT COMMENT '唯一标识符',
--     role_id       BIGINT COMMENT '角色ID',
--     page_id       BIGINT COMMENT '页面ID',
--     permission_id BIGINT COMMENT '权限ID',
--     create_user   VARCHAR(64) COMMENT '创建者',
--     create_time   DATETIME COMMENT '创建时间',
--     update_user   VARCHAR(64) COMMENT '更新者',
--     update_time   DATETIME COMMENT '更新时间'
-- ) charset utf8mb4
--   COLLATE utf8mb4_unicode_ci
--   ENGINE = INNODB COMMENT '角色-页面权限关联表';

CREATE TABLE IF NOT EXISTS auth_client_type
(
    id                            BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    client_name                   VARCHAR(128) NOT NULL COMMENT '名称',
    access_token_validity         BIGINT       NOT NULL DEFAULT 0 COMMENT 'access token 有效时长',
    access_token_refresh_interval BIGINT       NOT NULL DEFAULT 0 COMMENT 'access token 自动刷新时间间隔',
    refresh_token_validity        BIGINT       NOT NULL DEFAULT 0 COMMENT 'refresh token 有效时长',
    client_status                 INT          NOT NULL DEFAULT 1 COMMENT '状态 1:正常 2:禁用',
    client_remark                 VARCHAR(1024) COMMENT '备注',
    create_user                   VARCHAR(64) COMMENT '创建者',
    create_time                   DATETIME COMMENT '创建时间',
    update_user                   VARCHAR(64) COMMENT '更新者',
    update_time                   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '客户端表';

CREATE TABLE IF NOT EXISTS auth_client_type_delete
(
    id                     BIGINT COMMENT '原先表的唯一标识符',
    client_name            VARCHAR(128) COMMENT '名称',
    access_token_validity  BIGINT COMMENT 'access token 有效时长',
    refresh_token_validity BIGINT COMMENT 'refresh token 有效时长',
    client_status          INT COMMENT '状态 1:正常 2:禁用',
    client_remark          VARCHAR(1024) COMMENT '备注',
    create_user            VARCHAR(64) COMMENT '创建者',
    create_time            DATETIME COMMENT '创建时间',
    update_user            VARCHAR(64) COMMENT '更新者',
    update_time            DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '客户端备份表';

CREATE TABLE IF NOT EXISTS auth_token
(
    id                         BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    user_id                    BIGINT NOT NULL COMMENT '用户ID',
    client_type_id             BIGINT NOT NULL COMMENT '客户端类型ID',
    access_token               varchar(64) COMMENT 'access_token',
    refresh_token              varchar(64) COMMENT 'refresh_token',
    access_token_invalid_time  BIGINT COMMENT 'access_token失效时间',
    refresh_token_invalid_time BIGINT COMMENT 'refresh_token失效时间',
    create_user                VARCHAR(64) COMMENT '创建者',
    create_time                DATETIME COMMENT '创建时间',
    update_user                VARCHAR(64) COMMENT '更新者',
    update_time                DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '授权token存储的表';

CREATE TABLE IF NOT EXISTS auth_token_delete
(
    id                         BIGINT COMMENT '原表唯一标识符',
    user_id                    BIGINT COMMENT '用户ID',
    client_type_id             BIGINT COMMENT '客户端类型ID',
    access_token               VARCHAR(64) COMMENT 'access_token',
    refresh_token              VARCHAR(64) COMMENT 'refresh_token',
    access_token_invalid_time  BIGINT COMMENT 'access_token失效时间',
    refresh_token_invalid_time BIGINT COMMENT 'refresh_token失效时间',
    create_user                VARCHAR(64) COMMENT '创建者',
    create_time                DATETIME COMMENT '创建时间',
    update_user                VARCHAR(64) COMMENT '更新者',
    update_time                DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '授权token存储的表';

CREATE TABLE IF NOT EXISTS sys_config
(
    id               BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    company_id       BIGINT COMMENT '公司ID',
    config_module    VARCHAR(128)  NOT NULL COMMENT '所属模块',
    config_name      VARCHAR(128) COMMENT '配置名称',
    config_alias     VARCHAR(128) COMMENT '配置别名',
    config_type      VARCHAR(128) COMMENT '配置类型',
    config_key       VARCHAR(128)  NOT NULL COMMENT 'key',
    config_sub_key   VARCHAR(128) COMMENT '子key',
    config_value     VARCHAR(1024) NOT NULL COMMENT 'value',
    config_sub_value VARCHAR(1024) COMMENT '子Value',
    config_remark    VARCHAR(1024) COMMENT '备注',
    create_user      VARCHAR(64) COMMENT '创建者',
    create_time      DATETIME COMMENT '创建时间',
    update_user      VARCHAR(64) COMMENT '更新者',
    update_time      DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '系统设置表';

CREATE TABLE IF NOT EXISTS sys_config_delete
(
    id               BIGINT COMMENT '原表唯一标识符',
    company_id       BIGINT COMMENT '公司ID',
    config_module    VARCHAR(128) COMMENT '所属模块',
    config_name      VARCHAR(128) COMMENT '配置名称',
    config_alias     VARCHAR(128) COMMENT '配置别名',
    config_type      VARCHAR(128) COMMENT '配置类型',
    config_key       VARCHAR(128) COMMENT 'key',
    config_sub_key   VARCHAR(128) COMMENT '子key',
    config_value     VARCHAR(1024) COMMENT 'value',
    config_sub_value VARCHAR(1024) COMMENT '子Value',
    config_remark    VARCHAR(1024) COMMENT '备注',
    create_user      VARCHAR(64) COMMENT '创建者',
    create_time      DATETIME COMMENT '创建时间',
    update_user      VARCHAR(64) COMMENT '更新者',
    update_time      DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '系统设置表';

CREATE TABLE IF NOT EXISTS sys_company
(
    id                 BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    company_short_name VARCHAR(128) NOT NULL COMMENT '公司简称',
    company_full_name  VARCHAR(128) NOT NULL COMMENT '公司全称',
    company_remark     VARCHAR(1024) COMMENT '备注',
    create_user        VARCHAR(64) COMMENT '创建者',
    create_time        DATETIME COMMENT '创建时间',
    update_user        VARCHAR(64) COMMENT '更新者',
    update_time        DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '公司表';

CREATE TABLE IF NOT EXISTS sys_company_delete
(
    id                 BIGINT COMMENT '唯一标识符',
    company_short_name VARCHAR(128) COMMENT '公司名',
    company_full_name  VARCHAR(128) COMMENT '公司全称',
    company_remark     VARCHAR(1024) COMMENT '备注',
    create_user        VARCHAR(64) COMMENT '创建者',
    create_time        DATETIME COMMENT '创建时间',
    update_user        VARCHAR(64) COMMENT '更新者',
    update_time        DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '公司表';


CREATE TABLE IF NOT EXISTS doc_api
(
    id           BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    api_id       BIGINT COMMENT '接口ID',
    api_describe VARCHAR(4096) COMMENT '接口描述',
    create_user  VARCHAR(64) COMMENT '创建者',
    create_time  DATETIME COMMENT '创建时间',
    update_user  VARCHAR(64) COMMENT '更新者',
    update_time  DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口文档表';

CREATE TABLE IF NOT EXISTS doc_api_delete
(
    id           BIGINT COMMENT '唯一标识符',
    api_id       BIGINT COMMENT '接口ID',
    api_describe VARCHAR(4096) COMMENT '接口描述',
    create_user  VARCHAR(64) COMMENT '创建者',
    create_time  DATETIME COMMENT '创建时间',
    update_user  VARCHAR(64) COMMENT '更新者',
    update_time  DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口文档表';

CREATE TABLE IF NOT EXISTS doc_api_parm
(
    id            BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    api_id        BIGINT COMMENT '接口文档ID',
    parm_name     VARCHAR(128) NOT NULL COMMENT '参数名',
    data_type     INT COMMENT '参数类型 1 Integer 2 Long 3 Double 4 String 5 Date 6 Array 7 Object',
    source_type   INT COMMENT '源类型类型 1 请求 2 响应',
    parm_title    VARCHAR(128) COMMENT '参数标题',
    parm_describe VARCHAR(4096) COMMENT '参数描述',
    parm_sort     INT          NOT NULL COMMENT '排序',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口参数表';

CREATE TABLE IF NOT EXISTS doc_api_parm_delete
(
    id            BIGINT COMMENT '唯一标识符',
    api_id        BIGINT COMMENT '接口文档ID',
    parm_name     VARCHAR(128) COMMENT '参数名',
    data_type     INT COMMENT '参数类型 1 Integer 2 Long 3 Double 4 String 5 Date 6 Array 7 Object',
    source_type   INT COMMENT '源类型类型 1 请求 2 响应',
    parm_title    varchar(128) COMMENT '标题',
    parm_describe varchar(4096) COMMENT '描述',
    parm_sort     INT COMMENT '排序',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口参数表';

CREATE TABLE IF NOT EXISTS doc_api_parm_constraint
(
    id                  BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    parm_id             BIGINT COMMENT '参数ID',
    constraint_type     INT COMMENT '约束类型',
    constraint_value    BIGINT COMMENT '约束值',
    constraint_describe VARCHAR(4096) NOT NULL COMMENT '描述',
    constraint_sort     INT           NOT NULL COMMENT '排序',
    create_user         VARCHAR(64) COMMENT '创建者',
    create_time         DATETIME COMMENT '创建时间',
    update_user         VARCHAR(64) COMMENT '更新者',
    update_time         DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '参数约束表';

CREATE TABLE IF NOT EXISTS doc_api_parm_constraint_delete
(
    id                  BIGINT COMMENT '唯一标识符',
    parm_id             BIGINT COMMENT '参数ID',
    constraint_type     INT COMMENT '约束类型',
    constraint_value    BIGINT COMMENT '约束值',
    constraint_describe VARCHAR(4096) COMMENT '描述',
    constraint_sort     INT COMMENT '排序',
    create_user         VARCHAR(64) COMMENT '创建者',
    create_time         DATETIME COMMENT '创建时间',
    update_user         VARCHAR(64) COMMENT '更新者',
    update_time         DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '参数约束表';

CREATE TABLE IF NOT EXISTS doc_api_parm_example
(
    id            BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    parm_id       BIGINT        NOT NULL COMMENT '参数ID',
    value_format  INT           NOT NULL COMMENT '格式 1 text 2 json 3 xml',
    example_value VARCHAR(4096) NOT NULL COMMENT '示例值',
    example_sort  INT           NOT NULL COMMENT '排序',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口参数示例表';

CREATE TABLE IF NOT EXISTS doc_api_parm_example_delete
(
    id            BIGINT COMMENT '唯一标识符',
    parm_id       BIGINT COMMENT '参数ID',
    value_format  INT COMMENT '格式 1 text 2 json 3 xml',
    example_value VARCHAR(4096) COMMENT '示例值',
    example_sort  INT COMMENT '排序',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口参数示例表';

CREATE TABLE IF NOT EXISTS doc_api_body
(
    id            BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    api_id        BIGINT COMMENT '接口文档ID',
    body_title    VARCHAR(128) COMMENT '标题',
    body_describe VARCHAR(4096) COMMENT '内容描述',
    body_sort     INT NOT NULL COMMENT '排序',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口文档请求响应body表';

CREATE TABLE IF NOT EXISTS doc_api_body_delete
(
    id            BIGINT COMMENT '唯一标识符',
    api_id        BIGINT COMMENT '接口文档ID',
    body_title    VARCHAR(128) COMMENT '标题',
    body_describe VARCHAR(4096) COMMENT '内容描述',
    body_sort     INT COMMENT '排序',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口文档请求响应body表';

CREATE TABLE IF NOT EXISTS doc_api_body_example
(
    id            BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    body_id       BIGINT NOT NULL COMMENT 'Body ID',
    value_format  INT    NOT NULL COMMENT '格式 1 text 2 json 3 xml',
    example_value TEXT   NOT NULL COMMENT '示例值',
    source_type   INT    NOT NULL COMMENT 'body类型  1 请求 2 响应',
    example_sort  INT    NOT NULL COMMENT '排序',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口Body示例表';

CREATE TABLE IF NOT EXISTS doc_api_body_example_delete
(
    id            BIGINT COMMENT '唯一标识符',
    body_id       BIGINT COMMENT 'Body ID',
    value_format  INT COMMENT '格式 1 text 2 json 3 xml',
    example_value TEXT COMMENT '示例值',
    source_type   INT COMMENT 'body类型  1 请求 2 响应',
    example_sort  INT COMMENT '排序',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口Body示例表';

CREATE TABLE IF NOT EXISTS doc_api_response_code
(
    id            BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    api_id        BIGINT COMMENT '接口文档ID 为null是代表为全局通用返回值',
    code          INT COMMENT 'code值',
    code_describe VARCHAR(4096) COMMENT '描述',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口Body示例表';

CREATE TABLE IF NOT EXISTS doc_api_response_code_delete
(
    id            BIGINT COMMENT '唯一标识符',
    api_id        BIGINT COMMENT '接口文档ID 为null是代表为全局通用返回值',
    code          INT COMMENT 'code值',
    code_describe VARCHAR(4096) COMMENT '描述',
    create_user   VARCHAR(64) COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_user   VARCHAR(64) COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '接口Body示例表';

CREATE TABLE IF NOT EXISTS doc_const
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    title       VARCHAR(128)  NOT NULL COMMENT '标题',
    class_name  VARCHAR(1024) NOT NULL COMMENT '类名',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '常量表';

CREATE TABLE IF NOT EXISTS doc_const_delete
(
    id          BIGINT COMMENT '唯一标识符',
    title       VARCHAR(128) COMMENT '标题',
    class_name  VARCHAR(1024) NOT NULL COMMENT '类名',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '常量表';

CREATE TABLE IF NOT EXISTS doc_const_value
(
    id             BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    const_id       BIGINT       NOT NULL COMMENT '常量ID',
    title          VARCHAR(128) NOT NULL COMMENT '标题',
    constant_value VARCHAR(128) NOT NULL COMMENT '常量值',
    create_user    VARCHAR(64) COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_user    VARCHAR(64) COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '常量值表';

CREATE TABLE IF NOT EXISTS doc_const_value_delete
(
    id             BIGINT COMMENT '唯一标识符',
    const_id       BIGINT COMMENT '常量ID',
    title          VARCHAR(128) COMMENT '标题',
    constant_value VARCHAR(128) COMMENT '常量值',
    create_user    VARCHAR(64) COMMENT '创建者',
    create_time    DATETIME COMMENT '创建时间',
    update_user    VARCHAR(64) COMMENT '更新者',
    update_time    DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '常量值表';

CREATE TABLE IF NOT EXISTS doc_api_parm_const
(
    id          BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    parm_id     BIGINT NOT NULL COMMENT '参数ID',
    const_id    BIGINT NOT NULL COMMENT '常量ID',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '参数-常量-关联表';

CREATE TABLE IF NOT EXISTS doc_api_parm_const_delete
(
    id          BIGINT COMMENT '唯一标识符',
    parm_id     BIGINT COMMENT '参数ID',
    const_id    BIGINT COMMENT '常量ID',
    create_user VARCHAR(64) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_user VARCHAR(64) COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '参数-常量-关联表';


-- ------ 分割线 ---------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS sys_organization
(
    id                BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    organization_name VARCHAR(128) UNIQUE NOT NULL COMMENT '名称',
    father_id         BIGINT COMMENT '父节点',
    create_user       VARCHAR(64) COMMENT '创建者',
    create_time       DATETIME COMMENT '创建时间',
    update_user       VARCHAR(64) COMMENT '更新者',
    update_time       DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '组织机构表';

CREATE TABLE IF NOT EXISTS sys_organization_delete
(
    id                BIGINT COMMENT '唯一标识符',
    organization_name VARCHAR(128) COMMENT '名称',
    father_id         BIGINT COMMENT '父节点',
    create_user       VARCHAR(64) COMMENT '创建者',
    create_time       DATETIME COMMENT '创建时间',
    update_user       VARCHAR(64) COMMENT '更新者',
    update_time       DATETIME COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '组织机构表';

-- 代码生成模块

CREATE TABLE IF NOT EXISTS generator_table
(
    id           BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    table_name   VARCHAR(256) COMMENT '表名',
    table_remark VARCHAR(1024) COMMENT '表备注',
    create_user  VARCHAR(64) COMMENT '创建者',
    create_time  datetime COMMENT '创建时间',
    update_user  VARCHAR(64) COMMENT '更新者',
    update_time  datetime COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '数据库表信息';

CREATE TABLE IF NOT EXISTS generator_column
(
    id           BIGINT PRIMARY KEY auto_increment COMMENT '唯一标识符',
    table_id     BIGINT NOT NULL COMMENT '表ID',
    column_name  VARCHAR(32) COMMENT '列名',
    field_type   VARCHAR(32) COMMENT '字段类型',
    field_length BIGINT COMMENT '字段长度',
    field_remark VARCHAR(1024) COMMENT '字段备注',
    create_user  VARCHAR(64) COMMENT '创建者',
    create_time  datetime COMMENT '创建时间',
    update_user  VARCHAR(64) COMMENT '更新者',
    update_time  datetime COMMENT '更新时间'
) charset utf8mb4
  COLLATE utf8mb4_unicode_ci
  ENGINE = INNODB COMMENT '数据库列信息';

