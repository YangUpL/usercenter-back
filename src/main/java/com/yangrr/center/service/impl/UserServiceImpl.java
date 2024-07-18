package com.yangrr.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yangrr.center.common.ErrorCode;
import com.yangrr.center.exception.BusinessException;
import com.yangrr.center.model.domain.User;
import com.yangrr.center.model.request.SearchRequest;
import com.yangrr.center.model.request.UpdateRequest;
import com.yangrr.center.service.UserService;
import com.yangrr.center.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yangrr.center.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author yangRR
 * 用户接口实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    //加密的密钥
    private static final String SALT = "yangRR";


    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword, Long planetCode) {

        //校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名过短");
        }

        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 用户编号长度1~5位
        if (planetCode.toString().length() > 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号太长");
        }



        // 账户不包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        // 如果包含非法字符，则返回
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码不能包含特殊字符");
        }


        // 密码和校验密码不相等
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
//        long count = this.count(queryWrapper);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        }


        // 编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        // count大于0，说明有重复了
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"编号重复");
        }



            //加密密码（密码千万不要直接以明文存储到数据库中）
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT +
                userPassword).getBytes(StandardCharsets.UTF_8));


        // 3. 向数据库插入用户数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);

        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"系统异常");
        }


        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号过短");
        }

        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码过短");
        }


        // 账户不包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        // 如果包含非法字符，则返回
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码不能包含特殊字符");
        }


        //加密密码（密码千万不要直接以明文存储到数据库中）
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT +
                userPassword).getBytes(StandardCharsets.UTF_8));
        //查找数据库中是否存在此用户
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);

        if(user == null){
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码错误");
        }


        User safetyUser = this.getSafetyUser(user);


        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;

    }



    @Override
    public List<User> searchUsers(SearchRequest searchRequest) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNoneBlank(searchRequest.getUsername())){
            queryWrapper.like("username",searchRequest.getUsername());
        }

        if (StringUtils.isNoneBlank(searchRequest.getSortField())){
            String sortOrder = searchRequest.getSortOrder();
            if(sortOrder.equals("ascend")){

                queryWrapper.orderBy(true,true,searchRequest.getSortField());
            }else {
                queryWrapper.orderBy(true,false,searchRequest.getSortField());

            }
        }

        List<User> userList = this.list(queryWrapper);
        return userList.stream().map(user -> {
            User safetyUser = this.getSafetyUser(user);
            return safetyUser;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean deleteUser(Long id) {
        return this.removeById(id);
    }


    @Override
    public User getSafetyUser(User originUser){

        if(originUser == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        safetyUser.setPlanetCode(originUser.getPlanetCode());

        return safetyUser;
    }

    @Override
    public void userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
    }

    @Override
    public boolean updateUser(UpdateRequest updateRequest) {
        updateRequest.setUpdateTime(new Date());

        userMapper.updateUser(updateRequest);
        return true;
    }

    @Override
    public boolean addUser(User user) {

        Date date = new Date();
        user.setUserPassword("12345678");
        user.setCreateTime(date);
        user.setUpdateTime(date);
//        System.err.println(userMapper.selectCount(new QueryWrapper<>()));
        user.setPlanetCode(userMapper.selectCount(new QueryWrapper<>()) + 1);
        userMapper.addUser(user);

        return true;
    }
}




