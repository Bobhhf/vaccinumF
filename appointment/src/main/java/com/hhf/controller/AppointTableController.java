package com.hhf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhf.Dto.AppointmentVaccinumDto;
import com.hhf.entity.AppointableList;
import com.hhf.entity.Vaccinum;
import com.hhf.entity.VaccinumType;
import com.hhf.openService.VaccinumOpenService;
import com.hhf.service.AppointableListService;
import com.hhf.service.IVaccinumService;
import com.hhf.service.IVaccinumTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:hhf
 * @date: 2023/4/9
 * @time:20:38
 */
@RestController
@RequestMapping("appoint")
@Slf4j
public class AppointTableController {

    @Autowired
    AppointableListService appointableListService;

    @Autowired
    IVaccinumService vaccinumService;

    @Autowired
    IVaccinumTypeService vaccinumTypeService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    VaccinumOpenService vaccinumOpenService;

    @RequestMapping("list")
    public String list(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = true, defaultValue = "1") Integer pageSize,
                       @RequestParam(required = false) String keyword) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<AppointableList> queryWrapper = new QueryWrapper<>();
        Page<AppointableList> page = appointableListService.page(new Page<>(pageNum, pageSize));
        page.getRecords().forEach(appointableList -> {
//            Vaccinum vaccinum = restTemplate.getForObject("http://vaccinumF-vaccinum/vaccinum/getOne/" + appointableList.getId(), Vaccinum.class);
            Vaccinum vaccinum = vaccinumOpenService.getOne(appointableList.getVaccinumId());
            appointableList.setVaccinum(vaccinum);
        });
        map.put("list", page.getRecords());
        map.put("total", page.getTotal());
        map.put("ok", true);
        return objectMapper.writeValueAsString(map);
    }

    @RequestMapping("listAppointment")
    public String listAppointment(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
                                  @RequestParam(required = true, defaultValue = "1") Integer pageSize,
                                  @RequestParam(required = false) String keyword) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<AppointableList> queryWrapper = new QueryWrapper<>();
//        queryWrapper.apply("rums>app_num");
        Page<AppointableList> page = appointableListService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<AppointmentVaccinumDto> appointmentVaccinumDtoList = new ArrayList<>();
        page.getRecords().forEach(appointableList -> {
            AppointmentVaccinumDto appointmentVaccinumDto = new AppointmentVaccinumDto();
            Vaccinum vaccinum = vaccinumService.getById(appointableList.getVaccinumId());
            VaccinumType vaccinumType = vaccinumTypeService.getById(vaccinum.getType());
            BeanUtils.copyProperties(vaccinum, appointmentVaccinumDto);
            appointmentVaccinumDto.setTypeName(vaccinumType.getName());
            appointmentVaccinumDto.setRums(appointableList.getRums());
            appointmentVaccinumDto.setAppointListId(appointableList.getId());
            appointmentVaccinumDto.setAppDate(appointableList.getAppDate());
            appointmentVaccinumDto.setAddress(appointableList.getAddress());
            appointmentVaccinumDtoList.add(appointmentVaccinumDto);
        });
        map.put("list", appointmentVaccinumDtoList);
        map.put("total", page.getTotal());
        map.put("ok", true);
        return objectMapper.writeValueAsString(map);
    }


    @PostMapping("save")
    public String save(AppointableList appointableList) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean save = appointableListService.save(appointableList);
        if (save){
            map.put("ok", true);
            return objectMapper.writeValueAsString(map);
        }else {
            map.put("ok", false);
            map.put("message", "添加失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @PostMapping("update")
    public String update(AppointableList appointableList) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean b = appointableListService.updateById(appointableList);
        if (b) {
            map.put("ok", true);
            map.put("message", "修改成功");
            return objectMapper.writeValueAsString(map);
        }else {
            map.put("ok", false);
            map.put("message", "修改失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @PostMapping("delete")
    public String delete(Long id) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean b = appointableListService.removeById(id);
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
