package com.hhf.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhf.Dto.UserDto;
import com.hhf.config.QRCode.*;
import com.hhf.service.IUserService;
import com.hhf.service.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

/**
 * @Author:hhf
 * @date: 2023/4/6
 * @time:10:16
 */
@Service
public class QRServiceImpl implements QRService {

    //从ioc容器中获取二维码配置的对象
    @Autowired
    QrConfig qrconig;

    @Autowired
    IUserService userService;

    @Override
    public void generateStream(Long userid, HttpServletResponse response) throws IOException {
        UserDto userDto = userService.getUserDtoByUserId(userid);
        if (userDto.getStatus() == 0) {
            qrconig.setBackColor(Color.green.getRGB());
        } else if (userDto.getStatus() == 1) {
            qrconig.setBackColor(Color.yellow.getRGB());
        } else {
            qrconig.setBackColor(Color.red.getRGB());
        }
        //设置当前通行码的背景颜色
        ObjectMapper objectMapper = new ObjectMapper();
        //使用QrCodeUtil工具类generate生成二维码的图片流
        QrCodeUtil.generate(objectMapper.writeValueAsString(userDto), qrconig, "png", response.getOutputStream());
    }
}
