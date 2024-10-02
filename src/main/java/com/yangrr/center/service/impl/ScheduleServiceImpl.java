package com.yangrr.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yangrr.center.common.ErrorCode;
import com.yangrr.center.exception.BusinessException;
import com.yangrr.center.mapper.ScheduleMapper;
import com.yangrr.center.model.domain.Schedule;
import com.yangrr.center.model.domain.User;
import com.yangrr.center.model.request.ScheduleListRequest;
import com.yangrr.center.model.request.Sorted;
import com.yangrr.center.service.ScheduleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 31841
* @description 针对表【schedule(日程表)】的数据库操作Service实现
* @createDate 2024-08-18 09:52:06
*/
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule>
    implements ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public List<Schedule> getSchedule(Long id, Sorted sorted) {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uId", id);

        if(sorted.getSortField() != null){
            String sortOrder = sorted.getSortOrder();
            if(sortOrder.equals("ascend")){
                queryWrapper.orderByAsc(sorted.getSortField());
            }else {
                queryWrapper.orderByDesc(sorted.getSortField());
            }
        }

        List<Schedule> schedules = scheduleMapper.selectList(queryWrapper);

//        System.out.println(schedules);
        return schedules.stream().map(schedule -> {
            Schedule safetySchedule = this.getSafetySchedule(schedule);
            return safetySchedule;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleMapper.delete(
                new QueryWrapper<Schedule>().eq("id",id));
    }

    @Override
    public Integer saveSchedule(Schedule schedule) {
        Schedule tempSchedule = scheduleMapper.selectById(schedule.getId());

        Integer index = null;
        if (tempSchedule == null) {
            index = scheduleMapper.insert(schedule);
        }else {
            index = scheduleMapper.updateById(schedule);
        }


        return index;
    }


    public Schedule getSafetySchedule(Schedule originSchedule){

        if(originSchedule == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Schedule safetySchedule = new Schedule();
        safetySchedule.setId(originSchedule.getId());
        safetySchedule.setUid(originSchedule.getUid());
        safetySchedule.setTitle(originSchedule.getTitle());
        safetySchedule.setDescription(originSchedule.getDescription());
        safetySchedule.setState(originSchedule.getState());
        safetySchedule.setDeadline(originSchedule.getDeadline());

        return safetySchedule;
    }
}




