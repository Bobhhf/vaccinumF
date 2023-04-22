package com.hhf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhf.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author hhf
 * @since 2023-03-31
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
