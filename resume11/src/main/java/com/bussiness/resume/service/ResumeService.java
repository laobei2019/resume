package com.bussiness.resume.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bussiness.resume.entity.Resume;

public interface ResumeService extends IService<Resume> {
    String preview(String resumeId, String formworkId, String username);
}
