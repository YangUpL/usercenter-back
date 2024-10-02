package com.yangrr.center.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangrr.center.common.BaseResponse;
import com.yangrr.center.common.ErrorCode;
import com.yangrr.center.common.ResultUtils;
import com.yangrr.center.exception.BusinessException;
import com.yangrr.center.model.domain.User;
import com.yangrr.center.model.request.*;
import com.yangrr.center.service.ScheduleService;
import com.yangrr.center.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;

import static com.yangrr.center.constant.UserConstant.ADMIN_ROLE;
import static com.yangrr.center.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户接口
 */
@CrossOrigin(value = {"http://localhost:8000/","http://8.130.183.113/","http://www.yangrr.love/"},allowCredentials = "true")
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private ScheduleService scheduleService;

    @PostMapping("register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){

        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        Long planetCode = userService.count(new QueryWrapper<>()) + 1;

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        Long id = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);

        return ResultUtils.success(id);
    }


    @PostMapping("login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){

        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User user = userService.userLogin(userAccount, userPassword, request);

        return ResultUtils.success(user);

    }


    @GetMapping("current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        User currentUser = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        if(currentUser == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        Long userId = currentUser.getId();
        User user = userService.getById(userId);

        User safetyUser = userService.getSafetyUser(user);

        return ResultUtils.success(safetyUser);
    }

    @PostMapping("search")
    public BaseResponse<List<User>> searchUsers(@RequestBody SearchRequest searchRequest,HttpServletRequest request){

        if (!isAdmin(request)) {
            ArrayList<User> arrayList = new ArrayList<>();
            return ResultUtils.success(arrayList);
        }

        List<User> userList = userService.searchUsers(searchRequest);
        return ResultUtils.success(userList);
    }

    @PostMapping("delete")
    public boolean deleteUser(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request){

        Long id = deleteRequest.getId();
        if (!isAdmin(request) || id <= 0){
            return false;
        }

        System.out.println(id);
        return userService.deleteUser(id);
    }

    /**
     * 用户退出接口
     * @param request  请求
     * @return
     */
    @PostMapping("/logout")
    private void userLogout(HttpServletRequest request) {
        if(request == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        userService.userLogout(request);
    }

    @PostMapping("update")
    public BaseResponse<Boolean> updateUser(@RequestBody UpdateRequest updateRequest,HttpServletRequest request){

        if (updateRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        Boolean b = userService.updateUser(updateRequest);

        return ResultUtils.success(b);

    }


    @PostMapping("add")
    public BaseResponse<AddUserRequest> addUser(@RequestBody AddUserRequest addUserRequest,HttpServletRequest request){

        if (!isAdmin(request)) {
            return ResultUtils.error(ErrorCode.NO_AUTH,"无权限");
        }


        if (addUserRequest.getUserAccount() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账户必须存在");
        }

        userService.addUser(addUserRequest);
        return ResultUtils.success(addUserRequest);
    }


    @PostMapping("updateSelf")

    public BaseResponse<Boolean> updateSelf(@RequestBody UpdateSelfRequest updateSelfRequest,
                                         HttpServletRequest request) {
        if (updateSelfRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }

        updateSelfRequest.setId(user.getId());

        Boolean bool = userService.updateUserSelf(updateSelfRequest);

        return ResultUtils.success(bool);

    }



    private boolean isAdmin(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
