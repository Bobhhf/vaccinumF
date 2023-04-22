package com.hhf.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:hhf
 * @date: 2023/4/6
 * @time:10:16
 */
public interface QRService  {
    public void generateStream(Long userid, HttpServletResponse response) throws IOException;
}
