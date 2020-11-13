package com.eyedeng.cauchy.web;

import com.eyedeng.cauchy.domain.Tree;
import com.eyedeng.cauchy.domain.TreeFrame;
import com.eyedeng.cauchy.dto.Array;
import com.eyedeng.cauchy.service.BSTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/btree")
public class TreeController {

    @Autowired
    private BSTService bstService;

    private Tree initTree;

    @PostMapping("create")
    public TreeFrame create(@RequestBody Array array) {
//        Integer[] a = new Integer[]{84, 24, 57, 87, 13, 9, 56};
        TreeFrame treeFrame = bstService.create(array.getArray());
        initTree = treeFrame.getFrames().get(0);
        return treeFrame;
    }

    @GetMapping("inOrder")
    public TreeFrame inOrder() {
        return bstService.inorder(initTree);
    }
}
