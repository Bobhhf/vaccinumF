package com.hhf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhf.Dto.UserDto;
import com.hhf.entity.User;
import com.hhf.entity.UserInfo;
import com.hhf.mapper.UserInfoMapper;
import com.hhf.mapper.UserMapper;
import com.hhf.service.IUserInfoService;
import com.hhf.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author hhf
 * @since 2023-03-31
 */

@Service
public  class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    IUserInfoService userInfoService;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public boolean saveUserAndUserInfo(User user, UserInfo userInfo) {
        boolean save = this.save(user);
        userInfo.setUserId(user.getId());
        boolean save1 = userInfoService.save(userInfo);
        return save1&&save;
    }



    @Override
    public List<UserDto> findAllUserAndUserInfo(Integer pageNum, Integer pageSize, String keyWord, Integer role) {
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.notIn("role", 0).eq(role!=null&&role!=0,"role",role).
                like(keyWord != null && keyWord.equals(""), "name", keyWord);
        Page<User> userPage = this.page(new Page<User>(pageNum, pageSize),queryWrapper);
        List<User> userList = userPage.getRecords();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            UserInfo userInfo = userInfoService.getById(user.getId());
            BeanUtils.copyProperties(userInfo,userDto);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    public UserDto getUserDtoByUserId(Long id){
        UserInfo userInfo = userInfoService.getById(id);
        User user = this.getById(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        BeanUtils.copyProperties(userInfo,userDto);
        return userDto;
    }

    @Override
    @Transactional
    public boolean remove(Long id) {
        int i = userMapper.deleteById(id);
        int i1 = userInfoMapper.deleteById(id);
        if (i>0&&i1>0){
            return true;
        }else {
            return false;
        }
    }
}
