package com.bussiness.resumeHistory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.base.entity.BaseEntity;
import lombok.Data;

@TableName("sys_resume_history")
@Data
public class ResumeHistory extends BaseEntity {
    private String resumeId;
    private String context;
}
