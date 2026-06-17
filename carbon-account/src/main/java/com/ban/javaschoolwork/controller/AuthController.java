package com.ban.carbonaccount.controller;

import com.ban.carbonaccount.common.R;
import com.ban.carbonaccount.entity.User;
import com.ban.carbonaccount.service.UserService;
import com.ban.carbonaccount.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Resource
    private UserService userService;

    @Resource
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public R<?> register(@RequestBody User user) {
        // 检查用户名是否已存在
        long count = userService.lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .count();
        if (count > 0) {
            return R.error("用户名已存在");
        }
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setTotalPoints(0);
        user.setTotalCarbon(0.0);
        user.setRole("USER");
        userService.save(user);
        return R.ok("注册成功");
    }

    @PostMapping("/login")
    public R<?> login(@RequestBody User user) {
        User dbUser = userService.lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .one();
        if (dbUser == null || !passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return R.error("账号或密码错误");
        }
        String token = jwtUtil.createToken(dbUser.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", dbUser);
        return R.ok(map);
    }
}