package com.bussiness.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bussiness.user.entity.User;
import com.bussiness.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
