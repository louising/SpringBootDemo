package com.zero.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zero.core.domain.PageVO;
import com.zero.core.domain.ResponseVO;
import com.zero.demo.service.impl.DummyServiceImpl;

/**
 * http://localhost:8080/SpringBootDemo/user/usersPage?pageSize=5&pageIndex=1
 * http://localhost:8080/SpringBootDemo/user/users
 * 
 * @author Louisling
 * @since 2021-12-10
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private DummyServiceImpl dummyService;
    
    @GetMapping("/list")
    public ResponseVO users() {
        return process(() -> dummyService.users());        
    }
    
    // http://localhost:8080/SpringBootDemo/user/usersPage?pageSize=5&pageIndex=1&userName=&age=23
    // is not pass userName at url, userName == null
    // ?pageSize=5&pageIndex=1
    @GetMapping("/page")
    public ResponseVO usersPage(String userName, PageVO pageVO) {
        log.info("userName is null: {} is \"\" {}", (userName == null), ("".equals(userName)));
        log.info("{}, Page {}", userName, pageVO);
        
        return process(() -> dummyService.usersPage(pageVO));        
    }
}
