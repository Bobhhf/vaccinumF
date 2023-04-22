package com.hhf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhf.Dto.UserDto;
import com.hhf.entity.User;
import com.hhf.entity.UserInfo;
import com.hhf.service.IUserInfoService;
import com.hhf.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:
 * @date: 2023/3/31
 * @time:15:19
 */
@Slf4j
@RestController
@RequestMapping("user")
public class Usercontroller {

    @Autowired
    IUserService userService;

    @Autowired
    IUserInfoService userInfoService;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("listUser")
    public String listPage(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
                           @RequestParam(required = true, defaultValue = "1") Integer pageSize,
                           @RequestParam(required = false) String keyword,
                           @RequestParam(required = false) Integer role
    ) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();

        List<UserDto> info = userService.findAllUserAndUserInfo(pageNum, pageSize, keyword, role);
        int total = userService.count();
        if (info!=null&&info.size()!=0){
            map.put("ok", true);
            map.put("list", info);
            map.put("total", total);
            return objectMapper.writeValueAsString(map);
        }else {
            map.put("ok", false);
            map.put("message", "查询失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @RequestMapping("login")
    public String loginUser(User user, String code, HttpSession session) throws JsonProcessingException {
        Map map = new HashMap();
        String captcha = (String) session.getAttribute("captcha");

        log.info("code是---------------------------" + code + "user是---------------------" + user);
        if (!captcha.equals(code)) {
            map.put("put", true);
            map.put("message", "验证码错误");
            return objectMapper.writeValueAsString(map);
        }
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("phone", user.getPhone());
        wrapper.eq("password", user.getPassword());
        User one = userService.getOne(wrapper);
        if (one != null) {
            map.put("ok", true);
            map.put("user", one);
        } else {
            map.put("ok", false);
            map.put("message", "账号或密码错误");
        }
        return objectMapper.writeValueAsString(map);
    }

    @PostMapping("register")
    public String register(User user, UserInfo userInfo) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean b = userService.saveUserAndUserInfo(user, userInfo);
        if (b) {
            map.put("ok", true);
            map.put("user", user);
            return objectMapper.writeValueAsString(map);
        } else {
            map.put("ok", false);
            map.put("message", "注册失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @GetMapping("findPhone")
    public String findPhone(User user) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        int count = userService.count(queryWrapper);
        map.put("exist", count > 0);
        return objectMapper.writeValueAsString(map);
    }

    @PostMapping("update")
    public String update(User user) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean b = userService.updateById(user);
        if (b) {
            map.put("ok", true);
            map.put("message", "添加成功");
            return objectMapper.writeValueAsString(map);
        }else {
            map.put("ok", false);
            map.put("message", "添加失败");
            return objectMapper.writeValueAsString(map);
        }
    }

    @PostMapping("delete")
    public String delete(User user) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        boolean b = userService.remove(user.getId());
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

    @GetMapping("getDocIds")
    public String getDocIds() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("role", 2);
        List<User> list = userService.list(userQueryWrapper);
        map.put("list", list);
        return objectMapper.writeValueAsString(map);
    }
}
