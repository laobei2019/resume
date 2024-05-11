package com.bussiness.notice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bussiness.notice.entity.Notice;
import com.bussiness.notice.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
