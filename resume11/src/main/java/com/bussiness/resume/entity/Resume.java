package com.bussiness.resume.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.base.entity.BaseEntity;
import lombok.Data;

@TableName("sys_resume")
@Data
public class Resume extends BaseEntity {
    private String name;
    private String context;
    private String userId;
}
