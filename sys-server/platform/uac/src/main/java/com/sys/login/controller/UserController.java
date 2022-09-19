package com.sys.login.controller;

import com.sys.common.model.R;
import com.sys.common.model.userSecurity.SysUser;
import com.sys.login.dto.LibraryDto;
import com.sys.login.dto.ResultBodyTest;
import com.sys.login.mapper.UserMapper;
import com.sys.login.tokenSecurity.service.UserService;
import com.sys.login.validate.ValidateCodeProcessorHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/8 21:38
 * @Description:
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserMapper userMapper;
    /**
     * 获取当前的用户
     * @return  完整的Authentication
     */
    @GetMapping("/me1")
    @ResponseBody
    public Object currentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/me2")
    @ResponseBody
    public Object currentUser(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/getBook")
    @ResponseBody
    public ResultBodyTest getBook(HttpServletRequest request, HttpServletResponse response) {

        List<LibraryDto> bookInfo =
                userMapper.getBookInfo(new LibraryDto());
        ResultBodyTest resultBodyTest = new ResultBodyTest();
        resultBodyTest.setCode(0);
        resultBodyTest.setData(bookInfo);
        resultBodyTest.setMsg("sucess");
        return resultBodyTest;
    }

    @GetMapping("/logout")
    @ResponseBody
    public String  logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "/demo-login.html";
    }


    /**
     * @param userDetails
     * @return 只包含了userDetails
     */
    @GetMapping("/me3")
    @ResponseBody
    public Object cuurentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }


    @Autowired
    private UserService userService;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @PostMapping("/login")
    @ResponseBody
    public R login(@RequestBody SysUser user,HttpServletRequest request, HttpServletResponse response) {
        String token = null;
        try {
            token = userService.login(user);
        } catch (Exception e) {
            return R.error(10001, e.getMessage());
        }
        log.info("the token is created: {}", token);
        return R.ok().setData(token);
    }

//    @PostMapping("/sms/login")
//    @ResponseBody
//    public R smsLogin(@RequestBody SysUser user,HttpServletRequest request, HttpServletResponse response) {
//        return R.ok().setData(user);
//    }


}
