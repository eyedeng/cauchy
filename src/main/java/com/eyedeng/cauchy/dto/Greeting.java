package com.eyedeng.cauchy.dto;

import lombok.Data;

@Data
public class Greeting {
    private Long id;
    private String name;

    public Greeting(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
