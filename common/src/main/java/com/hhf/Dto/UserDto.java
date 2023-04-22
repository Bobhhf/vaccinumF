package com.hhf.Dto;

import com.hhf.entity.User;
import lombok.Data;

/**
 * @Author:hhf
 * @date: 2023/4/4
 * @time:12:18
 */
@Data
public class UserDto extends User {
    /**
     * 身份证
     */
    private String code;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 职位
     */
    private String job;

    /**
     * 用户通行码-0绿码-1黄码-2红码
     */
    private Integer status;
}
