package com.hhf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhf.Dto.AppointmentDto;
import com.hhf.Dto.AppointmentVaccinumDto;
import com.hhf.entity.AppointableList;
import com.hhf.entity.Appointment;
import com.hhf.mapper.AppointableListMapper;
import com.hhf.mapper.AppointmentMapper;
import com.hhf.service.IAppointmentService;
import com.hhf.service.IVaccinumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 疫苗预约 服务实现类
 * </p>
 *
 * @author hhf
 * @since 2023-03-31
 */
@Service
@Slf4j
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {

    @Autowired
    IVaccinumService vaccinumService;

    @Autowired
    AppointableListMapper appointableListMapper;

    @Override
    public List<AppointmentVaccinumDto> findDtoList(){
        return null;
    }

    @Override
    @Transactional
    public boolean updateWithRecord(AppointmentDto appointmentDto) {
        AppointableList appointableList = appointableListMapper.selectById(appointmentDto.getAppointListId());
        appointableList.setRums(appointableList.getRums()-1);
        appointableList.setAppNum(appointableList.getAppNum()+1);
        int b = appointableListMapper.updateById(appointableList);
        Appointment appointment = new Appointment();
        appointment.setUserId(appointmentDto.getUserId());
        BeanUtils.copyProperties(appointableList,appointment,"id","post_time");
        log.info(appointment.toString());
        boolean save = this.save(appointment);
        if (b==1&&save){
            return true;//只有添加成功直接返回
        }
        return false;
    }


}
