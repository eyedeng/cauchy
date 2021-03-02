package com.eyedeng.cauchy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("mes", "欢迎来到cauchy，本地访问地址：");
        List<String> kinds = new ArrayList<>();
        kinds.add("localhost:8080/sort");
        kinds.add("localhost:8080/btree");
        kinds.add("localhost:8080/graph");
//        kinds.add("动态规划");
        model.addAttribute("kinds", kinds);
        return "index";
    }

    @GetMapping("/btree")
    public String btree() {
        return "btree";
    }

    @GetMapping("/sort")
    public String sort() {
        return "sort";
    }

    @GetMapping("/graph")
    public String graph() {
        return "graph";
    }


    @GetMapping("/greet")
    public String greet() {
        return "greeting";
    }
}
