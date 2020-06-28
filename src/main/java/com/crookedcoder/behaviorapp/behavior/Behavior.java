package com.crookedcoder.behaviorapp.behavior;

import lombok.Data;

@Data
public class Behavior {

    private int ID;
    private String name;
    private int goal;
    private boolean recurring;
    private boolean minimum;

}