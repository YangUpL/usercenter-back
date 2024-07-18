package com.yangrr.center.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SearchRequest implements Serializable {

    private Long id;

    private String username;

    private String userAccount;

    private Integer gender;

    private String phone;

    private Integer userStatus;

    private Integer userRole;

    private String planetCode;

    private Integer current;

    private Integer pageSize;

    private String sortField;

    private String sortOrder;

}