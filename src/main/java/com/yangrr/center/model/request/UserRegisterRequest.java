package com.yangrr.center.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author yangRR
 */

@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = 5077348102061049589L;


    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
