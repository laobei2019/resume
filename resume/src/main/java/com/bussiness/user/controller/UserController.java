package com.bussiness.user.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.ResponseData;
import com.base.controller.BaseController;
import com.bussiness.user.entity.User;
import com.bussiness.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController extends BaseController<UserService, User> {
    @PostMapping("login")
    public ResponseData login() {
        QueryWrapper queryWrapper = getBaseQueryWrapper();
        long count = service.count(queryWrapper);
        if (count == 1) {
            User user = service.getOne(queryWrapper);
            user.setPassword(null);
            request.getSession().setAttribute("user", user);
            return ResponseData.success("登录成功", user);
        } else return ResponseData.fail("登陆失败，用户名或密码错误", null);
    }

    @PostMapping("register")
    public ResponseData register(@RequestBody User user) {
        service.save(user);
        return ResponseData.success("注册成功");
    }

    @GetMapping("do_download")
    public void do_download(HttpServletResponse response) throws IOException {

        List<Map<String, String>> classList = new ArrayList<>();
        // map的key 是表头，value是表头对应的值
        Map<String, String> map = new LinkedHashMap<>();
        map.put("姓名", "李小白");// 第一列
        map.put("账号", "1708065123");// 第二列
        map.put("性别", "男性");
        map.put("生日", "2002-01-19");
        map.put("联系方式", "17822579173");
        classList.add(map);
        OutputStream out = null;

        try (ExcelWriter writer = ExcelUtil.getWriter()) {
            writer.write(classList, true);// 写入数据
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户列表.xlsx", "UTF-8"));
            out = response.getOutputStream();// 获取流
            writer.flush(out, true); // 将数据流输出到文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();

    }

    @PostMapping("do_import")
    public ResponseData do_import(@RequestParam("file") MultipartFile file) {
        try {
            File file1 = new File(IdUtil.fastUUID());
            file.transferTo(file1);
            final ExcelReader reader = ExcelUtil.getReader(file1);
            List<Map<String, Object>> list = reader.readAll();
            List<User> users = new ArrayList<>();
            for (Map<String, Object> map : list) {
                final User user = new User();
                user.setName((String) map.getOrDefault("姓名", ""));
                user.setAccount((String) map.getOrDefault("账号", ""));
                user.setSex(map.getOrDefault("性别", "").equals("男性") ? "1" : "0");
                user.setBirth((String) map.getOrDefault("生日", ""));
                user.setPhone((String) map.getOrDefault("联系方式", ""));
                users.add(user);
            }
            service.saveBatch(users);
        } catch (IOException e) {
            return ResponseData.fail("导入失败");
        }
        return ResponseData.success("导入成功");
    }

    @PostMapping("updatePassword")
    public ResponseData updatePassword(@RequestBody User user) {
         User dbUser = service.getById(user.getId());
         if(dbUser.getPassword().equals(user.getPassword())){
             dbUser.setPassword(request.getParameter("pwd"));
             service.saveOrUpdate(dbUser);
             return ResponseData.success("修改密码成功");
         }
        return ResponseData.success("旧密码输入错误，请重试");
    }

}
