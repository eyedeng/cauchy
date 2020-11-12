package com.eyedeng.cauchy.web;

import com.eyedeng.cauchy.domain.TreeFrame;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/sort")
public class SortController {

    @GetMapping("select")
    public List<TreeFrame> selectSort(@RequestBody TreeFrame treeFrame) {
        return null;
    }
}
