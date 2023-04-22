package com.hhf.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author:hhf
 * @date: 2023/4/3
 * @time:22:12
 */
@Slf4j
@RestController
@RequestMapping("/user/file")
public class FileController {
    @RequestMapping("upload")
    public String upload(@RequestParam(value = "file",required = false) MultipartFile multipartFile) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String filename = multipartFile.getOriginalFilename();
        log.info("name",multipartFile.getName());
        log.info("oname",multipartFile.getOriginalFilename());
        String subfix = filename.substring(filename.lastIndexOf("."));
        filename = uuid + subfix;
        log.info("文件名是", filename);

        multipartFile.transferTo(new File("E:\\upload\\"  +filename));
        return "http://localhost:88/" + filename;
    }
}
