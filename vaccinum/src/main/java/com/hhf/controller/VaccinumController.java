package com.hhf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhf.entity.Vaccinum;
import com.hhf.entity.VaccinumType;
import com.hhf.service.IVaccinumService;
import com.hhf.service.IVaccinumTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 疫苗 前端控制器
 * </p>
 *
 * @author
 * @since 2023-03-31
 */
@Slf4j
@RestController
@RequestMapping("vaccinum")
public class VaccinumController {
    @Autowired
    IVaccinumTypeService vaccinumTypeService;

    @Autowired
    IVaccinumService vaccinumService;


    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("query")
    public String listVaccinum(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
                               @RequestParam(required = true, defaultValue = "1") Integer pageSize,
                               @RequestParam(required = false) String keyword) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        QueryWrapper<Vaccinum> queryWrapper = new QueryWrapper<Vaccinum>();
        queryWrapper.like(keyword != null && !keyword.equals(""), "name", keyword);
        Page<Vaccinum> page = vaccinumService.page(new Page<>(pageNum, pageSize), queryWrapper);
        page.getRecords().forEach(en -> {
            VaccinumType type = vaccinumTypeService.getById(en.getType());
            en.setTypeName(type.getName());
        });
        map.put("list", page.getRecords());
        map.put("total", page.getTotal());
        return objectMapper.writeValueAsString(map);
    }

    @RequestMapping("save")
    public String save(Vaccinum vaccinum) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean save = vaccinumService.save(vaccinum);
        if (save) {
            map.put("ok", true);
            return objectMapper.writeValueAsString(map);
        } else {
            map.put("ok", false);
            map.put("message", "添加失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @PostMapping("update")
    public String uodate(Vaccinum vaccinum) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean b = vaccinumService.updateById(vaccinum);
        if (b) {
            map.put("ok", true);
            return objectMapper.writeValueAsString(map);
        } else {
            map.put("ok", false);
            map.put("message", "更新失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @GetMapping("getOne/{id}")
    public Vaccinum getone(@PathVariable("id") Long id){
        return vaccinumService.getById(id);
    }

    @GetMapping("delete")
    public String delete(Integer id) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean b = vaccinumService.removeById(id);
        if (b) {
            map.put("ok", true);
            return objectMapper.writeValueAsString(map);
        } else {
            map.put("ok", false);
            map.put("message", "删除失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @RequestMapping("listAll")
    public String listAllVaccinum() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        List<Vaccinum> list = vaccinumService.list();
        map.put("options", list);
        return objectMapper.writeValueAsString(map);
    }


}
