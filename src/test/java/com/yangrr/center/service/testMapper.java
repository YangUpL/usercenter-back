package com.yangrr.center.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangrr.center.mapper.UserMapper;
import com.yangrr.center.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @PATH com.yangrr.center.service.testMapper
 * @Author YangRR
 * @CreateData 2024-07-21 14:02
 * @Description: 测试mapper
 */
@SpringBootTest
public class testMapper {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void test01(){
        List<User> userList = userMapper.selectList(new QueryWrapper<>());

        System.out.println(userList);
    }



}
