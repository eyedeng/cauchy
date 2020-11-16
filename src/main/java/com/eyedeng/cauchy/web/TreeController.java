package com.eyedeng.cauchy.web;

import com.eyedeng.cauchy.domain.TreeFrame;
import com.eyedeng.cauchy.dto.Array;
import com.eyedeng.cauchy.service.BSTService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/api/btree")
public class TreeController {

    private BSTService bstService;

//    private Tree initTree;

    @PostMapping("create")
    public TreeFrame create(@RequestBody Array array) {
//        Integer[] a = new Integer[]{84, 24, 57, 87, 13, 9, 56};
        // 每次响应create button都要new 对象,清除之前的树结构
        bstService = new BSTService();
        for (Integer integer : array.getArray()) {
            bstService.add(integer);
        }
        TreeFrame treeFrame = bstService.create();
//        initTree = treeFrame.getFrames().get(0);
        return treeFrame;
    }

    @GetMapping("inOrder")
    public TreeFrame inOrder() {
        return bstService.inorder();
    }

    @GetMapping("search")
    public TreeFrame search(@PathParam(value = "num") int num) {
        return bstService.search(num);
    }

    @GetMapping("preOrder")
    public TreeFrame search() {
        return bstService.preorder();
    }
}
