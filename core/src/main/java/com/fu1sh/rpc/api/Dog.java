package com.fu1sh.rpc.api;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Dog implements Animal {

    private static final Logger logger = LoggerFactory.getLogger(Dog.class);
    @Override
    public String eat(Food food) {
        logger.info("接受到投喂的{}", food.getName());
        return food.getName() + "的价格为: " + food.getPrice();
    }
}
