package com.example.resume;

import com.bussiness.formwork.service.FormworkService;
import com.bussiness.resume.service.ResumeService;
import com.bussiness.resumeHistory.service.ResumeHistoryService;
import com.bussiness.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ResumeApplicationTests {
    @Resource
    UserService userService;
    @Resource
    ResumeService resumeService;
    @Resource
    ResumeHistoryService resumeHistoryService;
    @Resource
    FormworkService formworkService;

    @Test
    void contextLoads() {
        resumeService.preview("024d778f7a88d3375e81ca3e280270cd", "0569348e30fab196659fe2f7428ddb49","测试");
    }

    @Test
    void contextLoads2() {
    }
}
