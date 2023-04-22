package com.hhf.Dto;

import com.hhf.entity.Appointment;
import lombok.Data;

/**
 * @Author:hhf
 * @date: 2023/4/13
 * @time:0:13
 */
@Data
public class AppointmentDto extends Appointment {
    private Long appointListId;
}
