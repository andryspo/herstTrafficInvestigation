package validator;

import com.okhrymovych_kalandyak.validator.AParameterValidator;
import com.okhrymovych_kalandyak.validator.TextFieldValueValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class AParameterValidatorTest {

    private TextFieldValueValidator aParameterValidator;

    @Before
    public void init() {
        aParameterValidator = new AParameterValidator();
    }

    @Test
    public void passCorrectParameters() {
        String a = "0.2";

        Assert.assertTrue(aParameterValidator.validate(a));
    }

    @Test
    public void passIncorrectParams() {
        String a = "-0.2";

        Assert.assertFalse(aParameterValidator.validate(a));
    }

    @Test
    public void passIncorrectString() {
        String a = "blablabla";

        Assert.assertFalse(aParameterValidator.validate(a));
    }

}
