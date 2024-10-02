package com.yangrr.center.controller;


import com.yangrr.center.common.BaseResponse;
import com.yangrr.center.common.ErrorCode;
import com.yangrr.center.common.ResultUtils;
import com.yangrr.center.exception.BusinessException;
import com.yangrr.center.model.domain.Schedule;
import com.yangrr.center.model.domain.User;
import com.yangrr.center.model.request.*;
import com.yangrr.center.service.ScheduleService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.yangrr.center.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 日程接口
 */
@CrossOrigin(value = {"http://localhost:8000/","http://8.130.183.113/","http://www.yangrr.love/"},allowCredentials = "true")
@RestController
@RequestMapping("schedule")
public class ScheduleController {


    @Resource
    private ScheduleService scheduleService;


    @PostMapping("getSchedule")
        public BaseResponse<List<Schedule>> getSchedule(@RequestBody Sorted sorted,HttpServletRequest request){

        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);

        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long id = user.getId();

        List<Schedule> schedules = scheduleService.getSchedule(id,sorted);


        return ResultUtils.success(schedules);
    }


    @GetMapping("deleteSchedule")
    public void deleteSchedule(Long id){
        if (id == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        scheduleService.deleteSchedule(id);
    }


    @PostMapping("saveSchedule")
    public BaseResponse<Integer> saveSchedule(@RequestBody Schedule schedule,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        schedule.setUid(user.getId());
        if (schedule.getId() == null || schedule.getUid() == null || schedule.getTitle() == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"日程名称必须填写");
        }

        Integer index = scheduleService.saveSchedule(schedule);

        return ResultUtils.success(index);
    }


}
