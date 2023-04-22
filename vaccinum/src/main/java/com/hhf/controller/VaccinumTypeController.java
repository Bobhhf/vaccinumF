package com.hhf.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhf.entity.VaccinumType;
import com.hhf.service.IVaccinumTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 疫苗类型 前端控制器
 * </p>
 *
 * @author hhf
 * @since 2023-03-31
 */
@Slf4j
@RestController
@RequestMapping("/vaccinum_type")
public class VaccinumTypeController {
    @Autowired
    IVaccinumTypeService vaccinumTypeService;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/query")
    public String listVaccinum(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
                               @RequestParam(required = true, defaultValue = "1") Integer pageSize,
                               @RequestParam(required = false) String keyword) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        Page<VaccinumType> page = vaccinumTypeService.page(new Page<>(pageNum, pageSize));
        log.info("info1-------" + pageNum);
        log.info("info2=======" + pageSize);
        map.put("list", page.getRecords());
        map.put("total", page.getTotal());
        return objectMapper.writeValueAsString(map);
    }

    @PostMapping("save")
    public String save(VaccinumType vaccinumType) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean save = vaccinumTypeService.save(vaccinumType);
        if (save) {
            map.put("ok", true);
            map.put("user", save);
            return objectMapper.writeValueAsString(map);
        } else {
            map.put("ok", false);
            map.put("message", "添加失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @GetMapping("listAll")
    public String listAll() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        List<VaccinumType> list = vaccinumTypeService.list();
        map.put("options", list);
        return objectMapper.writeValueAsString(map);
    }

    @PostMapping("update")
    public String update(VaccinumType vaccinumType) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean b = vaccinumTypeService.updateById(vaccinumType);
        if (b) {
            map.put("ok", true);
            return objectMapper.writeValueAsString(map);
        } else {
            map.put("ok", false);
            map.put("message", "更新失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @GetMapping("delete")
    public String delete(Integer id) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean b = vaccinumTypeService.removeById(id);
        if (b) {
            map.put("ok", true);
            return objectMapper.writeValueAsString(map);
        } else {
            map.put("ok", false);
            map.put("message", "删除失败");
            return objectMapper.writeValueAsString(map);
        }
    }
}
