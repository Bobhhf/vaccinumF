package com.hhf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhf.entity.Vaccinum;
import com.hhf.mapper.VaccinumMapper;
import com.hhf.service.IVaccinumService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 疫苗 服务实现类
 * </p>
 *
 * @author hhf
 * @since 2023-03-31
 */
@Service
public class VaccinumServiceImpl extends ServiceImpl<VaccinumMapper, Vaccinum> implements IVaccinumService {

}
