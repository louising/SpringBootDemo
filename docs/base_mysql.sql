create table T_DUMMY (
    scheme_id int NOT NULL auto_increment PRIMARY KEY,
    scheme_name VARCHAR(150) comment 'Dummy 示例表',
    project_code VARCHAR(10) comment 'Project Code',
    
    create_by VARCHAR(50),
    create_time DATETIME,
    update_by VARCHAR(50),
    update_time DATETIME
) comment = 'Dummy table';

create table T_ROLE(
    role_id int NOT NULL auto_increment PRIMARY KEY,
    role_name VARCHAR(50),
    
    create_by VARCHAR(30),
    create_time DATETIME,
    update_by VARCHAR(30),
    update_time DATETIME
);

create table T_USER (
    w3_account VARCHAR(20) not null PRIMARY KEY,
    user_name_cn VARCHAR(30),
    user_name_en VARCHAR(30),
    email VARCHAR(50)
);

create table T_USER_ROLE (
    user_role_id int NOT NULL auto_increment PRIMARY KEY,
    role_id int,
    w3_account VARCHAR(20),

    create_by VARCHAR(30),
    create_time DATETIME,
    update_by VARCHAR(30),
    update_time DATETIME
);

create table T_VISIT (
    visit_id int NOT NULL auto_increment PRIMARY KEY,
    
    request_uri VARCHAR(500),
    servlet_path VARCHAR(100),
    method VARCHAR(20),
    content_type VARCHAR(200),
    context_path VARCHAR(100),
    local_address VARCHAR(100),
    local_name VARCHAR(100),
    local_port int,
    remote_address VARCHAR(100),
    remote_host VARCHAR(50),
    remote_port int,
    w3_account VARCHAR(30),
    name_zh VARCHAR(50),
    name_en VARCHAR(50),
    access_date DATETIME
);

CREATE TABLE T_ATTACHMENT (
    attach_id INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    
    scheme_id INT(10) NULL DEFAULT NULL COMMENT 'FK',
    new_file_name VARCHAR(50) NULL DEFAULT NULL COMMENT '产生的文件名',
    original_file_name VARCHAR(300) NULL DEFAULT NULL COMMENT '原始文件名',
    
    create_by VARCHAR(50) NULL DEFAULT NULL,
    create_time DATETIME NULL DEFAULT NULL,
    update_by VARCHAR(50) NULL DEFAULT NULL,
    update_time DATETIME NULL DEFAULT NULL
);

