package com.bussiness.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.base.entity.BaseEntity;
import lombok.Data;

@TableName("sys_user")
@Data
public class User extends BaseEntity {
    private String name;
    private String account;
    private String password;
    private String sex;
    private String birth;
    private String phone;
    private String role;
}
