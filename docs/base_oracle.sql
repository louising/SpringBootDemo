create table T_DUMMY (
    scheme_id NUMBER(10) not null,
    scheme_name VARCHAR2(150),
    project_code VARCHAR2(10),
    
    create_by VARCHAR2(50),
    create_time DATE,
    update_by VARCHAR2(50),
    update_time DATE
);
alter table T_DUMMY add constraint PK_T_DUMMY primary key (SCHEME_ID);
comment on table T_DUMMY is 'Dummy信息表';
comment on column T_DUMMY.scheme_id is 'DummyID,主键自增';
comment on column T_DUMMY.project_code is '项目编码';
comment on column T_DUMMY.scheme_name is 'Dummy名称';
comment on column T_DUMMY.create_by is '创建人';
comment on column T_DUMMY.create_time is '创建时间';
comment on column T_DUMMY.update_by is '更新人';
comment on column T_DUMMY.update_time is '更新时间';

create table T_ROLE(
    role_id NUMBER(10) not null,
    role_name VARCHAR2(50),
    
    create_by VARCHAR2(30),
    create_time DATE,
    update_by VARCHAR2(30),
    update_time DATE
);
alter table T_ROLE add constraint PK_T_ROLE primary key (ROLE_ID);
comment on table T_ROLE is '角色';
comment on column T_ROLE.role_id is 'PK (1:文档审核员 2:系统管理员)';
comment on column T_ROLE.role_name is '角色名称.';

create table T_USER (
    w3_account VARCHAR2(20) not null,
    user_name_cn VARCHAR2(30),
    user_name_en VARCHAR2(30),
    email VARCHAR2(50)
);
alter table T_USER add constraint PK_T_USER primary key (W3_ACCOUNT);

create table T_USER_ROLE (
    user_role_id NUMBER(10) not null,
    role_id NUMBER(10),
    w3_account VARCHAR2(20),
    
    create_by VARCHAR2(30),
    create_time DATE,
    update_by VARCHAR2(30),
    update_time DATE
);
comment on table T_USER_ROLE is '用户-角色关系';
comment on column T_USER_ROLE.user_role_id is 'PK';
comment on column T_USER_ROLE.role_id is '角色ID(FK)';
comment on column T_USER_ROLE.w3_account is 'W3账号';
alter table T_USER_ROLE add constraint PK_T_USER_ROLE primary key (USER_ROLE_ID);

create table T_VISIT (
    visit_id NUMBER not null,
    request_uri VARCHAR2(500),
    servlet_path VARCHAR2(100),
    method VARCHAR2(20),
    content_type VARCHAR2(200),
    context_path VARCHAR2(100),
    local_address VARCHAR2(100),
    local_name VARCHAR2(100),
    local_port NUMBER,
    remote_address VARCHAR2(100),
    remote_host VARCHAR2(50),
    remote_port NUMBER,
    w3_account VARCHAR2(30),
    name_zh VARCHAR2(50),
    name_en VARCHAR2(50),
    access_date DATE
);
alter table T_VISIT add constraint PK_VISIT primary key (VISIT_ID);
