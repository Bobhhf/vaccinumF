package com.hhf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhf.Dto.AppointmentDto;
import com.hhf.entity.AppointableList;
import com.hhf.entity.Appointment;
import com.hhf.entity.Vaccinum;
import com.hhf.openService.VaccinumOpenService;
import com.hhf.service.AppointableListService;
import com.hhf.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 疫苗预约 前端控制器
 * </p>
 *
 * @author
 * @since 2023-03-31
 */
@RestController
@RequestMapping("appointment")
public class AppointmentController {

    @Autowired
    IAppointmentService appointmentService;

    @Autowired
    AppointableListService appointableListService;

    @Autowired
    VaccinumOpenService vaccinumOpenService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("save")
    public String save(AppointmentDto appointmentDto) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        AppointableList appointableList = appointableListService.getById(appointmentDto.getAppointListId());
        if (appointableList.getRums()<=0){
            map.put("ok", false);
            map.put("message", "可预约数量不足，预约失败");
            return objectMapper.writeValueAsString(map);
        }
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",appointmentDto.getUserId()).eq("vaccinum_id",appointableList.getVaccinumId());
        List<Appointment> list = appointmentService.list(queryWrapper);
        if (list!=null&&list.size()>0){
            map.put("ok", false);
            map.put("message", "预约失败不可以重复预约");
            return objectMapper.writeValueAsString(map);
        }
        boolean flag= appointmentService.updateWithRecord(appointmentDto);
        if (!flag){
            map.put("ok", false);
            map.put("message", "预约失败");
            return objectMapper.writeValueAsString(map);
        }
        map.put("ok", true);
        map.put("message", "预约成功");
        return objectMapper.writeValueAsString(map);
    }

    @RequestMapping("list")
    public String list(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = true, defaultValue = "1") Integer pageSize,
                       @RequestParam(required = false) String keyword) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        Page<Appointment> page = appointmentService.page(new Page<>(pageNum, pageSize));
//        page.getRecords().forEach(appointment -> {
//            Vaccinum vaccinum = restTemplate.getForObject("http://localhost:8088/vaccinum/getOne/" + appointment.getId(), Vaccinum.class);
//        });
        page.getRecords().forEach(appointment -> {
//            Vaccinum vaccinum = restTemplate.getForObject("http://vaccinumF-vaccinum/vaccinum/getOne/" + appointableList.getId(), Vaccinum.class);
            Vaccinum vaccinum = vaccinumOpenService.getOne(appointment.getVaccinumId());
            appointment.setVaccinumName(vaccinum.getName());
        });
        map.put("list", page.getRecords());
        map.put("total", page.getTotal());
        map.put("ok", true);

        return objectMapper.writeValueAsString(map);
    }

    @RequestMapping("listAp")
    public String listAp(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = true, defaultValue = "1") Integer pageSize,
                       @RequestParam(required = false) String keyword) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        Page<Appointment> page = appointmentService.page(new Page<>(pageNum, pageSize));
        map.put("list", page.getRecords());
        map.put("total", page.getTotal());
        map.put("ok", true);

        return objectMapper.writeValueAsString(map);
    }




    @GetMapping("update")
    public String update(Appointment appointment) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        if (appointment.getStatus()==1){
            map.put("ok", false);
            map.put("message", "接种失败,请不要重复接种");
            return objectMapper.writeValueAsString(map);
        }
        boolean b = appointmentService.updateById(appointment);
        if (b) {
            map.put("ok", true);
            map.put("message", "接种成功");
            return objectMapper.writeValueAsString(map);
        }else {
            map.put("ok", false);
            map.put("message", "接种失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @PostMapping("delete")
    public String delete(Long id) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean b = appointmentService.removeById(id);
        if (b) {
            map.put("ok", true);
            map.put("message", "删除成功");
            return objectMapper.writeValueAsString(map);
        }else {
            map.put("ok", false);
            map.put("message", "删除失败");
            return objectMapper.writeValueAsString(map);
        }
    }

}
