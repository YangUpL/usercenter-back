package com.yangrr.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangrr.center.model.domain.Schedule;
import com.yangrr.center.model.request.ScheduleListRequest;
import com.yangrr.center.model.request.Sorted;

import java.util.List;


/**
* @author 31841
* @description 针对表【schedule(日程表)】的数据库操作Service
* @createDate 2024-08-18 09:52:06
*/
public interface ScheduleService extends IService<Schedule> {

    List<Schedule> getSchedule(Long id, Sorted sorted);

    void deleteSchedule(Long id);

    Integer saveSchedule(Schedule schedule);
}
