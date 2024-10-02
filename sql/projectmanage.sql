-- auto-generated definition
create table projectmanager
(
    id              bigint auto_increment comment '主键'
        primary key,
    projectName     varchar(520)                       not null comment '项目名称',
    projectStatus   smallint default 0                 not null comment '项目状态 0-列入计划 1- 开发中 2-运行中 3-运行异常',
    projectPriority smallint default 4                 null comment '项目优先级 对应状态 运行异常 0 >开发中1-4>列入计划1-4>运行中5',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        smallint default 0                 not null comment '逻辑删除字段 0-正常 1-删除',
    puid            bigint                             not null comment '当前用户id',
    constraint puid
        foreign key (puid) references user (id)
)
    comment '项目管理';

