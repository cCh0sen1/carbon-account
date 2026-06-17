package com.ban.carbonaccount.service.impl;

import com.ban.carbonaccount.entity.User;
import com.ban.carbonaccount.mapper.UserMapper;
import com.ban.carbonaccount.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}