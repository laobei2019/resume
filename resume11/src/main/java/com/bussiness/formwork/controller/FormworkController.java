package com.bussiness.formwork.controller;

import com.base.controller.BaseController;
import com.bussiness.formwork.entity.Formwork;
import com.bussiness.formwork.service.FormworkService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("formwork")
@CrossOrigin
public class FormworkController extends BaseController<FormworkService, Formwork> {
}
