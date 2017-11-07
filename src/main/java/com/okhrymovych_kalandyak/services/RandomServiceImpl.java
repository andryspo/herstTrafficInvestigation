package com.okhrymovych_kalandyak.services;

import com.okhrymovych_kalandyak.services.interfaces.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomServiceImpl implements RandomService{

    private Random random = new Random();

    private Logger logger = LoggerFactory.getLogger(RandomServiceImpl.class);

    @Override
    public double percent(double value, double percent) {

        double dv = (random.nextDouble() * value * percent) / 100;

        if(random.nextBoolean()) {
            logger.info("add value {} by percent {} random {}", value, percent, value + dv);
            return value + dv;
        } else {
            logger.info("sub value {} by percent {} random {}", value, percent, value - dv);
            return value - dv;
        }
    }
}
