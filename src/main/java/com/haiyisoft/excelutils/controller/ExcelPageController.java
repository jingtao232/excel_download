package com.haiyisoft.excelutils.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ExcelPageController {

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

}
