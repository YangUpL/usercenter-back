package com.yangrr.center.model.request;

import lombok.Data;

import java.util.Date;

/**
 * @PATH com.yangrr.center.model.request.UpdateRequest
 * @Author YangRR
 * @CreateData 2024-07-17 20:03
 * @Description:
 */

@Data
public class UpdateRequest {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0 - 正常
     */
    private Integer userStatus;


    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 用户权限 0- 普通用户 1 - 管理员
     */
    private Integer userRole;
}
