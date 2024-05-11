package com.bussiness.problem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("sys_problem")
@Data
public class Problem extends BaseEntity {
    private String type;
    private String title;
    private String content;
    private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date eventTime;
    private String backContent;
    private String backUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date backEventTime;
}
