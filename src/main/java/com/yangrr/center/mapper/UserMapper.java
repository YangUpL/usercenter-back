package com.yangrr.center.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangrr.center.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangrr.center.model.request.UpdateRequest;
import com.yangrr.center.model.request.UpdateSelfRequest;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yangRR
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-05-08 09:19:04
* @Entity generator.domain.User
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {

    int updateUser(UpdateRequest updateRequest);

    int addUser(User user);

}




