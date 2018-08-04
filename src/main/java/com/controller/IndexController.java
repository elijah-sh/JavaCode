package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ShenShuaihu on 2018/8/3.
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    @ResponseBody
    public  String index(){
        System.out.println("开始项目");
        return "世界，你好！";
    }
}
