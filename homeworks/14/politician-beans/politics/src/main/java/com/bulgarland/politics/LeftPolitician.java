package com.bulgarland.politics;

import org.springframework.stereotype.Component;

@Component("leftPolitician")
public class LeftPolitician implements Politician {
    @Override
    public void talk() {
        System.out.println("We will increase pensions!");
    }
}
