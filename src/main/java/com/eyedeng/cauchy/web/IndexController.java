package com.eyedeng.cauchy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("mes", "欢迎来到cauchy");
        List<String> kinds = new ArrayList<>();
        kinds.add("冒泡");
        kinds.add("BFS");
        kinds.add("动态规划");
        model.addAttribute("kinds", kinds);
        return "index";
    }
}
