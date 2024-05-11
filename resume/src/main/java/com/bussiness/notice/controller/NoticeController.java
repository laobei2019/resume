package com.bussiness.notice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.ResponseData;
import com.base.controller.BaseController;
import com.bussiness.notice.entity.Notice;
import com.bussiness.notice.service.NoticeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("notice")
@CrossOrigin
public class NoticeController extends BaseController<NoticeService, Notice> {
    @PostMapping("list")
    public ResponseData to_user_list() {
        IPage<Notice> page = new Page<Notice>(Long.valueOf(1), Long.valueOf(200));
        QueryWrapper queryWrapper = new QueryWrapper();
        List<OrderItem> orders = page.orders();
        orders.add(OrderItem.desc("send_date"));
        queryWrapper.le("send_date", new Date());
        return ResponseData.success(service.page(page, getQueryWrapper()));
    }
}
