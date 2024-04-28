package com.bussiness.formwork.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bussiness.formwork.entity.Formwork;
import com.bussiness.formwork.mapper.FormworkMapper;
import org.springframework.stereotype.Service;

@Service
public class FormworkServiceImpl extends ServiceImpl<FormworkMapper, Formwork> implements FormworkService {
    @Override
    public void use(String id) {
        Formwork formwork = getById(id);
        formwork.setUseCnt(formwork.getUseCnt() + 1);
        saveOrUpdate(formwork);
    }
}
