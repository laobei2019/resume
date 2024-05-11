package com.bussiness.notice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("sys_notice")
@Data
public class Notice extends BaseEntity {
    private String title;
    private String content;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date sendDate;
}
