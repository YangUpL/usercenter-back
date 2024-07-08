package com.yangrr.center.service;

import com.yangrr.center.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author yangRR
*
 * 用户服务接口
*/
public interface UserService extends IService<User> {

    //session的key



    /**
     * 用户注册
     *
     * @param userAccount   账户
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @param planetCode
     * @return 影响行数
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);


    /**
     * 用户登录
     * @param userAccount  账户
     * @param userPassword 密码
     * @return  脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 管理员查用户
     * @param username  用户名(前端传参则根据username模糊查询 否则查全表)
     * @return  用户集合
     */
    List<User> searchUsers(String username);

    /**
     * 管理员删用户
     * @param id  用户id
     * @return 是否删除
     */
    boolean deleteUser(Long id);


    /**
     * 用户脱敏
     * @param originUser  脱敏前的用户
     * @return   脱敏后的用户
     */
    User getSafetyUser(User originUser);

    void userLogout(HttpServletRequest request);
}
