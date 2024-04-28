package com.bussiness.resumeHistory.controller;

import com.base.controller.BaseController;
import com.bussiness.resumeHistory.entity.ResumeHistory;
import com.bussiness.resumeHistory.service.ResumeHistoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resumeHistory")
@CrossOrigin
public class ResumeHistoryController extends BaseController<ResumeHistoryService, ResumeHistory> {

}
