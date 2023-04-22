package com.hhf.openService;

import com.hhf.entity.Vaccinum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author:hhf
 * @date: 2023/4/12
 * @time:16:48
 */
@FeignClient("vaccinumF-vaccinum")
public interface VaccinumOpenService {

    @GetMapping("/vaccinum/getOne/{id}")
    public Vaccinum getOne(@PathVariable("id")Long id);

}
