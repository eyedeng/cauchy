package com.eyedeng.cauchy.web;

import com.eyedeng.cauchy.domain.TreeFrame;
import com.eyedeng.cauchy.dto.Array;
import com.eyedeng.cauchy.service.TreeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/btree")
public class TreeController {

    @GetMapping("create")
    public TreeFrame create(@RequestBody Array<Integer> array) {
        TreeService<Integer> treeService = new TreeService<>();
        TreeFrame treeFrame = treeService.create(array.getArray());
        return treeFrame;
    }
}
