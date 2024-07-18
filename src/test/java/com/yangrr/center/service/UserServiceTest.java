package com.yangrr.center.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangrr.center.mapper.UserMapper;
import com.yangrr.center.model.domain.User;
import jakarta.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
public class UserServiceTest {

    @Resource
    public UserMapper userMapper;

    @Resource
    public UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("yangRR");
        user.setUserAccount("yangRR123");
        user.setAvatarUrl("https://portrait.gitee.com/uploads/avatars/user/3031/9094632_DXdaxia_1620607641.png!avatar30");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("111111");
        user.setEmail("222222@qq.com");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        int result = userMapper.insert(user);

        System.out.println(user.getId());
        Assertions.assertEquals(1, result);


    }


    @Test
    public void testDelUser() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",4);
        int delete = userMapper.delete(userQueryWrapper);
    }

    @Test
    void userRegister() {

        String userAccount = "yang";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        Long planetCode = 1L;


        Long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);

        Assertions.assertEquals(-1,result);
    }
}