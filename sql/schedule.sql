-- auto-generated definition
create table schedule
(
    id          bigint auto_increment comment '日程主键'
        primary key,
    title       varchar(250)                       not null comment '日程标题',
    description varchar(520)                       null comment '日程描述',
    state       smallint default 0                 not null comment '完成状态',
    deadline    datetime default CURRENT_TIMESTAMP not null comment '截止日期',
    uId         bigint                             not null comment '用户id',
    isDelete    smallint default 0                 not null comment '逻辑删除',
    constraint uId
        foreign key (uId) references user (id)
)
    comment '日程表';