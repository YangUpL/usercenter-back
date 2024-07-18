-- auto-generated definition
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    username     varchar(256)  default (rand())                                                                                       null comment '用户昵称',
    userAccount  varchar(256)                                                                                                         null comment '账号',
    avatarUrl    varchar(1024) default 'https://img1.baidu.com/it/u=1966616150,2146512490&fm=253&fmt=auto&app=138&f=JPEG?w=751&h=500' null comment '用户头像',
    gender       tinyint       default 0                                                                                              null comment '性别',
    userPassword varchar(512)                                                                                                         not null comment '密码',
    phone        varchar(128)                                                                                                         null comment '电话',
    email        varchar(512)                                                                                                         null comment '邮箱',
    userStatus   int           default 0                                                                                              not null comment '状态 0 - 正常',
    createTime   datetime      default CURRENT_TIMESTAMP                                                                              null comment '创建时间',
    updateTime   datetime      default CURRENT_TIMESTAMP                                                                              null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint       default 0                                                                                              not null comment '是否删除（逻辑删除）',
    userRole     int           default 0                                                                                              not null comment '用户权限 0- 普通用户 1 - 管理员',
    planetCode   bigint                                                                                                               null comment '星球编号'
);

