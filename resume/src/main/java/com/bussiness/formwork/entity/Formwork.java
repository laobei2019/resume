package com.bussiness.formwork.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.base.entity.BaseEntity;
import lombok.Data;

@TableName("sys_formwork")
@Data
public class Formwork extends BaseEntity {
    private String name;
    private String userId;
    private String fileUrl;
    private String filename;
    private String image;
    private int useCnt=0;
}
