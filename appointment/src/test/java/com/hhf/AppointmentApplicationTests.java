package com.hhf;

import com.hhf.Dto.AppointmentDto;
import com.hhf.entity.Appointment;
import com.hhf.service.IAppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppointmentApplicationTests {

    @Autowired
    IAppointmentService appointmentService;

    @Test
    void contextLoads() {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointListId(1L);
        appointmentDto.setUserId(1l);
        appointmentService.updateWithRecord(appointmentDto);
    }

}
