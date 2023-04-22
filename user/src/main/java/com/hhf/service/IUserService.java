package com.hhf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhf.Dto.UserDto;
import com.hhf.entity.User;
import com.hhf.entity.UserInfo;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author hhf
 * @since 2023-03-31
 */

public interface IUserService extends IService<User> {

    public boolean saveUserAndUserInfo(User user, UserInfo userInfo);

    public List<UserDto> findAllUserAndUserInfo(Integer pageNum, Integer PageSize, String keyWord,Integer role);

    public UserDto getUserDtoByUserId(Long id);

    boolean remove(Long id);

}
