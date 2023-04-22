package com.hhf.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhf.entity.Vaccinum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author:hhf
 * @date: 2023/4/10
 * @time:8:43
 */
@Data
public class AppointmentVaccinumDto extends Vaccinum {

    private Long appointListId;

    private String typeName;

    /**
     * 可接种数量
     */
    private Integer rums;

    /**
     * 接种地点
     */
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appDate;


}
