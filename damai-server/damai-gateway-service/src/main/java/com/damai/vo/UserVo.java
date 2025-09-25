package com.damai.vo;

import lombok.Data;

import java.util.Date;

/**
 * @description: 用户 返回vo
 **/
@Data
public class UserVo {
    
    private String id;
    
    private String name;
    
    private String password;
    
    private Integer age;
    
    private Integer status;
    
    private Date createTime;
    
    private String mobile;
    
    private Date editTime;
}
