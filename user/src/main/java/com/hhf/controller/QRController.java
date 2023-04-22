package com.hhf.controller;

import com.hhf.service.QRService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:hhf
 * @date: 2023/4/6
 * @time:10:17
 */
@RestController
@RequestMapping("user")
public class QRController {

    //依赖业务层对象
    @Resource
    QRService qrService;

    //定义请求接口
    @RequestMapping("code")
    public void generateUserPass(Long userid, HttpServletResponse servletResponse) throws  IOException {
        //content是二维码存放的信息，可以用于存放用户的信息
        qrService.generateStream(userid, servletResponse);
    }

}
