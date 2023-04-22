package com.hhf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhf.Dto.AppointmentDto;
import com.hhf.Dto.AppointmentVaccinumDto;
import com.hhf.entity.Appointment;

import java.util.List;

/**
 * <p>
 * 疫苗预约 服务类
 * </p>
 *
 * @author hhf
 * @since 2023-03-31
 */
public interface IAppointmentService extends IService<Appointment> {

    public List<AppointmentVaccinumDto> findDtoList();

    boolean updateWithRecord(AppointmentDto appointmentDto);

}
