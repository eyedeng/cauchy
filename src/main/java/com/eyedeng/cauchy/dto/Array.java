package com.eyedeng.cauchy.dto;

import lombok.Data;

import java.util.List;
@Data
public class Array<T extends Comparable<T>> {
    private Integer[] array;
}
