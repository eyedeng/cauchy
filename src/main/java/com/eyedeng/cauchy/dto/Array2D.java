package com.eyedeng.cauchy.dto;

import lombok.Data;

import java.util.List;

@Data
public class Array2D {
    private List<List<Integer>> arrays;  // can't be int[][]
}
