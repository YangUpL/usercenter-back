package com.yangrr.center.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * @PATH com.yangrr.center.model.request.ScheduleListRequest
 * @Author YangRR
 * @CreateData 2024-08-18 13:59
 * @Description:
 */
@Data
public class ScheduleListRequest {
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
    private Date deadline;

    /**
     * 用户id
     */
    private Long uId;
}
