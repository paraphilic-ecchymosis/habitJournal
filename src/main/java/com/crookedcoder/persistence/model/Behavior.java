package com.crookedcoder.persistence.model;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public @Data class Behavior {

    private String name;
    private int goal;
    private boolean recurring;
    private boolean minimum;

}