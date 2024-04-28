package com.bussiness.formwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bussiness.formwork.entity.Formwork;

public interface FormworkService extends IService<Formwork> {
    public void use(String id);
}
