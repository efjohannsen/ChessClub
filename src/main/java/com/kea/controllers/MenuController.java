package com.kea.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping({"/", "index"})
    public String index() {
        return "index";
    }
}
