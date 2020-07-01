package com.crookedcoder.behaviorapp.behavior;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Behavior {

    private int ID;
    private String name;
    private int goal;
    private boolean recurring;
    private boolean minimum;

}