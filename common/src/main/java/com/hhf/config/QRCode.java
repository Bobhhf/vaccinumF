package com.hhf.config;

import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

/**
 * @Author:hhf
 * @date: 2023/4/6
 * @time:10:10
 */

@Configuration
public class QRCode {
    @Bean
    public QrConfig qrConfig1() {
        QrConfig qrConfig = new QrConfig();
//        qrConfig.setBackColor(Color.white.getRGB());
        qrConfig.setForeColor(Color.black.getRGB());
        return qrConfig;
    }
}
