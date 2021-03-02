package com.eyedeng.cauchy.web;

import com.eyedeng.cauchy.domain.MazeFrame;
import com.eyedeng.cauchy.dto.Array2D;
import com.eyedeng.cauchy.service.GraphService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/graph")
public class GraphController {

    private GraphService graphService;

    @PostMapping("create")
    public MazeFrame create(@RequestBody Array2D arrays) {
        graphService = new GraphService();
        return graphService.create(arrays.getArrays());
    }

    @GetMapping("bfs")
    public MazeFrame bfs() {
        return graphService.bfs();
    }

    @GetMapping("dfs")
    public MazeFrame dfs() {
        return graphService.dfs();
    }
}
