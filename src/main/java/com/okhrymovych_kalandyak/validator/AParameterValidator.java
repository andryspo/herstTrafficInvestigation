package com.okhrymovych_kalandyak.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AParameterValidator implements TextFieldValueValidator {

    private Logger logger = LoggerFactory.getLogger(AParameterValidator.class);

    @Override
    public boolean validate(String value) {
        boolean result = false;
        double a;

        try {
            a = Double.parseDouble(value);
            if(a < 0) {
                logger.error("a is less then 0 a : {}", a);
            } else {
                logger.info("a is valid a : {}", a);
                result = true;
            }
        } catch (NumberFormatException e) {
            logger.error("cannot parse a : {}", value);
        }

        return result;
    }
}
