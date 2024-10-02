package com.yangrr.center.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 日程表
 * @TableName schedule
 */
@TableName(value ="schedule")
@Data
public class Schedule implements Serializable {
    /**
     * 日程主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 日程标题
     */
    private String title;

    /**
     * 日程描述
     */
    private String description;

    /**
     * 完成状态
     */
    private Integer state;

    /**
     * 截止日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date deadline;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}