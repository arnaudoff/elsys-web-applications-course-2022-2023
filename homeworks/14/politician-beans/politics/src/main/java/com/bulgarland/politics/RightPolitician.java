package com.bulgarland.politics;

import org.springframework.stereotype.Component;

@Component("rightPolitician")
public class RightPolitician implements Politician {
    @Override
    public void talk() {
        System.out.println("We will increase salaries!");
    }
}
