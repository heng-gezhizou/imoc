package com.greenism.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
public class TestController {

    @RequestMapping("test")
    public String test(Model model){

        String str = UUID.randomUUID().toString().substring(0,5);
        model.addAttribute("msg",str);
        return "hello";
    }

}
