package com.sys.library.controller;

import com.sys.common.model.R;
import com.sys.common.model.ResultBody;
import com.sys.library.dto.BaseResponse;
import com.sys.library.dto.LibraryDto;
import com.sys.library.service.LibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.util.SecurityConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 活动Controller
 *
 * @author heyn
 * @date 2022-03-03
 */
@Slf4j
@RestController
@RequestMapping("/myLibrary/")
@Api(tags = "图书馆", hidden = false)
public class LibraryController {

    @Autowired
    private LibraryService libraryService;


    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('/myLibrary/list')")
    public R list() {
        LibraryDto qry = new LibraryDto();
        return libraryService.qryBookInfo(qry);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('/myLibrary/delete')")
    public R delete() {
        return R.ok("删除请求模拟成功!!");
    }

    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('/myLibrary/add')")
    public R add() {
        return R.ok("添加请求模拟成功!!");
    }

    @PostMapping("/edite")
    @ResponseBody
    @PreAuthorize("hasAuthority('/myLibrary/edite')")
    public R edite() {
        return R.ok("编辑请求模拟成功!!");
    }



}
